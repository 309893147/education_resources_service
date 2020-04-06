package com.education.resources.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ServerEndpoint("/websocket")
@Component
public class WebSocketServer {

    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     *     * 连接建立成功调用的方法
     */

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this); //加入set中
        addOnlineCount(); //在线数加1
        // log.info("有新窗口开始监听:" + sid + ",当前在线人数为" + getOnlineCount());

//        try {
//           sendMessage("连接成功");
//           sendInfo("当前连接用户数为" + getOnlineCount());
//        } catch (IOException e) {
//            //log.error("websocket IO异常");
//        }
    }

    /**
     *     * 连接关闭调用的方法
     *    
     */

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();  //在线数减1
        //   log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     *     * 收到客户端消息后调用的方法
     *     *
     *     * @param message 客户端发送过来的消息
     */

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        long initialDelay = 1;
        long period = 5;
        // 从现在开始1秒钟之后，每隔1秒钟执行一次job1
        service.scheduleAtFixedRate(() -> {


            JSONArray jsonArray = JSON.parseArray(message);
            jsonArray.stream().map(object -> (JSONObject) object).forEach(jsonObject -> {
                String id = jsonObject.getString("id");
                jsonObject.put("value", (int) (1 + Math.random() * (100)));
            });
            String jsonString = jsonArray.toString();

            //群发消息
            for (WebSocketServer item : webSocketSet) {
                try {
                    item.sendMessage(jsonString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, initialDelay, period, TimeUnit.SECONDS);

    }

    /**
     *     *
     *     * @param session
     *     * @param error
     *    
     */

    @OnError
    public void onError(Session session, Throwable error) {
        //log.error("发生错误");
        error.printStackTrace();
    }
    /**
         * 实现服务器主动推送
         */
    public void sendMessage(String message)throws IOException{
        this.session.getBasicRemote().sendText(message);}
    public static void sendInfo (String message) throws IOException {
        System.out.println("------WebSocketServer----------sendInfo-----");
        //log.info("推送消息到窗口" + sid + "，推送内容:" + message);
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送

                item.sendMessage(message);

            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount () {
        return onlineCount;
    }

    public static synchronized void addOnlineCount () {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount () {
        WebSocketServer.onlineCount--;
    }
}
