package com.ezendai.credit2.audit.service;


public interface ContractConfirmService {

	void confirm(Long loanId);
	
	void extensionConfirm(Long extensionId);
	
	void refuse(Long loanId, String reason, Long refuseSecondReasonId, Integer extensionTime);
}
