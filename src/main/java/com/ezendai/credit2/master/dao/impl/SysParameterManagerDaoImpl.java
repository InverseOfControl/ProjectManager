package com.ezendai.credit2.master.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.SysParameterManagerDao;
import com.ezendai.credit2.master.model.BankManager;
import com.ezendai.credit2.master.model.SysParameterManager;
import com.ezendai.credit2.master.vo.SysParameterManagerVO;

/**
 * 
 * @Description: 系统参数接口实现
 * @author 张宜超
 * @date 2016年2月1日
 */
@Repository
public class SysParameterManagerDaoImpl extends BaseDaoImpl<SysParameterManager> implements SysParameterManagerDao {

	/**
	 * 获取数据列表
	 */
	@Override
	public Pager getSysParameterList(SysParameterManagerVO vo) {
		Object bankCount = null; 
		Integer count = null;
		Pager page = new Pager();
		try{
			bankCount = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getSysParameterCount", vo);
			if(null != bankCount && bankCount != ""){
				count = Integer.parseInt(bankCount.toString());
			}else{
				count = 0;
			}
		
			page = vo.getPager();
			List<BankManager> bankList = null;
		
			bankList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getSysParameterList", vo);
			BeanUtil.copyProperties(page, vo);
			if(null != bankList){
				//分页前总数据
				page.setTotalCount(count);
				page.setResultList(bankList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 获取一条数据
	 */
	@Override
	public SysParameterManager getSysParameter(Long id) {
		
		return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".getSysParameter",id);
	}

	/**
	 * 多条件获取数据
	 */
	@Override
	public List<SysParameterManager> getSysParameterByConditions(
			SysParameterManagerVO vo) {
		List<SysParameterManager> list = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getSysParameterByConditions", vo);
		return list;
	}

	/**
	 * 获取数据个数
	 */
	@Override
	public int getSysParameterCount(SysParameterManagerVO vo) {
		
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getSysParameterCount", vo);
	}

	/**
	 * 添加一条数据
	 */
	@Override
	public void addSysParameter(SysParameterManagerVO vo) {
		
		this.getSqlSession().insert(getIbatisMapperNameSpace()+".addSysParameter",vo);
	}

	/**
	 * 更新一条数据
	 */
	@Override
	public int updateSysParameter(SysParameterManagerVO vo) {
		
		return this.getSqlSession().update(getIbatisMapperNameSpace()+".updateSysParameter", vo);
	}

	@Override
	public int deleteSysParameter(Long id) {
		int i = 0;
		try{
			i = getSqlSession().delete(getIbatisMapperNameSpace() + ".deleteSysParameter" , id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return i;
	}



	
}
