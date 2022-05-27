package com.yiur.admin.lambda.upload.impl;

import com.yiur.admin.lambda.upload.Upload;
import org.func.spring.boot.annotation.*;
import org.func.spring.boot.utils.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 匿名函数链接
 * @author Yiur
 */
@FuncBean
@Component
public class UploadImpl implements Upload {

    @Override
    @FuncLogger(name = "admin-upload")
    @FuncLambda(classFile = Upload.class, alias = "upload", refs = { "upload", "watch-log" })
    public String upload(@FuncParameter("file") MultipartFile file, @FuncParameter("fileName") String fileName) throws IOException {
        // 获取upload文件夹路径 不存在则创建
        String dirPath = StringUtil.formatTranslate("?/sources/static/upload/image", System.getProperty("user.dir"));
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        file.transferTo(new File(StringUtil.format("?/?", dirPath, fileName)));
        return fileName;
    }

}
