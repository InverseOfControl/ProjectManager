package com.ezendai.credit2.sign.lcb.strategy.base;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ezendai.credit2.sign.constant.LcbEnum;
import com.ezendai.credit2.sign.lcb.strategy.service.LcbIfcStrategy;

/**
 * 捞财宝接口策略类生成工厂
 * @author YM10159
 */
public class LcbIfcStrategyFactory {
	private final static Log log = LogFactory.getLog(LcbIfcStrategyFactory.class);
	private final static String basePackage = "com.ezendai.credit2.sign.lcb.strategy.service.impl.";

	public static LcbIfcStrategy getStrategy(String originClassName){
		LcbIfcStrategy lcbIfcStrategy = null;
		String val = LcbEnum.getValueByCode(originClassName);
		if(StringUtils.isBlank(val)){
			return null;
		}
		try {
			Class<?> cls = Class.forName(basePackage+val.split("\\|")[1]);
			lcbIfcStrategy = (LcbIfcStrategy) cls.newInstance();
		} catch (Exception e) {
			log.error("捞财宝策略对象获取异常：",e);
		}
		return lcbIfcStrategy;
	}
}
