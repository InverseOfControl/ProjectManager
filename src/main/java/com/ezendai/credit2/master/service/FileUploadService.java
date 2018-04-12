package com.ezendai.credit2.master.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezendai.credit2.apply.vo.AttachmentTreeVO;

/**
 * <pre>
 * 文件上传接口
 * </pre>
 *
 * @author chenqi
 */
public interface FileUploadService {

	/**
	 * <pre>
	 * 图片上传方法
	 * </pre>
	 *
	 * @param request
	 * @param loanId 借款ID
	 * @param personId 借款人ID
	 * @param guaranteeId 担保人ID
	 * @param attachmentType 资料分类
	 * @param attachmentTypeSub 资料二级分类
	 * @param dirLevel 目录级别
	 * @param optModule 操作模块
	 * @param productId 产品ID
	 * @return
	 */
	int imageUpload(HttpServletRequest request, Long loanId, Long personId, Long guaranteeId, Integer attachmentType, Integer attachmentTypeSub, Integer dirLevel, Integer optModule, Integer productId);

	/**
	 * <pre>
	 * 根据借款ID获取附件树的List
	 * </pre>
	 *
	 * @param loanId
	 * @param productId
	 * @return
	 */
	List<AttachmentTreeVO> getAttachmentTree(Long loanId, Long productId);

	/**
	 * <pre>
	 * 根据AttachmentDetailID对图片做逻辑删除
	 * </pre>
	 *
	 * @param attachmentDetailId
	 * @param optModule
	 * @return
	 */
	void deleteAttachmentDetail(Long attachmentDetailId, Integer optModule);

	/**
	 * <pre>
	 * 根据AttachmentDetailID对图片做下载
	 * </pre>
	 *
	 * @param attachmentDetailId
	 * @param response
	 * @param optModule
	 * @return
	 */
	void downloadAttachmentDetail(Long attachmentDetailId, HttpServletResponse response, Integer optModule);

	/**
	 * <pre>
	 * 根据菜单名字，借款id,产品id进行批量删除操作
	 * </pre>
	 *
	 * @param nodeText
	 * @param loanId
	 * @param productId
	 * @param optModule
	 */
	void batchDeleteAttachmentDetail(String nodeText, Long loanId, Long productId, Integer optModule);

	/**
	 * <pre>
	 * 根据菜单名字，借款id,产品id进行批量下载操作
	 * </pre>
	 *
	 * @param nodeText
	 * @param loanId
	 * @param productId
	 * @param response
	 * @param optModule
	 */
	void batchDownloadAttachmentDetail(String nodeText, Long loanId, Long productId, Integer optModule,HttpServletResponse response);

}
