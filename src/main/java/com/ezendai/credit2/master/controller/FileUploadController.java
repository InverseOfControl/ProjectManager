package com.ezendai.credit2.master.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.vo.AttachmentTreeVO;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.service.FileUploadService;
import com.ezendai.credit2.system.controller.BaseController;

/**
 * <pre>
 * 附件相关功能，包括文件上传，文件树形显示，删除附件，下载附件等功能
 * </pre>
 *
 * @author chenqi
 * @version $Id: FileUploadController.java, v 0.1 2014年9月12日 下午3:48:24 chenqi Exp $
 */
@Controller
@RequestMapping("/fileUpload")
public class FileUploadController extends BaseController {

	@Autowired
	private FileUploadService fileUploadService;

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/imageUpload")
	@ResponseBody
	public Map imageUpload(HttpServletRequest request, Long loanId, Long personId, Integer optModule, Integer productId) {
		Map<String, Object> result = new HashMap<String, Object>();
		String comboTypeParameter = request.getParameter("combo_type");
		Integer attachmentType = null;
		Integer dirLevel = null;
		if (comboTypeParameter != null) {
			attachmentType = Integer.valueOf(comboTypeParameter);
			dirLevel = EnumConstants.DirLevel.TWO_LEVEL.getValue();
		}
		String subComboTypeParameter = request.getParameter("subCombo_type");
		Integer attachmentTypeSub = null;
		if (subComboTypeParameter != null) {
			attachmentTypeSub = Integer.valueOf(subComboTypeParameter);
			dirLevel = EnumConstants.DirLevel.THREE_LEVEL.getValue();
		}
		if (attachmentType != null) {
			int ret = fileUploadService.imageUpload(request, loanId, personId, null, attachmentType, attachmentTypeSub, dirLevel, optModule, productId);
			if (ret == 0)
				result.put("success", true);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/showImgJson")
	public List<AttachmentTreeVO> showImgJson(Long loanId, Long productId) {
		return fileUploadService.getAttachmentTree(loanId, productId);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/deleteAttachmentDetail")
	@ResponseBody
	public Map deleteAttachmentDetail(Long attachmentDetailId, Integer optModule) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			fileUploadService.deleteAttachmentDetail(attachmentDetailId, optModule);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/downloadAttachmentDetail")
	@ResponseBody
	public Map downloadAttachmentDetail(Long attachmentDetailId, HttpServletResponse response, Integer optModule) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			fileUploadService.downloadAttachmentDetail(attachmentDetailId, response, optModule);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/batchDeleteAttachmentDetail")
	@ResponseBody
	public Map batchDeleteAttachmentDetail(String nodeText, Long loanId, Long productId, Integer optModule) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			fileUploadService.batchDeleteAttachmentDetail(nodeText, loanId, productId, optModule);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/batchDownloadAttachmentDetail")
	@ResponseBody
	public Map batchDownloadAttachmentDetail(String nodeText, Long loanId, Long productId, HttpServletRequest request, Integer optModule, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			nodeText = java.net.URLDecoder.decode(request.getParameter("nodeText"), "utf-8");
			fileUploadService.batchDownloadAttachmentDetail(nodeText, loanId, productId, optModule, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
