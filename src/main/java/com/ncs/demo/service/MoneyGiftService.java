package com.ncs.demo.service;

import com.ncs.demo.po.MoneyGift;
import com.ncs.demo.query.MoneyGiftQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/11
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public interface MoneyGiftService {

    /**
     * 根据ID查询礼金表
     * @param id
     * @return
     */
    MoneyGift selectMoneyGiftById(int id);

    /**
     * 逻辑删除
     * @param ids
     * @return
     */
    void batchLogicDelete(List<Integer> ids);

    /**
     * 查询送出的礼金表
     * @param moneyGiftQuery
     * @return
     */
    List<MoneyGift> getMoneyGift(MoneyGiftQuery moneyGiftQuery);

    /**
     * 查询送出的礼金表的count
     * @param moneyGiftQuery
     * @return
     */
    int getMoneyGiftCount(MoneyGiftQuery moneyGiftQuery);

    /**
     * 插入MoneyGift
     * @param record
     * @return
     */
    void insert(MoneyGift record);

    /**
     * 更新MoneyGift
     * @param record
     * @return
     */
    int updateByPrimaryKey(MoneyGift record);
}
