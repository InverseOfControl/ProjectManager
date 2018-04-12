package com.ezendai.credit2.report.service;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.report.model.DeliveryDetail;
import com.ezendai.credit2.report.vo.DeliveryDetailVO;

/**   
*    
* 项目名称：credit2-report   
* 类名称：DeliveryDetailService   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2015年11月6日 下午2:49:10   
* 修改人：liboyan   
* 修改时间：2015年11月6日 下午2:49:10   
* 修改备注：   
* @version    
*    
*/
public interface DeliveryDetailService {
	
	Pager  findWithPG(DeliveryDetailVO vo);
	
	List<DeliveryDetail> findWith(DeliveryDetailVO vo);
	
    Integer	getCountByDeliveryDetailVO(DeliveryDetailVO deliveryDetailVO);
    
	/**
	 * 过件总汇
	 * @param loanvo
	 * @return
	 */
	List<DeliveryDetail> makeSummaryList(DeliveryDetailVO deliveryDetailVO);
	
	/**
	 * 助学贷营业部
	 * @return
	 */
	List<BaseArea> getStudentLoanSalesDepts(Map<String, String> map);
	
	/**
	 * 车贷营业部
	 * @return
	 */
	List<BaseArea> getCarLoanSalesDepts(Map<String, String> map);
	
	/**
	 * 统计符合条件数据
	 * @param deliveryDetailVO
	 * @return
	 */
	Integer countmakeSummaryList(DeliveryDetailVO deliveryDetailVO);
}
