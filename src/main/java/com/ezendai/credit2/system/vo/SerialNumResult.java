package com.ezendai.credit2.system.vo;

import java.util.Date;

/**
 * <pre>
 * 序列号结果对象
 * </pre>
 *
 * @author fangqingyuan
 * @version $Id: SerialNumResult.java, v 0.1 2014-7-5 下午3:03:39 fangqingyuan Exp $
 */
public class SerialNumResult {
	
	/** 是否成功 */
	private boolean success;
	
	/** 序号编码 */
	private String code;
	
	/** 序列号 */
	private Long seq;
	
	/** 产生序列号的时间 */
	private Date date;
	
	/** 失败时有错误 */
	private String message;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "SerialNumResult [success=" + success + ", code=" + code + ", seq=" + seq
				+ ", date=" + date + ", message=" + message + "]";
	}
	
}
