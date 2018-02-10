package com.ncs.demo.query;

import com.ncs.demo.commons.BaseQuery;

import java.util.List;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/4
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */
public class UserQuery extends BaseQuery{
    private String name;
    private int userId;
    private List<Integer> userIds;
    private String birthday;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
