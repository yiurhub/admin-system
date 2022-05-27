package com.yiur.admin.utils;

import org.func.spring.boot.utils.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取文件工具类
 * @author Yiur
 */
public final class ReaderUtil {

    /**
     * 文件上传日志读取
     * @param index 从第几行开始
     * @param count 读取的数量
     * @return List<Map<String, String>
     */
    public static List<Map<String, String>> uploadLogRead(int index, int count) {
        List<Map<String, String>> list = new ArrayList<>();
        String[] key = { "target", "date", "invoke", "url" };
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(StringUtil.formatTranslate("?/log/admin-upload.log", System.getProperty("user.dir")))))) {
            String readLine = "";
            int currentIndex = 0;
            int currentCount = 0;

            while ((readLine = reader.readLine()) != null) {
                if (currentIndex++ != index) {
                    continue;
                }
                if (currentCount++ == count) {
                    break;
                }

                String[] values = readLine.split(" ");
                Map<String, String> map = new HashMap<>();
                for (int i = 0; i < values.length; i++) {
                    if ("url".equals(key[i])) {
                        map.put(key[i], "http://localhost:9786/user/get/image/" + values[i]);
                        continue;
                    }
                    map.put(key[i], values[i]);
                }
                list.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
