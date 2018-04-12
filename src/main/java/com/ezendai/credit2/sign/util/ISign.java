package com.ezendai.credit2.sign.util;

/**
 * 签约接口
 * @author YM10159
 */
public interface ISign<T,U> {
	/**
	 * @descripton 业务处理
	 * @param reqObj 请求参数对象
	 * @param resObj 响应参数对象
	 * @return 处理结果
	 */
	boolean execute(T reqObj, U resObj);
}
