package com.ezendai.credit2.master.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.SysEnumerateManagerDao;
import com.ezendai.credit2.master.model.SysEnumerateManager;
import com.ezendai.credit2.master.vo.SysEnumerateManagerVO;

/**
 * 
 * @Description: 枚举接口信息
 * @author 张宜超
 * @date 2016年2月2日
 */
@Repository
public class SysEnumerateManagerDaoImpl extends BaseDaoImpl<SysEnumerateManager> implements SysEnumerateManagerDao {

	/**
	 * 获取枚举列表
	 */
	@Override
	public Pager getSysEnumerateList(SysEnumerateManagerVO vo) {
		Object SysEnumerateCount = null; 
		Integer count = null;
		Pager page = new Pager();
		try{
			SysEnumerateCount = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getSysEnumerateCount", vo);
			if(null != SysEnumerateCount && SysEnumerateCount != ""){
				count = Integer.parseInt(SysEnumerateCount.toString());
			}else{
				count = 0;
			}
		
			page = vo.getPager();
			List<SysEnumerateManager> SysEnumerateList = null;
		
			SysEnumerateList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getSysEnumerateList", vo);
			BeanUtil.copyProperties(page, vo);
			if(null != SysEnumerateList){
				//分页前总数据
				page.setTotalCount(count);
				page.setResultList(SysEnumerateList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return page;
	}

	/**
	 * 获取某个枚举信息
	 */
	@Override
	public SysEnumerateManager getSysEnumerate(Long id) {
		
		return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".getSysEnumerate",id);
	}

	/**
	 * 添加枚举信息
	 */
	@Override
	public void addSysEnumerate(SysEnumerateManagerVO sysEnumerate) {
		
		this.getSqlSession().insert(getIbatisMapperNameSpace()+".addSysEnumerate",sysEnumerate);
	}

	/**
	 *修改枚举信息 
	 */
	@Override
	public int updateSysEnumerate(SysEnumerateManagerVO vo) {
		return this.getSqlSession().update(getIbatisMapperNameSpace()+".updateSysEnumerate", vo);
	}

	/**
	 * 多条件查询枚举信息 用作校验
	 */
	@Override
	public List<SysEnumerateManager> getSysEnumerateByConditions(SysEnumerateManagerVO vo) {
		List<SysEnumerateManager> list = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getSysEnumerateByConditions", vo);
		/*int SysEnumerateCount = 0;
		if(null != list && list.size() > 0){
			SysEnumerateCount = list.size();
		}*/
		
		return list;
	}

	@Override
	public int deleteSysEnumerate(Long id) {
		
		int i = 0;
		try{
			i = getSqlSession().delete(getIbatisMapperNameSpace() + ".deleteSysEnumerate" , id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return i;
	}


	
}
