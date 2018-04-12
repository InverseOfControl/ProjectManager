package  com.ezendai.credit2.apply.dao;

import com.ezendai.credit2.apply.model.*;
import com.ezendai.credit2.framework.dao.BaseDao;
public interface VehicleDao extends BaseDao<Vehicle>{ 
	Integer getIdNoTwo(String id);
  
}