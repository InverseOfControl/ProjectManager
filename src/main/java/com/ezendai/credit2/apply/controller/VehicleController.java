package com.ezendai.credit2.apply.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.service.VehicleService;
import com.ezendai.credit2.apply.vo.VehicleVO;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.util.Pager;

/**
 * <pre>
 * 车辆控制器
 * </pre>
 *
 * @author fangqingyuan
 * @version $Id: VehicleController.java, v 0.1 2014-6-25 下午12:57:07 fangqingyuan Exp $
 */
@Controller
@RequestMapping("/vehicle")
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;

	@RequestMapping("/initList")
	public String initList(HttpServletRequest request) {
		return "apply/carLoan/carLoanList";
	}

	@RequestMapping("/list.json")
	@ResponseBody
	public Map list(@RequestParam(value = Constants.PAGE_NUMBER_NAME, defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
					@RequestParam(value = Constants.PAGE_ROWS_NAME, defaultValue = Constants.DEFAULT_PAGE_ROWS) int pageSize,
					@ModelAttribute("vo") VehicleVO vo) {
		if (vo == null) {
			vo = new VehicleVO();
		}
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(pageSize);
		p.setPgNumber(page - 1);
		vo.setPager(p);
		Pager pager = vehicleService.findWithPg(vo);
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", pager.getResultList());
		return result;
	}

	@RequestMapping("/addVehicle")
	@ResponseBody
	public Map<String, Object> add(VehicleVO vo) {
		vehicleService.insert(vo);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}

	@RequestMapping("/editVehicle")
	@ResponseBody
	public Map<String, Object> edit(VehicleVO vo) {
		vehicleService.update(vo);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}

	@RequestMapping("/deleteVehicle")
	@ResponseBody
	public Map<String, Object> delete(VehicleVO vo) {
		vehicleService.deleteByIdList(vo) ;
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}
}
