package com.ezendai.credit2.after.service;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.after.model.TppCallBackData;
import com.ezendai.credit2.after.vo.TppCallBackDataVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * @author LinSanfu
 */

public interface TppCallBackDataService {
	
	TppCallBackData insert(TppCallBackData tppCallBackData);
	
	boolean exists(Map<String, Object> map);
	
	/** 通过查询vo查找符合条件的TppCallBackData集合 */
	List<TppCallBackData> findListByVo(TppCallBackDataVO tppCallBackDataVO);
	
	/** 通过VO更新记录*/
	int update(TppCallBackDataVO tppCallBackDataVO);
	
	/** 通过查询vo查找符合条件的Offer集合 */
	Pager findWithPg(TppCallBackDataVO tppCallBackDataVO);
	
	/**通过vo查询单日符合条件记录的总和**/
	Integer count(TppCallBackDataVO tppCallBackDataVO);
}
