package com.ezendai.credit2.system.intercepter;

import static com.ezendai.credit2.master.constant.WebConstants.LOGIN_PAGE_URI;
import static com.ezendai.credit2.master.constant.WebConstants.contextPath;
import static com.ezendai.credit2.master.constant.WebConstants.cookieKey;
import static com.ezendai.credit2.master.constant.WebConstants.notAuthFilterURIList;
import static com.ezendai.credit2.master.constant.WebConstants.webUrl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.cache.CacheService;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.listener.CookieManager;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.system.Credit2Properties;

import static com.ezendai.credit2.master.constant.WebConstants.contextReportPath;
import static com.ezendai.credit2.master.constant.WebConstants.webReportUrl;
import static com.ezendai.credit2.master.constant.WebConstants.honorUrl;
/**
 * <pre>
 * 授权拦截器，用于控制用户登录
 * </pre>
 *
 * @author fangqingyuan
 * @version $Id: AuthIntercepter.java, v 0.1 2014-7-5 下午1:13:07 fangqingyuan Exp $
 */
public class AuthIntercepter implements HandlerInterceptor {

	protected static Log logger = LogFactory.getLog(AuthIntercepter.class);

	@Autowired
	private CacheService cacheService;

	@Autowired
	private Credit2Properties credit2Properties;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		contextPath = request.getContextPath();
		webUrl = credit2Properties.getWebUrl();
		contextReportPath = credit2Properties.getContextReportPath();
		webReportUrl = credit2Properties.getWebReportUrl();
		cookieKey=Constants.CREDIT2_USERINFO_COOKIE_NAME;
		honorUrl = credit2Properties.getHonorUrl();
		/** 是否需要验证登录 */
		if (isAuthFilter(request)) {
			if (checkSessionCookieExists(request, response) && setApplicationContext(request)) {
				/** 验证登录成功 */
				
				return true;
			} else {
				/** 验证登录失败，重定向到登录页面 */
				response.sendRedirect(contextPath + LOGIN_PAGE_URI);
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}

	/**
	 * <pre>
	 * 是否需要验证登录
	 * </pre>
	 *
	 * @param request
	 * @return
	 */
	private boolean isAuthFilter(HttpServletRequest request) {
		String uri = request.getRequestURI();
		for (String e : notAuthFilterURIList) {
			if (uri.startsWith(contextPath + e)) {
				return false;
			}
		}
		return true;
	}

	private boolean setApplicationContext(HttpServletRequest request) {
		UserSession backOperatorSessionCache = null;
		// 本地服务器端的session被销毁(应用服务器被重启)
		CookieManager cookieManager = new CookieManager(request, null);
		String cookieValue = cookieManager.getCookieValue(Constants.CREDIT2_USERINFO_COOKIE_NAME);
		// 如果cookie的value为空，LoginInterceptor会跳转到登录页面
		if (StringUtils.isNotBlank(cookieValue)) {
			// 通过key从memcached中获取session信息
			Object obj = cacheService.getDataFromCache(cookieValue);
			// memcached中session信息丢失了(memcached服务器被重启)
			if (null == obj) {
				// 可能缓存过期，或者memcached重起
				logger.warn("The cache is expired or restarted!");
				return false;
			} else {
				backOperatorSessionCache = (UserSession) obj;
				ApplicationContext.setUser(backOperatorSessionCache);
			}
		}	 

		return true;
	}

	private boolean checkSessionCookieExists(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		CookieManager cookieManager = new CookieManager(httpServletRequest, httpServletResponse);
		String cookieValue = cookieManager.getCookieValue(Constants.CREDIT2_USERINFO_COOKIE_NAME);
		// cookie被清除
		if (StringUtils.isBlank(cookieValue)) {
			return false;
		}
		Object obj = cacheService.getDataFromCache(cookieValue);
		if (obj == null) {
			return false;
		}
		UserSession session = (UserSession) obj;
		Date oldDate = session.getLastExtension();

		long oldLoginTime = oldDate.getTime();
		long currentTime = new Date().getTime();
		// 会话cookie过期时间 (60分钟)
		long expiretime = Constants.SESSION_EXPIRE * 60 * 1000L;
		long halfExpiretime = expiretime / 2;
		// 当前时间减去最新登陆时间
		long leftTime = currentTime - oldLoginTime;
		// 用户开着浏览器60分钟一直不用，之后再用就要跳到登陆页面
		if (expiretime < leftTime) {
			return false;
		}
		// 如果用户使用系统过了过期时间的一半，对会话cookie进行续命
		if (halfExpiretime <= leftTime) {
			session.setLastExtension(new Date());
			cacheService.data2Cache(cookieValue, session, Constants.SESSION_EXPIRE);
		}
		return true;
	}

}
