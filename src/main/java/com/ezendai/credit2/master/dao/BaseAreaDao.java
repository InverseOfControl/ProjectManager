package com.ezendai.credit2.master.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.vo.BaseAreaVO;

/**
 * 基础数据Dao接口
 * Author: kimi
 * Date: 14-6-24
 * Time: 上午9:53
 */
public interface BaseAreaDao extends BaseDao<BaseArea> {
	
    String selectMaxCode(BaseAreaVO vo);
    
    List<BaseArea> queryAllBaseArea(String identifier);

    List<BaseArea> queryCitiesOfOneArea(Long id);

    int existsCompany(BaseAreaVO vo);

    int existsArea(BaseAreaVO vo);

    int existsCity(BaseAreaVO vo);

    int existsSalesDepartment(BaseAreaVO vo);

    int existsSalesTeam(BaseAreaVO vo);

    BaseArea maxCodeAreaItem(Long companyId);

    BaseArea maxCodeCityItem(String areaId);

    BaseArea maxCodeSalesDepartmentItem(String cityId);

    BaseArea maxCodeSalesTeamItem(String salesDeptId);

    BaseArea querySingleCityByName(String name);
    
    int countSalesDepartment(BaseAreaVO vo);

    String queryCurrentMaxDeptNoByDeptType(int deptType);

    List<BaseArea> findBaseAreaById(BaseAreaVO baseAreaVo);
    
    /**
	 * 查询营业网点数据 带分页功能
	 * @param baseAreaVO
	 * @return
	 */
	public Pager findWithPGExtension(BaseAreaVO baseAreaVO);
	
	/**
	 * 
	 * <pre>
	 * 获取可操作助学贷的营业网点信息
	 * </pre>
	 *
	 * @return
	 */
	List<BaseArea> getStudentLoanSalesDepts();

	List<BaseArea> getSeloanDept();
	
	List<BaseArea> findWithList(BaseAreaVO baseAreaVO);
	
	
	/**
	 * 获取该用户所拥有的所有营业网点（目前只限刘娜（上海营业部经理）操作沈阳以及青岛的营业网点权限））
	 * @param baseAreaVO
	 * @return
	 */
	List<BaseArea> getDeptsByUserIdAndDeptsTypes(BaseAreaVO baseAreaVO);


}