package com.admin.data;

import java.util.HashMap;
import java.util.Map;

public class ResultCode {

    public static int ERROR = -1;//系统异常
    public static int SUCCESS = 0;//操作成功
    public static int FAIL = 1;//业务错误
    //短信错误标志
    public static Map<String,String> ERROR_MAP = new HashMap<String,String>(){
        {
            put("error_0_0_1","发送次数超过当日限制（10次），请24小时后再次尝试");
            put("error_0_0_2","验证码验证次数过多，请5分钟后再次尝试");
            put("error_1_1_5","发送短信的手机号码在黑名单中");
            put("error_1_2_1","验证码错误");
            put("error_1_2_2","验证码已过期");
        }
    };
}
