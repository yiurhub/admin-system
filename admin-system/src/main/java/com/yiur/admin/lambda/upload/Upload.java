package com.yiur.admin.lambda.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 用户上传日志
 * @author Yiur
 */
public interface Upload {

    /**
     * 文件上传
     * @param file 上传的文件
     * @param fileName 匿名函数链接映射变量
     * @return String
     */
    String upload(MultipartFile file, String fileName) throws IOException;

}
