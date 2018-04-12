package com.ezendai.credit2.system.intercepter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.exception.ExcelException;
import com.ezendai.credit2.framework.util.CommonUtil;

@Component
public class CustomHandlerExceptionResolver implements HandlerExceptionResolver {
	private static final Logger logger = Logger.getLogger(CustomHandlerExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 根据不同错误转向不同页面
		if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			if (ex instanceof BusinessException) {
				logger.error("resolveException BusinessException", ex);
				BusinessException be = (BusinessException) ex;
				model.put("errorCode", be.getErrCode());
				model.put("errorMsg", be.getMessage());
				model.put("stackTraceMsg", CommonUtil.getStackTrace(ex));
				return new ModelAndView("error/business_error", model);
			} else if (ex instanceof ExcelException) {
				logger.error("resolveException ExcelException", ex);
				ExcelException be = (ExcelException) ex;
				model.put("errorCode", be.getErrCode());
				return new ModelAndView("error/excel_error", model);
			} else {
				logger.error("resolveException", ex);
				model.put("errorMsg", ex.getMessage());
				model.put("stackTraceMsg", CommonUtil.getStackTrace(ex));
				return new ModelAndView("error/500", model);
			}
		} else {// JSON格式返回   
			PrintWriter writer = null;
			try {
				logger.error("resolveException JSON", ex);
				writer = response.getWriter();
				writer.write(ex.getMessage());
				writer.flush();
			} catch (IOException e) {
				logger.error("resolveException JSON IOException", e);
				writer.write(e.getMessage());
				writer.flush();
			} catch (Exception e) {
				logger.error("resolveException JSON Exception", e);
				writer.write(e.getMessage());
				writer.flush();
			}
			return null;
		}
	}
}
