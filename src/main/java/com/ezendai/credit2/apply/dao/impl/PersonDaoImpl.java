package com.ezendai.credit2.apply.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.dao.PersonDao;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.vo.PersonVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

/***
 * 客户信息
 * <pre>
 * 
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: PersonDaoImpl.java, v 0.1 2014年6月23日 下午4:21:32 HQ-AT6 Exp $
 */
@Repository
public class PersonDaoImpl extends BaseDaoImpl<Person> implements PersonDao {
    
    /***
     * 根据身份证号查找用户
     * <pre>
     * 
     * </pre>
     *
     * @param idum
     * @return
     */
    @Override
    public  Person getPersonByIdNum(PersonVO personVo){        
        return getSqlSession().selectOne(getIbatisMapperNameSpace()+".getUserByIdnum", personVo);
    }
    
    @Override
    public int insertSelective(Person person) {
        return getSqlSession().insert(getIbatisMapperNameSpace() + ".insertSelective", person);
    }

    @Override
    public Person selectByPrimaryKey(Long id) {
        return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".selectByPrimaryKey", id);
    }

    @Override
    public int updateByPrimaryKeySelective(Person person) {
        return getSqlSession().update(getIbatisMapperNameSpace() + ".updateByPrimaryKeySelective",
            person);
    }

}