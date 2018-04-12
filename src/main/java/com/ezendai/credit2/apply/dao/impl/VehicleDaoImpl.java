package  com.ezendai.credit2.apply.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.dao.VehicleDao;
import com.ezendai.credit2.apply.model.Vehicle;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

/***
 * 车辆信息
 * <pre>
 * 
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: VehicleDaoImpl.java, v 0.1 2014年6月23日 下午4:08:46 HQ-AT6 Exp $
 */
@Repository
public class  VehicleDaoImpl extends BaseDaoImpl<Vehicle> implements VehicleDao{

	@Override
	public Integer getIdNoTwo(String id) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id",id);
		return super.getSqlSession().selectOne(getIbatisMapperNameSpace()+".getIdNoTwo", map);
	} 
  
   

   
}