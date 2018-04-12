package com.ezendai.credit2.sign.util;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.sign.constant.LcbEnum;
import com.ezendai.credit2.sign.lcb.strategy.base.LcbIfcStrategyFactory;
import com.ezendai.credit2.sign.lcb.strategy.service.LcbIfcStrategy;

/**
 * 为了不跟现有逻辑代码耦合，把对接捞财宝的代码独立处理。
 * @author YM10159
 */
@Component
@Aspect
public class SignAspect{
	
	
	private static final Logger logger = Logger.getLogger(SignAspect.class);
	
	@Around("within(com.ezendai.credit2.*.controller.*)")
	public Object doExecute(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();  
		Method method = methodSignature.getMethod(); 
		String originBusinessClass = method.getDeclaringClass().getSimpleName()+"."+method.getName();
		
		//不调用捞财宝，直接通过
		if(!ifCallLcb(originBusinessClass)){
			return joinPoint.proceed();
		}
		
		//判断在原业务方法之前还是之后调用捞财宝
		String lcbEnumVal = LcbEnum.getValueByCode(originBusinessClass);
		String pointOrder = lcbEnumVal.split("\\|")[2];
		
		//之前调用捞财宝
		if(pointOrder.equals("before")){
			JSONObject jsonObj = (JSONObject) businessExecute(originBusinessClass,args);
			if(!jsonObj.getString("repCode").equals("000000")){
				return jsonObj;
			}
		}
		
		//执行原业务方法
		Object retval = null;
		try{
			retval = joinPoint.proceed();
		}catch(Exception e){
			logger.error("原业务方法["+originBusinessClass+"]执行失败：", e);
			throw new RuntimeException("原业务方法执行失败！");
		}
		
		//之后调用捞财宝
		if(pointOrder.equals("after")){
			args = new Object[]{args,retval};
			return (JSONObject) businessExecute(originBusinessClass,args);
		}
		return retval;
	}
	
	private boolean ifCallLcb(String originBusinessClass){
		if(!LcbEnum.ifLcbNode(originBusinessClass)){
			return false;
		}
		return true;
	}
	
	private Object businessExecute(String originBusinessClass, Object[] args) throws Throwable{
		//获取需要对接捞财宝接口的转发对象
		LcbIfcStrategy lcbIfcStrategy = LcbIfcStrategyFactory.getStrategy(originBusinessClass);
		String lcbEnumVal = LcbEnum.getValueByCode(originBusinessClass);
		//真正的业务类，需要调用捞财宝接口
		JSONObject jsonObj = lcbIfcStrategy.callLcb(lcbEnumVal.split("\\|")[0], args);
		return jsonObj;
	}
}
