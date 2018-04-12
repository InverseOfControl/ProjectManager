package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.vo.BaseAreaVO;

public interface BaseAreaService {

	/**
	 * 
	 * <pre>
	 * 根据VO查询所有需要的区域信息
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	List<BaseArea> findListByVo(BaseAreaVO baseAreaVO);

	/**
	 * 
	 * <pre>
	 * 根据ID获取BaseArea
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	BaseArea get(Long id);

	/**
	 * 
	 * <pre>
	 * 获取当前BaseArea的Code(可能包含七位员工编号)所在的指定类型（identifier）的上级区域
	 * </pre>
	 *
	 * @param id
	 * @param identifier
	 * @return
	 */
	BaseArea getUpperBaseAreaByIdentifier(String code, String identifier);
	
	/**
	 * 
	 * <pre>
	 * 获取当前用户给出的data permission code和指导类型的(identifier)的base area Id list
	 * </pre>
	 *
	 * @param id
	 * @param identifier
	 * @return
	 */
	List<Long> getUpperBaseAreaIdListByIdentifier(String code, String identifier);
	/**
	 * 
	 * <pre>
	 * 获取当前用户给出的data permission code和指导类型的(identifier),DeptType的base area Id list
	 * </pre>
	 *
	 * @param id
	 * @param identifier
	 * @return
	 */
	List<Long> getUpperBaseAreaIdListByIdentifierAndDeptType(String code, String identifier);
	/**
	 * 
	 * <pre>
	 * 获取当前用户给出的data permission code和指导类型的(identifier)的base area  list
	 * </pre>
	 *
	 * @param id
	 * @param identifier
	 * @return
	 */
	List<BaseArea> getUpperBaseAreaListByIdentifier(String code, String identifier);

	/**
	 * 
	 * <pre>
	 * 获取所有的营业网点信息
	 * </pre>
	 *
	 * @return
	 */
	List<BaseArea> getAllSalesDepts();
	
	/**
	 * 
	 * <pre>
	 * 获取所有的城市
	 * </pre>
	 *
	 * @return
	 */
	List<BaseArea> getAllCitys();

	/**
	 * 
	 * <pre>
	 * 获取所有为同一类型（identifier）的所有BaseArea
	 * </pre>
	 *
	 * @param identifier
	 * @return
	 */
	List<BaseArea> getBaseAreasByIdentifier(String identifier);
	
	/**
	 * 
	 * <pre>
	 * 获取属于（cityId）的所有营业部
	 * </pre>
	 *
	 * @param cityId
	 * @return
	 */
	List<BaseArea> getSalesDeptFrCityId(String cityId);
	/**
	 * 
	 * <pre>
	 * 获取属于（cityId）的所有营业部
	 * </pre>
	 *
	 * @param cityId
	 * @return
	 */
	List<BaseArea> getSalesCityFrAreaId(String areaId);
	
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
	
	void updateBaseArea(BaseAreaVO baseAreaVO);
}
