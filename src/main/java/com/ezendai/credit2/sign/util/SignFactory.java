package com.ezendai.credit2.sign.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

/**
 * 签约工厂
 * @author YM10159
 */
@Component
public final class SignFactory extends ApplicationObjectSupport{

	private final static Log log = LogFactory.getLog(SignFactory.class);

	@SuppressWarnings("rawtypes")
	private final static Map<String, BaseSign> sign_handler_map = new HashMap<>();

	@Override  
	protected void initApplicationContext(ApplicationContext context) throws BeansException {  
		super.initApplicationContext(context);  
		Map<String, Object> beanMap = context.getBeansWithAnnotation(SignHandler.class);  

		for (String mapKey : beanMap.keySet()) {  
			Object bean = beanMap.get(mapKey);  
			Class<?> clazz = bean.getClass();  
			if (bean instanceof BaseSign && null != clazz.getAnnotation(SignHandler.class)) {  
				SignHandler signHandler = (SignHandler) clazz.getAnnotation(SignHandler.class);  
				String flowNode = signHandler.flowNode();
				sign_handler_map.put(flowNode, (BaseSign)beanMap.get(mapKey));  
			}  
		}  
	}  

	@SuppressWarnings("rawtypes")
		public static BaseSign getSignHandler(String flowNode) {  
			return sign_handler_map.get(flowNode);
		}  
}
