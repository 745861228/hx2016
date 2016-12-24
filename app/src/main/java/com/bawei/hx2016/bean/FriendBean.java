package com.bawei.hx2016.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * @author :   郗琛
 * @date :   2016/12/24
 */

@Table(name = "friendTb")
public class FriendBean {

    @Column(isId = true, name = "ID", autoGen = true)
    private int id;
    @Column(name = "NAME", property = "NOT NULL")
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FriendBean() {
    }

    public FriendBean(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
