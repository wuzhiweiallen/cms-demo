package com.ncs.demo.query;

import com.ncs.demo.commons.BaseQuery;

import java.util.List;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/11
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
public class MoneyGiftQuery extends BaseQuery {

    private String name;
    private int userId;
    //送礼金的日期
    private String givenDate;
    private List<Integer> ids;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGivenDate() {
        return givenDate;
    }

    public void setGivenDate(String givenDate) {
        this.givenDate = givenDate;
    }

    @Override
    public String toString() {
        return "MoneyGiftQuery{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                ", givenDate='" + givenDate + '\'' +
                ", ids=" + ids +
                '}';
    }
}
