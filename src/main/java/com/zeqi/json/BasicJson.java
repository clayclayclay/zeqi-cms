package com.zeqi.json;


/**
 * Created by Max on 2016/4/9.
 * 标准json实体类
 */
public class BasicJson {

    private boolean status;
    private ErrMsg errMsg;
    private Object jsonStr;

    public BasicJson(boolean status) {
        this.errMsg = new ErrMsg();
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public ErrMsg getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(ErrMsg errMsg) {
        this.errMsg = errMsg;
    }

    public Object getJsonStr() {
        return jsonStr;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public void setJsonStr(Object jsonStr) {
        this.jsonStr = jsonStr;
    }
}
