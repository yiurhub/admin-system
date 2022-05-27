package com.yiur.admin.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Yiur
 */
@RestController
@RequestMapping("/code")
public class HttpCodeController {

    @ApiOperation("200处理")
    @GetMapping("/200")
    public int code200(HttpServletResponse response) {
        response.setStatus(200);
        return 200;
    }

    @ApiOperation("401处理")
    @GetMapping("/401")
    public int code401(HttpServletResponse response) {
        response.setStatus(401);
        return 401;
    }

    @ApiOperation("403处理")
    @GetMapping("/403")
    public int code403(HttpServletResponse response) {
        response.setStatus(403);
        return 403;
    }

}
