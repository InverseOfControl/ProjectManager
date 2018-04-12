package com.ezendai.credit2.master.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.ReasonManagerDao;
import com.ezendai.credit2.master.model.RefusedReason;
import com.ezendai.credit2.master.vo.RefusedReasonVO;

@Repository
public class ReasonManagerDaoImpl extends BaseDaoImpl<RefusedReason> implements
		ReasonManagerDao {
	
	@Override
	public Pager getReasonList(RefusedReasonVO vo) {
		Object reasonCount = null; 
		Integer count = null;
		Pager page = new Pager();
		try{
			reasonCount = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getReasonCount", vo);
			if(null != reasonCount && reasonCount != ""){
				count = Integer.parseInt(reasonCount.toString());
			}else{
				count = 0;
			}
		
			page = vo.getPager();
			List<RefusedReason> reasonList = null;
		
			reasonList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getReasonList", vo);
			BeanUtil.copyProperties(page, vo);
			if(null != reasonList){
				//分页前总数据
				page.setTotalCount(count);
				page.setResultList(reasonList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return page;
	}
	
	@Override
	public List<RefusedReason> findAllParentReasonList(){
		List<RefusedReason> list= new ArrayList<RefusedReason>();
		list = getSqlSession().selectList(getIbatisMapperNameSpace()+".findAllParentReasonList",null);
		return list;
	}
	
	public int addReason(RefusedReasonVO vo){
		int count = getSqlSession().insert(getIbatisMapperNameSpace()+".addReason",vo);
		return count;
	}
	
	@Override
	public RefusedReason getReasonById(Long id){
		RefusedReason reason = getSqlSession().selectOne(getIbatisMapperNameSpace()+".getReasonById",id);
		return reason;
	}
	
	@Override
	public int updateReason(RefusedReasonVO vo){
		return getSqlSession().update(getIbatisMapperNameSpace()+".updateReason",vo);
	}
	
	@Override
	public int disableReason(RefusedReasonVO reason){
		return getSqlSession().update(getIbatisMapperNameSpace()+".disableReason",reason);
	}

}
