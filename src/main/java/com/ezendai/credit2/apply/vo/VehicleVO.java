/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.vo;

import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 车辆信息
 * </pre>
 *
 * @author fangqingyuan
 * @version $Id: Vehicle.java, v 0.1 2014-6-24 下午1:29:42 fangqingyuan Exp $
 */
public class VehicleVO extends BaseVO {

	private static final long serialVersionUID = -687959381096242528L;

	/** 客户ID */
	private Long personId;

	/** 车辆品牌 */
	private String brand;

	/** 车型 */
	private String model;

	/** 车龄 */
	private Integer coty;
	
	 /***车架号 **/
    private String frameNumber;
    
    /***车牌号 **/
    private String  plateNumber;

	/** 行驶里程 */
	private Long mileage;

	/** 备注 */
	private String remark;
	
	/**借款id*/
	private Long loanId;
	
	private List<Long> idList;
	
	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getCoty() {
		return coty;
	}

	public void setCoty(Integer coty) {
		this.coty = coty;
	}

	public Long getMileage() {
		return mileage;
	}

	public void setMileage(Long mileage) {
		this.mileage = mileage;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

}
