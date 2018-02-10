package com.ncs.demo.dao;

import com.ncs.demo.po.BirthPerson;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import java.util.List;
import java.util.Map;

public interface BirthPersonDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(BirthPerson record);

    BirthPerson selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BirthPerson record);

    List<BirthPerson> selectUser(Map<String, Object> map, @Param("RowBounds ") RowBounds rowBounds);

    int selectUserCount(Map<String, Object> map);

    void logicDeleteUser(BirthPerson birthPerson);

    BirthPerson selectUserById(int id);

    void updateUser(BirthPerson birthPerson);

    void insertUser(BirthPerson birthPerson);

    List<BirthPerson> selectAllBirthPerson();
}