package com.ezendai.credit2.system.assembler;

import com.ezendai.credit2.system.model.Organ;
import com.ezendai.credit2.system.vo.OrganDetailVO;
import com.ezendai.credit2.system.vo.OrganVO;

/**
 * 
 * <pre>
 * 机构信息	VO/Model转换
 * </pre>
 *
 * @version $Id: SysUserAssembler.java, v 0.1 2014年8月1日 下午2:49:11 zhangshihai Exp $
 */
public class OrganAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 * @return
	 */
	public static OrganVO transferModel2VO (Organ organ) {
		if (organ == null) {
			return null;
		}
		
		OrganVO organVO = new OrganVO();
		organVO.setAddress(organ.getAddress());
		organVO.setCode(organ.getCode());
		organVO.setName(organ.getName());
		organVO.setPostCode(organ.getPostCode());
		organVO.setLegalRepresentative(organ.getLegalRepresentative());
		organVO.setLegalRepresentativeId(organ.getLegalRepresentativeId());
		organVO.setLegalTel(organ.getLegalTel());
		organVO.setSignedDate(organ.getSignedDate());
		organVO.setMargin(organ.getMargin());
		organVO.setMemo(organ.getMemo());
		organVO.setStatus(organ.getStatus());
		organVO.setOrgLevel(organ.getOrgLevel());
		return organVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转为Model
	 * </pre>
	 * @return
	 */
	public static Organ transferVO2Model (OrganVO organVO) {
		if (organVO == null) {
			return null;
		}
		
		Organ organ = new Organ();
		organ.setAddress(organVO.getAddress());
		organ.setCode(organVO.getCode());
		organ.setName(organVO.getName());
		organ.setPostCode(organVO.getPostCode());
		organ.setLegalRepresentative(organVO.getLegalRepresentative());
		organ.setLegalRepresentativeId(organVO.getLegalRepresentativeId());
		organ.setLegalTel(organVO.getLegalTel());
		organ.setSignedDate(organVO.getSignedDate());
		organ.setMargin(organVO.getMargin());
		organ.setMemo(organVO.getMemo());
		organ.setStatus(organVO.getStatus());
		organ.setOrgLevel(organVO.getOrgLevel());
		return organ;
	}
	
	/**
	 * 
	 * <pre>
	 * OrganDetailVO->Organ
	 * </pre>
	 *
	 * @return
	 */
	public static Organ convertOrganDetailVO2Organ(OrganDetailVO organDetailVO) {
		if (organDetailVO == null) {
			return null;
		}
		Organ organ = new Organ();
		organ.setAddress(organDetailVO.getAddress());
		organ.setTel(organDetailVO.getTel());
		organ.setCode(organDetailVO.getCode());
		organ.setName(organDetailVO.getName());
		organ.setPostCode(organDetailVO.getPostCode());
		organ.setLegalRepresentative(organDetailVO.getLegalRepresentative());
		organ.setLegalRepresentativeId(organDetailVO.getLegalRepresentativeId());
		organ.setLegalTel(organDetailVO.getLegalTel());
		organ.setSignedDate(organDetailVO.getSignedDate());
		organ.setMargin(organDetailVO.getMargin());
		organ.setMemo(organDetailVO.getMemo());
		organ.setStatus(organDetailVO.getStatus());
		organ.setOrgLevel(organDetailVO.getOrgLevel());
		return organ;
	}
	
	/**
	 * 
	 * <pre>
	 * OrganDetailVO->OrganVO
	 * </pre>
	 *
	 * @return
	 */
	public static OrganVO convertOrganDetailVO2OrganVO(OrganDetailVO organDetailVO) {
		if (organDetailVO == null) {
			return null;
		}
		OrganVO organVO = new OrganVO();
		organVO.setAddress(organDetailVO.getAddress());
		organVO.setTel(organDetailVO.getTel());
		organVO.setCode(organDetailVO.getCode());
		organVO.setName(organDetailVO.getName());
		organVO.setPostCode(organDetailVO.getPostCode());
		organVO.setLegalRepresentative(organDetailVO.getLegalRepresentative());
		organVO.setLegalRepresentativeId(organDetailVO.getLegalRepresentativeId());
		organVO.setLegalTel(organDetailVO.getLegalTel());
		organVO.setSignedDate(organDetailVO.getSignedDate());
		organVO.setMargin(organDetailVO.getMargin());
		organVO.setMemo(organDetailVO.getMemo());
		organVO.setStatus(organDetailVO.getStatus());
		organVO.setOrgLevel(organDetailVO.getOrgLevel());
		return organVO;
	}
	
	/**
	 * 
	 * <pre>
	 * OrganDetailVO->Organ
	 * </pre>
	 *
	 * @return
	 */
	public static void convertOrgan2OrganDetailVO(OrganDetailVO organDetailVO,Organ organ) {
		if (organ == null) {
			return ;
		}
		organDetailVO.setAddress(organ.getAddress());
		organDetailVO.setTel(organ.getTel());
		organDetailVO.setCode(organ.getCode());
		organDetailVO.setName(organ.getName());
		organDetailVO.setPostCode(organ.getPostCode());
		organDetailVO.setLegalRepresentative(organ.getLegalRepresentative());
		organDetailVO.setLegalRepresentativeId(organ.getLegalRepresentativeId());
		organDetailVO.setLegalTel(organ.getLegalTel());
		organDetailVO.setSignedDate(organ.getSignedDate());
		organDetailVO.setMargin(organ.getMargin());
		organDetailVO.setMemo(organ.getMemo());
		organDetailVO.setStatus(organ.getStatus());
		organDetailVO.setOrgLevel(organ.getOrgLevel());
	}
	
	
}
