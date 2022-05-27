package com.yiur.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yiur.admin.pojo.User;
import com.yiur.admin.service.UserService;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.UUID;

@SpringBootTest
class AdminSystemApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void test() {
        System.out.println("test");
    }

    @Test
    void contextLoads() throws Throwable {
        // 1
        parse("Thread1", 15239, 100000);
    }

    @Test
    void update() {
        for (int i = 27; i <= 158; i++) {
            userService.update(
                    new User()
                            .setUid(i)
                            .setUsername(UUID.randomUUID().toString().replaceAll("-", ""))
            );
        }
    }

    void parse(String key, int index, int count) {
        System.out.printf("%s --> run start%n", key);
        for (int i = index; i <= count + index; i++) {
//            HttpHost proxy = new HttpHost("112.84.192.174", 8118);
            HttpGet httpGet = new HttpGet(String.format("https://api.bilibili.com/x/space/acc/info?mid=%d&jsonp=jsonp", i));
            RequestConfig configDL = RequestConfig.custom().setConnectTimeout(1000).setSocketTimeout(1000).build();
//            RequestConfig configIP = RequestConfig.custom().setProxy(proxy).build();
            httpGet.setConfig(configDL);
//            httpGet.setConfig(configIP);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                Thread.sleep(1000);
                CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
                int status = httpResponse.getStatusLine().getStatusCode();
                System.out.printf("%s request status %d%n", key, status);
                if (status == 200) {
                    String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                    JSONObject parse = (JSONObject) JSON.parse(result);
                    int code = (int) parse.get("code");
                    if (code == 0) {
                        JSONObject data = (JSONObject) parse.get("data");
                        String name = (String) data.get("name");
                        String face = (String) data.get("face");
                        User user = new User();
                        user.setName(name);
                        user.setFace(face);
                        user.setUsername(UUID.randomUUID().toString().replaceAll("-", ""));
                        user.setPassword("123");
                        user.setAddress("保密");
                        user.setPerms("user");
                        System.out.println(userService.insert(user) == 1 ? String.format("%s --> 执行成功！---添加用户---%nObject:%s", key, user) : String.format("%s --> 执行失败！---添加用户---%nObject:%s", key, user));
                    }
                }
                httpResponse.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
