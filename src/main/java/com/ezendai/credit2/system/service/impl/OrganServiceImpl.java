/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.system.assembler.OrganAssembler;
import com.ezendai.credit2.system.dao.OrganDao;
import com.ezendai.credit2.system.model.Organ;
import com.ezendai.credit2.system.model.OrganBank;
import com.ezendai.credit2.system.model.OrganSalesDepartment;
import com.ezendai.credit2.system.model.OrganSalesManager;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.OrganBankService;
import com.ezendai.credit2.system.service.OrganSalesDepartmentService;
import com.ezendai.credit2.system.service.OrganSalesManagerService;
import com.ezendai.credit2.system.service.OrganService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.OrganBankVO;
import com.ezendai.credit2.system.vo.OrganDetailVO;
import com.ezendai.credit2.system.vo.OrganSalesDepartmentVO;
import com.ezendai.credit2.system.vo.OrganSalesManagerVO;
import com.ezendai.credit2.system.vo.OrganVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: OrganServiceImpl.java, v 0.1 2015年9月15日 下午4:36:06 00226557 Exp $
 */
@Service
public class OrganServiceImpl implements OrganService {
	@Autowired
	private OrganDao organDao;
	@Autowired
	private OrganBankService organBankService;
	@Autowired
	private OrganSalesDepartmentService organSalesDepartmentService;
	@Autowired
	private OrganSalesManagerService organSalesManagerService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private SysUserService sysUserService;
	/** 
	 * @param organVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganService#update(com.ezendai.credit2.system.vo.OrganVO)
	 */
	@Override
	public int update(OrganVO organVO) {
		return organDao.update(organVO);
	}

	/** 
	 * @param organVO
	 * @see com.ezendai.credit2.system.service.OrganService#deleteByVO(com.ezendai.credit2.system.vo.OrganVO)
	 */
	@Override
	public void deleteByVO(OrganVO organVO) {
//		organDao.deleteByIdList(organVO);
	}

	/** 
	 * @param offer
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganService#insert(com.ezendai.credit2.after.model.Offer)
	 */
	@Override
	public Organ insert(Organ organ) {
		return organDao.insert(organ);
	}

	/** 
	 * @param organVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganService#findListByVo(com.ezendai.credit2.system.vo.OrganVO)
	 */
	@Override
	public List<Organ> findListByVo(OrganVO organVO) {
		return organDao.findListByVo(organVO);
	}

	/** 
	 * @param organVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganService#findWithPg(com.ezendai.credit2.system.vo.OrganVO)
	 */
	@Override
	public Pager findWithPg(OrganVO organVO) {
		if (EnumConstants.UserType.CUSTOMER_SERVICE.getValue().equals(organVO.getUser().getUserType())
			|| EnumConstants.UserType.STORE_MANAGER.getValue().equals(organVO.getUser().getUserType())
			|| EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue().equals(
				organVO.getUser().getUserType())){
			return organDao.findWithPgByUser(organVO);
		}else{
			
			return organDao.findWithPg(organVO);
		}
	}

	/** 
	 * @param organVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganService#count(com.ezendai.credit2.system.vo.OrganVO)
	 */
	@Override
	public Integer count(OrganVO organVO) {
		return null;
	}

	/** 
	 * @param offerId
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganService#get(java.lang.Long)
	 */
	@Override
	public Organ get(Long id) {
		return organDao.get(id);
	}

	/** 
	 * @param organId
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganService#exist(java.lang.Long)
	 */
	@Override
	public boolean existValidCheckPlan(Long organId) {
		return organDao.existValidCheckPlan(organId);
	}

	/** 
	 * @param vo
	 * @see com.ezendai.credit2.system.service.OrganService#processOrganInsert(com.ezendai.credit2.system.vo.OrganDetailVO)
	 */
	@Override
	@Transactional
	public void processOrganInsert(OrganDetailVO vo) {
		//新增机构信息
		Organ organ = OrganAssembler.convertOrganDetailVO2Organ(vo);
		organ.setStatus(EnumConstants.YesOrNo.YES.getValue());
		organ = this.insert(organ);
		
		//新增银行卡信息
		if (vo.getBankAccountList() != null) {
			for (BankAccount bankAccount : vo.getBankAccountList()) {
				if (bankAccount != null && StringUtil.isNotEmpty(bankAccount.getAccount())) {
					bankAccount = bankAccountService.insert(bankAccount);
					OrganBank organBank = new OrganBank();
					organBank.setBankAccountId(bankAccount.getId());
					organBank.setOrganId(organ.getId());
					organBankService.insert(organBank);
				}
			}
		}
		//新增签约门店信息
		if (vo.getSalesDepartmentList() != null) {
			for (BaseArea baseArea : vo.getSalesDepartmentList()) {
				if (baseArea != null && baseArea.getId() != null) {
					OrganSalesDepartment organSalesDepartment = new OrganSalesDepartment();
					organSalesDepartment.setOrganId(organ.getId());
					organSalesDepartment.setSalesDeptId(baseArea.getId());
					organSalesDepartmentService.insert(organSalesDepartment);
				}
			}
		}
		//新增客户经理信息
		if (vo.getSalesManagerList() != null) {
			for (OrganSalesManager user : vo.getSalesManagerList()) {
				OrganSalesManager organSalesManager = new OrganSalesManager();
				if (user != null && StringUtil.isNotEmpty(user.getSalesManager())) {
					SysUser sys = sysUserService.getSysUserByLoginName(user.getCode());
					organSalesManager.setSalesManager(user.getSalesManager());
					organSalesManager.setCode(user.getCode());
					organSalesManager.setOrganId(organ.getId());
					organSalesManager.setUserId(sys.getId());
					organSalesManagerService.insert(organSalesManager);
				}
			}
		}
	
	}

	/** 
	 * @param vo
	 * @param organId
	 * @see com.ezendai.credit2.system.service.OrganService#processOrganEdit(com.ezendai.credit2.system.vo.OrganDetailVO, java.lang.Long)
	 */
	@Override
	@Transactional
	public void processOrganEdit(OrganDetailVO vo, Long organId) {

		//新增机构信息
		OrganVO organVO = OrganAssembler.convertOrganDetailVO2OrganVO(vo);
		organVO.setId(organId);
		this.update(organVO);
		//delete old info
		OrganBankVO organBankVO = new OrganBankVO();
		organBankVO.setOrganId(organId);
		organBankService.deleteListByVo(organBankVO);
		OrganSalesDepartmentVO organSalesDepartmentVO =  new OrganSalesDepartmentVO();
		organSalesDepartmentVO.setOrganId(organId);
		organSalesDepartmentService.deleteListByVo(organSalesDepartmentVO);
		OrganSalesManagerVO organSalesManagerVO = new OrganSalesManagerVO();
		organSalesManagerVO.setOrganId(organId);
		organSalesManagerService.deleteListByVo(organSalesManagerVO);
		
		//新增银行卡信息
		if (vo.getBankAccountList() != null) {
			for (BankAccount bankAccount : vo.getBankAccountList()) {
				if(bankAccount != null && StringUtil.isNotEmpty(bankAccount.getAccount())){
					bankAccount = bankAccountService.insert(bankAccount);
					OrganBank organBank = new OrganBank();
					organBank.setBankAccountId(bankAccount.getId());
					organBank.setOrganId(organId);
					organBankService.insert(organBank);
				}
			}
		}
		//新增签约门店信息
		if (vo.getSalesDepartmentList() != null) {
			for (BaseArea baseArea : vo.getSalesDepartmentList()) {
				if (baseArea != null && baseArea.getId() != null) {
					OrganSalesDepartment organSalesDepartment = new OrganSalesDepartment();
					organSalesDepartment.setOrganId(organId);
					organSalesDepartment.setSalesDeptId(baseArea.getId());
					organSalesDepartmentService.insert(organSalesDepartment);
				}
			}
		}
		//新增客户经理信息
		if (vo.getSalesManagerList() != null) {
			for (OrganSalesManager user : vo.getSalesManagerList()) {
				OrganSalesManager organSalesManager = new OrganSalesManager();
				if (user != null && StringUtil.isNotEmpty(user.getSalesManager())) {
					SysUser sys = sysUserService.getSysUserByLoginName(user.getCode());
					organSalesManager.setSalesManager(user.getSalesManager());
					organSalesManager.setCode(user.getCode());
					organSalesManager.setOrganId(organId);
					organSalesManager.setUserId(sys.getId());
					organSalesManagerService.insert(organSalesManager);
				}
			}
		}
	}

	/** 
	 * @param organId
	 * @see com.ezendai.credit2.system.service.OrganService#deleteOrgan(java.lang.Long)
	 */
	@Override
	@Transactional
	public void deleteOrgan(Long organId,Organ organ) {
		OrganVO vo = OrganAssembler.transferModel2VO(organ);
		vo.setStatus(EnumConstants.YesOrNo.NO.getValue());
		vo.setId(organId);
		//1.机构表(organ-status->0)
		this.update(vo);
		//2.方案表(channel_plan-is_deleted-->1)
		organDao.deleteChannelPlan(organId);
		//3.方案表批复表(channel_plan—check-is_deleted-->1)
		organDao.deleteChannelPlanCheck(organId);
	}

}
