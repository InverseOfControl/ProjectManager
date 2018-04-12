package com.ezendai.credit2.sign.util;

public abstract class Sign<T,U> implements ISign<T,U> {

	/**
	 * 业务处理前
	 */
	public abstract boolean before(T reqObj, U resObj);

	/**
	 * 业务处理后
	 */
	public abstract boolean after(T reqObj, U resObj);
}
