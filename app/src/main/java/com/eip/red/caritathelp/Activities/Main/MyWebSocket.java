package com.eip.red.caritathelp.Activities.Main;

import android.os.Build;

import com.eip.red.caritathelp.Activities.Main.ViewPager.MyPagerAdapter;
import com.eip.red.caritathelp.Activities.Main.ViewPager.MySecondPage;
import com.eip.red.caritathelp.Activities.Main.ViewPager.MyThirdPage;
import com.eip.red.caritathelp.Models.Notifications.Notification;
import com.eip.red.caritathelp.Models.User.User;
import com.eip.red.caritathelp.Models.mailbox.WebSocketMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by pierr on 04/07/2016.
 */

public class MyWebSocket {

    private MyPagerAdapter  pagerAdapter;
    private WebSocketClient webSocketClient;

    public MyWebSocket(MyPagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
    }

    public void connectWebSocket(final String userUid) {
        URI uri;

        try {
//            uri = new URI("ws://api.caritathelp.me:8080");
            uri = new URI("ws://ws.staging.caritathelp.me");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                JsonObject json = new JsonObject();

                json.addProperty("token", "token");
//                json.addProperty("token_user", token);
                json.addProperty("user_uid", userUid);
                webSocketClient.send(json.toString());
            }


            @Override
            public void onMessage(String json) {
                System.out.println("****************************************** MSG : " + json);

                Gson gson = new Gson();
                Notification notification = gson.fromJson(json, Notification.class);
//
                if (notification != null)
                    ((MyThirdPage) pagerAdapter.getFragment(2)).getNotificationsView().getPresenter().onMessage(notification);
                else {
                    WebSocketMessage webSocketMessage = gson.fromJson(json, WebSocketMessage.class);
                    ((MySecondPage) pagerAdapter.getFragment(1)).getMailBoxView().getPresenter().onMessage(json);
                }
//
//                ((MySecondPage) pagerAdapter.getFragment(1)).getMailBoxView().getPresenter().onMessage(s);

                /* DEBUG */
//                System.out.println("Websocket MSG : " + s);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
            }

            @Override
            public void onError(Exception e) {
            }
        };
        webSocketClient.connect();
    }

    public void sendMessage() {
//        webSocketClient.send("COCO");
    }
}
