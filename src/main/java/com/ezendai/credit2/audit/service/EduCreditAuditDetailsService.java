package com.ezendai.credit2.audit.service;

import com.ezendai.credit2.audit.vo.ApproveResultVO;

public interface EduCreditAuditDetailsService {

	void approve(ApproveResultVO approveVO, Long userId);

}
