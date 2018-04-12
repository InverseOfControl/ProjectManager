package com.ezendai.credit2.apply.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.dao.SysProductUserDao;
import com.ezendai.credit2.apply.model.SysProductUser;
import com.ezendai.credit2.apply.vo.SysProductUserVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

/***
 * 用户产品
 * <pre>
 * 
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: SysProductUserDaoImpl.java, v 0.1 2014年6月23日 下午4:50:28 HQ-AT6 Exp $
 */
@Repository
public class SysProductUserDaoImpl extends BaseDaoImpl<SysProductUser> implements SysProductUserDao {

	@Override
	public List<String> findLoanTypeByUserId(Long userId) {
		return this.getSqlSession().selectList(getIbatisMapperNameSpace() + ".selectByUserId",
			userId);
	}

	@Override
	public List<Long> findProductIdByUserId(Long userId) {
		return this.getSqlSession().selectList(getIbatisMapperNameSpace() + ".selectProductIdByUserId", userId);
	}
	
	/**
	 * 修改员工与权限组对照列表
	 * @param sysGroupUserVO
	 * @return
	 */
	@Override
	@Transactional
	public void modifyProductUserMap(SysProductUserVO sysProductUserVO) {
		//执行批量解除绑定关系
		if (sysProductUserVO.getRemoveProductIdList() != null && sysProductUserVO.getRemoveProductIdList().size() > 0) {
			getSqlSession().insert(getIbatisMapperNameSpace() + ".deleteByProductIdList", sysProductUserVO);
		}
		List<Long> addProductIdList = sysProductUserVO.getAddProductIdList();
		if (addProductIdList != null && addProductIdList.size() > 0) {
			for (int i=0;i<addProductIdList.size();i++) {
				Long productId = addProductIdList.get(i);
				sysProductUserVO.setProductId(productId);
				getSqlSession().insert(getIbatisMapperNameSpace() + ".insert", sysProductUserVO);
			}
		}
	}
	
}