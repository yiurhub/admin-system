package com.yiur.admin.controller;

import com.yiur.admin.lambda.upload.Upload;
import com.yiur.admin.utils.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

/**
 * 上传管理
 * @author Yiur
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    /**
     * 默认的文件名
     */
    private final String defaultFileName = "blob";

    /**
     * 获取匿名函数对象uploadLog
     */
    @Autowired
    private Upload upload;

    /**
     * 上传图片
     * @param file 图片文件
     * @return String
     */
    @ApiOperation("图片上传")
    @PostMapping("/image")
    public String upload(
            @RequestParam("file")
            @RequestBody
            MultipartFile file) throws IOException {
        if (defaultFileName.equals(file.getOriginalFilename())) {
            String[] split = Objects.requireNonNull(file.getContentType()).split("/");
            return  upload.upload(file, StringUtil.random() + "." + split[split.length - 1]);
        }
        return upload.upload(file, StringUtil.random() + Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".")));
    }

}
