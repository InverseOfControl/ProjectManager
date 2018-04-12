package com.ezendai.credit2.master.constant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.PropertyFilter;



/**
 * 自定义JSON输出格式
 * @author Ivan
 *
 */
public class CustomJsonConfig {
	
	/**
	 * 生成JSONObject模板
	 * @return
	 */
	public static JsonConfig  createJsonConfig() {
		return createJsonConfig(new String[]{});
	}
	
	public static JsonConfig  createJsonConfig(String[] keepFields) {
		return createJsonConfig(keepFields,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 
	 * @param keepFieldsString 输出指定的属性名
	 * @param dateFormat 日期格式默认(yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static JsonConfig  createJsonConfig(String[] keepFields,String dateFormat) {
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new KeepFieldProcessorImpl(keepFields));
		config.registerJsonValueProcessor(Date.class,  new JsonDateValueFormat(dateFormat));
		return config;
	}
	
	static class JsonDateValueFormat implements JsonValueProcessor {
		/** 日期格式 **/
		public String dateFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = null;
		JsonDateValueFormat() {
			init();
		}
		
		JsonDateValueFormat(String dateFormat) {
			this.dateFormat = dateFormat;
			init();
		}
		
		void init() {
			sdf = new SimpleDateFormat(this.dateFormat);
		}
		
		
		@Override
		public Object processArrayValue(Object arg0, JsonConfig arg1) {
			return null;
		}

		@Override
		public Object processObjectValue(String key, Object value,
				JsonConfig jsonConfig) {
			if (value == null) {
				return null;
			}
			return sdf.format(value);
		}
		
	}
	
	/**
	 * 自定义JSON项输出
	 * @author Ivan
	 *
	 */
	static class KeepFieldProcessorImpl implements PropertyFilter {
		List<String> keepItem = new ArrayList<String>();
		KeepFieldProcessorImpl() {
			
		}
		KeepFieldProcessorImpl(String [] keepItem) {
			this.keepItem.addAll(Arrays.asList(keepItem));
		}
		@Override
		public boolean apply(Object source, String key, Object value) {
			if (keepItem == null || keepItem.size() == 0) {
				return false;
			}
			if (value instanceof Collection) {
				return false;
			}
			return !keepItem.contains(key);
		}
	}
}
