package com.ezendai.credit2.apply.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.Bank;
import com.ezendai.credit2.apply.service.BankService;
import com.ezendai.credit2.apply.vo.BankVO;

@Controller
@RequestMapping("/bank")
public class BankController {
	
	@Autowired
	private BankService bankService;

	@RequestMapping("/getBankList.json")
	@ResponseBody
	public List<Bank> getBankList() {
		List<Bank> bankList = bankService.findListByVo(new BankVO());
		return bankList;
	}
	
	@RequestMapping("/getBankListIn.json")
	@ResponseBody
	public List<Bank> getBankListIn() {
		BankVO bankVO=new BankVO();
		bankVO.setBankType(0);
		List<Bank> bankList = bankService.findListByVo(bankVO);
		return bankList;
	}
	
}
