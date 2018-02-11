package com.ncs.demo.dao;

import com.ncs.demo.po.MoneyGift;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface MoneyGiftDao {
    int deleteByPrimaryKey(Integer id);

    int insert(MoneyGift record);

    int insertSelective(MoneyGift record);

    MoneyGift selectMoneyGiftById(Integer id);

    int updateByPrimaryKeySelective(MoneyGift record);

    int updateByPrimaryKey(MoneyGift record);

    /**
     * 逻辑删除
     * @param record
     * @return
     */
    int logicDelete(MoneyGift record);

    /**
     * 查询送出的礼金表
     * @param map
     * @param rowBounds
     * @return
     */
    List<MoneyGift> selectMoneyGift(Map<String, Object> map, @Param("RowBounds ") RowBounds rowBounds);

    /**
     * 查询送出的礼金表的count
     * @param map
     * @return
     */
    int selectMoneyGiftCount(Map<String, Object> map);
}