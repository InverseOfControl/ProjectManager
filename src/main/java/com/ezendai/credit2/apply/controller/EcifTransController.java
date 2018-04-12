package com.ezendai.credit2.apply.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.assembler.PersonAssembler;
import com.ezendai.credit2.apply.model.EcifTransfer;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.service.EcifTransferService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.vo.EcifTransferVO;
import com.ezendai.credit2.apply.vo.LoanDetailsVO;
import com.ezendai.credit2.apply.vo.PersonVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.service.SysParameterService;

@Controller
@RequestMapping("/ecif")
public class EcifTransController extends BaseController {

	private static final Logger logger = Logger.getLogger(EcifTransController.class);
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	private PersonService personService;
	@Autowired
	private EcifTransferService ecifTransferService;

	/**
	 * 明细
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/detail")
	public String viewList(HttpServletRequest request) {

		Integer userType = ApplicationContext.getUser().getUserType();

		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID, EnumConstants.PRODUCT_TYPE,
				EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		request.setAttribute("userType", userType);

		return "master/ecif/ecifTransDetail";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/getEcifTransList")
	@ResponseBody
	public Map<String, Object> ecifTransList(
			@RequestParam(value = Constants.PAGE_NUMBER_NAME, defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = Constants.PAGE_ROWS_NAME, defaultValue = Constants.DEFAULT_PAGE_ROWS) int pageSize,
			EcifTransferVO ecifTransferVO) {
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(pageSize);
		p.setSidx("ECIF_TRANSFER.CREATED_TIME");
		p.setSort("DESC");
		ecifTransferVO.setPager(p);

		Pager result = ecifTransferService.findWithPg(ecifTransferVO);
		List<EcifTransfer> ecifTransferList = result.getResultList();

		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

		resultMap.put("total", result.getTotalCount());
		resultMap.put("rows", ecifTransferList);
		return resultMap;

	}

	@RequestMapping("insertCustomer")
	@ResponseBody
	public void insertCustomer(LoanDetailsVO loanDetailsVo, Person person) throws DocumentException {
		MediaType mediaType = MediaType.TEXT_XML_TYPE;
		String url = "";
		String xmlStr = "";
		url = getEcifUrl(EnumConstants.EcifInterface.新建客户信息.getValue());
		Map<String, String> resMap = new HashMap<String, String>();
		UserSession user = ApplicationContext.getUser();

		String enableCarLoan = sysParameterService.getByCode("ECIF_CARLOAN_ENABLE").getParameterValue();
		String enableSeloan = sysParameterService.getByCode("ECIF_SELOAN_ENABLE").getParameterValue();
		String enableTestData = sysParameterService.getByCode("ECIF_TESTDATA_ENABLE").getParameterValue();
		String personIdnum = null;
		if (EnumConstants.ProductType.CAR_LOAN.getValue().equals(loanDetailsVo.getProductTypeId())) {

			personIdnum = loanDetailsVo.getCarPersonIdnum();
		} else {
			personIdnum = loanDetailsVo.getPersonIdnum();
		}
		Person dbPerson = personService.getPersonByIdNum(personIdnum,
				Integer.valueOf(String.valueOf(loanDetailsVo.getProductTypeId())));
		xmlStr = transformPersonToStr(loanDetailsVo, dbPerson);
		if (user.getLoginName().startsWith("test") && enableTestData.equals("false")) {
			return;
		}

		if (dbPerson.getProductType().equals(EnumConstants.ProductType.SE_LOAN.getValue())
				&& enableSeloan.equals("true")) {
			send(url, mediaType, xmlStr, resMap);

		} else if (dbPerson.getProductType().equals(EnumConstants.ProductType.CAR_LOAN.getValue())
				&& enableCarLoan.equals("true")) {
			send(url, mediaType, xmlStr, resMap);

		} else {
			return;
		}

		EcifTransfer ecifTransfer = new EcifTransfer();
		Document document = DocumentHelper.parseText(resMap.get("entity"));
		Node custIdNode = document.selectSingleNode("returnData/returnBody/custNo");
		Node statusMessage = document.selectSingleNode("returnData/returnBody/message");
		Node returnCode = document.selectSingleNode("returnData/returnCode/code");
		String ecifID = null;
		if (custIdNode != null) {

			ecifID = custIdNode.getText();
		}else if(statusMessage.getText().split("客户号为:").length>1){
			ecifID =statusMessage.getText().split("客户号为:")[1] ;
		}
		PersonVO personVO = PersonAssembler.transferModel2VO(dbPerson);
		personVO.setEcifID(ecifID);
		if (returnCode != null && ecifID != null) {
			if ("E0000004".equals(returnCode.getText()) || "AAAAAAAA".equals(returnCode.getText())) {

				ecifTransfer.setEcifId(ecifID);

				personService.update(personVO);
			}
		}
		ecifTransfer.setPersonId(dbPerson.getId());
		ecifTransfer.setStatus(String.valueOf(returnCode.getText()));
		ecifTransfer.setEcifReq(xmlStr);
		ecifTransfer.setEcifRes(resMap.get("entity"));
		ecifTransfer.setEcifId(ecifID);
		ecifTransfer.setInterfaceType(EnumConstants.EcifInterfaceType.新建客户信息.getValue());
		ecifTransfer.setPersonIdnum(dbPerson.getIdnum());
		ecifTransfer.setPersonName(dbPerson.getName());
		ecifTransfer.setProductId(loanDetailsVo.getProductId());
		ecifTransfer.setProductType(dbPerson.getProductType());
		ecifTransfer.setStatusMessage(String.valueOf(statusMessage.getText()));
		ecifTransferService.insert(ecifTransfer);
		if ("E0000004".equals(returnCode.getText()) && ecifID != null) {
			insertEcifRel(loanDetailsVo, ecifID, dbPerson);
		}

	}

	private void insertEcifRel(LoanDetailsVO loanDetailsVo, String ecifID, Person dbPerson) throws DocumentException {
		MediaType mediaType = MediaType.TEXT_XML_TYPE;
		String url = "";
		String xmlStr = "";
		Map<String, String> resMap = new HashMap<String, String>();
		url = getEcifUrl(EnumConstants.EcifInterface.新建ECIF客户关系.getValue());

		xmlStr = transformCusRel(loanDetailsVo, ecifID, dbPerson);
		
		send(url, mediaType, xmlStr, resMap );
		
		Document document = DocumentHelper.parseText(resMap.get("entity"));
		Node statusMessage = document.selectSingleNode("returnData/returnBody/message");
		Node returnCode = document.selectSingleNode("returnData/returnCode/code");
		
		EcifTransfer ecifTransfer = new EcifTransfer();
		ecifTransfer.setPersonId(dbPerson.getId());
		ecifTransfer.setStatus(String.valueOf(returnCode.getText()));
		ecifTransfer.setEcifReq(xmlStr);
		ecifTransfer.setEcifRes(resMap.get("entity"));
		ecifTransfer.setEcifId(ecifID);
		ecifTransfer.setInterfaceType(EnumConstants.EcifInterfaceType.新建ECIF客户关系.getValue());
		ecifTransfer.setPersonIdnum(dbPerson.getIdnum());
		ecifTransfer.setPersonName(dbPerson.getName());
		ecifTransfer.setProductId(loanDetailsVo.getProductId());
		ecifTransfer.setProductType(dbPerson.getProductType());
		ecifTransfer.setStatusMessage(String.valueOf(statusMessage.getText()));
		ecifTransferService.insert(ecifTransfer);

	}

	private String transformCusRel(LoanDetailsVO loanDetailsVo, String ecifID, Person person) {
		String userId = ApplicationContext.getUser().getLoginName();
		String sysSrc = "";
		if (loanDetailsVo.getProductTypeId().equals(EnumConstants.ProductType.CAR_LOAN.getValue())) {
			sysSrc = EnumConstants.EcifSysSrc.车贷.getValue();

		} else {
			sysSrc = EnumConstants.EcifSysSrc.小企业贷.getValue();
		}

		Element root = DocumentHelper.createElement("requestData");
		createContrEle(userId, sysSrc, root);
		Element bodyData = root.addElement("bodyData");

		bodyData.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		bodyData.addAttribute("xsi:type", "requestEcifCusRela");

		bodyData.addElement("certNo").setText(StringUtil.notNullString(person.getIdnum()));
		bodyData.addElement("certType").setText("01");
		bodyData.addElement("cnName").setText(StringUtil.notNullString(person.getName()));
		bodyData.addElement("custId").setText(StringUtil.notNullString(person.getId()));
		bodyData.addElement("custNo").setText(ecifID);

		Document document = DocumentHelper.createDocument(root);

		return document.asXML();
	}

	private String getEcifUrl(String code) {
		String remotePath = sysParameterService.getByCode(code).getParameterValue();
		String host = sysParameterService.getByCode("ECIF_HOST").getParameterValue();
		String port = sysParameterService.getByCode("ECIF_PORT").getParameterValue();
		if(port == null){
			port  = "";
		}
		String url = host + port + remotePath;
		return url;
	}

	private String transformPersonToStr(LoanDetailsVO loanDetailsVo, Person person) {

		String userId = ApplicationContext.getUser().getLoginName();
		String sysSrc = "";
		if (loanDetailsVo.getProductTypeId().equals(EnumConstants.ProductType.CAR_LOAN.getValue())) {
			sysSrc = EnumConstants.EcifSysSrc.车贷.getValue();

		} else {
			sysSrc = EnumConstants.EcifSysSrc.小企业贷.getValue();
		}

		Element root = DocumentHelper.createElement("requestData");
		createContrEle(userId, sysSrc, root);
		Element bodyData = root.addElement("bodyData");

		bodyData.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		bodyData.addAttribute("xsi:type", "customer");

		Element contactInfo = bodyData.addElement("contactInfo");
		Element contactOther = bodyData.addElement("contactOther");
		Element customerCert = bodyData.addElement("customerCert");
		Element customerInfo = bodyData.addElement("customerInfo");
		Element customerOther = bodyData.addElement("customerOther");
		Element customerWork = bodyData.addElement("customerWork");

		// contactInfo.addElement("commAddress").setText(StringUtil.notNullString(person.getAddress()));
		// contactInfo.addElement("fax").setText("");
		contactInfo.addElement("homeAddress").setText(StringUtil.notNullString(person.getAddress()));
		contactInfo.addElement("homePhone").setText(StringUtil.notNullString(person.getHomePhone()));
		// contactInfo.addElement("homePhoneCtry").setText("");
		contactInfo.addElement("homeZip").setText(StringUtil.notNullString(person.getZipCode()));
		contactInfo.addElement("issuerAddress").setText(StringUtil.notNullString(person.getPlaceDomicile()));
		contactInfo.addElement("mobile").setText(StringUtil.notNullString(person.getMobilePhone()));
		// contactInfo.addElement("officePhone").setText("");

		// contactOther.addElement("homePhone2").setText("");
		// contactOther.addElement("homePhone3").setText("");
		// contactOther.addElement("homePhone4").setText("");
		// contactOther.addElement("mobile2").setText("");
		// contactOther.addElement("mobile3").setText("");
		// contactOther.addElement("mobile4").setText("");
		// contactOther.addElement("msn").setText("");
		// contactOther.addElement("officePhone2").setText("");
		// contactOther.addElement("officePhone3").setText("");
		// contactOther.addElement("officePhone4").setText("");
		// contactOther.addElement("officePhoneCtry").setText("");
		// contactOther.addElement("qq").setText("");
		contactOther.addElement("eMail").setText(StringUtil.notNullString(person.getEmail()));

		// customerCert.addElement("certAddress").setText(StringUtil.notNullString(person.getPlaceDomicile()));
		customerCert.addElement("certNo").setText(StringUtil.notNullString(person.getIdnum()));
		customerCert.addElement("certType").setText("01");
		// customerCert.addElement("expiryDate").setText("");
		// customerCert.addElement("issueDate").setText("");

		// customerInfo.addElement("birthday").setText("");
		// customerInfo.addElement("city").setText("");
		customerInfo.addElement("cnName").setText(StringUtil.notNullString(person.getName()));
		// customerInfo.addElement("country").setText("");
		customerInfo.addElement("custId").setText(StringUtil.notNullString(person.getId()));
		// customerInfo.addElement("education").setText("");
		// customerInfo.addElement("enName").setText("");
		// customerInfo.addElement("homePlace").setText("");
		// customerInfo.addElement("issuerAddress").setText("");
		// customerInfo.addElement("major").setText("");
		// customerInfo.addElement("nation").setText("");
		// customerInfo.addElement("province").setText("");
		customerInfo.addElement("sex").setText(StringUtil.notNullString(person.getSex()));

		customerOther.addElement("custStatus").setText("1");
		// customerOther.addElement("healthStatus").setText("");
		// customerOther.addElement("houseStyle").setText("");
		// customerOther.addElement("innerFlag").setText("");
		// customerOther.addElement("marriageStatus").setText("");

		// customerWork.addElement("basicSalary").setText("");
		customerWork.addElement("dept").setText(StringUtil.notNullString(person.getDeptName()));
		customerWork.addElement("inCome").setText(StringUtil.notNullString(person.getIncomePerMonth()));
		// customerWork.addElement("inComeSource").setText("");
		// customerWork.addElement("occupation").setText("");
		// customerWork.addElement("officeAddress").setText("");
		// customerWork.addElement("officeDate").setText("");
		// customerWork.addElement("officeName").setText("");
		// customerWork.addElement("officePost").setText("");
		// customerWork.addElement("officeSize").setText("");
		// customerWork.addElement("officeType").setText("");
		customerWork.addElement("otherInCome").setText(StringUtil.notNullString(person.getOtherIncome()));
		customerWork.addElement("payday").setText(StringUtil.notNullString(person.getPayDate()));
		// customerWork.addElement("position").setText("");
		// customerWork.addElement("title").setText("");
		// customerWork.addElement("trade").setText("");
		// customerWork.addElement("workDate").setText("");
		// customerWork.addElement("workType").setText("");
		// customerWork.addElement("workYear").setText("");
		// customerWork.addElement("yearInCome").setText("");

		Document document = DocumentHelper.createDocument(root);

		return document.asXML();
	}

	private void createContrEle(String userId, String sysSrc, Element root) {
		Element controlData = root.addElement("controlData");
		controlData.addElement("sendDate").setText(DateUtil.getDate(DateUtil.getToday(), "yyyyMMdd"));
		controlData.addElement("sysSrc").setText(sysSrc);
		controlData.addElement("tranSeqNo").setText("secredit" + genRandomNum());
		controlData.addElement("userId").setText(String.valueOf(userId));
	}

	private void send(String url, MediaType mediaType, String xmlStr, Map<String, String> resMap) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		
		logger.info("ECIF  client: " + client);
		logger.info("ECIF target url: " + url);
		logger.info("ECIF target : " + target);
		logger.info("ECIF XML STR : " + xmlStr);
		Response response = null;
		try {
			response = target.request().post(Entity.entity(xmlStr, mediaType));
			String entity = response.readEntity(String.class);
			int status = response.getStatus();
			if (response.getStatus() != 200) {
				logger.error("Failed with HTTP error code : " + response.getStatus());
				throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
			}
			logger.info("Successfully got result: " + entity);
			resMap.put("entity", entity);
			resMap.put("status", String.valueOf(String.valueOf(status)));
		} finally {
			try{
				response.close();
				client.close();
				
			}catch (Exception e){
				logger.info("ECIF access error:"+e.getMessage());
			}
			
		}
	}


	public String genRandomNum() {
		int num_len = 24;
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0;
		char[] str = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer num_id = new StringBuffer("");
		Random r = new Random();
		while (count < num_len) {
			// 生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				num_id.append(str[i]);
				count++;
			}
		}

		return num_id.toString();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

}
