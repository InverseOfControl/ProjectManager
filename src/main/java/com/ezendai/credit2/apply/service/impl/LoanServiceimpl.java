package com.ezendai.credit2.apply.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.apply.model.*;
import com.ezendai.credit2.apply.vo.*;
import com.ezendai.credit2.common.util.HttpUtils;
import com.ezendai.credit2.sign.constant.LcbConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.apply.dao.LoanDao;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.LoanExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants.SerialNum;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.model.WorkPlaceInfo;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.WorkPlaceInfoService;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysSerialNumService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.SerialNumResult;

@Service
public class LoanServiceimpl implements LoanService {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysSerialNumService sysSerialNumService;
	@Autowired
	private WorkPlaceInfoService workPlaceInfoService;
	@Autowired
	private BusinessLogService businessLogService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private ExtensionService extensionService;
	@Autowired
	private LoanExtensionService loanExtensionService;
	@Autowired
	private SpecialRepaymentService specialRepaymentService;
	@Autowired
	LoanDao loanDao;
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private SysParameterService sysParameterService;

	@Autowired
    private LcbConfig lcbConfig;
	@Autowired
	private PersonServiceImpl personService;


    @Override
	public Loan insert(Loan loan) {
		return loanDao.insert(loan);
	}

	@Override
	public void deleteById(Long id) {
		loanDao.deleteById(id);
	}

	@Override
	public void deleteByIdList(LoanVO loanVO) {
		loanDao.deleteByIdList(loanVO);
	}

	@Transactional
	@Override
	public int update(LoanVO loanVO) {
		return loanDao.update(loanVO);
	}

	@Override
	public Loan get(Long id) {
		return loanDao.get(id);
	}

	@Override
	public List<Loan> findListByVO(LoanVO loanVO) {
		return loanDao.findListByVo(loanVO);
	}

	@Override
	public List<Loan> findListByVOExtension(LoanVO loanVO) {
		return loanDao.findListByVoExtension(loanVO);
	}

	/**
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.LoanService#findListByVOWithUnion(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	public List<Long> findListByVOWithUnion(LoanVO loanVO) {
		return loanDao.findListByVOWithUnion(loanVO);
	}

	@Override
	public boolean exists(Map<String, Object> map) {
		return false;
	}

	@Override
	public Pager findWithPg(LoanVO loanVO) {
		return loanDao.findWithPg(loanVO);
	}

	@Override
	public Loan get(LoanVO loanVO) {
		return loanDao.get(loanVO);
	}

	@Override
	public boolean exists(Long id) {
		return loanDao.exists(id);
	}

	@Override
	public boolean canEdit(Loan loan, Long userId) {

		if (loan.getExtensionTime() > 0)
			return false;

		if (loan.getStatus() !=null && (loan.getStatus().compareTo(EnumConstants.LoanStatus.初审退回.getValue()) == 0 ||loan.getStatus().compareTo(EnumConstants.LoanStatus.终审退回门店.getValue()) == 0 || loan.getStatus().compareTo(EnumConstants.LoanStatus.新建.getValue()) == 0
				|| loan.getStatus().compareTo(EnumConstants.LoanStatus.有条件同意.getValue()) == 0 || loan.getStatus().compareTo(EnumConstants.LoanStatus.退回门店.getValue()) == 0)
				&& (userId.compareTo(loan.getServiceId()) == 0 || userId.compareTo(loan.getAssessorId()) == 0)) {
			return true;
		}
		SysUser user = sysUserService.get(userId);
		if(loan.getProductId().compareTo(EnumConstants.ProductList.STUDENT_LOAN.getValue())==0 
				|| loan.getProductId().compareTo(EnumConstants.ProductList.WS_SE_LOAN.getValue())==0
				|| loan.getProductId().compareTo(EnumConstants.ProductList.CITY_WIDE_POS_LOAN.getValue())==0
				|| loan.getProductId().compareTo(EnumConstants.ProductList.CITY_WIDE_SE_LOAN.getValue())==0){
			
			if( user.getUserType().equals(EnumConstants.UserType.CHANNEL_SPECIALIST.getValue())){
				return true;
				
			}
			
		}
		
		String carLoanEditNO = sysParameterService.getByCodeNoCache("CAR_LOAN_EDIT_EMPLNO").getParameterValue();
		boolean ifEditCarLoan = false;
		if(carLoanEditNO != null){
			
			ifEditCarLoan =  carLoanEditNO.contains(user.getLoginName());
		}
		
		if(EnumConstants.ProductType.CAR_LOAN.getValue().equals(loan.getProductType()) 
				&&  ifEditCarLoan){
			return true;
			
		}
		return false;
	}

	/**
	 * 
	 * <pre>
	 * 对公还款 -&gt; 领取借款记录列表
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	@Override
	public Pager findBusinessLoanListWithPg(LoanVO loanVO) {
		return loanDao.findBusinessLoanListWithPg(loanVO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean canSubmit(Loan loan, Long userId) {
		// 验证复核人员是否是当前客服
	if (loan.getAssessorId()!=null&&loan.getAssessorId().compareTo(userId) == 0) {
			if (loan.getExtensionTime() > 0)
				return false;
			if (loan.getStatus().compareTo(EnumConstants.LoanStatus.初审退回.getValue()) == 0 || loan.getStatus().compareTo(EnumConstants.LoanStatus.终审退回门店.getValue()) == 0 || loan.getStatus().compareTo(EnumConstants.LoanStatus.新建.getValue()) == 0
					|| loan.getStatus().compareTo(EnumConstants.LoanStatus.有条件同意.getValue()) == 0 || loan.getStatus().compareTo(EnumConstants.LoanStatus.退回门店.getValue()) == 0) {
				return true;
			}
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean canSubmitRestore(Loan loan, Long userId) {
		BusinessLogVO businessLogVO = new BusinessLogVO();
		businessLogVO.setLoanId(loan.getId());
		
		Pager businessLog = businessLogService.findWithPg(businessLogVO );
		List<BusinessLog > businessLogList = businessLog.getResultList();
		BusinessLog tempBusiLog = new BusinessLog();
		if(businessLogList.size()>0){
			 tempBusiLog= businessLogList.get(0);
			for(BusinessLog busiLog:businessLogList){
				
				if(busiLog.getCreateDate() != null){
					if(busiLog.getCreateDate().after(tempBusiLog.getCreateDate())){
						tempBusiLog = busiLog;
					}
				}
				
			}
		}
		// 验证复核人员是否是当前客服
		if(loan.getStatus().compareTo(EnumConstants.LoanStatus.取消.getValue())==0 &&loan.getProductId().compareTo(EnumConstants.ProductList.STUDENT_LOAN.getValue())==0 && tempBusiLog!= null){
			if(tempBusiLog.getFlowStatus() !=null && tempBusiLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.TIME_OUT_CANCEL.getValue())==0){
				loan.setFirstTrialId(Long.valueOf(0));
				return true;
				
			}
			
		}
		return false;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public boolean canRestoreLoan(Loan loan,Long userId) {
		BusinessLogVO businessLogVO = new BusinessLogVO();
		businessLogVO.setLoanId(loan.getId());
		
		Pager businessLog = businessLogService.findWithPg(businessLogVO );
		List<BusinessLog > businessLogList = businessLog.getResultList();
		BusinessLog tempBusiLog = new BusinessLog();
		if(businessLogList.size()>0){
			 tempBusiLog= businessLogList.get(0);
			for(BusinessLog busiLog:businessLogList){
				
				if(busiLog.getCreateDate() != null){
					if(busiLog.getCreateDate().after(tempBusiLog.getCreateDate())){
						tempBusiLog = busiLog;
					}
				}
				
			}
		}
		SysUser user = sysUserService.get(userId);
		if(loan.getStatus().compareTo(EnumConstants.LoanStatus.取消.getValue())==0 &&loan.getProductId().compareTo(EnumConstants.ProductList.STUDENT_LOAN.getValue())==0 
				&& tempBusiLog!= null){
			if(tempBusiLog.getFlowStatus() !=null 
					&& tempBusiLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.TIME_OUT_CANCEL.getValue())==0
					&& user.getUserType().equals(EnumConstants.UserType.CHANNEL_SPECIALIST.getValue())){
				
				return true;
				
			}
			
		}
		
		
		return false;
	}

	@Override
	@Transactional
	public void submit(LoanVO loanVO, Long userId) {
		Loan loan = loanDao.get(loanVO);
		if (canSubmit(loan, userId) || canSubmitRestore(loan, userId)) {
			if (loan.getStatus().compareTo(EnumConstants.LoanStatus.新建.getValue()) == 0 || loan.getStatus().compareTo(EnumConstants.LoanStatus.取消.getValue()) == 0) {

				BusinessLog businessLog = new BusinessLog();
				
	
					if(loan.getProductId() == 8){
	
						
						
						 loanVO.setStatus(EnumConstants.LoanStatus.初审待分配.getValue());
						 loanVO.setFirstTrialId(loan.getFirstTrialId());
						
					} else {
	
						loanVO.setStatus(EnumConstants.LoanStatus.审批中.getValue());
	
					};
					businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.SUBMIT.getValue());
	
					
					
					businessLog.setLoanId(loan.getId());
					businessLog.setMessage("提交申请");
					businessLogService.insert(businessLog);
					// 插入系统日志
					SysLog sysLog = new SysLog();
					sysLog.setOptModule(EnumConstants.OptionModule.APPLY_LOAN.getValue());
					sysLog.setOptType(EnumConstants.OptionType.SUBMIT_LOAN.getValue());
					sysLog.setRemark("新建   提交申请   借款ID   " + loan.getId());
					sysLogService.insert(sysLog);
				
				}
				else if (loan.getStatus().compareTo(EnumConstants.LoanStatus.有条件同意.getValue()) == 0) {
					loanVO.setStatus(EnumConstants.LoanStatus.合同签订.getValue());
					BusinessLog businessLog = new BusinessLog();
					businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.SUBMIT.getValue());
					businessLog.setLoanId(loan.getId());
					businessLog.setMessage("提交申请");
					businessLogService.insert(businessLog);
					// 插入系统日志
					SysLog sysLog = new SysLog();
					sysLog.setOptModule(EnumConstants.OptionModule.APPLY_LOAN.getValue());
					sysLog.setOptType(EnumConstants.OptionType.SUBMIT_LOAN.getValue());
					sysLog.setRemark("有条件签约   提交申请  借款ID   " + loan.getId());
					sysLogService.insert(sysLog);
				}
				else if (loan.getStatus().compareTo(EnumConstants.LoanStatus.退回门店.getValue()) == 0) {
					loanVO.setStatus(EnumConstants.LoanStatus.审批中.getValue());
					BusinessLog businessLog = new BusinessLog();
					businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.SUBMIT.getValue());
					businessLog.setLoanId(loan.getId());
					businessLog.setMessage("退回后再次提交申请  借款ID   " + loan.getId());
					businessLogService.insert(businessLog);
					// 插入系统日志
					SysLog sysLog = new SysLog();
					sysLog.setOptModule(EnumConstants.OptionModule.APPLY_LOAN.getValue());
					sysLog.setOptType(EnumConstants.OptionType.SUBMIT_LOAN.getValue());
					sysLog.setRemark("退回后再次提交申请   借款ID   " + loan.getId());
					sysLogService.insert(sysLog);
				}
				
				else if(loan.getStatus().equals(EnumConstants.LoanStatus.初审退回.getValue())||loan.getStatus().equals(EnumConstants.LoanStatus.终审退回门店.getValue())){
					BusinessLog businessLog = new BusinessLog();
					loanVO.setStatus(EnumConstants.LoanStatus.门店重提.getValue());
					
//					loanVO.setFirstTrialId(Long.valueOf("0"));
					businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.STORE_AGAIN.getValue());
					
					businessLog.setLoanId(loan.getId());
					businessLog.setMessage("门店重提");
					businessLogService.insert(businessLog);
					// 插入系统日志
					SysLog sysLog = new SysLog();
					sysLog.setOptModule(EnumConstants.OptionModule.APPLY_LOAN.getValue());
					sysLog.setOptType(EnumConstants.OptionType.SUBMIT_LOAN.getValue());
					sysLog.setRemark("门店重提   提交申请   借款ID   " + loan.getId());
					sysLogService.insert(sysLog);
					 
				}
				
				loanVO.setSubmitDate(new Date());
				loanDao.update(loanVO);
			}
		
	}

	@Override
	@Transactional
	public void extensionSubmit(ExtensionVO extensionVO) {
		Extension extension = extensionService.get(extensionVO.getId());
		if (extension.getStatus().compareTo(EnumConstants.LoanStatus.展期申请中.getValue()) == 0) {
			extensionVO.setStatus(EnumConstants.LoanStatus.展期签批中.getValue());
			BusinessLog businessLog = new BusinessLog();
			businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.SUBMIT.getValue());
			businessLog.setLoanId(extension.getId());
			businessLog.setMessage("提交展期申请");
			businessLogService.insert(businessLog);
			// 插入系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.APPLY_LOAN.getValue());
			sysLog.setOptType(EnumConstants.OptionType.SUBMIT_LOAN.getValue());
			sysLog.setRemark("新建   提交申请   展期借款ID   " + extension.getId());
			sysLogService.insert(sysLog);
		} else if (extension.getStatus().compareTo(EnumConstants.LoanStatus.展期退回门店.getValue()) == 0) {
			extensionVO.setStatus(EnumConstants.LoanStatus.展期签批中.getValue());
			BusinessLog businessLog = new BusinessLog();
			businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.SUBMIT.getValue());
			businessLog.setLoanId(extension.getId());
			businessLog.setMessage("退回后再次提交申请 展期 借款ID   " + extension.getId());
			businessLogService.insert(businessLog);
			// 插入系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.APPLY_LOAN.getValue());
			sysLog.setOptType(EnumConstants.OptionType.SUBMIT_LOAN.getValue());
			sysLog.setRemark("退回后再次提交申请   借款ID   " + extension.getId());
			sysLogService.insert(sysLog);
		} else if (extension.getStatus().compareTo(EnumConstants.LoanStatus.有条件同意.getValue()) == 0) {
			extensionVO.setStatus(EnumConstants.LoanStatus.展期合同签订.getValue());
			BusinessLog businessLog = new BusinessLog();
			businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.SUBMIT.getValue());
			businessLog.setLoanId(extension.getId());
			businessLog.setMessage("有条件同意再次提交  展期 借款ID   " + extension.getId());
			businessLogService.insert(businessLog);
			// 插入系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.APPLY_LOAN.getValue());
			sysLog.setOptType(EnumConstants.OptionType.SUBMIT_LOAN.getValue());
			sysLog.setRemark("有条件同意再次提交申请   借款ID   " + extension.getId());
			sysLogService.insert(sysLog);
		}
		extensionVO.setSubmitDate(new Date());
		extensionService.update(extensionVO);

	}

	/*
	 * 判断客服、客户经理，复核人员在不在一个区域中 客户经理:只能在一个网点中 客服/复核人员:可以管理多个网点。
	 */
	public boolean checkManager(Long serviceId, Long managerId, Long assessorId) {

		List<Long> service = sysUserService.getSalesDeptIdByUserId(serviceId);
		List<Long> manager = sysUserService.getSalesDeptIdByUserId(managerId);
		List<Long> assessor = sysUserService.getSalesDeptIdByUserId(assessorId);
		// 客服和复核人员属于单一网点
		if (service.size() == 1 && assessor.size() == 1) {
			if (service.get(0).equals(manager.get(0)) && service.get(0).equals(assessor.get(0))) {
				return true;
			} else {
				return false;
			}
		} else {// 客服和复核人员属于多个网点,并且客服和复核人员网点权限一样
			if (service.size() == assessor.size()) {
				for (int i = 0; i < service.size(); i++) {
					// for (int j = 0; j < service.size(); j++) {
					// if ( service.get(i).equals(assessor.get(j))) {
					// break;
					// }
					// }
					if (service.get(i).equals(manager.get(0))) {
						return true; // 客户经理在客服的网点中
					}
				}
				return true;
			} else {
				return false;
			}
		}
	}

	/***
	 * 
	 * <pre>
	 * 生成客户编号
	 * </pre>
	 *
	 * @param serialNum
	 * @return
	 */
	public String getPersonNoByType(SerialNum serialNum, Long salesDeptId) {

		SerialNumResult serialNumResult = sysSerialNumService.getSerialNum(serialNum);
		if (serialNumResult.isSuccess() == false) {
			throw new BusinessException("获取序列号失败，请重试。");
		}
		// SysUser sysUser =
		// sysUserService.get(ApplicationContext.getUser().getId());
		// List<BaseArea> baseAreaList = new ArrayList<BaseArea>();
		// baseAreaList.add(sysUserService.getSalesDeptByUserId(sysUser.getId()));
		BaseArea baseArea = baseAreaService.get(salesDeptId);

		// baseAreaVo.setId(sysUser.getSalesTeamId());
		// baseAreaVo.setIdentifier(Constants.CREDIT2_SALESDEPARTMENT);
		// List<BaseArea> baseAreaList = applyService.findAreaById(baseAreaVo);
		WorkPlaceInfo workPlaceInfo = workPlaceInfoService.loadOneWorkPlaceInfoById(baseArea.getWorkPlaceInfoId());
		String personNo = serialNum.name().toString() + workPlaceInfo.getZoneCode();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		Date date = serialNumResult.getDate();
		String date2 = dateFormat.format(date).toString();
		Long seq = serialNumResult.getSeq();
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumIntegerDigits(4);
		formatter.setGroupingUsed(false);
		String s = formatter.format(seq.intValue());
		String personNos = personNo + date2 + s;

		return personNos;
	}

	/* 预留 */
	@Override
	public boolean canApprove(Loan loan, Long userId) {
		if (loan.getStatus().compareTo(EnumConstants.LoanStatus.审批中.getValue()) == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canContractSign(Loan loan, Long userId) {
		// 如果没有执行生成合同操作，则不能进行合同签约操作
		if (StringUtils.isEmpty(loan.getContractNo()))
			return false;
		// 如果贷款状态不是合同签订，则不能进行合同签约操作
		if (!loan.getStatus().equals(EnumConstants.LoanStatus.合同签订.getValue()))
			return false;

		return true;
	}

	@Override
	public boolean canExtensionContractSign(Loan loan) {
		// 如果没有执行生成展期合同操作，则不能进行展期合同签约操作
		if (StringUtils.isEmpty(loan.getContractNo()))
			return false;
		// 如果贷款状态不是展期合同签订，则不能进行展期合同签约操作
		if (!loan.getStatus().equals(EnumConstants.LoanStatus.展期合同签订.getValue()))
			return false;

		return true;
	}

	@Override
	public boolean canContractSubmit(Loan loan, Long userId) {
		if (loan.getStatus().equals(EnumConstants.LoanStatus.合同确认退回.getValue()) || loan.getStatus().equals(EnumConstants.LoanStatus.财务审核退回.getValue())
				|| loan.getStatus().equals(EnumConstants.LoanStatus.财务放款退回.getValue()))
			return true;

		return false;
	}

	@Override
	public boolean canExtensionContractSubmit(Loan loan) {
		if (loan.getStatus().equals(EnumConstants.LoanStatus.展期合同确认退回.getValue()))
			return true;

		return false;
	}

	@Override
	public int getCountByLoanVO(LoanVO loanVO) {
		return loanDao.loanCount(loanVO);
	}

	@Override
	public Loan getLatestLoanByperson(Long personId) {
		Loan latestLoan = null;
		LoanVO loanVO = new LoanVO();
		loanVO.setPersonId(personId);

		List<Loan> loanList = findListByVO(loanVO);
		for (int i = 0; i < loanList.size(); i++) {
			if (latestLoan == null || loanList.get(i).getModifiedTime().after(latestLoan.getModifiedTime()))
				latestLoan = loanList.get(i);

		}
		return latestLoan;
	}

	/**
	 * <pre>
	 * 更新状态不是某个状态的记录
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	@Override
	public int updateLoanByStatus(LoanVO loanVO) {
		return loanDao.updateLoanByStatus(loanVO);
	}

	@Override
	public String updateByIdList(LoanVO loanVO) {
		if (loanVO != null) {
			// 变更管理
			loanDao.updateLoanByIdList(loanVO);
			ExtensionVO extensionVO = new ExtensionVO();
			extensionVO.setIdList(loanVO.getIdList());
			extensionVO.setManagerId(loanVO.getManagerId());
			extensionService.updateByIdList(extensionVO);
			// 记录系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.CHANGE_MANAGE.getValue());
			sysLog.setOptType(EnumConstants.OptionType.CHANGE.getValue());
			List<Long> idList = loanVO.getIdList();
			StringBuilder remark = new StringBuilder();
			for (Long id : idList) {
				remark.append(id + ";");
			}
			sysLog.setRemark(remark.toString());
			sysLogService.insert(sysLog);

			return "success";
		}
		return "false";
	}

	/**
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.LoanService#changeManageFindWithPG(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	public Pager changeManageFindWithPG(LoanVO loanVO) {
		return loanDao.changeManageFindWithPG(loanVO);
	}

	@Override
	public Pager findRepayTrialWithPG(LoanVO loanVO) {
		return loanDao.findRepayTrialWithPG(loanVO);
	}

	/**
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.LoanService#specialRepaymentFindWithPG(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	public Pager specialRepaymentFindWithPG(LoanVO loanVO) {
		return loanDao.specialRepaymentFindWithPG(loanVO);
	}

	@Override
	public Pager findRepayEntryWithPG(LoanVO loanVO) {
		return loanDao.findRepayEntryWithPG(loanVO);
	}

	@Override
	public boolean canExtension(Loan loan) {
		// 判断借款状态为正常或者逾期的,产品为车贷且为车贷短期,并且最后一期
		if ((loan.getStatus().compareTo(EnumConstants.LoanStatus.正常.getValue()) == 0 || loan.getStatus().compareTo(EnumConstants.LoanStatus.逾期.getValue()) == 0)
				&& (loan.getProductType().compareTo(EnumConstants.ProductType.CAR_LOAN.getValue()) == 0) && (loan.getTime() != null && loan.getTime().longValue() <= 4)
				&& (!specialRepaymentService.isOneTimeRepayment(loan.getId())) && (!specialRepaymentService.isInAdvanceRepayment(loan.getId())) && (loan.getResidualTime().intValue() == 1)) {

			int time = loan.getTime().intValue() - 2;
			Date startRepayDate = loan.getStartRepayDate();
			Date endRepayDate = loan.getEndRepayDate();
			Date startLimitDate = DateUtil.getNowDateAfter(time, startRepayDate);
			Date endLimitDate = DateUtil.getNowDateAfter(1, loan.getEndRepayDate());

			Date today = DateUtil.getToday();
			if (today.compareTo(endRepayDate) != 0 && today.before(endLimitDate) && today.after(startLimitDate)) {
				LoanExtensionVO loanExtensionVO = new LoanExtensionVO();
				loanExtensionVO.setLoanId(loan.getLoanId());
				List<LoanExtension> loanExtensionList = loanExtensionService.findListByVo(loanExtensionVO);
				if (loanExtensionList.size() == 0) {
					return true;
				} else {
					Long extensionId = loanExtensionList.get(0).getExtensionId();
					Extension extension = extensionService.get(extensionId);
					if (extension.getStatus().compareTo(EnumConstants.LoanStatus.展期拒绝.getValue()) == 0 || extension.getStatus().compareTo(EnumConstants.LoanStatus.取消.getValue()) == 0) {
						return true;
					} else {
						if (loan.getExtensionTime().intValue() + 1 == extension.getExtensionTime().intValue()) {
							return false;
						} else {
							return true;
						}
					}
				}
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	@Override
	public Pager findWithPGUnionExtension(LoanVO loanVO) {
		return loanDao.findWithPGUnionExtension(loanVO);
	}

	@Override
	@Transactional
	public Map<String,String> createdExtension(LoanVO loanVO) {
		Map<String,String> map = new HashMap<String,String>();
		String isSuccess = "fail";
		String msg = "展期申请失败";
		//申请展期之前进行判断，如果展期已经存在而且不是取消状态，则不允许再一次申请展期
		if(StringUtils.isNotEmpty(String.valueOf(loanVO.getId()))){
			ExtensionVO extensionVO = new ExtensionVO();
			extensionVO.setId(loanVO.getLoanId());
			List<Extension> list = extensionService.findExtensionByCondition(extensionVO);
			if(list !=null && list.size()>0){
				isSuccess = "fail";
				msg = "该展期申请已经存在，请核实！";
				map.put("isSuccess", isSuccess);
				map.put("msg", msg);
				return map;
			}
			
		}
		
		int extensionTime = loanVO.getExtensionTime().intValue();
		Extension extension = new Extension();
		Long personId = null;
		Integer loanType = null;
		Long productId = null;
		Integer productType = null;
		Long crmId = null;
		Long bizDirectorId = null;
		BigDecimal monthRate = null;
		BigDecimal auditMoney = null;
		Long salesTeamId = null;
		Long salesDeptId = null;
		if (extensionTime == 0) {
			Loan loan = loanDao.get(loanVO.getId());
			personId = loan.getPersonId();
			loanType = loan.getLoanType();
			productId = loan.getProductId();
			productType = loan.getProductType();
			crmId = loan.getCrmId();
			bizDirectorId = loan.getBizDirectorId();
			monthRate = loan.getMonthRate();
			auditMoney = loan.getAuditMoney();
			salesTeamId = loan.getSalesTeamId();
			salesDeptId = loan.getSalesDeptId();
		} else {
			Extension extensionGet = extensionService.get(loanVO.getId());
			personId = extensionGet.getPersonId();
			loanType = extensionGet.getLoanType();
			productId = extensionGet.getProductId();
			productType = extensionGet.getProductType();
			crmId = extensionGet.getCrmId();
			bizDirectorId = extensionGet.getBizDirectorId();
			monthRate = extensionGet.getMonthRate();
			auditMoney = extensionGet.getAuditMoney();
			salesTeamId = extensionGet.getSalesTeamId();
			salesDeptId = extensionGet.getSalesDeptId();
		}
		extension.setPersonId(personId);
		extension.setProductId(productId);
		extension.setProductType(productType);
		extension.setLoanType(loanType);
		extension.setServiceId(ApplicationContext.getUser().getId());
		extension.setManagerId(ApplicationContext.getUser().getId());
		extension.setSalesTeamId(salesTeamId);
		extension.setSalesDeptId(salesDeptId);
		extension.setCrmId(crmId);
		extension.setBizDirectorId(bizDirectorId);
		extension.setRequestDate(DateUtil.getToday());
		extension.setSubmitDate(DateUtil.getToday());
		extension.setStatus(EnumConstants.LoanStatus.展期申请中.getValue());
		extension.setExtensionTime(extensionTime + 1);
		extension.setMonthRate(monthRate);
		extension.setRequestMoney(auditMoney);

		extension.setVersion(1L);
		Extension extensionResult = extensionService.insert(extension);

		LoanExtension loanExtension = new LoanExtension();
		loanExtension.setExtensionId(extensionResult.getId());
		loanExtension.setLoanId(loanVO.getLoanId());
		loanExtensionService.insert(loanExtension);

		BusinessLog businessLog = new BusinessLog();
		businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.NEW.getValue());
		businessLog.setLoanId(extensionResult.getId());
		businessLog.setMessage("申请展期");
		businessLogService.insert(businessLog);

		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.APPLY_LOAN.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CREATE_LOAN.getValue());
		sysLog.setRemark("申请展期");
		sysLogService.insert(sysLog);
		isSuccess =  "success";
		msg = "展期申请成功！";
		
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}

	/**
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.LoanService#findListByVOUnionExtension(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	public List<Loan> findListByVOUnionExtension(LoanVO loanVO) {
		return loanDao.findListByVOUnionExtension(loanVO);
	}

	@Override
	public boolean canExtensionApprove(Loan loan) {
		if (loan.getStatus().compareTo(EnumConstants.LoanStatus.展期签批中.getValue()) == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtensionSubmit(Loan loan) {
		Long userId = ApplicationContext.getUser().getId();
		if (loan.getStatus().compareTo(EnumConstants.LoanStatus.展期申请中.getValue()) == 0 || loan.getStatus().compareTo(EnumConstants.LoanStatus.展期退回门店.getValue()) == 0) {
			return true;
		}
		if (loan.getAssessorId()!=null&&loan.getAssessorId().compareTo(userId) == 0) {

			if (loan.getExtensionTime() > 0 && loan.getStatus().compareTo(EnumConstants.LoanStatus.有条件同意.getValue()) == 0)
				return true;
		}
		return false;
	}

	/**
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.LoanService#updateLoanOrExtension(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	public int updateLoanOrExtension(LoanVO loanVO) {
		Long id = loanVO.getId();
		Loan loan = loanDao.get(id);
		Extension extension = extensionService.get(id);
		// 修改目标为Loan
		if (loan != null) {
			return loanDao.update(loanVO);
		}
		// 修改目标为Extension
		else if (extension != null) {
			ExtensionVO extensionVO = new ExtensionVO();
			extensionVO.setId(extension.getId());
			extensionVO.setRepayAccountId(loanVO.getRepayAccountId());
			return extensionService.update(extensionVO);
		} else {
			return 0;
		}
	}

	/**
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.LoanService#findLastOverdueList(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	public List<Loan> findLastOverdueList(LoanVO loanVO) {
		return loanDao.findLastOverdueList(loanVO);
	}

	/**
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.apply.service.LoanService#isCloseOut(java.lang.Long)
	 */
	@Override
	public boolean isCloseOut(Long id) {
		Loan loan = get(id);
		if (loan != null) {
			if (loan.getStatus().compareTo(EnumConstants.LoanStatus.结清.getValue()) == 0) {
				return true;
			}
		} else {
			Extension extension = extensionService.get(id);
			if (extension.getStatus().compareTo(EnumConstants.LoanStatus.结清.getValue()) == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param loan
	 * @return
	 * @see com.ezendai.credit2.apply.service.LoanService#canCancelLoan(com.ezendai.credit2.apply.model.Loan)
	 */
	@Override
	@Transactional
	public boolean canCancelLoan(Loan loan) {
		Loan newLoan = get(loan.getId());
		if (newLoan != null && EnumConstants.LoanStatus.展期合同确认退回.getValue().compareTo(newLoan.getStatus()) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/** (non-Javadoc)
	 * @see com.ezendai.credit2.apply.service.LoanService#updateLoanByIdList(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	@Transactional
	public String updateLoanByIdList(LoanVO vo) {

		if (vo != null) {
			List<Long> idList = vo.getIdList();
			for (Long long1 : idList) {
				Loan loan = loanDao.get(long1);
				LoanVO loanVO = new LoanVO();
				loanVO.setFirstTrialId(vo.getFirstTrialId());
				loanVO.setId(long1);
				if (loan.getStatus().equals(EnumConstants.LoanStatus.门店重提.getValue())) {
					loanVO.setStatus(EnumConstants.LoanStatus.门店重提.getValue());
				} else {
					loanVO.setStatus(EnumConstants.LoanStatus.初审中.getValue());
				}
				// 信审分派
				loanDao.update(loanVO);
				
				BusinessLog businessLog = new BusinessLog();
				businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.Letter_trial.getValue());
				businessLog.setLoanId(loan.getId());
				businessLog.setMessage("信审分单");
				businessLogService.insert(businessLog);
				// 记录系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.LETTER_TRIAL.getValue());
				sysLog.setOptType(EnumConstants.OptionType.LETTER_TRIAL_ASSIGN.getValue());
				StringBuilder remark = new StringBuilder();
				remark.append(EnumConstants.OptionType.LETTER_TRIAL_ASSIGN.toString());
				sysLog.setRemark("借款ID   "+loanVO.getId());
				sysLogService.insert(sysLog);
			}
			return "success";
		}
		return "false";

	}
	@Override
	public List<Loan> alreadyProcessedList(LoanVO loanVO) {
		 	
		return loanDao.alreadyProcessedList(loanVO);
	}

	@Override
	public Pager plFindWithPGUnionExtension(LoanVO loanVO) {
		return loanDao.plFindWithPGUnionExtension(loanVO);
	}

	@Override
	public void updatePushLcbReturnFraud(Map<String, Object> map) {
		 loanDao.updatePushLcbReturnFraud(map);
		
	}

	@Override
	public JSONObject findResidualPactMoney(String name, String idNum) {

        JSONObject json=new JSONObject();
        json.put("repCode", "000000");

        PersonVO personVO = new PersonVO();
        personVO.setIdnum(idNum);

        Person person = personService.get(personVO);
        if(person != null && !person.getName().equals(name)){
            json.put("repCode", "000001");
            json.put("repMsg", "用户姓名与身份证不匹配!");
            return json;
        }


        BigDecimal residual = new BigDecimal(0);

        //调用核心获取个贷未还款的取值逻辑
        Map<String,String> sendHexinMap=new HashMap<>();
        sendHexinMap.put("idNum", idNum);
        sendHexinMap.put("name", name);

        String result= HttpUtils.post(lcbConfig.getHexinInfo(), sendHexinMap);
        JSONObject jsonObject = JSON.parseObject(result);
        if ( jsonObject.getString("code").equals("000000") ) {
			BigDecimal coreResidual = jsonObject.getBigDecimal("residualPactMoney");
            residual = residual.add(coreResidual);
        }else{
            json.putAll(jsonObject);
        }

        BigDecimal carResidual = loanDao.findResidualPactMoney(idNum);
        if(carResidual != null){
            residual = residual.add(carResidual);
        }

        residual = lcbConfig.getContractMany().subtract(residual);
        json.put("residual", residual);

        return json;
	}
}
