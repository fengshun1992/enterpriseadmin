package com.admin.data;

public class ResultData extends Result {

    public ResultData() {
        result = ResultCode.SUCCESS;// 默认操作成功
        msg = "";
    }

    public ResultData(int result, String msg, Object data) {
        this.result = result;
        this.msg = msg;
        this.data = data;
    }
}
