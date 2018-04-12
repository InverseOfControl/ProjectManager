package com.ezendai.credit2.sign.constant;

/**
 * 对接捞财宝流程节点枚举类
 * @author YM10159
 */
public enum LcbEnum {
	/**
	 * 配置格式说明：
	 * code：配的是要对接捞财宝的控制层方法
	 * value：1|2|3|4
	 * 1表示：对接捞财宝的业务类的注解
	 * 2表示：转发对接捞财宝的业务类的中转策略类，后缀名必须已Strategy结尾
	 * 3表示：是在原控制方法之前还是之后对接捞财宝
	 */
	
	/**捞财宝手机一致性、注册、登录、实名、绑卡、推标、生成合同 */
	CONTRACT_GENERATE_PRE("ContractController.generateContractPre", "lcbContractGenerate|ContractGenerateStrategy|before"),
	/**合同签约*/
	CONTRACT_SIGN_("ContractController.signContractPre", "lcbContractSign|ContractSignStrategy|before"),
    /** 合同确认*/
    CONTRACT_CONFIRM("ContractConfirmController.submitContractConfirm", "lcbContractConfirm|ContractConfirmStrategy|before"),
	/** 合同确认 终止借款 */
	CONTRACT_TERMINATE_BORROW("ContractConfirmController.submitRefuse", "lcbTerminateBorrow|ContractTerminateBorrowStrategy|before"),
	/** 财务审核 终止借款*/
    AUDIT_TERMINATE_BORROW("FinancialAuditController.financialReturn", "lcbTerminateBorrow|TerminateBorrowStrategy|before"),
	/** 财务放款 终止借款*/
    GRANT_TERMINATE_BORROW("FinancialGrantController.financialReturn", "lcbTerminateBorrow|TerminateBorrowStrategy|before"),
	/**众安反欺诈*/
	ZONGAN_RISK_PUSH("ApplyController.getlcbzaFraud", "LcbZonganRiskService|ZonganRiskStrategy|after"),
	/**调用判断捞财宝终止借款*/
	JUDGE_PUSH_STANDARD("ApplyController.lcbJudgePushStandard", "JudgePushStandardService|JudgePushStandardStrategy|before");

	private String value;
	private String code;

	private LcbEnum(String value) {
		this.value = value;
	}
	
	private LcbEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public static String getValueByCode(String code) {
		for(LcbEnum lcbEnum : values()){
			if(lcbEnum.getCode().equals(code))
				return lcbEnum.getValue();
		}
		return null;
	}
	
	public static boolean ifLcbNode(String code) {
		boolean bool = false;
		for(LcbEnum lcbEnum : values()){
			if(lcbEnum.getCode().equals(code)){
				bool = true;
				break;
			}
		}
		return bool;
	}
}
