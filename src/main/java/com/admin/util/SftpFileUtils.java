package com.admin.util;

import com.admin.listener.SFTPListener;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by hanchunlei on 2016/4/11.
 */
public class SftpFileUtils {

    public String SFTPFileForUeditor(InputStream stream, String upLoadFolder, String fileName, String Path) throws JSchException, SftpException, IOException {
        String targetFile1 = SysConstant.FILE_PATH;
        String httpPath = SysConstant.HTTP_PATH;
        targetFile1 += Path + "/" + upLoadFolder;
        String httpUrl = SysConstant.FILE_HTTPURL;
        String filePath = httpPath + Path + "/" + upLoadFolder + "/" + fileName;
        String disfile = targetFile1 + "/" + fileName;
        String fileUrl = httpUrl + filePath;
        ChannelSftp sftpChannel = SFTPListener.channelSftp;
        sftpChannel.setFilenameEncoding("UTF-8");

        String[] complPath = targetFile1.split("/");
        sftpChannel.cd("/");
        for (String folder : complPath) {
            if (folder.length() > 0) {
                try {
                    System.out.println("Current Dir : " + sftpChannel.pwd());
                    sftpChannel.cd(folder);
                } catch (SftpException e2) {
                    sftpChannel.mkdir(folder);
                    sftpChannel.cd(folder);
                }
            }
        }
        sftpChannel.cd("/");
        sftpChannel.put(stream, disfile);
        stream.close();

        //调接口刷新缓存
//        String userName = SysConstant.CDN_USERNAME;
//        String passWd = SysConstant.CDN_USERNAME+SysConstant.CDN_PASSWORD+SysConstant.CDN_URL;
//        passWd = new MD5().md5(passWd);
//        String url = "http://wscp.lxdns.com:8080/wsCP/servlet/contReceiver?username="+userName+"&passwd="+passWd+"&url="+SysConstant.CDN_URL;
//        new WebRestClient("").get(url, null);
//        sftpChannel.exit();
//        session.disconnect();
        return fileUrl;
    }
}