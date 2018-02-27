package com.admin.listener;

import com.admin.util.SysConstant;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;

/**
 * Created by hanchunlei on 2016/6/8.
 */
public class SFTPListener implements ServletContextListener {

    public static Session session;

    public static ChannelSftp channelSftp;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("加载业务字典");

        JSch jsch = new JSch();
        String fileHost = SysConstant.FILE_HOST;
        String userName = SysConstant.FILE_USERNAME;
        String password = SysConstant.FILE_PASSWORD;
        String port = SysConstant.FILE_PORT;
        try {
            session = jsch.getSession(userName, fileHost, Integer.parseInt(port));
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(password);
            session.connect();
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            System.out.println("sftpChannel 已连接");
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
