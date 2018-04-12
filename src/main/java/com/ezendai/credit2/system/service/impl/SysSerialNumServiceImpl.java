package com.ezendai.credit2.system.service.impl;

import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.master.enumerate.EnumConstants.SerialNum;
import com.ezendai.credit2.system.dao.SysSerialNumDao;
import com.ezendai.credit2.system.model.SysSerialNum;
import com.ezendai.credit2.system.service.SysSerialNumService;
import com.ezendai.credit2.system.vo.SerialNumResult;
import com.ezendai.credit2.system.vo.SysSerialNumVO;

@Service
public class SysSerialNumServiceImpl implements SysSerialNumService {
	
	private static final Logger LOG = Logger.getLogger(SysSerialNumServiceImpl.class);
	
	@Autowired
	private SysSerialNumDao SysSerialNumDao;

	@Override
	public SerialNumResult getSerialNum(SerialNum serialNum) {
		SerialNumResult result = new SerialNumResult();
		try {
			if (serialNum == null) {
				result.setMessage("serialNum不能为空。");
				return result;
			}
			result.setCode(serialNum.getCode());
			int update=0;
			while(update != 1){
				SysSerialNumVO vo = new SysSerialNumVO();
				vo.setCode(serialNum.getCode());
				List<SysSerialNum> voList = SysSerialNumDao.findListByVo(vo);
				SysSerialNum sysSerialNum = voList.get(0);
				SysSerialNumVO updateVO = new SysSerialNumVO();
				updateVO.setId(sysSerialNum.getId());
				updateVO.setVersion(sysSerialNum.getVersion());
				int mode;
				if (sysSerialNum.getModifiedTime() == null) {
					mode = 1;
				} else {
					mode = DateUtils.truncatedCompareTo(sysSerialNum.getDbDate(), sysSerialNum.getModifiedTime(), serialNum.getField());
				}
				updateVO.setMode(mode);
				try {
					update = SysSerialNumDao.update(updateVO);
				} catch (BusinessException e) { // 对于更新为0，基类做了处理
					update = 0;
					result.setMessage("获取序列号失败，请重试。");
				}
				if (update == 1) {
					/** 更新数据库成功 */
					result.setSuccess(true);
					result.setDate(sysSerialNum.getDbDate());
					if (mode == 1) {
						result.setSeq(1L);
					} else if (mode == 0) {
						result.setSeq(sysSerialNum.getCurrSeq());
					}
					result.setDate(sysSerialNum.getDbDate());
				} else if (update > 1) {
					result.setMessage("获取序列号失败，请重试。");
				}
			}
		} catch (Exception e) {
			LOG.error(e);
			result.setMessage(e.getMessage());
		}
		LOG.info(result);
		return result;
	}

}
