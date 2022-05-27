package com.yiur.admin.utils;

import org.lionsoul.ip2region.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Method;
import java.net.InetAddress;

/**
 * @author Yiur
 */
@Component
public final class IPUtil {

    /**
     * 获取IP地址
     * @param request request
     * @return IP地址
     */
    public String getIpAddress(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            if (ipAddress != null && ipAddress.length() > 15) {
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        return ipAddress;
    }

    /**
     * 根据IP地址获取城市
     * @param ip IP地址
     * @return 城市地址
     */
    public String getAddress(String ip) {
        String path = System.getProperty("user.dir") + "/sources/static/ip2region.db";
        try {
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("Error: Invalid ip2region.db file, filePath:" + file.getPath());
            }
            //查询算法
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, file.getPath());
            Method method = getMethod(searcher, DbSearcher.BTREE_ALGORITHM);

            DataBlock dataBlock;
            if (!Util.isIpAddress(ip)) {
                System.out.println("Error: Invalid ip address");
            }
            dataBlock = (DataBlock) method.invoke(searcher, ip);
            return getAddressByRegion(dataBlock.getRegion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据地区获取地址
     * @param region 地区
     * @return 地址
     */
    private String getAddressByRegion(String region) {
        StringBuilder builder = new StringBuilder();
        String[] splits = region.split("\\|");
        for (String split : splits) {
            if (!split.equals("0")) {
                builder.append(split);
            }
        }
        return builder.toString();
    }

    /**
     * 查询算法
     * @param algorithm 算法
     * <pre>
     *  DbSearcher.BTREE_ALGORITHM BTree
     *  DbSearcher.BINARY_ALGORITHM Binary
     *  DbSearcher.MEMORY_ALGORITYM Memory
     * </pre>
     * @param searcher 搜索
     * @return Method
     */
    private Method getMethod(DbSearcher searcher, int algorithm) throws NoSuchMethodException {
        return switch (algorithm) {
            case DbSearcher.BTREE_ALGORITHM -> searcher.getClass().getMethod("btreeSearch", String.class);
            case DbSearcher.BINARY_ALGORITHM -> searcher.getClass().getMethod("binarySearch", String.class);
            case DbSearcher.MEMORY_ALGORITYM -> searcher.getClass().getMethod("memorySearch", String.class);
            default -> null;
        };
    }

}
