package com.yiur.admin.controller;

import com.yiur.admin.utils.ImageIoUtil;
import com.yiur.admin.utils.ReaderUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 资源管理
 * @author Yiur
 */
@RestController
@RequestMapping("/res")
public class ResourceController {

    /**
     * 更具admin-upload.log文件解析图片集合
     * @param index 从第几行开始
     * @param count 读取总数量
     * @return List<Map<String, String>>
     */
    @ApiOperation("根据日志获取图片数组")
    @GetMapping("/get/images")
    public List<Map<String, String>> getImages(int index, int count) {
        return ReaderUtil.uploadLogRead(index, count);
    }

    /**
     * 获取/sources/下的图片
     * @param imageName 图片名称
     * @param response 获取响应对象
     */
    @ApiOperation("获取图片资源")
    @GetMapping("/get/image/{imageName}")
    public void getImage(@PathVariable("imageName") String imageName, HttpServletResponse response) {
        ImageIoUtil.push(response, imageName);
    }

}
