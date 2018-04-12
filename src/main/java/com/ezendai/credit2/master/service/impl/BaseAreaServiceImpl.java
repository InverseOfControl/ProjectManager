package com.ezendai.credit2.master.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.dao.BaseAreaDao;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants.UserType;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysUserService;

@Service
public class BaseAreaServiceImpl implements BaseAreaService {
	@Autowired
	private BaseAreaDao dao;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ProductService productService;

	@Override
	public List<BaseArea> findListByVo(BaseAreaVO baseAreaVO) {
	
		return dao.findListByVo(baseAreaVO);
	}

	@Override
	public BaseArea get(Long id) {
		if (id == null)
			return null;

		return dao.get(id);
	}

	public BaseArea getUpperBaseAreaByIdentifier(String code, String identifier) {
		String realCode = code;
		if (code.length() > 18)
			realCode = code.substring(0, 18);

		String targetCode = getCodeByIdentifier(realCode, identifier);

		if (targetCode == null)
			return null;
		BaseAreaVO vo = new BaseAreaVO();
		vo.setCode(targetCode);
		List<BaseArea> candidates = dao.findListByVo(vo);

		if (candidates != null && candidates.size() > 0) {
			return candidates.get(0);
		}

		return null;
	}

	/**
	 * 只支持传入CREDIT2_SALESDEPARTMENT查询
	 * 
	 * @param code
	 * @param identifier
	 * @return
	 * @see com.ezendai.credit2.master.service.BaseAreaService#getUpperBaseAreaListByIdentifier(java.lang.String,
	 *      java.lang.String)
	 */
	public List<Long> getUpperBaseAreaIdListByIdentifier(String code, String identifier) {
		String realCode = code;
		if (code.length() > 18)
			realCode = code.substring(0, 18);

		if (identifier == null || identifier.equals(""))
			return null;
		if (identifier.equals(BizConstants.CREDIT2_SALESDEPARTMENT) && realCode.length() >= 13) {
			realCode = realCode.substring(0, 13);
		}

		BaseAreaVO vo = new BaseAreaVO();
		vo.setMatchCode(realCode);
		vo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
		List<BaseArea> candidates = dao.findListByVo(vo);
		List<Long> areaIdList = new ArrayList<Long>();
		if (CollectionUtil.isNotEmpty(candidates)) {
			for (BaseArea baseArea : candidates) {
				areaIdList.add(baseArea.getId());
			}
		}
		return areaIdList;
	}
	/**
	 * 只支持传入CREDIT2_SALESDEPARTMENT查询
	 * 
	 * @param code
	 * @param identifier
	 * @return
	 * @see com.ezendai.credit2.master.service.BaseAreaService#getUpperBaseAreaListByIdentifier(java.lang.String,
	 *      java.lang.String)
	 */
	public List<Long> getUpperBaseAreaIdListByIdentifierAndDeptType(String code, String identifier) {
		String realCode = code;
		if (code.length() > 18)
			realCode = code.substring(0, 18);

		if (identifier == null || identifier.equals(""))
			return null;
		if (identifier.equals(BizConstants.CREDIT2_SALESDEPARTMENT) && realCode.length() >= 13) {
			realCode = realCode.substring(0, 13);
		}

		BaseAreaVO vo = new BaseAreaVO();
		vo.setMatchCode(realCode);
		vo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
		vo.setDeptType(1);
		List<BaseArea> candidates = dao.findListByVo(vo);
		List<Long> areaIdList = new ArrayList<Long>();
		if (CollectionUtil.isNotEmpty(candidates)) {
			for (BaseArea baseArea : candidates) {
				areaIdList.add(baseArea.getId());
			}
		}
		return areaIdList;
	}
	/**
	 * 只支持传入CREDIT2_SALESDEPARTMENT查询
	 * 
	 * @param code
	 * @param identifier
	 * @return
	 * @see com.ezendai.credit2.master.service.BaseAreaService#getUpperBaseAreaListByIdentifier(java.lang.String,
	 *      java.lang.String)
	 */
	public List<BaseArea> getUpperBaseAreaListByIdentifier(String code, String identifier) {
		String realCode = code;
		if (code.length() > 18)
			realCode = code.substring(0, 18);

		if (identifier == null || identifier.equals(""))
			return null;
		if (identifier.equals(BizConstants.CREDIT2_SALESDEPARTMENT) && realCode.length() >= 13) {
			realCode = realCode.substring(0, 13);
		}
	
		BaseAreaVO vo = new BaseAreaVO();
		vo.setMatchCode(realCode);
		vo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
		List<BaseArea> candidates = dao.findListByVo(vo);
		if (CollectionUtil.isNotEmpty(candidates)) {
			if (code.equals("01")) {
				BaseArea baseArea = new BaseArea();
				baseArea.setId(null);
				baseArea.setName("全部");
				candidates.add(0, baseArea);
				return candidates;
			} else {
				return candidates;
			}
		} else {
			return new ArrayList<BaseArea>();
		}
	}

	public String getCodeByIdentifier(String realCode, String identifier) {
		if (identifier == null || identifier.equals(""))
			return null;
		if (identifier.equals(BizConstants.CREDIT2_SALESTEAM) && realCode.length() >= 18)
			return realCode.substring(0, 18);
		if (identifier.equals(BizConstants.CREDIT2_SALESDEPARTMENT) && realCode.length() >= 13)
			return realCode.substring(0, 13);
		if (identifier.equals(BizConstants.CREDIT2_CITY) && realCode.length() >= 8)
			return realCode.substring(0, 8);
		if (identifier.equals(BizConstants.CREDIT2_AREA) && realCode.length() >= 4)
			return realCode.substring(0, 4);
		if (identifier.equals(BizConstants.CREDIT2_COMPANY) && realCode.length() >= 2)
			return realCode.substring(0, 2);

		return null;
	}

	@Override
	public List<BaseArea> getBaseAreasByIdentifier(String identifier) {
		BaseAreaVO vo = new BaseAreaVO();
		vo.setIdentifier(identifier);
		List<BaseArea> baseAreas = dao.findListByVo(vo);
		if (baseAreas == null)
			return new ArrayList<BaseArea>();
		return baseAreas;
	}

	@Override
	public List<BaseArea> getAllSalesDepts() {
		return getBaseAreasByIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
	}

	@Override
	public List<BaseArea> getAllCitys() {
		return getBaseAreasByIdentifier(BizConstants.CREDIT2_CITY);
	}
	@Override
	public List<BaseArea> getSalesCityFrAreaId(String areaId) {
			BaseAreaVO vo=new BaseAreaVO();
			vo.setAreaId(areaId);
		return dao.findListByVo(vo);
	}
	@Override
	public List<BaseArea> getSalesDeptFrCityId(String cityId) {
		BaseAreaVO vo = new BaseAreaVO();
		vo.setCityId(cityId);
		Long userId = ApplicationContext.getUser().getId();
		int userType = ApplicationContext.getUser().getUserType();
		SysUser sysUser = sysUserService.get(userId);
		if (sysUser != null && sysUser.getDataPermission() == null)
			return null;
		// 判断当该用户数据权限长度等于13位。说明是客服的权限，需要做数据权限的过滤
		if (sysUser.getDataPermission().length() == 13) {
			vo.setCode(sysUser.getDataPermission());
		//销售经理、销售主管显示所在网店的信息
		} else if ((sysUser.getDataPermission().length() >= 25 && userType == UserType.CUSTOMER_SERVICE.getValue()) || (EnumConstants.UserType.BUSINESS_DIRECTOR.getValue().equals(sysUser.getUserType()) && sysUser.getDataPermission().length() >= 18)) { // 用户是业务主任且他的用户权限code应该>=18
			vo.setCode(sysUser.getDataPermission().substring(0, 13));// 设置为查询网点的权限
		}
		List<BaseArea> baseAreas = new ArrayList<BaseArea>();
		baseAreas = dao.findListByVo(vo);
		// 判断当前登入用户是否是门店经理和客服专员,如果是则加载当前城市下所有的营业网点
		if (userType == UserType.STORE_MANAGER.getValue() || userType == UserType.CUSTOMER_SERVICE.getValue()) {
			List<BaseArea> curSalesDept = sysUserService.getCurSalesDept();
			if (curSalesDept != null) {
				BaseArea baseArea = new BaseArea();
				baseArea.setId(null);
				baseArea.setName("全部");
				baseAreas.add(0, baseArea);
				return baseAreas;
			}
		}
		if (baseAreas == null)
			return new ArrayList<BaseArea>();
		return baseAreas;
	}

	/**
	 * 查询营业网点数据 带分页功能
	 * 
	 * @param baseAreaVO
	 * @return
	 */
	@Override
	public Pager findWithPGExtension(BaseAreaVO baseAreaVO) {
		UserSession user = ApplicationContext.getUser();
		if(!user.getUserType().equals(EnumConstants.UserType.SYSTEM_ADMIN.getValue())){
			// 车贷或小企贷
			List<Product> products = productService.findProductTypeByUserId(user.getId());
			for (Product product : products) {
				if(product.getProductType() == 2){
					baseAreaVO.setDeptType(EnumConstants.ProductType.CAR_LOAN.getValue());
				}else{
					baseAreaVO.setDeptType(Integer.valueOf(EnumConstants.ProductType.SE_LOAN.getValue()));
				}
			}
			//门店人员操作当前门店的网点信息
			if(!user.getUserType().equals(EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue()) ||!user.getUserType().equals(EnumConstants.UserType.STORE_MANAGER.getValue()) 
					|| !user.getUserType().equals(EnumConstants.UserType.CUSTOMER_SERVICE.getValue())){
					SysUser sysUser = sysUserService.get(user.getId());
					if(sysUser != null){
						baseAreaVO.setMatchCode(sysUser.getDataPermission());
					}
			}
			//当前登入的是门店的人不查询公司信息
			baseAreaVO.setIsDeptPerson(0);
		}
		return dao.findWithPGExtension(baseAreaVO);
	}
	@Override
	public List<BaseArea> getStudentLoanSalesDepts() {
		return dao.getStudentLoanSalesDepts();
	}


	@Override
	public List<BaseArea> getSeloanDept() {
		List<BaseArea> seloanSalesDepts = dao.getSeloanDept();
		BaseArea baseArea = new BaseArea();
		baseArea.setId(null);
		baseArea.setName("全部");
		seloanSalesDepts.add(0, baseArea);
		return seloanSalesDepts;
	}
	
	@Override
	public List<BaseArea> findWithList(BaseAreaVO baseAreaVO) {
		return dao.findWithList(baseAreaVO);
	}
	@Override
	public void updateBaseArea(BaseAreaVO baseAreaVO) {
		 dao.update(baseAreaVO);
		
	}
}
