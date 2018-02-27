package com.admin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

public class ConfigUtils {

//    private static String BUSINESS_FILENAME = "business.properties";
    private static String ENVIRONMENT_FILENAME = "environment.properties";
//    private static String VIPAPR_FILENAME = "vip.properties";

    private static Properties properties;

    static {
        try {
            loadMessage();
        } catch (IOException e) {
            throw new IllegalStateException("load message resource error", e);
        }
    }

    public static String getConfigMessage(String name, Object... args) {
        String message = properties.getProperty(name);
        if (args == null || args.length == 0) {
            return message;
        }
        return MessageFormat.format(message, args);
    }

    private static void loadMessage() throws IOException {
        if (properties != null) {
            return;
        }
        properties = new Properties();

//        properties.load(new BufferedReader(new InputStreamReader(getBusinessInputStream(), "utf-8")));
        properties.load(new BufferedReader(new InputStreamReader(getEnvironmentInputStream(), "utf-8")));
//        properties.load(new BufferedReader(new InputStreamReader(getVipAprInputStream(), "utf-8")));
    }

//    private static InputStream getBusinessInputStream() {
//        // 从当前类加载器中加载资源
//        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(BUSINESS_FILENAME);
//        if (is != null) {
//            return is;
//        }
//        // 从系统类加载器中加载资源
//        is = ClassLoader.getSystemResourceAsStream(BUSINESS_FILENAME);
//        if (is != null) {
//            return is;
//        }
//        throw new IllegalStateException("cannot find " + BUSINESS_FILENAME + " anywhere");
//    }

    private static InputStream getEnvironmentInputStream() {
        // 从当前类加载器中加载资源
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(ENVIRONMENT_FILENAME);
        if (is != null) {
            return is;
        }
        // 从系统类加载器中加载资源
        is = ClassLoader.getSystemResourceAsStream(ENVIRONMENT_FILENAME);
        if (is != null) {
            return is;
        }
        throw new IllegalStateException("cannot find " + ENVIRONMENT_FILENAME + " anywhere");
    }

//    private static InputStream getVipAprInputStream() {
//        // 从当前类加载器中加载资源
//        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(VIPAPR_FILENAME);
//        if (is != null) {
//            return is;
//        }
//        // 从系统类加载器中加载资源
//        is = ClassLoader.getSystemResourceAsStream(VIPAPR_FILENAME);
//        if (is != null) {
//            return is;
//        }
//        throw new IllegalStateException("cannot find " + VIPAPR_FILENAME + " anywhere");
//    }
}
