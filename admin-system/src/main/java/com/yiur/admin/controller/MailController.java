package com.yiur.admin.controller;

import com.yiur.admin.pojo.Mail;
import com.yiur.admin.service.MailService;
import com.yiur.admin.state.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 邮件管理
 * @author Yiur
 */
@RestController
@RequestMapping("mail")
public class MailController {

    /**
     * 邮件service
     */
    @Autowired
    private MailService mailService;

    /**
     * 获取全部邮件
     * @return List
     */
    @ApiOperation("获取全部邮件")
    @GetMapping("/get/all")
    public List<Mail> getMails() {
        return mailService.queryAll();
    }

    /**
     * 根据用户id获取邮件
     * @return List
     */
    @ApiOperation("根据用户id获取邮件")
    @GetMapping("/get/{uid}")
    public List<Mail> getMails(@PathVariable("uid") int uid) {
        return mailService.queryById(uid);
    }

    /**
     * 设置邮件状态
     * @param mail 邮件属性
     * @return int
     */
    @ApiOperation("设置邮件状态")
    @PostMapping("/set/state")
    public int setMailState(@RequestBody Mail mail) {
        if (mailService.updateState(mail) != 1) {
            return Result.ERROR.state;
        } else {
            return Result.OK.state;
        }
    }

    /**
     * 删除邮件
     * @param mailId 邮件id
     * @return int
     */
    @ApiOperation("删除邮件")
    @DeleteMapping("/delete/{mailId}")
    public int deleteMail(@PathVariable("mailId") int mailId) {
        if (mailService.delete(mailId) != 1) {
            return Result.ERROR.state;
        } else {
            return Result.OK.state;
        }
    }

    /**
     * 根据集合删除邮件
     * @param list 邮件id集合
     * @return int
     */
    @ApiOperation("根据集合删除邮件")
    @DeleteMapping("/delete/list")
    public int deleteMailByList(@RequestBody List<Mail> list) {
        boolean result = true;
        for (Mail mail : list) {
            result = mailService.delete(mail.getMailId()) != 1;
        }

        if (result) {
            return Result.ERROR.state;
        } else {
            return Result.OK.state;
        }
    }

}
