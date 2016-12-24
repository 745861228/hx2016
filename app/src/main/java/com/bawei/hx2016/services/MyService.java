package com.bawei.hx2016.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bawei.hx2016.ChatActivity;
import com.bawei.hx2016.R;
import com.bawei.hx2016.RequestFriendActivity;
import com.bawei.hx2016.bean.FriendBean;
import com.bawei.hx2016.utils.DBUtils;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

import org.xutils.ex.DbException;

import static android.content.ContentValues.TAG;

public class MyService extends Service {

    /**
     * 发送一个通知
     *
     * @param username
     * @param message
     * @param activity
     */
    private void sendNotification(String username, String message, Class activity) {
        //得到通知管理类
        NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        //创建一个新通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //设置builder
        builder.setTicker("好友请求");
        builder.setContentText(message);
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_ALL);

        Intent intent = new Intent(this, activity);
        intent.putExtra("userName", username);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);

        //发送通知
        notificationManager.notify(653, builder.build());
    }


    /**
     * 对服务器返回的好友状态进行监听
     */
    private void friendListener() {
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAgreed(String username) {
                Log.i(TAG, "onContactAgreed: " + username);
                //好友请求被同意
                //  sendNotification(username, username + "同意了你的请求,找他聊聊天吧", ChatActivity.class);
                try {
                    DBUtils.getDbUtilsInstance().saveOrUpdate(new FriendBean(username));
                } catch (DbException e) {
                    e.printStackTrace();
                }
                sendNotification(username, username + "同意了你的请求,找他聊聊天吧", ChatActivity.class);
            }

            @Override
            public void onContactRefused(String username) {
                //好友请求被拒绝
                Log.i(TAG, "onContactAgreed: " + username);
            }

            @Override
            public void onContactInvited(String username, String reason) {
                //收到好友邀请
                sendNotification(username, username + "请求加你为好友", RequestFriendActivity.class);
            }

            @Override
            public void onContactDeleted(String username) {
                //被删除时回调此方法
            }


            @Override
            public void onContactAdded(String username) {
                //增加了联系人时回调此方法
            }
        });
    }


    public MyService() {
        //添加好友状态监听事件
        friendListener();
    }

    @Override
    public IBinder onBind(Intent intent) {
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
}
