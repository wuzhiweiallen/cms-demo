package com.ncs.demo.service;

import com.ncs.demo.po.AffairRemind;
import com.ncs.demo.query.AffairRemindQuery;

import java.util.List;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/13
 * Time: 9:27
 * To change this template use File | Settings | File Templates.
 */
public interface AffairRemindService {

    List<AffairRemind> selectAffairRemind(AffairRemindQuery affairRemindQuery);

    int selectAffairRemindCount(AffairRemindQuery affairRemindQuery);

    void deldeteAffairRemind(AffairRemindQuery affairRemindQuery);

    void updateAffairRemind(AffairRemind affairRemind);

    void saveAffairRemind(AffairRemind affairRemind);
}
