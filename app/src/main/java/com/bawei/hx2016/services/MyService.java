package com.bawei.hx2016.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bawei.hx2016.ChatActivity;
import com.bawei.hx2016.R;
import com.bawei.hx2016.RequestFriendActivity;
import com.bawei.hx2016.adapater.ChatRecyalerViewAdapater;
import com.bawei.hx2016.bean.FriendBean;
import com.bawei.hx2016.interfaces.SingleCharListener;
import com.bawei.hx2016.utils.DBUtils;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import org.xutils.ex.DbException;

import java.util.List;

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
        builder.setTicker(message);
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
     * 发送一个通知
     *
     * @param username
     * @param message
     * @param activity
     */
    private void sendRequestNotification(String username, String message, Class activity) {
        //得到通知管理类
        NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        //创建一个新通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //设置builder
        builder.setTicker(message);
        builder.setContentText(message);
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_ALL);

        Intent intent = new Intent(this, activity);
        intent.putExtra("requestUserName", username);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);

        //发送通知
        notificationManager.notify(659, builder.build());
    }

    private void sendEmptyNotification() {
        //得到通知管理类
        NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        //创建一个新通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        //发送通知
        notificationManager.notify(666, builder.build());
    }

    /**
     * 单人聊天的监听
     */
    private EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            if (isShow && singleCharListener != null) {
                singleCharListener.receiveMessage(messages);
            } else {
                sendNotification(messages.get(0).getUserName(), "你接收到" + messages.size() + "条消息", ChatActivity.class);
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };


    private SingleCharListener singleCharListener;
    /**
     * 是否正在聊天界面
     */
    private boolean isShow = false;


    /**
     * 初始化单人聊天的监听
     */
    private void initSingleChatListener() {
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
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
                sendRequestNotification(username, username + "同意了你的请求,找他聊聊天吧", ChatActivity.class);
            }

            @Override
            public void onContactRefused(String username) {
                //好友请求被拒绝
                Log.i(TAG, "onContactAgreed: " + username);
            }

            @Override
            public void onContactInvited(String username, String reason) {
                //收到好友邀请
                sendRequestNotification(username, username + "请求加你为好友", RequestFriendActivity.class);
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
        //添加单人聊天监听
        initSingleChatListener();
    }

    @Override
    public MyBinder onBind(Intent intent) {
//        throw new UnsupportedOperationException("Not yet implemented");
        return new MyBinder();
    }

    public class MyBinder extends Binder {

        /**
         * 设置在前台 后台
         *
         * @param show
         */
        public void setShow(boolean show) {
            isShow = show;
        }


        /**
         * 添加接收消息的监听
         *
         * @param singleCharListener
         */
        public void setSingleCharListener(SingleCharListener singleCharListener) {
            MyService.this.singleCharListener = singleCharListener;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }
}
