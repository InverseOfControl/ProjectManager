package com.ezendai.credit2.master.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.OfferManagerDao;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.OfferManager;
import com.ezendai.credit2.master.service.OfferManagerServcie;
import com.ezendai.credit2.master.util.HttpUtil;
import com.ezendai.credit2.master.vo.OfferManagerVO;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

/**
 * 
 * @Description: 报盘管理
 * @author 徐安龙
 * @date 2016年8月1日
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class OfferMangerServiceImpl implements OfferManagerServcie {

	@Autowired
	private OfferManagerDao offerManagerDao;
	
	@Autowired
	private  Credit2Properties credit2Properties;
	
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 得到报盘列表page
	 */
	@Override
	public Pager getOfferManagerList(OfferManagerVO vo) {
		return offerManagerDao.getOfferList(vo);
	}

	@Override
	public OfferManager getOfferManagerDetail(Long id) {
		return offerManagerDao.get(id);
	}


	@Override
	public OfferManager addOfferManager(OfferManager om) {
		return offerManagerDao.insert(om);
	}

	@Override
	public int updateOfferManager(OfferManagerVO vo) {
		return offerManagerDao.updateOffer(vo);
	}

	@Override
	public String addJob(OfferManager om,String add) throws Exception{
		String param = "";
		OfferManager offer = new OfferManager();
		if(add.equals("add")){
			offer =addOfferManager(om);
			sysLogService.insert(new SysLog(EnumConstants.OptionModule.OFFER_MANAGER.getValue(), 
					EnumConstants.OptionType.ADD_OFFER_MANAGER.getValue() ,"报盘新增成功 ID："+ offer.getId()));
		}
		param = setParam(om);
		String code = HttpUtil.sendPostRequest(credit2Properties.getWebJobUrl()+"/scheduleTask/addJob", param, true);
		if(org.apache.commons.lang.StringUtils.equals(code, "success")){
			return "success";
		}else
			throw new RuntimeException("添加动态调度失败！");
	}

	@Override
	public String updateJob(OfferManagerVO vo) throws Exception{
		int suc = updateOfferManager(vo);
		if(suc==1){
			if(vo.getStatus().equals(1)){
				String param = setParam(getOfferManagerDetail(vo.getId()));
				String code = HttpUtil.sendPostRequest(credit2Properties.getWebJobUrl()+"/scheduleTask/modifyJob", param, true);
				if(org.apache.commons.lang.StringUtils.equals(code, "success")){
					return "success";
				}else
					throw new RuntimeException("修改动态调度失败！");
			}
			return "success";
		}else{
			return "failed";
		}
	}

	@Override
	public String removeJob(OfferManager om) {
		try {
			String param = setParam(om);
			String code = HttpUtil.sendPostRequest(credit2Properties.getWebJobUrl()+"/scheduleTask/removeJob", param, true);
			if(org.apache.commons.lang.StringUtils.equals(code, "success")){
				return "success";
			}else
				return "failed";
		} catch (Exception e) {
			return "failed";
		}
	}
	
	public String setParam(OfferManager om){
		Map<String, String> param = new HashMap<String, String>();
		param.put("id", om.getId().toString());// 生成时间
		param.put("day", om.getDay().toString());// 生成时间
		param.put("beforeDay", om.getBeforeDay().toString());// 生成时间
		param.put("afterDay", om.getAfterDay().toString());// 生成时间
		param.put("generateTime", om.getGenerateTime());// 生成时间
		param.put("sendTime", om.getSendTime());// 发送时间
		// 拼接发送请求
		JSONObject json = JSONObject.fromObject(param);
		return json.toString();
	}

	@Override
	public List<OfferManager> getOfferListByStatus() {
		return offerManagerDao.getOfferListByStatus();
	}
	
}
