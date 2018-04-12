package com.ezendai.credit2.master.service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.WorkPlaceInfo;
import com.ezendai.credit2.master.vo.WorkPlaceInfoVO;

/**
 * <pre>
 * 办公地信息管理
 * </pre>
 *
 * @author majl
 * @version $Id: WorkPlaceInfoService.java, v 0.1 2014-6-26 下午1:31:02 majl Exp $
 */
public interface WorkPlaceInfoService {

	/**
	 * <pre>
	 * 新增办公地信息
	 * </pre>
	 *
	 * @param vo
	 */
	void addWorkPlaceInfo(WorkPlaceInfoVO vo);

	/**
	 * <pre>
	 * 修改办公地信息
	 * </pre>
	 *
	 * @param vo
	 */
	void editWorkPlaceInfo(WorkPlaceInfoVO vo);

	/**
	 * <pre>
	 * 删除办公地信息
	 * </pre>
	 *
	 * @param id
	 */
	void deleteWorkPlaceInfo(Long id);

	//    /**
	//     * <pre>
	//     * 分页查询全部办公地信息
	//     * </pre>
	//     *
	//     * @return
	//     */
	//    public List<WorkPlaceInfo> loadAllWorkPlaceInfo();

	/**
	 * <pre>
	 * 单条查询 by Id
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	WorkPlaceInfo loadOneWorkPlaceInfoById(Long id);

	/**
	 * <pre>
	 * 单条查询 by Site
	 * </pre>
	 *
	 * @param site
	 * @return
	 */
	WorkPlaceInfo loadOneWorkPlaceInfoBySite(String site);
	
    Pager	findListByVo(WorkPlaceInfoVO workPlaceInfoVO);
    
   int existsWorkPlaceInfo( WorkPlaceInfoVO vo);
}
