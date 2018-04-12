package com.ezendai.credit2.system.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.model.TextResource;
import com.ezendai.credit2.system.vo.TextResourceVO;

/**
 * @author LinSanfu
 */

public interface TextResourcesService {
	 List<TextResource> findTextResourcesByType(Integer type) ;
	 Pager findTextResourcesWithPG(TextResourceVO textResourceVO);
	 
	 /**
	  * 插入文字资源
	  * @param textResourceVO
	  * @throws Exception
	  */
	 public Long insertTextResource(TextResource textResource) throws Exception;
	 /**
	  * 更新文字资源
	  * @param textResourceVO
	  * @throws Exception
	  */
	 public void updateTextResource(TextResource textResource) throws Exception;
	 /**
	  * 获取所有符合条件的文字资源
	  * @param textResourceVO
	  * @return
	  */
	 public List<TextResource> findTextResourceWithCondition(TextResourceVO textResourceVO);
	 
	 
	 public TextResource findTextResourcesById(Long id);
	 
	 
	 
}
