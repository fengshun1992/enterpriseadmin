package com.admin.service.system.template;

import com.admin.data.ResultData;
import com.admin.util.JsonUtils;
import com.admin.util.SysConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

@Service
public class HttpRequestDomainService {

    private static final Log log = LogFactory.getLog(HttpRequestDomainService.class);

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @return 所代表远程资源的响应结果
     */
    public static ResultData sendPost(String param, String type) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String msg = "";
        String url = "";
        url = SysConstant.TEMPLATE + "sms/sends";
        ResultData result = new ResultData();
        try {
//
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            System.out.println(param);
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                msg += line;
            }
            System.out.println(msg);
            SsoSendResult ssoSendResult = JsonUtils.getPerson(msg, SsoSendResult.class);
            if (type.equals("check")) {
                result.setData(ssoSendResult);
            } else {
                result.setData(ssoSendResult.getOperatorList() == null ? "" : ssoSendResult.getOperatorList());
            }
            if (ssoSendResult.getStates() == -1) {
                result.setResult(1);
                result.setMsg(ssoSendResult.getResult());
            }
        } catch (Exception e) {
            result.setResult(-1);
            result.setMsg("短信发送 POST 请求出现异常！");
            log.info("短信发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @return 所代表远程资源的响应结果
     */
    public static Map<String, String> sendPost2(String url, String type) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String msg = "";
        Map<String, String> result = new HashMap<String, String>();
        try {
//
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
//             获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
//             发送请求参数
            out.write("");
//             flush输出流的缓冲
            out.flush();
//             定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                msg += line;
            }
            result = JsonUtils.getPerson(msg, HashMap.class);
        } catch (Exception e) {
            result.put("states", "-1");
            result.put("msg", "短信发送 POST 请求出现异常！");
            log.info("短信发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
