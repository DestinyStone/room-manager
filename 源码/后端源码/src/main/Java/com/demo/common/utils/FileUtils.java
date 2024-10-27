package com.demo.common.utils;

import cn.hutool.core.io.FileUtil;
import lombok.SneakyThrows;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

public class FileUtils {

    private static final String FILE_BASE = "D:\\兼职\\P1135-密码学\\源码\\后端源码";
    private static final String FOLDER = "upload";

    public static String upload(InputStream in) {
        String fileName = UUID.randomUUID().toString();
        FileUtil.writeFromStream(in, getUploadName(fileName));
        return fileName;
    }



    @SneakyThrows
    public static void download(String uuid, HttpServletResponse response) {
        String filePath = getUploadName(uuid);
        byte[] bytes = FileUtil.readBytes(filePath);

        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition", "attachment;fileName=" + uuid);// 设置文件名

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    public static String getUploadName(String uuid) {
        return FILE_BASE + File.separator + FOLDER + File.separator + uuid;
    }
}
