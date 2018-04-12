package com.ezendai.credit2.master.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.WorkPlaceInfoDao;
import com.ezendai.credit2.master.model.WorkPlaceInfo;
import com.ezendai.credit2.master.service.WorkPlaceInfoService;
import com.ezendai.credit2.master.vo.WorkPlaceInfoVO;

/**
 * <pre>
 * 办公地信息管理
 * </pre>
 *
 * @author majl
 * @version $Id: WorkPlaceInfoService.java, v 0.1 2014-6-26 下午1:31:02 majl Exp $
 */
@Service
public class WorkPlaceInfoServiceImpl implements WorkPlaceInfoService {
	@Autowired
	private WorkPlaceInfoDao dao;
	
	@Override
	public Pager findListByVo(WorkPlaceInfoVO workPlaceInfoVO) {
	return  dao.findWithPg(workPlaceInfoVO);
	}
	/**
	 * <pre>
	 * 新增办公地信息
	 * </pre>
	 *
	 * @param vo
	 * @return 
	 */
    public void addWorkPlaceInfo(WorkPlaceInfoVO vo) {
    	int existsWorkPlaceInfo = existsWorkPlaceInfo(vo); 
     	if(existsWorkPlaceInfo > 0 ){
     		throw new BusinessException();
     	}
    	WorkPlaceInfo workPlaceInfo = new WorkPlaceInfo();
        assembleVo(workPlaceInfo, vo);
        dao.insert(workPlaceInfo);
    }
    
    /**
     * <pre>
     * 修改办公地信息
     * </pre>
     *
     * @param vo
     */
    public void editWorkPlaceInfo(WorkPlaceInfoVO vo) {
    	int existsWorkPlaceInfo = existsWorkPlaceInfo(vo); 
     	if(existsWorkPlaceInfo > 0 ){
     		throw new BusinessException();
     	}
        dao.update(vo);
    }

    /**
     * <pre>
     * 删除办公地信息
     * </pre>
     *
     * @param id
     */
    public void deleteWorkPlaceInfo(Long id) {
        dao.deleteById(id);
    }

//    /**
//     * <pre>
//     * 分页查询全部办公地信息
//     * </pre>
//     *
//     * @return
//     */
//    public List<WorkPlaceInfo> loadAllWorkPlaceInfo() {
//        return dao.queryAllWorkPlaceInfo();
//    }
//    
    /**
     * <pre>
     * 单条查询 by Id
     * </pre>
     *
     * @param id
     * @return
     */
    public WorkPlaceInfo loadOneWorkPlaceInfoById(Long id) {
        return dao.get(id);
    }
    
    /**
     * <pre>
     * 单条查询 by Site
     * </pre>
     *
     * @param site
     * @return
     */
    public WorkPlaceInfo loadOneWorkPlaceInfoBySite(String site) {
    	WorkPlaceInfoVO vo = new WorkPlaceInfoVO();
    	vo.setSite(site);
        return dao.get(vo);
    }
    
    /**
     * <pre>
     * 画面参数转换对象
     * </pre>
     *
     * @param workPlaceInfo
     * @param vo
     */
    private void assembleVo(WorkPlaceInfo workPlaceInfo, WorkPlaceInfoVO vo) {
    	workPlaceInfo.setSite(vo.getSite());
    	workPlaceInfo.setZoneCode(vo.getZoneCode());
    	workPlaceInfo.setCityNo(vo.getCityNo());
    	workPlaceInfo.setTel(vo.getTel());
    }

    @Override
    public int existsWorkPlaceInfo(WorkPlaceInfoVO vo) {
    	
    	return dao.existsWorkPlaceInfo(vo);
    }
    
}
