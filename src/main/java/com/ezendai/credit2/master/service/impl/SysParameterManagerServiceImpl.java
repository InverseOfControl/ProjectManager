package com.ezendai.credit2.master.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.SysParameterManagerDao;
import com.ezendai.credit2.master.model.SysParameterManager;
import com.ezendai.credit2.master.service.SysParameterManagerService;
import com.ezendai.credit2.master.vo.SysParameterManagerVO;

/**
 * 
 * @Description: 参数接口实现
 * @author 张宜超
 * @date 2016年2月1日
 */
@Service
@Transactional
public class SysParameterManagerServiceImpl implements SysParameterManagerService {

	@Autowired
	private SysParameterManagerDao sysParamterDao ;

	/**
	 * 获取参数list
	 */
	@Override
	public Pager getSysParameterList(SysParameterManagerVO vo) {
		
		return sysParamterDao.getSysParameterList(vo);
	}

	/**
	 * 获取一条数据
	 */
	@Override
	public SysParameterManager getSysParameter(Long id) {
		
		return sysParamterDao.getSysParameter(id);
	}

	/**
	 * 多条件获取数据
	 */
	@Override
	public List<SysParameterManager> getSysParameterByConditions(
			SysParameterManagerVO vo) {
		
		return sysParamterDao.getSysParameterByConditions(vo);
	}

	/**
	 * 获取参数个数
	 */
	@Override
	public int getSysParameterCount(SysParameterManagerVO vo) {
		
		return sysParamterDao.getSysParameterCount(vo);
	}

	/**
	 * 添加一条信息
	 */
	@Override
	public void addSysParameter(SysParameterManagerVO vo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(vo.getInputType() == null ){
			vo.setInputType(1);
		}
		if(vo.getCreatorId() == null ){
			vo.setCreatorId(-1l);
		}
		if(vo.getCreator() == null || vo.getCreator() == ""){
			vo.setCreator("system");
		}
		if(vo.getCreatedTime() == null){
			try {
				String dt = sdf.format(new Date());
				vo.setCreatedTime(sdf.parse(dt));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(vo.getModifierId() == null ){
			vo.setModifierId(-1l);
		}
		if(vo.getModifier() == null || vo.getModifier() == ""){
			vo.setModifier("system");
		}
		if(vo.getModifiedTime() == null){
			try {
				String dt = sdf.format(new Date());
				vo.setModifiedTime(sdf.parse(dt));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(vo.getSpmversion() == null ){
			vo.setSpmversion(1);
		}
		sysParamterDao.addSysParameter(vo);
	}

	/**
	 * 修改一条信息
	 */
	@Override
	public int updateSysParameter(SysParameterManagerVO vo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String dt = sdf.format(new Date());
			vo.setModifiedTime(sdf.parse(dt));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sysParamterDao.updateSysParameter(vo);
	}

	@Override
	public int deleteSysParameter(Long id) {
		
		return sysParamterDao.deleteSysParameter(id);
	}

	
}
