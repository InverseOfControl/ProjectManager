package com.ezendai.credit2.master.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.WorkPlaceInfo;
import com.ezendai.credit2.master.service.WorkPlaceInfoService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.master.vo.WorkPlaceInfoVO;

/**   
*    
* 项目名称：credit2-main   
* 类名称：WorkPlaceInfoController   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2015年9月8日 下午2:35:28   
* 修改人：   
* 修改时间：2015年9月8日 下午2:35:28   
* 修改备注：   
* @version    
*    
*/
@Controller
@RequestMapping("/workPleaceInfo")
public class WorkPlaceInfoController {
	
	@Autowired
	WorkPlaceInfoService workPlaceInfoService;

	@RequestMapping("/getWorkPlaceInfoJson")
	@ResponseBody
	public Map getWorkPlaceInfoJson(WorkPlaceInfoVO workPlaceInfoVO,int rows, int page) {
		Pager pager = new Pager();
		pager.setRows(rows);
		pager.setPage(page);
		pager.setSidx("ID");
		pager.setSort("ASC");
		workPlaceInfoVO.setPager(pager);
		
		//查询
		pager= workPlaceInfoService.findListByVo(workPlaceInfoVO);
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", pager.getResultList());
		return result;
	}
	/**
	 * 加载某办公地点信息
	 * @author Ivan
	 * @return
	 */
	@RequestMapping("/loadworkPlaceInfo")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public Map loadNetWorkInfo(long id) {
		Map map = new HashMap();
		String msg = "";
		boolean isSuccess = true;
		WorkPlaceInfo loadOneWorkPlaceInfoById = workPlaceInfoService.loadOneWorkPlaceInfoById(id);
		if (loadOneWorkPlaceInfoById == null) {
	
			isSuccess = false;
			msg = "记录不存在";
		}
		map.put("isSuccess", isSuccess);
		map.put("workPlaceInfo", loadOneWorkPlaceInfoById);
		map.put("msg", msg);
		return map;
	}
	@RequestMapping(value="/addWorkPlaceInfo",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public Map saveNetWorkrInfo(WorkPlaceInfoVO vo) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try{
			if(vo.getId() == null){
				//牛逼，修改这么做
				String workAddress = vo.getSite();
				if(workAddress.indexOf("市") == -1 ? true : (workAddress.indexOf("县") == -1 && workAddress.indexOf("区") == -1)  ? true : false ) {
//				if((workAddress.indexOf("市") == -1) ||  ((workAddress.indexOf("市") != -1) && (workAddress.indexOf("县") == -1 || workAddress.indexOf("区") == -1))) {
					isSuccess = false;
					msg = "新增办公地点缺少市或区县字样信息";
					map.put("isSuccess", isSuccess);
					map.put("msg", msg);
					return map;
				}else if(workAddress.indexOf("市") >= (workAddress.indexOf("县") == -1 ? Integer.MAX_VALUE : workAddress.indexOf("县")) 
					     || workAddress.indexOf("市") >= (workAddress.indexOf("区") == -1 ? Integer.MAX_VALUE : workAddress.indexOf("区"))) {
					isSuccess = false;
					msg = "新增办公地点格式不正确";
					map.put("isSuccess", isSuccess);
					map.put("msg", msg);
					return map;
				}
				workPlaceInfoService.addWorkPlaceInfo(vo);
				msg = "新增办公地点成功";
			}else{
				String workAddress = vo.getSite();
				if(workAddress.indexOf("市") == -1 ? true : (workAddress.indexOf("县") == -1 && workAddress.indexOf("区") == -1)  ? true : false ) {
					isSuccess = false;
					msg = "新增办公地点缺少市或区县字样信息";
					map.put("isSuccess", isSuccess);
					map.put("msg", msg);
					return map;
				}else if(workAddress.indexOf("市") >= (workAddress.indexOf("县") == -1 ? Integer.MAX_VALUE : workAddress.indexOf("县")) 
					     || workAddress.indexOf("市") >= (workAddress.indexOf("区") == -1 ? Integer.MAX_VALUE : workAddress.indexOf("区"))) {
					isSuccess = false;
					msg = "新增办公地点格式不正确";
					map.put("isSuccess", isSuccess);
					map.put("msg", msg);
					return map;
				}
				workPlaceInfoService.editWorkPlaceInfo(vo);
				msg = "修改办公地点成功";
			}
		} catch(BusinessException ex) {
			isSuccess = false;
			msg = "办公地点重复";
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
}