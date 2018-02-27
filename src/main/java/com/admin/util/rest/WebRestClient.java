package com.admin.util.rest;

import com.admin.data.ResultCode;
import com.admin.data.ResultData;
import com.admin.util.JsonUtil;
import com.admin.util.JsonUtils;
import com.admin.util.StringUtils;
import com.google.common.net.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URLDecoder;

public class WebRestClient {

    private String serviceHostPath;

    private String atoken;

    private static Logger logger = LoggerFactory.getLogger(WebRestClient.class);

    public WebRestClient(String serviceHostPath) {
        this.serviceHostPath = serviceHostPath;
    }

    public WebRestClient(String serviceHostPath, String atoken) {
        this.serviceHostPath = serviceHostPath;
        this.atoken = atoken;
    }

    public <T> T get(String uri, Type type) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet((serviceHostPath + uri).trim().replaceAll(" ", "%20"));
            httpGet.setConfig(RequestConfig.DEFAULT);
            if (StringUtils.isNotEmpty(atoken))
                httpGet.setHeader("atoken", URLDecoder.decode(atoken, "UTF-8"));
            HttpResponse response = httpClient.execute(httpGet);
            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), "UTF-8"));
            return JsonUtil.parseJson(br, type);
        } catch (IOException exception) {
            logger.error(uri + "网络异常", exception);
            return (T) resultError();
        }
    }

    public <T> T get(String uri, Object parameter, Type type) {
        return get(uri, type);
    }

    public Object getRest(String uri, Class type) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet((serviceHostPath + uri).trim().replaceAll(" ", "%20"));
            if (StringUtils.isNotEmpty(atoken))
                httpGet.setHeader("atoken", URLDecoder.decode(atoken, "UTF-8"));
            HttpResponse response = httpClient.execute(httpGet);
            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), "UTF-8"));
            String str = null;
            String valuesStr;
            while ((valuesStr = br.readLine()) != null) {
                str = valuesStr;
            }
//            return JSONUtils.toBean(str, type);
            return null;
        } catch (IOException exception) {
            logger.error(uri + "网络异常", exception);
            return resultError();
        }
    }

    public <T> T put(String uri, Object parameter, Type type) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPut httpPut = new HttpPut((serviceHostPath + uri).trim().replaceAll(" ", "%20"));
            StringEntity httpEntity = new StringEntity(JsonUtil.toJson(parameter), "UTF-8");
            httpEntity.setChunked(true);
            httpPut.setEntity(httpEntity);
            if (StringUtils.isNotEmpty(atoken))
                httpPut.setHeader("atoken", URLDecoder.decode(atoken, "UTF-8"));
            httpPut.addHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
            HttpResponse response = httpClient.execute(httpPut);
            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), "utf-8"));
            return JsonUtil.parseJson(br, type);
        } catch (IOException exception) {
            logger.error(uri + "网络异常", exception);
            ResultData resultData = new ResultData();
            resultData.setResult(-1);
            return (T) resultData;
        }
    }

    public <T> T delete(String uri, Type type) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpDelete httpDelete = new HttpDelete((serviceHostPath + uri).trim().replaceAll(" ", "%20"));
            if (StringUtils.isNotEmpty(atoken))
                httpDelete.setHeader("atoken", URLDecoder.decode(atoken, "UTF-8"));
            HttpResponse response = httpClient.execute(httpDelete);
            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), "UTF-8"));
            return JsonUtil.parseJson(br, type);
        } catch (IOException exception) {
            logger.error(uri + "网络异常", exception);
            return (T) resultError();
        }
    }

    public static <T> T post(String uri, Object parameter, Type type) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost((uri).trim().replaceAll(" ", "%20"));
        StringEntity httpEntity = new StringEntity(JsonUtils.toJson(parameter), "UTF-8");
        httpEntity.setChunked(true);
        httpPost.setEntity(httpEntity);
        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        HttpResponse response = httpClient.execute(httpPost);
        BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), "utf-8"));
        return JsonUtils.parseJson(br, type);
    }

    private ResultData resultError() {
        ResultData resultData = new ResultData();
        resultData.setResult(ResultCode.ERROR);
        return resultData;
    }

}