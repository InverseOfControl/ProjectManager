package com.ezendai.credit2.sign.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author YM20051
 * @description: 捞财宝 配置类
 * @date 2018/1/23 11:51
 */
@Component
public class LcbConfig {

    /** 电子签章模版存放路径 */
    @Value("${eleSignaturePath}")
    private String eleSignaturePath;

    /** 捞财宝签名密钥 */
    @Value("${lcbSignSecretKey}")
    private String lcbSignSecretKey;

    /** 捞财宝签约银行 */
    @Value("${lcbBank}")
    private String lcbBank;

    /** PIC合同上传接口 */
    @Value("${picContractUpload}")
    private String picContractUpload;

    /** 校验合同金额 20W */
    @Value("${contractMany}")
    private BigDecimal contractMany;

    /** 调用核心获取个贷已放款还未还款的金额 */
    @Value("${hexinInfo}")
    private String hexinInfo;

    /** 捞财宝服务地址 */
    @Value("${lcbService}")
    private String lcbService;

    /** 验证用户手机号 */
    private String lcbValidatePhone = "verifyCellPhone";
    /** 注册 */
    private String lcbRegister = "register";
    /** 获取验证码 */
    private String lcbValidateCode = "getVerificationCode";
    /** 登录 */
    private String lcbLogin = "entry";
    /** 实名认证 */
    private String lcbRealNameAuth = "realName";
    /** 绑卡 */
    private String lcbBindingBankCard ="bindBankCard";
    /** 标的推送 */
    private String lcbBidPush = "targetPushed";
    /** 捞财宝推送借款人信息网关 */
    private String lcbBorrowerInfo ="borrowerInfo";
    /** 终止借款接口 */
    private String lcbStopLoan ="terminationLoan";
    /** 合同确认接口 */
    private String lcbContractConfirmation ="contractConfirmation";

    public String getEleSignaturePath() {
        return eleSignaturePath;
    }

    public String getLcbSignSecretKey() {
        return lcbSignSecretKey;
    }

    public String getLcbBank() {
        return lcbBank;
    }

    public String getPicContractUpload() {
        return picContractUpload;
    }

    public BigDecimal getContractMany() {
        return contractMany;
    }

    public String getHexinInfo() {
        return hexinInfo;
    }

    public String getLcbValidatePhone() {
        return lcbService + lcbValidatePhone;
    }

    public String getLcbRegister() {
        return lcbService + lcbRegister;
    }

    public String getLcbValidateCode() {
        return lcbService + lcbValidateCode;
    }

    public String getLcbLogin() {
        return lcbService + lcbLogin;
    }

    public String getLcbRealNameAuth() {
        return lcbService + lcbRealNameAuth;
    }

    public String getLcbBindingBankCard() {
        return lcbService + lcbBindingBankCard;
    }

    public String getLcbBidPush() {
        return lcbService + lcbBidPush;
    }

    public String getLcbBorrowerInfo() {
        return lcbService + lcbBorrowerInfo;
    }

    public String getLcbStopLoan() {
        return lcbService + lcbStopLoan;
    }

    public String getLcbContractConfirmation() {
        return lcbService + lcbContractConfirmation;
    }
}
