package com.ezendai.credit2.master.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ezendai.credit2.apply.model.Attachment;
import com.ezendai.credit2.apply.model.AttachmentDetail;
import com.ezendai.credit2.apply.service.AttachmentDetailService;
import com.ezendai.credit2.apply.service.AttachmentService;
import com.ezendai.credit2.apply.vo.AttachmentAttributesVO;
import com.ezendai.credit2.apply.vo.AttachmentDetailVO;
import com.ezendai.credit2.apply.vo.AttachmentTreeVO;
import com.ezendai.credit2.apply.vo.AttachmentVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.CommonUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.FileUtil;
import com.ezendai.credit2.framework.util.ZipUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.service.FileUploadService;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	private Credit2Properties credit2Properties;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private AttachmentDetailService attachmentDetailService;

	@Autowired
	private SysLogService sysLogService;

	private Long counter;

	private static final String SLASH = File.separator;

	private static final String UNDERLINE = "_";
	public static final Log log = LogFactory.getLog(FileUploadServiceImpl.class);

	@Override
	@Transactional
	public int imageUpload(HttpServletRequest request, Long loanId, Long personId, Long guaranteeId, Integer attachmentType, Integer attachmentTypeSub, Integer dirLevel, Integer optModule,
							Integer productId) {
		String defaultFormatMinutesDate = DateUtil.defaultFormatMinutesDate(new Date());
		String uuid = CommonUtil.getUUID();
		String saveDir = credit2Properties.getUploadPath();
		String classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(attachmentType);
		if (attachmentTypeSub != null) {
			classifyName = classifyName + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(attachmentType) + UNDERLINE + String.valueOf(attachmentTypeSub);
		}
		//attachment表的title字段
		String title = null;
		if (dirLevel.compareTo(EnumConstants.DirLevel.TWO_LEVEL.getValue()) == 0) {
			title = getAttachmentTypeName(attachmentType);
		} else if (dirLevel.compareTo(EnumConstants.DirLevel.THREE_LEVEL.getValue()) == 0) {
			title = getAttachmentSubTypeName(attachmentTypeSub, productId);
		}
		String fileTruthName = defaultFormatMinutesDate + UNDERLINE + uuid;

		// 文件处理器 
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 文件列表 
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		// 配置的文件目录
		String suffix = null;
		String originalFilename = null;
		MultipartFile mf = null;
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			mf = entity.getValue();
			//文件后缀
			originalFilename = mf.getOriginalFilename();
			suffix = FileUtil.getSuffixName(originalFilename);
			// 新的文件名
			String fullFileTruthName = fileTruthName + "." + suffix;
			//文件保存的路径
			String fullSavePath = saveDir + SLASH + classifyName + SLASH + fileTruthName + "." + suffix;

			File saveFile = new File(fullSavePath);
			try {// 保存
				FileUtils.writeByteArrayToFile(saveFile, mf.getBytes());
			} catch (IOException e) {
				log.error("FileUtils.writeByteArrayToFile error" + e.getMessage());
				e.printStackTrace();
				return -1;
			}
			AttachmentVO attachmentVO = new AttachmentVO();
			attachmentVO.setClassifyName(classifyName);
			List<Attachment> attachmentList = attachmentService.findListByVO(attachmentVO);
			Long attachmentId = null;
			//判断如果Attachment表中不存在，说明是第一次添加新增
			if (attachmentList != null && attachmentList.size() == 0) {
				Attachment attachment = new Attachment();
				attachment.setClassifyName(classifyName);
				attachment.setType(dirLevel);
				attachment.setLoanId(loanId);
				if (personId != null) {
					attachment.setPersonId(personId);
				}
				if (guaranteeId != null) {
					attachment.setGuaranteeId(guaranteeId);
				}
				attachment.setTitle(title);
				attachment.setIsDeleted(EnumConstants.YesOrNo.NO.getValue());
				Attachment insertAttachment = attachmentService.insert(attachment);
				attachmentId = insertAttachment.getId();
			}
			//判断如果Attachment表中存在，且只有一条记录则获取ID
			else if (attachmentList != null && attachmentList.size() == 1) {
				Attachment attachment = attachmentList.get(0);
				attachmentId = attachment.getId();
			} else {
				return -1;
			}
			AttachmentDetail attachmentDetail = new AttachmentDetail();
			attachmentDetail.setAttachmentId(attachmentId);
			attachmentDetail.setOriginalName(originalFilename);
			attachmentDetail.setName(fullFileTruthName);
			try {
				attachmentDetail.setFileSize(Long.valueOf(mf.getBytes().length));
			} catch (IOException e) {
				log.error("setFileSize error" + e.getMessage());
				e.printStackTrace();
				return -1;
			}
			attachmentDetail.setSuffix(suffix);
			attachmentDetail.setIsDeleted(EnumConstants.YesOrNo.NO.getValue());
			AttachmentDetail insertAttachmentDetail = attachmentDetailService.insert(attachmentDetail);
			Long attachmentDetailId = insertAttachmentDetail.getId();
			//插入系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(optModule);
			Integer optType = null;
			if (optModule.compareTo(EnumConstants.OptionModule.APPLY_LOAN.getValue()) == 0) {
				optType = EnumConstants.OptionType.APPLY_UPLOAD.getValue();
			} else if (optModule.compareTo(EnumConstants.OptionModule.CONTRACT.getValue()) == 0) {
				optType = EnumConstants.OptionType.CONTRACT_UPLOAD.getValue();
			} else if (optModule.compareTo(EnumConstants.OptionModule.CONTRACT_CONFIRM.getValue()) == 0) {
				optType = EnumConstants.OptionType.CONTRACT_CONFIRM_UPLOAD.getValue();
			} else if (optModule.compareTo(EnumConstants.OptionModule.ORG_MANAGE.getValue()) == 0) {
				optType = EnumConstants.OptionType.ORG_UPLOAD_ATTACHMENT.getValue();
			}	 else if (optModule.compareTo(EnumConstants.OptionModule.COLLECTION_TASK_MANAGE.getValue()) == 0) {
				optType = EnumConstants.OptionType.COLLECTION_TASK_UPLOAD.getValue();
			}	 else if (optModule.compareTo(EnumConstants.OptionModule.REFUSAL_ENTRY.getValue()) == 0) {
				optType = EnumConstants.OptionType.APPLY_UPLOAD.getValue();
			}	
			sysLog.setOptType(optType);
			if(optModule.compareTo(EnumConstants.OptionModule.ORG_MANAGE.getValue()) == 0){
				sysLog.setRemark("上传附件操作,机构ID:" + (loanId-10000000000000L) + ",附件明细ID:" + attachmentDetailId.toString());
			}else{
				sysLog.setRemark("上传附件操作,借款ID:" + loanId.toString() + ",附件明细ID:" + attachmentDetailId.toString());
			}
			sysLogService.insert(sysLog);
		}

		return 0;
	}

	private AttachmentTreeVO addToTree(String treeName, List<AttachmentTreeVO> attachmentTreeList, String classifyName, Integer dirLevel, AttachmentTreeVO attachmentTree,
										List<AttachmentTreeVO> firstLevelTree) {

		if (dirLevel.compareTo(EnumConstants.DirLevel.ONE_LEVEL.getValue()) == 0) {
			++counter;
			AttachmentTreeVO attachmentTreeVO = new AttachmentTreeVO();
			attachmentTreeVO.setId(counter);
			attachmentTreeVO.setText(treeName);
			attachmentTreeList.add(attachmentTreeVO);
			return attachmentTreeVO;
		}
		if (dirLevel.compareTo(EnumConstants.DirLevel.THREE_LEVEL.getValue()) == 0) {

			AttachmentVO attachmentVO = new AttachmentVO();
			attachmentVO.setClassifyName(classifyName);
			attachmentVO.setType(dirLevel);
			List<Attachment> attachmentListByVO = attachmentService.findListByVO(attachmentVO);
			if (attachmentListByVO != null && attachmentListByVO.size() == 1) {
				++counter;
				AttachmentTreeVO secondLevelTree = new AttachmentTreeVO();
				secondLevelTree.setId(counter);
				secondLevelTree.setText(treeName);
				firstLevelTree.add(secondLevelTree);
				attachmentTree.setChildren(firstLevelTree);

				Attachment attachment = attachmentListByVO.get(0);
				Long attachmentId = attachment.getId();
				AttachmentDetailVO attachmentDetailVO = new AttachmentDetailVO();
				attachmentDetailVO.setAttachmentId(attachmentId);
				List<AttachmentDetail> attachmentDetailList = attachmentDetailService.findListByVO(attachmentDetailVO);
				List<AttachmentTreeVO> thirdLevelTree = new ArrayList<AttachmentTreeVO>();
				for (int i = 0; i < attachmentDetailList.size(); i++) {
					++counter;
					AttachmentDetail attachmentDetail = attachmentDetailList.get(i);
					//原始名字
					String originalName = attachmentDetail.getOriginalName();
					//名字
					String name = attachmentDetail.getName();
					Long attachmentDetailId = attachmentDetail.getId();
					AttachmentTreeVO attachmentTreeSecondVO = new AttachmentTreeVO();
					attachmentTreeSecondVO.setId(counter);
					attachmentTreeSecondVO.setText(originalName);
					AttachmentAttributesVO attributes = new AttachmentAttributesVO();
					attributes.setAttachmentDetailId(String.valueOf(attachmentDetailId));
					String src = attachment.getClassifyName() + SLASH + name;
					//本地自用
					//src = "resources/uploads/" + src;
					//正式环境用
					src = credit2Properties.getUploadPath() + SLASH + src;
					attributes.setSrc(src);
					attachmentTreeSecondVO.setAttributes(attributes);
					thirdLevelTree.add(attachmentTreeSecondVO);
				}
				secondLevelTree.setChildren(thirdLevelTree);
			}

		}

		if (dirLevel.compareTo(EnumConstants.DirLevel.TWO_LEVEL.getValue()) == 0) {

			AttachmentVO attachmentVO = new AttachmentVO();
			attachmentVO.setClassifyName(classifyName);
			attachmentVO.setType(dirLevel);
			List<Attachment> attachmentListByVO = attachmentService.findListByVO(attachmentVO);
			if (attachmentListByVO != null && attachmentListByVO.size() == 1) {
				++counter;
				AttachmentTreeVO attachmentTreeVO = new AttachmentTreeVO();
				attachmentTreeVO.setId(counter);
				attachmentTreeVO.setText(treeName);
				Attachment attachment = attachmentListByVO.get(0);
				Long attachmentId = attachment.getId();
				AttachmentDetailVO attachmentDetailVO = new AttachmentDetailVO();
				attachmentDetailVO.setAttachmentId(attachmentId);
				List<AttachmentDetail> attachmentDetailList = attachmentDetailService.findListByVO(attachmentDetailVO);

				List<AttachmentTreeVO> children = new ArrayList<AttachmentTreeVO>();
				for (int i = 0; i < attachmentDetailList.size(); i++) {
					++counter;
					AttachmentDetail attachmentDetail = attachmentDetailList.get(i);
					//原始名字
					String originalName = attachmentDetail.getOriginalName();
					//名字
					String name = attachmentDetail.getName();
					Long attachmentDetailId = attachmentDetail.getId();
					AttachmentTreeVO attachmentTreeSecondVO = new AttachmentTreeVO();
					attachmentTreeSecondVO.setId(counter);
					attachmentTreeSecondVO.setText(originalName);
					AttachmentAttributesVO attributes = new AttachmentAttributesVO();
					attributes.setAttachmentDetailId(String.valueOf(attachmentDetailId));
					String src = attachment.getClassifyName() + SLASH + name;
					//本地自用
					//src = "resources/uploads/" + src;
					//正式环境用
					src = credit2Properties.getUploadPath() + SLASH + src;
					attributes.setSrc(src);
					attachmentTreeSecondVO.setAttributes(attributes);
					children.add(attachmentTreeSecondVO);
				}
				attachmentTreeVO.setChildren(children);
				attachmentTreeList.add(attachmentTreeVO);
			}
		}
		return null;

	}

	@Override
	public List<AttachmentTreeVO> getAttachmentTree(Long loanId, Long productId) {
		UserSession user = ApplicationContext.getUser();
		Integer userType = null;
		if (user != null) {
			userType = user.getUserType();
		}
		List<AttachmentTreeVO> attachmentTreeList = new ArrayList<AttachmentTreeVO>();
		String contractConfirmClassifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.CONTRACT_CONFIRM.getValue());
		String contractSignClassfyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.CONTRACT_SIGN.getValue());
		String organSignClassfyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.ORGAN_SIGN.getValue());
		String collectionSignClassfyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.COLLECTION_SIGN.getValue());
		AttachmentVO attachmentVO = new AttachmentVO();
		attachmentVO.setLoanId(loanId);
		List<Attachment> attachmentListByVO = attachmentService.findListByVO(attachmentVO);
		if (attachmentListByVO != null && attachmentListByVO.size() > 0) {
			if (productId != null) {
				attachmentVO.setType(EnumConstants.DirLevel.THREE_LEVEL.getValue());
				//查询是否有借款申请即3级目录的附件
				List<Attachment> attachmentListTreelevelByVO = attachmentService.findListByVO(attachmentVO);
				if (attachmentListTreelevelByVO != null && attachmentListTreelevelByVO.size() > 0) {
					setCounter(-1L);
					List<AttachmentTreeVO> firstLevelTree = new ArrayList<AttachmentTreeVO>();
					AttachmentTreeVO attachmentTreeVO = addToTree("信审资料", attachmentTreeList, null, EnumConstants.DirLevel.ONE_LEVEL.getValue(), null, null);
					if (productId.compareTo(Long.valueOf(EnumConstants.ProductType.CAR_LOAN.getValue())) == 0) {
						String applyCarBasicClassifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue())
															+ SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
															+ String.valueOf(EnumConstants.AttachmentSubType.CAR_BASIC.getValue());

						String applyCarWaterClassifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue())
															+ SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
															+ String.valueOf(EnumConstants.AttachmentSubType.CAR_WATER.getValue());

						String applyCarVehicleDataClassifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE
																	+ String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH + String.valueOf(loanId) + UNDERLINE
																	+ String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
																	+ String.valueOf(EnumConstants.AttachmentSubType.CAR_VEHICLE_DATA.getValue());

						String applyCarPatchClassifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue())
															+ SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
															+ String.valueOf(EnumConstants.AttachmentSubType.CAR_PATCH.getValue());

						String applyCarOtherClassifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue())
															+ SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
															+ String.valueOf(EnumConstants.AttachmentSubType.CAR_OTHER.getValue());
						
						String applyCarIdNoClassifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue())
															+ SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
															+ String.valueOf(EnumConstants.AttachmentSubType.CAR_IDNO.getValue());
						
						//车贷
						addToTree("基本申请资料", attachmentTreeList, applyCarBasicClassifyName, EnumConstants.DirLevel.THREE_LEVEL.getValue(), attachmentTreeVO, firstLevelTree);
						addToTree("流水", attachmentTreeList, applyCarWaterClassifyName, EnumConstants.DirLevel.THREE_LEVEL.getValue(), attachmentTreeVO, firstLevelTree);
						addToTree("车辆资料", attachmentTreeList, applyCarVehicleDataClassifyName, EnumConstants.DirLevel.THREE_LEVEL.getValue(), attachmentTreeVO, firstLevelTree);
						addToTree("补件", attachmentTreeList, applyCarPatchClassifyName, EnumConstants.DirLevel.THREE_LEVEL.getValue(), attachmentTreeVO, firstLevelTree);
						addToTree("其他", attachmentTreeList, applyCarOtherClassifyName, EnumConstants.DirLevel.THREE_LEVEL.getValue(), attachmentTreeVO, firstLevelTree);
						addToTree("身份证正反面", attachmentTreeList, applyCarIdNoClassifyName, EnumConstants.DirLevel.THREE_LEVEL.getValue(), attachmentTreeVO, firstLevelTree);
					} else if (productId.compareTo(Long.valueOf(EnumConstants.ProductType.SE_LOAN.getValue())) == 0) {

						String applySeBasicClassifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue())
															+ SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
															+ String.valueOf(EnumConstants.AttachmentSubType.SE_BASIC.getValue());

						String applSePublicWaterClassifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue())
																+ SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
																+ String.valueOf(EnumConstants.AttachmentSubType.SE_PUBLIC_WATER.getValue());

						String applySePrivateWaterClassifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE
																	+ String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH + String.valueOf(loanId) + UNDERLINE
																	+ String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
																	+ String.valueOf(EnumConstants.AttachmentSubType.SE_PRIVATE_WATER.getValue());

						String applySePatchClassifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue())
															+ SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
															+ String.valueOf(EnumConstants.AttachmentSubType.SE_PATCH.getValue());

						String applySeOtherClassifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue())
															+ SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
															+ String.valueOf(EnumConstants.AttachmentSubType.SE_OTHER.getValue());
						//小企业贷
						addToTree("基本申请资料", attachmentTreeList, applySeBasicClassifyName, EnumConstants.DirLevel.THREE_LEVEL.getValue(), attachmentTreeVO, firstLevelTree);
						addToTree("对公流水", attachmentTreeList, applSePublicWaterClassifyName, EnumConstants.DirLevel.THREE_LEVEL.getValue(), attachmentTreeVO, firstLevelTree);
						addToTree("对私流水", attachmentTreeList, applySePrivateWaterClassifyName, EnumConstants.DirLevel.THREE_LEVEL.getValue(), attachmentTreeVO, firstLevelTree);
						addToTree("补件", attachmentTreeList, applySePatchClassifyName, EnumConstants.DirLevel.THREE_LEVEL.getValue(), attachmentTreeVO, firstLevelTree);
						addToTree("其他", attachmentTreeList, applySeOtherClassifyName, EnumConstants.DirLevel.THREE_LEVEL.getValue(), attachmentTreeVO, firstLevelTree);
					}
				} 
				//当没有3级附件上传的时候，counter从0开始计
				else {
					setCounter(0L);
				}
			}
			addToTree("合同签约", attachmentTreeList, contractSignClassfyName, EnumConstants.DirLevel.TWO_LEVEL.getValue(), null, null);
			
			addToTree("机构签约", attachmentTreeList, organSignClassfyName, EnumConstants.DirLevel.TWO_LEVEL.getValue(), null, null);
			
			addToTree("外访资料", attachmentTreeList, collectionSignClassfyName, EnumConstants.DirLevel.TWO_LEVEL.getValue(), null, null);
			//判断只有当用户类型为审贷会决议或者财务的时候才可查看合同确认这个菜单下的附件
			if (userType != null && (userType.compareTo(EnumConstants.UserType.AUDIT.getValue()) == 0 || userType.compareTo(EnumConstants.UserType.FINANCE.getValue()) == 0)) {
				addToTree("合同确认", attachmentTreeList, contractConfirmClassifyName, EnumConstants.DirLevel.TWO_LEVEL.getValue(), null, null);
			}
		}
		return attachmentTreeList;
	}

	@Override
	@Transactional
	public void deleteAttachmentDetail(Long attachmentDetailId, Integer optModule) {
		AttachmentDetail attachmentDetail = attachmentDetailService.get(attachmentDetailId);
		String name = attachmentDetail.getName();
		Attachment attachment = attachmentService.get(attachmentDetail.getAttachmentId());
		String classifyName = attachment.getClassifyName();

		//正式环境用
		String deletePath = credit2Properties.getUploadPath() + SLASH + classifyName + SLASH + name;
		FileUtil.deleteFile(deletePath);
		attachmentDetailService.deleteById(attachmentDetailId);

		//插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(optModule);
		Integer optType = null;
		if (optModule.compareTo(EnumConstants.OptionModule.APPLY_LOAN.getValue()) == 0) {
			optType = EnumConstants.OptionType.APPLY_DEL_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.CONTRACT.getValue()) == 0) {
			optType = EnumConstants.OptionType.CONTRACT_DEL_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.CONTRACT_CONFIRM.getValue()) == 0) {
			optType = EnumConstants.OptionType.CONTRACT_CONFIRM_DEL_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.ORG_MANAGE.getValue()) == 0) {
			optType = EnumConstants.OptionType.ORG_DEL_ATTACHMENT.getValue();
		}else if (optModule.compareTo(EnumConstants.OptionModule.COLLECTION_TASK_MANAGE.getValue()) == 0) {
			optType = EnumConstants.OptionType.COLLECTION_TASK_DEL_ATTACHMENT.getValue();
		}else if (optModule.compareTo(EnumConstants.OptionModule.REFUSAL_ENTRY.getValue()) == 0) {
			optType = EnumConstants.OptionType.CONTRACT_DEL_ATTACHMENT.getValue();
		}
		sysLog.setOptType(optType);
		sysLog.setRemark("删除附件操作,附件明细ID:" + attachmentDetailId.toString());
		sysLogService.insert(sysLog);
	}

	@Override
	public void downloadAttachmentDetail(Long attachmentDetailId, HttpServletResponse response, Integer optModule) {
		AttachmentDetail attachmentDetail = attachmentDetailService.get(attachmentDetailId);
		Long attachmentId = attachmentDetail.getAttachmentId();
		String name = attachmentDetail.getName();
		String originalName = attachmentDetail.getOriginalName();
		Attachment attachment = attachmentService.get(attachmentId);
		String classifyName = attachment.getClassifyName();

		//插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(optModule);
		Integer optType = null;
		if (optModule.compareTo(EnumConstants.OptionModule.APPLY_LOAN.getValue()) == 0) {
			optType = EnumConstants.OptionType.APPLY_DOWNLOAD_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.CONTRACT.getValue()) == 0) {
			optType = EnumConstants.OptionType.CONTRACT_DOWNLOAD_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.CONTRACT_CONFIRM.getValue()) == 0) {
			optType = EnumConstants.OptionType.CONTRACT_CONFIRM_DOWNLOAD_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.AUDIT_LOAN.getValue()) == 0) {
			optType = EnumConstants.OptionType.AUDIT_DOWNLOAD_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.FINANCE_AUDIT.getValue()) == 0) {
			optType = EnumConstants.OptionType.FINANCE_AUDIT_DOWNLOAD_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.FINANCE_REPAY.getValue()) == 0) {
			optType = EnumConstants.OptionType.FINANCE_REPAY_DOWNLOAD_ATTACHMENT.getValue();
		}else if (optModule.compareTo(EnumConstants.OptionModule.ORG_MANAGE.getValue()) == 0) {
			optType = EnumConstants.OptionType.ORG_DOWNLOAD_ATTACHMENT.getValue();
		}else if (optModule.compareTo(EnumConstants.OptionModule.REFUSAL_ENTRY.getValue()) == 0) {
			optType = EnumConstants.OptionType.APPLY_DOWNLOAD_ATTACHMENT.getValue();
		}
		sysLog.setOptType(optType);
		sysLog.setRemark("下载附件操作,附件明细ID:" + attachmentDetailId.toString());
		sysLogService.insert(sysLog);

		String frSaveDirPath = credit2Properties.getUploadPath() + SLASH + classifyName + SLASH + name;

		File frSaveDir = new File(frSaveDirPath);
		if (!frSaveDir.exists())
			return;
		try {
			FileUtil.downloadFile(frSaveDir, false, response, StringUtils.trim(originalName));
		} catch (IOException e) {
			log.error("downloadFile error" + e.getMessage());
			return;
		}
	}

	@Override
	@Transactional
	public void batchDeleteAttachmentDetail(String nodeText, Long loanId, Long productId, Integer optModule) {

		String classifyName = null;
		Integer dirLevel = null;
		String rootDeletePath = null;
		if (StringUtils.endsWithIgnoreCase("信审资料", nodeText)) {
			rootDeletePath = credit2Properties.getUploadPath() + SLASH + String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue());
		}
		dirLevel = EnumConstants.DirLevel.THREE_LEVEL.getValue();
		if (productId.compareTo(Long.valueOf(EnumConstants.ProductType.CAR_LOAN.getValue())) == 0) {
			if (StringUtils.endsWithIgnoreCase("基本申请资料", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.CAR_BASIC.getValue());
			} else if (StringUtils.endsWithIgnoreCase("流水", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.CAR_WATER.getValue());
			} else if (StringUtils.endsWithIgnoreCase("车辆资料", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.CAR_VEHICLE_DATA.getValue());
			} else if (StringUtils.endsWithIgnoreCase("补件", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.CAR_PATCH.getValue());
			} else if (StringUtils.endsWithIgnoreCase("其他", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.CAR_OTHER.getValue());
			}
		} else if (productId.compareTo(Long.valueOf(EnumConstants.ProductType.SE_LOAN.getValue())) == 0) {
			if (StringUtils.endsWithIgnoreCase("基本申请资料", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.SE_BASIC.getValue());
			} else if (StringUtils.endsWithIgnoreCase("对公流水", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.SE_PUBLIC_WATER.getValue());
			} else if (StringUtils.endsWithIgnoreCase("对私流水", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.SE_PRIVATE_WATER.getValue());

			} else if (StringUtils.endsWithIgnoreCase("补件", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.SE_PATCH.getValue());
			} else if (StringUtils.endsWithIgnoreCase("其他", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.SE_OTHER.getValue());
			}
		}
		if (StringUtils.endsWithIgnoreCase("合同签约", nodeText)) {
			dirLevel = EnumConstants.DirLevel.TWO_LEVEL.getValue();
			classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.CONTRACT_SIGN.getValue());
		} else if (StringUtils.endsWithIgnoreCase("合同确认", nodeText)) {
			dirLevel = EnumConstants.DirLevel.TWO_LEVEL.getValue();
			classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.CONTRACT_CONFIRM.getValue());
		}else if(StringUtils.endsWithIgnoreCase("机构签约", nodeText)){
			dirLevel = EnumConstants.DirLevel.TWO_LEVEL.getValue();
			classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.ORGAN_SIGN.getValue());
		}else if(StringUtils.endsWithIgnoreCase("外访资料", nodeText)){
			dirLevel = EnumConstants.DirLevel.TWO_LEVEL.getValue();
			classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.COLLECTION_SIGN.getValue());
		}
		//正式环境用
		String deletePath = credit2Properties.getUploadPath() + SLASH + classifyName;
		//判断假如选择的是借款申请根目录，则删除根目录下所有文件
		if (!StringUtils.isEmpty(rootDeletePath)) {
			FileUtil.deleteDirectory(rootDeletePath);
		} else {
			FileUtil.deleteDirectory(deletePath);
		}
		AttachmentVO attachmentSeVO = new AttachmentVO();
		if (!StringUtils.isEmpty(classifyName)) {
			attachmentSeVO.setClassifyName(classifyName);
		}
		attachmentSeVO.setType(dirLevel);
		attachmentSeVO.setLoanId(loanId);

		List<Attachment> attachmentListByVO = attachmentService.findListByVO(attachmentSeVO);
		for (int i = 0; i < attachmentListByVO.size(); i++) {
			Attachment attachment = attachmentListByVO.get(i);
			AttachmentVO attachmentUpVO = new AttachmentVO();
			attachmentUpVO.setId(attachment.getId());
			attachmentUpVO.setRemark("批量删除");
			attachmentUpVO.setIsDeleted(EnumConstants.YesOrNo.YES.getValue());
			attachmentService.update(attachmentUpVO);

			AttachmentDetailVO attachmentDetailSeVO = new AttachmentDetailVO();
			attachmentDetailSeVO.setAttachmentId(attachment.getId());
			List<AttachmentDetail> attachmentDetailListByVO = attachmentDetailService.findListByVO(attachmentDetailSeVO);
			for (int j = 0; j < attachmentDetailListByVO.size(); j++) {
				AttachmentDetail attachmentDetail = attachmentDetailListByVO.get(j);
				AttachmentDetailVO attachmentDetailUpVO = new AttachmentDetailVO();
				attachmentDetailUpVO.setId(attachmentDetail.getId());
				attachmentDetailUpVO.setRemark("批量删除");
				attachmentDetailUpVO.setIsDeleted(EnumConstants.YesOrNo.YES.getValue());
				attachmentDetailService.update(attachmentDetailUpVO);
			}
		}
		//插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(optModule);
		Integer optType = null;
		if (optModule.compareTo(EnumConstants.OptionModule.APPLY_LOAN.getValue()) == 0) {
			optType = EnumConstants.OptionType.APPLY_BATCH_DEL_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.CONTRACT.getValue()) == 0) {
			optType = EnumConstants.OptionType.CONTRACT_BATCH_DEL_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.CONTRACT_CONFIRM.getValue()) == 0) {
			optType = EnumConstants.OptionType.CONTRACT_CONFIRM_BATCH_DEL_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.ORG_MANAGE.getValue()) == 0) {
			optType = EnumConstants.OptionType.ORG_BATCH_DEL_ATTACHMENT.getValue();
		}else if (optModule.compareTo(EnumConstants.OptionModule.COLLECTION_TASK_MANAGE.getValue()) == 0) {
			optType = EnumConstants.OptionType.COLLECTION_TASK_DEL_ATTACHMENT.getValue();
		}else if (optModule.compareTo(EnumConstants.OptionModule.REFUSAL_ENTRY.getValue()) == 0) {
			optType = EnumConstants.OptionType.CONTRACT_BATCH_DEL_ATTACHMENT.getValue();
		}  
		sysLog.setOptType(optType);
		if(optModule.compareTo(EnumConstants.OptionModule.ORG_MANAGE.getValue()) == 0){
			sysLog.setRemark("批量删除附件操作,所删除目录名称:" + nodeText.toString() + ",机构ID:" + String.valueOf(loanId-10000000000000L));
		}else{
			sysLog.setRemark("批量删除附件操作,所删除目录名称:" + nodeText.toString() + ",借款ID:" + String.valueOf(loanId));
		}
		sysLogService.insert(sysLog);
	}

	@Override
	public void batchDownloadAttachmentDetail(String nodeText, Long loanId, Long productId, Integer optModule, HttpServletResponse response) {
		String saveDir = credit2Properties.getUploadPath();
		Integer dirLevel = null;
		String classifyName = null;
		String zipTitle = null;
		String zipClassifyName = null;
		zipTitle = String.valueOf(loanId) + UNDERLINE;
		if (StringUtils.endsWithIgnoreCase("信审资料", nodeText)) {
			dirLevel = EnumConstants.DirLevel.ONE_LEVEL.getValue();
			zipTitle = zipTitle + "信审资料";
			classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue());
			zipClassifyName = String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue());
		} else if (StringUtils.endsWithIgnoreCase("合同签约", nodeText)) {
			dirLevel = EnumConstants.DirLevel.TWO_LEVEL.getValue();
			zipTitle = nodeText;
			classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.CONTRACT_SIGN.getValue());
			zipClassifyName = String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.CONTRACT_SIGN.getValue());
		} else if (StringUtils.endsWithIgnoreCase("机构签约", nodeText)) {
			dirLevel = EnumConstants.DirLevel.TWO_LEVEL.getValue();
			zipTitle = nodeText;
			classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.ORGAN_SIGN.getValue());
			zipClassifyName = String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.ORGAN_SIGN.getValue());
		} else if (StringUtils.endsWithIgnoreCase("合同确认", nodeText)) {
			dirLevel = EnumConstants.DirLevel.TWO_LEVEL.getValue();
			zipTitle = nodeText;
			classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.CONTRACT_CONFIRM.getValue());
			zipClassifyName = String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.CONTRACT_CONFIRM.getValue());
		} else if (StringUtils.endsWithIgnoreCase("外访资料", nodeText)) {
			dirLevel = EnumConstants.DirLevel.TWO_LEVEL.getValue();
			zipTitle = nodeText;
			classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.COLLECTION_SIGN.getValue());
			zipClassifyName = String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.COLLECTION_SIGN.getValue());
		} else if (productId.compareTo(Long.valueOf(EnumConstants.ProductType.CAR_LOAN.getValue())) == 0) {
			zipTitle = zipTitle + "信审资料" + UNDERLINE + nodeText;
			dirLevel = EnumConstants.DirLevel.THREE_LEVEL.getValue();
			if (StringUtils.endsWithIgnoreCase("基本申请资料", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.CAR_BASIC.getValue());
				zipClassifyName = String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
									+ String.valueOf(EnumConstants.AttachmentSubType.CAR_BASIC.getValue());
			} else if (StringUtils.endsWithIgnoreCase("流水", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.CAR_WATER.getValue());
				zipClassifyName = String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
									+ String.valueOf(EnumConstants.AttachmentSubType.CAR_WATER.getValue());
			} else if (StringUtils.endsWithIgnoreCase("车辆资料", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.CAR_VEHICLE_DATA.getValue());
				zipClassifyName = String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
									+ String.valueOf(EnumConstants.AttachmentSubType.CAR_VEHICLE_DATA.getValue());
			} else if (StringUtils.endsWithIgnoreCase("补件", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.CAR_PATCH.getValue());
				zipClassifyName = String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
									+ String.valueOf(EnumConstants.AttachmentSubType.CAR_PATCH.getValue());
			} else if (StringUtils.endsWithIgnoreCase("其他", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.CAR_OTHER.getValue());
				zipClassifyName = String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
									+ String.valueOf(EnumConstants.AttachmentSubType.CAR_OTHER.getValue());
			} else if(StringUtils.endsWithIgnoreCase("身份证正反面", nodeText)){
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.CAR_IDNO.getValue());
				zipClassifyName = String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
									+ String.valueOf(EnumConstants.AttachmentSubType.CAR_IDNO.getValue());
			}
		} else if (productId.compareTo(Long.valueOf(EnumConstants.ProductType.SE_LOAN.getValue())) == 0) {
			dirLevel = EnumConstants.DirLevel.THREE_LEVEL.getValue();
			zipTitle = zipTitle + "信审资料" + UNDERLINE + nodeText;
			if (StringUtils.endsWithIgnoreCase("基本申请资料", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.SE_BASIC.getValue());
				zipClassifyName = String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
									+ String.valueOf(EnumConstants.AttachmentSubType.SE_BASIC.getValue());
			} else if (StringUtils.endsWithIgnoreCase("对公流水", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.SE_PUBLIC_WATER.getValue());
				zipClassifyName = String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
									+ String.valueOf(EnumConstants.AttachmentSubType.SE_PUBLIC_WATER.getValue());
			} else if (StringUtils.endsWithIgnoreCase("对私流水", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.SE_PRIVATE_WATER.getValue());
				zipClassifyName = String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
									+ String.valueOf(EnumConstants.AttachmentSubType.SE_PRIVATE_WATER.getValue());
			} else if (StringUtils.endsWithIgnoreCase("补件", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.SE_PATCH.getValue());
				zipClassifyName = String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
									+ String.valueOf(EnumConstants.AttachmentSubType.SE_PATCH.getValue());
			} else if (StringUtils.endsWithIgnoreCase("其他", nodeText)) {
				classifyName = String.valueOf(loanId) + SLASH + String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + SLASH
								+ String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
								+ String.valueOf(EnumConstants.AttachmentSubType.SE_OTHER.getValue());
				zipClassifyName = String.valueOf(loanId) + UNDERLINE + String.valueOf(EnumConstants.AttachmentType.LOAN_APPLY.getValue()) + UNDERLINE
									+ String.valueOf(EnumConstants.AttachmentSubType.SE_OTHER.getValue());
			}
		}
		String toSaveDirPath = credit2Properties.getDownloadPath() + SLASH + "attachment" + SLASH + zipClassifyName + UNDERLINE + DateUtil.defaultFormatMSDate(new Date()) + ".zip";

		// 附件存放的目录
		String frSaveDirPath = saveDir + SLASH + classifyName;

		File frSaveDir = new File(frSaveDirPath);
		if (!frSaveDir.exists())
			return;

		File toSaveDir = new File(toSaveDirPath);
		if (toSaveDir.exists()) {
			toSaveDir.delete();
		}
		zipTitle = zipTitle + UNDERLINE + DateUtil.defaultFormatMSDate(new Date());

		AttachmentVO attachmentSeVO = new AttachmentVO();
		if (!StringUtils.isEmpty(classifyName) && dirLevel.compareTo(EnumConstants.DirLevel.THREE_LEVEL.getValue()) == 0) {
			attachmentSeVO.setClassifyName(classifyName);
		}
		if (dirLevel.compareTo(EnumConstants.DirLevel.ONE_LEVEL.getValue()) == 0 || dirLevel.compareTo(EnumConstants.DirLevel.THREE_LEVEL.getValue()) == 0) {
			attachmentSeVO.setType(EnumConstants.DirLevel.THREE_LEVEL.getValue());
		} else if (dirLevel.compareTo(EnumConstants.DirLevel.TWO_LEVEL.getValue()) == 0) {
			attachmentSeVO.setType(EnumConstants.DirLevel.TWO_LEVEL.getValue());
		}
		attachmentSeVO.setLoanId(loanId);
		List<Attachment> attachmentListByVO = attachmentService.findListByVO(attachmentSeVO);
		Map<String, Object> dirMap = new HashMap<String, Object>();
		Map<String, Object> fileMap = new HashMap<String, Object>();

		for (int i = 0; i < attachmentListByVO.size(); i++) {
			Attachment attachment = attachmentListByVO.get(i);
			int lastIndexOf = attachment.getClassifyName().lastIndexOf(SLASH);
			String secondKey = attachment.getClassifyName().substring(lastIndexOf + 1);
			dirMap.put(secondKey, attachment.getTitle());
			AttachmentDetailVO attachmentDetailSeVO = new AttachmentDetailVO();
			attachmentDetailSeVO.setAttachmentId(attachment.getId());
			List<AttachmentDetail> attachmentDetailListByVO = attachmentDetailService.findListByVO(attachmentDetailSeVO);
			for (int j = 0; j < attachmentDetailListByVO.size(); j++) {
				AttachmentDetail attachmentDetail = attachmentDetailListByVO.get(j);
				fileMap.put(attachmentDetail.getName(), attachmentDetail.getOriginalName());
			}
		}

		// 压缩文件
		try {
			ZipUtil.Zip(frSaveDirPath, toSaveDirPath, zipTitle, dirMap, fileMap);
		} catch (IOException e) {
			log.error("Zip error" + e.getMessage());
		}
		//插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(optModule);
		Integer optType = null;
		if (optModule.compareTo(EnumConstants.OptionModule.APPLY_LOAN.getValue()) == 0) {
			optType = EnumConstants.OptionType.APPLY_BATCH_DOWNLOAD_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.CONTRACT.getValue()) == 0) {
			optType = EnumConstants.OptionType.CONTRACT_BATCH_DOWNLOAD_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.CONTRACT_CONFIRM.getValue()) == 0) {
			optType = EnumConstants.OptionType.CONTRACT_CONFIRM_BATCH_DOWNLOAD_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.AUDIT_LOAN.getValue()) == 0) {
			optType = EnumConstants.OptionType.AUDIT_BATCH_DOWNLOAD_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.FINANCE_AUDIT.getValue()) == 0) {
			optType = EnumConstants.OptionType.FINANCE_AUDIT_BATCH_DOWNLOAD_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.FINANCE_REPAY.getValue()) == 0) {
			optType = EnumConstants.OptionType.FINANCE_REPAY_BATCH_DOWNLOAD_ATTACHMENT.getValue();
		} else if (optModule.compareTo(EnumConstants.OptionModule.ORG_MANAGE.getValue()) == 0) {
			optType = EnumConstants.OptionType.ORG_BATCH_DOWNLOAD_ATTACHMENT.getValue();
		}else if (optModule.compareTo(EnumConstants.OptionModule.COLLECTION_TASK_MANAGE.getValue()) == 0) {
			optType = EnumConstants.OptionType.APPLY_DOWNLOAD_ATTACHMENT.getValue();
		}else if (optModule.compareTo(EnumConstants.OptionModule.REFUSAL_ENTRY.getValue()) == 0) {
			optType = EnumConstants.OptionType.APPLY_DOWNLOAD_ATTACHMENT.getValue();
		}
		sysLog.setOptType(optType);
		if (optModule.compareTo(EnumConstants.OptionModule.ORG_MANAGE.getValue()) == 0) {
			sysLog.setRemark("批量下载附件操作,所下载目录名称:" + nodeText.toString() + ",机构ID:" + String.valueOf(loanId-10000000000000L));
		}else{
			sysLog.setRemark("批量下载附件操作,所下载目录名称:" + nodeText.toString() + ",借款ID:" + String.valueOf(loanId));
		}
		sysLogService.insert(sysLog);

		try {
			FileUtil.downloadFile(toSaveDir, true, response, zipTitle + ".zip");
		} catch (IOException e) {
			log.error("downloadFile error" + e.getMessage());
			return;
		}
	}

	private String getAttachmentTypeName(Integer attachmentType) {
		if (attachmentType.compareTo(EnumConstants.AttachmentType.CONTRACT_CONFIRM.getValue()) == 0) {
			return "合同确认";
		} else if (attachmentType.compareTo(EnumConstants.AttachmentType.CONTRACT_SIGN.getValue()) == 0) {
			return "合同签约";
		}else if(attachmentType.compareTo(EnumConstants.AttachmentType.ORGAN_SIGN.getValue()) == 0){
			return "机构签约";
		}else if(attachmentType.compareTo(EnumConstants.AttachmentType.COLLECTION_SIGN.getValue()) == 0){
			return "外访资料";
		}else {
			return null;
		}
	}

	private String getAttachmentSubTypeName(Integer attachmentSubType, Integer productId) {
		if (productId.compareTo(EnumConstants.ProductType.SE_LOAN.getValue()) == 0) {
			if (attachmentSubType.compareTo(EnumConstants.AttachmentSubType.SE_BASIC.getValue()) == 0) {
				return "基本申请资料";
			} else if (attachmentSubType.compareTo(EnumConstants.AttachmentSubType.SE_PUBLIC_WATER.getValue()) == 0) {
				return "对公流水";
			} else if (attachmentSubType.compareTo(EnumConstants.AttachmentSubType.SE_PRIVATE_WATER.getValue()) == 0) {
				return "对私流水";
			} else if (attachmentSubType.compareTo(EnumConstants.AttachmentSubType.SE_PATCH.getValue()) == 0) {
				return "补件";
			} else if (attachmentSubType.compareTo(EnumConstants.AttachmentSubType.SE_OTHER.getValue()) == 0) {
				return "其他";
			}
		} else if (productId.compareTo(EnumConstants.ProductType.CAR_LOAN.getValue()) == 0) {
			if (attachmentSubType.compareTo(EnumConstants.AttachmentSubType.CAR_BASIC.getValue()) == 0) {
				return "基本申请资料";
			} else if (attachmentSubType.compareTo(EnumConstants.AttachmentSubType.CAR_WATER.getValue()) == 0) {
				return "流水";
			} else if (attachmentSubType.compareTo(EnumConstants.AttachmentSubType.CAR_VEHICLE_DATA.getValue()) == 0) {
				return "车辆资料";
			} else if (attachmentSubType.compareTo(EnumConstants.AttachmentSubType.CAR_PATCH.getValue()) == 0) {
				return "补件";
			} else if (attachmentSubType.compareTo(EnumConstants.AttachmentSubType.CAR_OTHER.getValue()) == 0) {
				return "其他";
			}else if(attachmentSubType.compareTo(EnumConstants.AttachmentSubType.CAR_IDNO.getValue()) == 0){
				return "身份证正反面";
			}
		}

		return null;
	}

	public Long getCounter() {
		return counter;
	}

	public void setCounter(Long counter) {
		this.counter = counter;
	}
}
