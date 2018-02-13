package com.ncs.demo.service;

import com.ncs.demo.dao.AffairRemindDao;
import com.ncs.demo.po.AffairRemind;
import com.ncs.demo.query.AffairRemindQuery;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/13
 * Time: 9:27
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AffairRemindServiceImpl implements AffairRemindService{

    @Autowired
    private AffairRemindDao affairRemindDao;

    @Override
    public List<AffairRemind> selectAffairRemind(AffairRemindQuery affairRemindQuery) {
        int page = affairRemindQuery.getPage();
        int size = affairRemindQuery.getSize();
        String subject = affairRemindQuery.getSubject();
        String remindTime = affairRemindQuery.getRemindTime();
        int userId = affairRemindQuery.getUserId();
        Map map = new HashMap();
        map.put("subject", subject);
        map.put("remindTime", remindTime);
        map.put("userId", userId);
        RowBounds rowBounds = new RowBounds((page - 1) * size, size);

        return affairRemindDao.selectAffairRemind(map, rowBounds);
    }

    @Override
    public int selectAffairRemindCount(AffairRemindQuery affairRemindQuery) {
        String subject = affairRemindQuery.getSubject();
        String remindTime = affairRemindQuery.getRemindTime();
        int userId = affairRemindQuery.getUserId();
        Map map = new HashMap();
        map.put("subject", subject);
        map.put("remindTime", remindTime);
        map.put("userId", userId);

        return affairRemindDao.selectAffairRemindCount(map);
    }

    @Override
    public void deldeteAffairRemind(AffairRemindQuery affairRemindQuery) {
        List<Integer> ids = affairRemindQuery.getIds();
        if(ids != null && ids.size() > 0){
            ids.forEach((id) -> {
                AffairRemind affairRemind = affairRemindDao.selectAffairRemindById(id);
                affairRemindDao.logicDeleteAffairRemind(affairRemind);
            });
        }

    }

    @Override
    public void updateAffairRemind(AffairRemind affairRemind) {
        AffairRemind affairRemindPO  = null;
        if(affairRemind != null){
            affairRemindPO = affairRemindDao.selectAffairRemindById(affairRemind.getId());
            affairRemindPO.setSubject(affairRemind.getSubject());
            affairRemindPO.setContent(affairRemind.getContent());
            affairRemindPO.setCron(affairRemind.getCron());
            affairRemindPO.setRemindTime(affairRemind.getRemindTime());
        }
        affairRemindDao.updateByPrimaryKey(affairRemindPO);
    }

    @Override
    public void saveAffairRemind(AffairRemind affairRemind) {
        affairRemindDao.insert(affairRemind);
    }
}
