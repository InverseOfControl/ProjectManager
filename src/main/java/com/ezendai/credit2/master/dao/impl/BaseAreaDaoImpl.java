package com.ezendai.credit2.master.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.BaseAreaDao;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.vo.BaseAreaVO;

/**
 * 基础数据Dao实现类
 * Author: kimi
 * Date: 14-6-24
 * Time: 上午11:34
 */
@Repository
public class BaseAreaDaoImpl extends BaseDaoImpl<BaseArea> implements BaseAreaDao {
    @Override
    public String selectMaxCode(BaseAreaVO vo) {
        return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".maxCode",vo);
    }

    @Override
    public List<BaseArea> queryAllBaseArea(String identifier) {
    	return this.getSqlSession().selectList(getIbatisMapperNameSpace() + ".queryAllBaseArea", identifier);
    }

    @Override
    public List<BaseArea> queryCitiesOfOneArea(Long id) {
        return this.getSqlSession().selectList(getIbatisMapperNameSpace() + ".queryCitiesOfOneArea",id);
    }

    @Override
    public int existsCompany(BaseAreaVO vo) {
        return (Integer)(this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".existsCompany",vo));
    }

    @Override
    public int existsArea(BaseAreaVO vo) {
        return (Integer)(this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".existsArea",vo));
    }

    @Override
    public int existsCity(BaseAreaVO vo) {
        return (Integer)(this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".existsCity",vo));
    }

    @Override
    public int existsSalesDepartment(BaseAreaVO vo) {
        return (Integer)(this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".existsSalesDepartment",vo));
    }

    @Override
    public int existsSalesTeam(BaseAreaVO vo) {
        return (Integer)(this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".existsSalesTeam",vo));
    }

    @Override
    public BaseArea maxCodeAreaItem(Long companyId) {
        return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".maxCodeAreaItem",companyId);
    }

    @Override
    public BaseArea maxCodeCityItem(String areaId) {
        return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".maxCodeCityItem",areaId);
    }

    @Override
    public BaseArea maxCodeSalesDepartmentItem(String cityId) {
        return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".maxCodeSalesDepartmentItem",cityId);
    }

    @Override
    public BaseArea maxCodeSalesTeamItem(String salesDeptId) {
        return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".maxCodeSalesTeamItem",salesDeptId);
    }

    @Override
    public BaseArea querySingleCityByName(String name) {
        return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".querySingleCityByName",name);
    }
    
    @Override
    public int countSalesDepartment(BaseAreaVO vo) {
        return (Integer)(this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".countSalesDepartment", vo));
    }

    @Override
    public String queryCurrentMaxDeptNoByDeptType(int deptType) {
        return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".queryCurrentMaxDeptNoByDeptType",deptType);
    }
    /***
     * 根据用户ID查找营业网点 
     * @param baseAreaVo
     * @return
     * @see com.ezendai.credit2.master.dao.BaseAreaDao#findBaseAreaById(com.ezendai.credit2.master.vo.BaseAreaVO)
     */
    @Override
    public List<BaseArea> findBaseAreaById(BaseAreaVO baseAreaVo){
       return  getSqlSession().selectList(getIbatisMapperNameSpace()+".findById", baseAreaVo);
    }
    
    /**
	 * 查询营业网点数据 带分页功能
	 * @author Ivan
	 * @param baseAreaVO
	 * @return
	 */
    @Override
	public Pager findWithPGExtension(BaseAreaVO baseAreaVO) {
		Pager pager = baseAreaVO.getPager();
		//查询总行数
		int totalCount = (Integer)getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", baseAreaVO);
		pager.setTotalCount(totalCount);
		pager.calStart();
		List<BaseArea> resultList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findWithPG",baseAreaVO);
		pager.setResultList(resultList);
		return pager;
	}
    @Override
    public List<BaseArea> getStudentLoanSalesDepts() {
    	 return  getSqlSession().selectList(getIbatisMapperNameSpace()+".getStudentLoanSalesDepts");
    }

	@Override
	public List<BaseArea> getSeloanDept() {
		 return  getSqlSession().selectList(getIbatisMapperNameSpace()+".getSeloanDept");
	}

	@Override
	public List<BaseArea> findWithList(BaseAreaVO baseAreaVO) {
		return  getSqlSession().selectList(getIbatisMapperNameSpace()+".findWithList",baseAreaVO);
	}

	@Override
	public List<BaseArea> getDeptsByUserIdAndDeptsTypes(BaseAreaVO baseAreaVO) {
		return getSqlSession().selectList(getIbatisMapperNameSpace()+".getDeptsByUserIdAndDeptsTypes",baseAreaVO);
	}
}