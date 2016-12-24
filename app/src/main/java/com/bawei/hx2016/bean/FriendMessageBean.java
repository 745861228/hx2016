package com.bawei.hx2016.bean;

/**
 * @author :   郗琛
 * @date :   2016/12/24
 */

public class FriendMessageBean {
    private String name;
    private String message;

    public FriendMessageBean() {
    }

    public FriendMessageBean(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
