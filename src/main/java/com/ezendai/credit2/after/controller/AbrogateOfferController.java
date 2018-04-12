package com.ezendai.credit2.after.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.after.model.SpecialRepayment;
import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.after.vo.SpecialRepaymentVO;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;

/**
 * <pre>
 * 停止报盘
 * </pre>
 *
 * @author liuhy
 * @version $Id: AbrogateOfferController.java, v 0.1 2016年02月22日 上午9:39:29 00219930 Exp $
 */
@Controller
@RequestMapping("/after/abrogateOffer")
public class AbrogateOfferController extends BaseController {
	
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private SpecialRepaymentService specialRepaymentService;
	
	@RequestMapping("/abrogateOfferMain")
	public String init(HttpServletRequest request) {
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.LOAN_STATUS ,EnumConstants.REPAYMENT_PLAN_STATE});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "after/abrogateOffer/abrogateOffer";
	}
	
	@RequestMapping("/abrogateOfferGene")
	public String initGene(HttpServletRequest request) {
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.LOAN_STATUS ,EnumConstants.REPAYMENT_PLAN_STATE});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "after/abrogateOffer/abrogateOfferGene";
	}
	
	/**
	 * <pre>
	 * 停止报盘分页查询
	 * </pre>
	 *
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getAbrogateOfferPage")
	@ResponseBody
	public Map<String, Object> getAbrogateOfferPage(SpecialRepaymentVO specialRepaymentVO, int rows, int page){
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		List<SpecialRepayment> specialRepaymentlist=new ArrayList<SpecialRepayment>();
		Pager p = new Pager();
		p.setRows(rows);
		p.setPage(page);
		p.setSidx("t.ID");
		p.setSort("DESC");
		specialRepaymentVO.setPager(p);
		//配置条件为取消报盘并且为申请状态
		List<Integer> typeList = new ArrayList<Integer>();
		typeList.add(EnumConstants.SpecialRepaymentType.REFUSE_OFFER.getValue());
		specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
		specialRepaymentVO.setTypeList(typeList);
		specialRepaymentVO.setNeedOffer(EnumConstants.YesOrNo.YES.getValue());//需要报盘的设置为1
		Pager specialRepaymentPager=specialRepaymentService.findListByVOWihtBaseExtend(specialRepaymentVO);
		specialRepaymentlist=specialRepaymentPager.getResultList();
		result.put("total", specialRepaymentPager.getTotalCount());
		result.put("rows", specialRepaymentlist);
		return result;
	}
	
	
	
	/**
	 * <pre>
	 * 可进行停止报盘的交易分页查询
	 * </pre>
	 *
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getAbrogateGenePage")
	@ResponseBody
	public Map<String, Object> getAbrogateGenePage(LoanVO loanVO, int rows, int page){
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		List<Loan> loanlist=new ArrayList<Loan>();
		if(loanVO!=null && loanVO.getPersonIdnum() != null && loanVO.getPersonName() != null){
			Pager p = new Pager();
			p.setRows(rows);
			p.setPage(page);
			p.setSidx("REQUEST_DATE");
			p.setSort("ASC");
			loanVO.setPager(p);
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.LoanStatus.正常.getValue());
			statusList.add(EnumConstants.LoanStatus.逾期.getValue());
			statusList.add(EnumConstants.LoanStatus.展期合同签订.getValue());
			loanVO.setStatusList(statusList);
			loanVO.setProductType(EnumConstants.ProductType.CAR_LOAN.getValue());//车贷
			Pager loanPager=loanService.findWithPGUnionExtension(loanVO);
			loanlist=loanPager.getResultList();
			result.put("total", loanPager.getTotalCount());
			result.put("rows", loanlist);
		}else{
			result.put("total", 0);
			result.put("rows", loanlist);
		}
		return result;
	}
	
	/**
	 * 
	 * <pre>
	 * 验证是否可以停止报盘
	 * </pre>
	 *
	 * @param specialRepaymentVO 
	 * @return
	 */
	@RequestMapping("/checkValidOffer")
	@ResponseBody
	public Map<String, Object> checkValidOffer(SpecialRepaymentVO specialRepaymentVO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(specialRepaymentVO != null) {
			List<Integer> typeList = new ArrayList<Integer>();
			typeList.add(EnumConstants.SpecialRepaymentType.REFUSE_OFFER.getValue());
			specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
			specialRepaymentVO.setTypeList(typeList);
			Pager specialRepaymentPager=specialRepaymentService.findListByVOWihtBaseExtend(specialRepaymentVO);
			if(specialRepaymentPager.getTotalCount()==1){
				resultMap.put("result", "error");
				resultMap.put("message", "该借款数据已经生成停止报盘");
			}else if(specialRepaymentPager.getTotalCount()==0){
				resultMap.put("result", "success");
				resultMap.put("message", "成功");
			}else{
				resultMap.put("result", "error");
				resultMap.put("message", "该借款数据不可生成停止报盘");
			}
		}
		return resultMap; 
	}
	
	/**
	 * 
	 * <pre>
	 * 生成停止报盘
	 * </pre>
	 *
	 * @param loanId 
	 * @return
	 */
	@RequestMapping("/submitAbrogateGene")
	@ResponseBody
	public Map<String, Object> submitAbrogateGene(Long loanId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(loanId != null) {
			String result=specialRepaymentService.submitAbrogateGene(loanId);
			resultMap.put("result", result);
			resultMap.put("message", "成功");
		}else{
			resultMap.put("result", "error");
			resultMap.put("message", "生成失败");
		}
		return resultMap; 
	}
	
	/**
	 * 
	 * <pre>
	 * 取消停止报盘
	 * </pre>
	 *
	 * @param loanId 
	 * @return
	 */
	@RequestMapping("/cancelAbrogateOffer")
	@ResponseBody
	public Map<String, Object> cancelAbrogateOffer(Long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(id != null) {
			String result=specialRepaymentService.cancelAbrogateOffer(id);
			resultMap.put("result", result);
			resultMap.put("message", "成功");
		}else{
			resultMap.put("result", "error");
			resultMap.put("message", "取消失败");
		}
		return resultMap; 
	}
}
