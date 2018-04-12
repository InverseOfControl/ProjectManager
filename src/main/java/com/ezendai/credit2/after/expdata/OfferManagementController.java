/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */

package com.ezendai.credit2.after.expdata;

import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.after.expdata.common.TPPConstants;
import com.ezendai.credit2.after.expdata.vo.TPPTranVO;
import com.ezendai.credit2.after.expdata.vo.TPPTransVO;
import com.ezendai.credit2.after.model.TppCallBackData;
import com.ezendai.credit2.after.service.TppCallBackDataService;
import com.ezendai.credit2.framework.util.BeanDump;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.CommonUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.framework.util.SystemUtil;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.SysParameterService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: OfferManagementController.java, v 0.1 2014年12月9日 上午11:46:11 00226557 Exp $
 */

@Controller
@RequestMapping("/after/offerManagement")
public class OfferManagementController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(OfferManagementController.class);
	
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	private TppCallBackDataService tppCallBackDataService;
	
	@RequestMapping("/tppCallBackData")
	@ResponseBody
	public void tppCallBackData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			boolean result = this.ipValidationCheck(CommonUtil.getIp(request));
			//判断发起请求的地址,防止恶意请求
			if(!result) {
				response.getOutputStream().write("Request IP Address is wrong".getBytes());
				return;
			}
 			handleTppBackData(request);
		} catch (Exception e) {
			logger.error("处理TPP回盘结果时异常:",e);
			response.getOutputStream().write(TPPConstants.RETURN_FAILURE_CODE.getBytes());
			return;
		}
		response.getOutputStream().write(TPPConstants.RETURN_SUCCESS_CODE.getBytes());
		return;
	}
    /**
     * 
     * <pre>
     * 处理TPP返回的XML数据
     * </pre>
     *
     * @param request
     * @throws JAXBException
     * @throws Exception
     */
	private void handleTppBackData(HttpServletRequest request) throws JAXBException, Exception {
		logger.info("开始处理TPP返回数据->执行开始时间: " + DateUtil.defaultFormatDate(new Date()));
		TPPTransVO trans = this.parseXMLToObject(TPPTransVO.class, request.getInputStream());
		List<TPPTranVO> datas = trans != null ? trans.getTransData()
			: new ArrayList<TPPTranVO>();
		logger.info("开始处理TPP返回原始数据: " + BeanDump.dump(datas));
		if (trans != null && CollectionUtil.isNotEmpty(datas)) {
			logger.info("此次TPP返回数据数量: " + datas.size());
			for (TPPTranVO tranVO : datas) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("offerId", tranVO.getOrderNo());
				boolean exist = tppCallBackDataService.exists(map);
				if (exist) {
					logger.info("TPP返回重复的offerId: " + tranVO.getOrderNo());
					continue; //通过offerid来判断，如果tpp返回的数据包含已存在的,忽略这条数据
				} else {
					insertTppCallBackData(tranVO);
				}
			}
			logger.info("开始处理TPP返回数据->执行完成时间: " + DateUtil.defaultFormatDate(new Date()));
		} else {
			logger.error("此次TPP没有相应的返回数据!");
			return;
		}
	}
	
	/**
	 * 
	 * <pre>
	 * 构建TppCallBackDataVO并写入表
	 * </pre>
	 *
	 * @param tranVO
	 */
	private void insertTppCallBackData(TPPTranVO tranVO) {
		TppCallBackData tppData= new TppCallBackData();
		tppData.setOfferId(tranVO.getOrderNo());
		tppData.setRequestCode(String.valueOf(tranVO.getRequestId()));
		tppData.setReturnCode(tranVO.getReturnCode());
		tppData.setReturnInfo(tranVO.getReturnInfo());
		tppData.setHandleState(TPPConstants.RECEIVED);
		tppData.setReceiveTime(new Date());
		tppCallBackDataService.insert(tppData);
	}
	
	/**
	 * 
	 * <pre>
	 * 使用JAXB把XML转化为对应的对象
	 * </pre>
	 *
	 * @param clazz
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public  <T> T parseXMLToObject(Class<T> clazz, InputStream context) throws Exception {
		try {
			// 读取响应数据
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller u = jc.createUnmarshaller();
			return (T) u.unmarshal(context);
		} catch (JAXBException e) {
			throw e;
		}
	}
	
    /**
     * 
     * <pre>
     * 结算平台服务器IP验证
     * </pre>
     *
     * @param ipAddr
     * @return
     * @throws UnknownHostException
     */
	public boolean  ipValidationCheck(String ipAddr) throws UnknownHostException {
	   		logger.info("结算平台服务器IP地址:::::::::"+ipAddr);
	   		SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.TPP_IP.name());
	   		if (sysParameter == null
	   				|| StringUtil.isEmpty(sysParameter.getParameterValue())) {
	   			logger.info("没有配置结算平台服务器IP地址");
	   			return false;
	   		}
	   		// 获取IP列表,并存储到一个String 数组中
	   		String[] ipArray = sysParameter.getParameterValue().split(";");
	   		// 如果IP列表中不包含本机IP
	   		if (!SystemUtil.containsLocalIP(ipArray, ipAddr)) {
	   			logger.info("结算平台请求的IP地址不在配置列表中: "+ipAddr);
	   			return false;
	   		}
			return true;
	   	}
}
