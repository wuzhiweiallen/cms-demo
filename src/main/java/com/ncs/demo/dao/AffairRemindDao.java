package com.ncs.demo.dao;


import com.ncs.demo.po.AffairRemind;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface AffairRemindDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AffairRemind record);

    int insertSelective(AffairRemind record);

    AffairRemind selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AffairRemind record);

    void updateByPrimaryKey(AffairRemind record);

    List<AffairRemind> selectAllAffairRemind();

    List<AffairRemind> selectAffairRemind(Map<String, Object> map, @Param("RowBounds ") RowBounds rowBounds);

    int selectAffairRemindCount(Map<String, Object> map);

    void logicDeleteAffairRemind(AffairRemind record);

    AffairRemind selectAffairRemindById(int id);
}