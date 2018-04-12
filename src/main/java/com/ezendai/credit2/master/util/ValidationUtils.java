package com.ezendai.credit2.master.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.commons.lang.StringUtils;

import com.alibaba.dubbo.common.utils.CollectionUtils;


public class ValidationUtils {

	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	/**多个错误描述中间逗号分开*/
	private static final String COMMA = ",";

	/**
	 * 该方法校验接口入参是否合法，如果不合法，返回不合法的错误描述，多个描述间用逗号分开！拼接好的字符串最后一个逗号需要去掉
	 * 该方法针对于接口入参对象中的注解如NotNull,NotBlank等等，非注解无法校验
	 * @param reqBean
	 * @return String
	 */
	public static <T> String validateEntity(T reqBean) {
		StringBuilder stringBuilder = new StringBuilder();
		Set<ConstraintViolation<T>> set = validator.validate(reqBean, Default.class);
		if (CollectionUtils.isNotEmpty(set)) {
			for (ConstraintViolation<T> constraint : set) {
				stringBuilder.append(constraint.getMessage() + COMMA);
			}
			if (stringBuilder.lastIndexOf(COMMA) > -1) {
				stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			}
		}
		return stringBuilder.toString();
	}
	/**
	 * 组装推送给aite的编号
	 * @param loanNo
	 * @param pushNo
	 * @return
	 * @author lix YM10160
	 * @date 2017年10月27日上午10:11:53
	 */
	public static String composeAitePushLoanNo(String loanNo,Integer pushNo) {
		if(StringUtils.isNotBlank(loanNo)){
			return loanNo+"AITE"+pushNo;
		}
		return null;
	}
	/**
	 * 解析aite传入的编号
	 * @param loanNo
	 * @return
	 * @author lix YM10160
	 * @date 2017年10月27日上午10:12:16
	 */
	public static String analysisAitePushLoanNo(String loanNo) {
		if(StringUtils.isNotEmpty(loanNo)){
			return loanNo.split("AITE")[0];
		}
		return null;
	}
}
