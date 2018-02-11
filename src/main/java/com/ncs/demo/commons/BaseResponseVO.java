package com.ncs.demo.commons;

/**
 * Created by 84234261@qq.com
 * User: Zhiwei Wu(Allen)
 * Date: 2018/2/6
 * Time: 10:24
 * To change this template use File | Settings | File Templates.
 */
public class BaseResponseVO {
    protected String code = CommonConstants.SUCCESS_CODE;
    protected String desc = CommonConstants.SUCCESS_DESC;

    public BaseResponseVO(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public BaseResponseVO(){

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "BaseResponseVO{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
