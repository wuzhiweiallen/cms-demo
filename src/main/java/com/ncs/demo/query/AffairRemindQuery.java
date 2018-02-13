package com.ncs.demo.query;

import com.ncs.demo.commons.BaseQuery;

import java.util.List;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/13
 * Time: 9:28
 * To change this template use File | Settings | File Templates.
 */
public class AffairRemindQuery extends BaseQuery {

    private String subject;
    private String remindTime;
    private List<Integer> ids;
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    @Override
    public String toString() {
        return "AffairRemindQuery{" +
                "subject='" + subject + '\'' +
                ", remindTime='" + remindTime + '\'' +
                ", ids=" + ids +
                ", userId=" + userId +
                '}';
    }
}
