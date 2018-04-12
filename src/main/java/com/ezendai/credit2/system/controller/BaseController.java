package com.ezendai.credit2.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezendai.credit2.framework.cache.CacheService;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.listener.CookieManager;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.master.model.SysEnumerate;
import com.ezendai.credit2.master.service.SysEnumerateService;
import com.ezendai.credit2.system.dao.TextResourcesDao;
import com.ezendai.credit2.system.model.TextResource;
import com.ezendai.credit2.system.service.TextResourcesService;

/**
 * 
 * <pre>
 * Controller基类
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: BaseController.java, v 0.1 2014-8-1 下午02:16:08 liyuepeng Exp $
 */
public class BaseController {


	@Autowired
	private SysEnumerateService sysEnumerateService;
	
	@Autowired
	private TextResourcesService textResourcesService;
	@Autowired
	private CacheService cacheService;
	

	protected Map<String, Object> gridMap;
	
	protected Map<String, Object> textMap;

	private String[] dataDictionaryArr;
	
	protected static final String GRID_ENUM_JSON = "gridEnumJson";
	
	protected static final String 	TEXT_RESOURCE_JSON = "textResourceJson";
	
	public BaseController() {
		if (gridMap == null) {
			gridMap = new HashMap<String, Object>();
		}
		if (textMap == null) {
			textMap = new HashMap<String, Object>();
		}
	}

	/**
	 * 
	 * <pre>
	 * 设置字典数据
	 * </pre>
	 *
	 * @param typeArr
	 */
	@SuppressWarnings("rawtypes")
	private void setGridDataDictionary(String[] typeArr) {
		Map<String, List> map = new HashMap<String, List>();
		for (String type : typeArr) {
			List<SysEnumerate> sysEnumList = sysEnumerateService.findSysEnumerateListByType(type);
			map.put(type, sysEnumList);
		}
		fillGridDataDictionary(map);
	}

	/**
	 * 
	 * <pre>
	 * 设置grid自定义用户数据,用于数据字典或其他
	 * </pre>
	 *
	 * @param map 数据字典map或其他
	 */
	@SuppressWarnings("rawtypes")
	private void fillGridDataDictionary(Map map) {
		gridMap.put("dicData", map);
	}

	protected String getEnumJson() {
		setGridDataDictionary(dataDictionaryArr);
		JSONObject jsonObject = JSONObject.fromObject(this.gridMap);
		return jsonObject.toString();
	}
	protected void setDataDictionaryArr(String[] dataDictionaryArr) {
		this.dataDictionaryArr = dataDictionaryArr;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 根据类型取得文字资源，用于文字统一管理
	 * </pre>
	 *
	 *  
	 */
	protected String getTextTypeJson(Integer[] typeArr) {
		Map<String, List> map = new HashMap<String, List>();
		for (Integer type : typeArr) {
			if(isTextCacheTimeout(type.toString())){
				List<TextResource> textResourceList=getTextCache(type.toString());
				map.put(type.toString(), textResourceList);
			}else{
				List<TextResource> textResourceList = textResourcesService.findTextResourcesByType(type);
				setTextCache(textResourceList, type.toString());
				map.put(type.toString(), textResourceList);
			}
			
		}
		textMap.put("textJson", map);
		JSONObject jsonObject = JSONObject.fromObject(this.textMap);
		return jsonObject.toString();
	}
	

	/**
	 * 
	 * <pre>
	 * 设置文字缓存
	 * </pre>
	 *
	 *  
	 */
	private void setTextCache(List<TextResource> textResourceList,String type){
		 
		cacheService.data2Cache(type+"time", new Date(), Constants.SESSION_EXPIRE);//缓存时间
		cacheService.data2Cache(type, textResourceList, Constants.SESSION_EXPIRE);
	}
	/**
	 * 
	 * <pre>
	 * 获取文字缓存
	 * </pre>
	 *
	 *  
	 */
	private List<TextResource> getTextCache(String type){
		Object obj = cacheService.getDataFromCache(type);
		List<TextResource> list = (List<TextResource> ) obj; 
		return list;
	}
	/**
	 * 
	 * <pre>
	 * 判断缓存的文字资源是否存在，如果缓存超过有效时间一半 就延长有效时间,保持缓存一直有效
	 * </pre>
	 *
	 *  
	 */
	
	private boolean isTextCacheTimeout(String type) {

		Object obj = cacheService.getDataFromCache(type);
		if (obj == null) {
			return false;
		}
		List<TextResource> list = (List<TextResource> ) obj; 
		if (list .size()==0) {
			return false;
		}
		Object objDate = cacheService.getDataFromCache(type+"time");
		
		Date oldDate = (Date)objDate;

		long oldLoginTime = oldDate.getTime();
		long currentTime = new Date().getTime();
		// 会话cookie过期时间 (60分钟)
		long expiretime = Constants.SESSION_EXPIRE * 60 * 1000L;
		long halfExpiretime = expiretime / 2;
		// 当前时间减去最新登陆时间
		long leftTime = currentTime - oldLoginTime;
		// 开着浏览器60分钟一直不用 ,
		if (expiretime < leftTime) {
			return false;
		}
		// 如果用户使用系统过了过期时间的一半，对会话cookie进行续命
		if (halfExpiretime <= leftTime) {
			oldDate =new Date();
			cacheService.data2Cache(type+"time", oldDate, Constants.SESSION_EXPIRE);
			cacheService.data2Cache(type, list, Constants.SESSION_EXPIRE);
		}
		cacheService.data2Cache(type+"time", oldDate, Constants.SESSION_EXPIRE);
		cacheService.data2Cache(type, list, Constants.SESSION_EXPIRE);
		 return true;
		 
	}
	
}
