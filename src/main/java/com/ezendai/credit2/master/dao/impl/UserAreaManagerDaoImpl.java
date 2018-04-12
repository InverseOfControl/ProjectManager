package com.ezendai.credit2.master.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.SysParameterManagerDao;
import com.ezendai.credit2.master.dao.UserAreaManagerDao;
import com.ezendai.credit2.master.model.BankManager;
import com.ezendai.credit2.master.model.SysParameterManager;
import com.ezendai.credit2.master.model.UserAreaManager;
import com.ezendai.credit2.master.vo.SysParameterManagerVO;
import com.ezendai.credit2.master.vo.UserAreaManagerVO;

/**
 * 
 * @Description: 系统参数接口实现
 * @author 张宜超
 * @date 2016年2月1日
 */
@Repository
public class UserAreaManagerDaoImpl extends BaseDaoImpl<UserAreaManager> implements UserAreaManagerDao {

	/**
	 * 获取数据列表
	 */
	//@Override
//	public Pager getSysParameterList(SysParameterManagerVO vo) {
//		Object bankCount = null; 
//		Integer count = null;
//		Pager page = new Pager();
//		try{
//			bankCount = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getSysParameterCount", vo);
//			if(null != bankCount && bankCount != ""){
//				count = Integer.parseInt(bankCount.toString());
//			}else{
//				count = 0;
//			}
//		
//			page = vo.getPager();
//			List<BankManager> bankList = null;
//		
//			bankList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getSysParameterList", vo);
//			BeanUtil.copyProperties(page, vo);
//			if(null != bankList){
//				//分页前总数据
//				page.setTotalCount(count);
//				page.setResultList(bankList);
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return page;
//	}

	@Override
	public Pager getUserAreaManagerList(UserAreaManagerVO vo) {
		Object bankCount = null; 
		Integer count = null;
		Pager page = new Pager();
		try{
			bankCount = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getUserAreaManagerCount", vo);
			if(null != bankCount && bankCount != ""){
				count = Integer.parseInt(bankCount.toString());
			}else{
				count = 0;
			}
		
			page = vo.getPager();
			List<UserAreaManager> bankList = null;
		
			bankList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getUserAreaManagerList", vo);
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

	@Override
	public List<UserAreaManagerVO> findUserAreaManagerByCondition(
			UserAreaManagerVO vo) {
		List<UserAreaManagerVO> bankList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findUserAreaManagerByCondition", vo);
		return bankList;
	}

	
}
