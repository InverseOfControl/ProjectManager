package com.ezendai.credit2.master.dao;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.WorkPlaceInfo;
import com.ezendai.credit2.master.vo.WorkPlaceInfoVO;

/**
 * 办公地信息管理Dao接口
 * Author: kimi
 * Date: 14-6-24
 * Time: 上午9:54
 */
public interface WorkPlaceInfoDao extends BaseDao<WorkPlaceInfo> {


	public Pager findWithPg(WorkPlaceInfoVO vo);
	
	
	public int existsWorkPlaceInfo(WorkPlaceInfoVO vo);
}
