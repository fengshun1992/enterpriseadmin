package com.admin.service.system.template;

/**
 * operatorList：运营商号码
 * states：返回的是执行状态码
 *         -1表示失败
 *         1表示执行成功
 * result：返回的是说明文
 */
public class SsoSendResult {
    private String operatorList;
    private int states;
    private String result;

    public String getOperatorList() {
        return operatorList;
    }

    public void setOperatorList(String operatorList) {
        this.operatorList = operatorList;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SsoSendResult{" +
                "operatorList='" + operatorList + '\'' +
                ", states=" + states +
                ", result='" + result + '\'' +
                '}';
    }
}
