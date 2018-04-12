package com.ezendai.credit2.sign.util;

/**
 * 签约基类，签约业务类需继承此类
 * @author YM10159
 */
public class BaseSign<T,U> extends Sign<T,U> {

	@Override
	public boolean execute(T reqObj, U resObj) {
		return true;
	}

	@Override
	public boolean before(T reqObj, U resObj) {
		return true;
	}

	@Override
	public boolean after(T reqObj, U resObj) {
		return true;
	}
}
