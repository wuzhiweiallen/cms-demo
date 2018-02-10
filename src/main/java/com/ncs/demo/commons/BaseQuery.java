package com.ncs.demo.commons;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/4
 * Time: 18:36
 * To change this template use File | Settings | File Templates.
 */
public class BaseQuery {
    private int page;
    private int size;

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

    @Override
    public String toString() {
        return "BaseQuerry{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
