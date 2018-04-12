/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.vo;

import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: SysParameterVO.java, v 0.1 2014-8-19 下午02:40:25 liyuepeng Exp $
 */
public class SysParameterVO extends BaseVO{

	/**  */
	private static final long serialVersionUID = 9192214536117787164L;
	
		private List<Integer> idList;
		/**/
		private Long id;
		/**/
		private String code;
		/**/
		private String name;
		/**/
		private String parameterValue;
		/**/
		private String remark;

		private Integer inputType;
		
		private Integer isDisabled;

		public List<Integer> getIdList() {
			return idList;
		}

		public void setIdList(List<Integer> idList) {
			this.idList = idList;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getParameterValue() {
			return parameterValue;
		}

		public void setParameterValue(String parameterValue) {
			this.parameterValue = parameterValue;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public Integer getInputType() {
			return inputType;
		}

		public void setInputType(Integer inputType) {
			this.inputType = inputType;
		}

		public Integer getIsDisabled() {
			return isDisabled;
		}

		public void setIsDisabled(Integer isDisabled) {
			this.isDisabled = isDisabled;
		}
		
		

}
