package com.ncs.demo.commons;

import java.util.List;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/5
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
public class ResponseVO extends BaseResponseVO{
    private int page;
    private int size;
    private int total;
    private List rows;

    public ResponseVO(int page, int size, int tota, List items) {
        this.page = page;
        this.size = size;
        this.total = tota;
        this.rows = items;
    }

    private ResponseVO(){

    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int tota) {
        this.total = tota;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }


    @Override
    public String toString() {
        return "ResponseVO{" +
                "page=" + page +
                ", size=" + size +
                ", total=" + total +
                ", rows=" + rows +
                '}';
    }
}
