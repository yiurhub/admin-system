package com.yiur.admin.utils;

import org.apache.commons.io.FileUtils;
import org.func.spring.boot.utils.StringUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片IO工具类
 * @author Yiur
 */
public final class ImageIoUtil {

    /**
     * 默认图片路径
     */
    private static final String DEF_PATH = "?/sources/static/image/?";
    /**
     * 图片上传路径
     */
    private static final String UPLOAD_PATH = "?/sources/static/upload/image/?";
    /**
     * 后缀默认替换规则
     */
    private static final Map<String, String> REPLACEMENT_RULE = new HashMap<>();

    static {
        REPLACEMENT_RULE.put("jpg", "jpeg");
    }

    /**
     * 输出图片
     * @param response 获取响应对象
     * @param imageName 图片名称s
     */
    public static void push(HttpServletResponse response, String imageName) {
        try {
            File file = null;
            if ("noface.jpg".equals(imageName)) {
                file = new File(StringUtil.formatTranslate(DEF_PATH, System.getProperty("user.dir"), imageName));
            } else {
                file = new File(StringUtil.formatTranslate(UPLOAD_PATH, System.getProperty("user.dir"), imageName));
            }

            response.setContentType("image/" + parseSuffix(imageName));
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(FileUtils.readFileToByteArray(file));
            outputStream.flush();
            outputStream.close();
        } catch (IOException ignored) {
        }
    }

    /**
     * 从路径查找输出图片
     * @param response 获取响应对象
     * @param imagePath 输出路径
     * @param imageName 图片名称s
     */
    public static void push(HttpServletResponse response, String imagePath, String imageName) {
        try {
            File file = new File(StringUtil.formatTranslate(imagePath, System.getProperty("user.dir"), imageName));

            response.setContentType("image/" + parseSuffix(imageName));
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(FileUtils.readFileToByteArray(file));
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析后缀
     * @param fileName 文件名
     * @return 后缀
     */
    private static String parseSuffix(String fileName) {
        final String[] suffix = {fileName.substring(fileName.lastIndexOf(".") + 1)};

        REPLACEMENT_RULE.forEach((key, value) -> {
            if (key.equals(suffix[0])) {
                suffix[0] = value;
            }
        });

        return suffix[0];
    }

}
