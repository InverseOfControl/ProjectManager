package com.ezendai.credit2.master.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.BankManagerDao;
import com.ezendai.credit2.master.model.BankManager;
import com.ezendai.credit2.master.vo.BankManagerVO;

/**
 * 
 * @Description: 银行接口信息
 * @author 张宜超
 * @date 2016年1月25日
 */
@Repository
public class BankManagerDaoImpl extends BaseDaoImpl<BankManager> implements BankManagerDao {

	/**
	 * 获取银行列表
	 */
	@Override
	public Pager getBankList(BankManagerVO vo) {
		Object bankCount = null; 
		Integer count = null;
		Pager page = new Pager();
		try{
			bankCount = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getBankCount", vo);
			if(null != bankCount && bankCount != ""){
				count = Integer.parseInt(bankCount.toString());
			}else{
				count = 0;
			}
		
			page = vo.getPager();
			List<BankManager> bankList = null;
		
			bankList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getBankList", vo);
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
	 * 获取某个银行信息
	 */
	@Override
	public BankManager getBank(Long id) {
		
		return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".getBank",id);
	}

	/**
	 * 添加银行信息
	 */
	@Override
	public void addBank(BankManagerVO bank) {
		
		this.getSqlSession().insert(getIbatisMapperNameSpace()+".addBank",bank);
	}

	/**
	 *修改银行信息 
	 */
	@Override
	public int updateBank(BankManagerVO vo) {
		//System.out.println("vo.getBankType == " + vo.getBankType());
		return this.getSqlSession().update(getIbatisMapperNameSpace()+".updateBank", vo);
	}

	/**
	 * 多条件查询银行信息 用作校验
	 */
	@Override
	public List<BankManager> getBankByConditions(BankManagerVO vo) {
		List<BankManager> list = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getBankByConditions", vo);
		/*int bankCount = 0;
		if(null != list && list.size() > 0){
			bankCount = list.size();
		}*/
		
		return list;
	}

	/**
	 * 删除银行信息
	 */
	@Override
	public int deleteBank(Long id) {
		int i = 0;
		try{
			i = getSqlSession().delete(getIbatisMapperNameSpace() + ".deleteBank" , id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return i;
	}


	
}
