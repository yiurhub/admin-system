package com.yiur.admin.server;

import lombok.extern.slf4j.Slf4j;
import org.func.spring.boot.utils.StringUtil;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * socket服务器
 * @author Yiur
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{username}")
public class WebSocketServer {

    /**
     * 当前在线连接数
     */
    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * 用来存放每个客户端对应的 WebSocketServer对象
     */
    private static final ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 储存username
     */
    private String username;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        this.session = session;
        this.username = username;
        if (webSocketMap.containsKey(username)) {
            webSocketMap.remove(username);
            webSocketMap.put(username, this);
        } else {
            webSocketMap.put(username, this);
            addOnlineCount();
        }
        log.info(StringUtil.format("用户连接: ? -- 当前在线人数为: ? - ?", username, getOnlineCount(), webSocketMap.size()));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(username)) {
            webSocketMap.remove(username);
            subOnlineCount();
        }
        log.info(StringUtil.format("用户退出: ? -- 当前在线人数为: ? - ?", username, getOnlineCount(), webSocketMap.size()));
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info(StringUtil.format("用户消息: ? -- 报文: ?", username, message));

        try {
            // code..
        } catch (Exception e) {
            sendMessage(e.getMessage());
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(StringUtil.format("用户错误: ? -- 原因: ?", username, error));
    }

    /**
     * 向客户端发送消息
     * @param object 推送的对象
     */
    public void sendObject(Object object) {
        try {
            this.session.getBasicRemote().sendObject(object);
        } catch (IOException | EncodeException e) {
            log.error(StringUtil.format("用户退出: ? -- 当前在线人数为: ?", username, getOnlineCount()));
        }
    }

    /**
     * 向客户端发送消息
     * @param message 字符串
     */
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error(StringUtil.format("用户退出: ? -- 当前在线人数为: ?", username, getOnlineCount()));
        }
    }

    public void close() {
        try {
            this.session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized AtomicInteger getOnlineCount() {
        return onlineCount;
    }

    public static synchronized Map<String, WebSocketServer> getWebSocketMap() {
        return webSocketMap;
    }

    public static synchronized void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    public static synchronized void subOnlineCount() {
        onlineCount.getAndDecrement();
    }

}