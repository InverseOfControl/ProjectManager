package com.ezendai.credit2.system.intercepter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.ezendai.credit2.framework.vo.BaseVO;

@Aspect
@Component
public class ControllerPropertiesAspect {
	@Pointcut("execution(* com.ezendai.credit2.*.controller.*Controller.*(..))")
	public void controllerSetProperties() {

	}

	private static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

	/**
	 * 过滤提交的字符串中的前后空格
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("controllerSetProperties()")
	public Object securityAccess(ProceedingJoinPoint pjp) throws Throwable {
		Object[] objs = pjp.getArgs();
		Object obj = null;
		if (objs != null) {
			for (int i = 0; i < objs.length; i++) {
				obj = objs[i];
				String str = null;
				if (obj instanceof String) {
					str = (String) obj;
					objs[i] = StringUtils.trim(str);
				} else if (obj instanceof BaseVO) {
					Class<?> clz = obj.getClass();
					Field[] fields = clz.getDeclaredFields();
					for (Field field : fields) {
						if (StringUtils.equalsIgnoreCase(field.getGenericType().toString(), "class java.lang.String")) {
							Method m = (Method) obj.getClass().getMethod("get" + getMethodName(field.getName()));
							str = (String) m.invoke(obj);
							Method method = clz.getDeclaredMethod("set" + getMethodName(field.getName()), field.getType());
							method.invoke(obj, StringUtils.trim(str));
						}
					}

				}
			}
			return pjp.proceed(objs);
		} else {
			return pjp.proceed();
		}
	}
}