package com.admin.data;

public class Result {

    public int result; //操作结果 0：成功、  1：业务错误(msg写失败描述)、 -1：系统异常

    public String msg; //说明信息

    protected Object data; //结果

    public Result() {
        this.result = ResultCode.SUCCESS;
        this.msg = "";
    }

    public Result(String msg) {
        this.result = ResultCode.FAIL;
        this.msg = msg;
    }

    public Result(int result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
