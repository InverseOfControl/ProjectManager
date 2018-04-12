package com.ezendai.credit2.master.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.RejectReason;
import com.ezendai.credit2.master.service.RejectReasonService;
import com.ezendai.credit2.system.controller.BaseController;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author chenqi
 * @version $Id: RejectReasonController.java, v 0.1 2014年9月26日 下午1:28:49 chenqi Exp $
 */
@Controller
@RequestMapping("/master/rejectReason")
public class RejectReasonController extends BaseController {
	@Autowired
	private RejectReasonService rejectReasonService;
	@Autowired
	private ProductService productService;

	/***
	 * 
	 * <pre>
	 * 获取一级原因
	 * </pre>
	 *
	 * @param productId
	 * @return
	 */
	@RequestMapping("/getRefuseFirstReason")
	@ResponseBody
	public List<RejectReason> getRefuseFirstReason(Integer productType) {
		if (productType != null) {
			return findRejectReasonByTypeAndLoanType(productType);
		} else {
			Long userId = ApplicationContext.getUser().getId();
			List<Product> loanType = productService.findProductTypeByUserId(userId);
			if (loanType.size() > 0  ) {
				Product product = loanType.get(0);
				if (product != null) {
					productType = product.getProductType();
					return findRejectReasonByTypeAndLoanType(productType);
				}
			}
		}
		return null;
	}

	/**
	 * <pre>
	 * 根据产品ID获取RejectReason对应的列表
	 * </pre>
	 *
	 * @param productId
	 * @return
	 */
	private List<RejectReason> findRejectReasonByTypeAndLoanType(Integer productType) {
		if (productType.compareTo(EnumConstants.ProductType.SE_LOAN.getValue()) == 0) {
			return rejectReasonService.findRejectReasonByTypeAndLoanType(EnumConstants.RejectReason.ONE_LEVEL.getValue(), EnumConstants.ProductType.SE_LOAN.getValue());
		} else if (productType.compareTo(EnumConstants.ProductType.CAR_LOAN.getValue()) == 0) {
			return rejectReasonService.findRejectReasonByTypeAndLoanType(EnumConstants.RejectReason.ONE_LEVEL.getValue(), EnumConstants.ProductType.CAR_LOAN.getValue());
		} else {
			return null;
		}
	}

	/***
	 * 
	 * <pre>
	 * 根据选择的二级原因获取一级原因
	 * </pre>
	 *
	 * @param productId
	 * @return
	 */
	@RequestMapping("/getRefuseSecondReason")
	@ResponseBody
	public List<RejectReason> getRefuseSecondReason(Long parentId) {
		return rejectReasonService.findRejectReasonByParentId(parentId);
	}
}
