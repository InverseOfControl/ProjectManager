package com.ezendai.credit2.report.service;
import java.util.Map;
import com.ezendai.credit2.report.model.Smallrp;

public interface SmallrpService {
	/**
	 * 
	 * <pre>
	 * 根据合同编号查询
	 * </pre>
	 *
	 * @param contractNo
	 * @return
	 */
	public Smallrp getSmallrpByContractNo(Map<String,Object> map);

    /**
     * 车贷打印需要的字段
     * @param contractNo
     * @return Smallrp
     */
    Smallrp queryCarCreditByContractNo(Map<String,Object> map);
    
    /**
     * 车贷打印需要的字段
     * @param ExtensionId
     * @return Smallrp
     */
    Smallrp queryCarCreditByExtensionId(Map<String,Object> map);
}
