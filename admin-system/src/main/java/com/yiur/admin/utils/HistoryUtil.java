package com.yiur.admin.utils;

import com.yiur.admin.pojo.ServiceHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.func.spring.boot.utils.StringUtil.matcher;

/**
 * 历史记录工具类
 * @author Yiur
 */
@Component
public final class HistoryUtil {

    /**
     * redis操作工具类
     */
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取service层操作历史记录
     * @return List
     */
    public List<ServiceHistory> getServiceHistory() {
        List<Object> histories = redisUtil.lGet("service:history", 0, -1);
        List<ServiceHistory> serviceHistories = new ArrayList<>();
        if (histories != null) {
            for (Object history : histories) {
                ServiceHistory serviceHistory = new ServiceHistory();
                String[] values = matcher("\\{[\\W\\w]*}", (String) history).split("#\tresult");
                if (!(values.length < 2)) {
                    serviceHistory.setInfo(values[0]);
                    serviceHistory.setResult(values[1]);
                    serviceHistories.add(serviceHistory);
                }
            }
        }
        return serviceHistories;
    }

}
