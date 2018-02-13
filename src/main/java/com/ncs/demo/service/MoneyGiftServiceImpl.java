package com.ncs.demo.service;

import com.ncs.demo.dao.MoneyGiftDao;
import com.ncs.demo.po.MoneyGift;
import com.ncs.demo.query.MoneyGiftQuery;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/11
 * Time: 14:39
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MoneyGiftServiceImpl implements MoneyGiftService{

    @Autowired
    private MoneyGiftDao moneyGiftDao;

    @Override
    public MoneyGift selectMoneyGiftById(int id) {

        return moneyGiftDao.selectMoneyGiftById(id);
    }

    @Override
    public void batchLogicDelete(List<Integer> ids) {
        ids.forEach((id) -> {
            MoneyGift moneyGift = moneyGiftDao.selectMoneyGiftById(id);
            moneyGiftDao.logicDelete(moneyGift);
        });
    }

    @Override
    public List<MoneyGift> getMoneyGift(MoneyGiftQuery moneyGiftQuery) {
        int page = moneyGiftQuery.getPage();
        int size = moneyGiftQuery.getSize();
        String name = moneyGiftQuery.getName();
        String givenDate = moneyGiftQuery.getGivenDate();
        int userId = moneyGiftQuery.getUserId();
        Map map = new HashMap();
        map.put("name", name);
        map.put("givenDate", givenDate);
        map.put("userId", userId);
        RowBounds rowBounds = new RowBounds((page - 1) * size, size);

        return moneyGiftDao.selectMoneyGift(map, rowBounds);
    }

    @Override
    public int getMoneyGiftCount(MoneyGiftQuery moneyGiftQuery) {
        String name = moneyGiftQuery.getName();
        String givenDate = moneyGiftQuery.getGivenDate();
        int userId = moneyGiftQuery.getUserId();
        Map map = new HashMap();
        map.put("name", name);
        map.put("givenDate", givenDate);
        map.put("userId", userId);

        return moneyGiftDao.selectMoneyGiftCount(map);
    }

    @Override
    public int insert(MoneyGift record) {

        return moneyGiftDao.insert(record);
    }

    @Override
    public int updateByPrimaryKey(MoneyGift moneyGift) {
        MoneyGift moneyGiftPO = new MoneyGift();
        if(moneyGift != null){
            moneyGiftPO = moneyGiftDao.selectMoneyGiftById(moneyGift.getId());
            if(moneyGiftPO != null){
                moneyGiftPO.setName(moneyGift.getName());
                moneyGiftPO.setGivenDate(moneyGift.getGivenDate());
                moneyGiftPO.setMoney(moneyGift.getMoney());
                moneyGiftPO.setReason(moneyGift.getReason());
            }
        }
        return moneyGiftDao.updateByPrimaryKey(moneyGiftPO);
    }
}
