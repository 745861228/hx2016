package com.bawei.hx2016.bean;

import com.hyphenate.chat.EMGroup;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.List;

/**
 * @author :   郗琛
 * @date :   2016/12/24
 */
@Table(name = "groupTb")
public class GroupBean {
    @Column(isId = true, name = "ID", autoGen = true)
    private int id;
    /**
     * 群组名
     */
    @Column(name = "GROUP_NAME", property = "NOT NULL")
    private String groupName;
    /**
     * 群组简介
     */
    @Column(name = "DESC", property = "NOT NULL")
    private String desc;
    /**
     * 群主名字
     */
    @Column(name = "OWNER", property = "NOT NULL")
    private String owner;

    /**
     * 群成员
     */
    @Column(name = "MEMBERS", property = "NOT NULL")
    private List<String> members;

    /**
     * 是否公开
     */
    @Column(name = "MEMBERS", property = "NOT NULL")
    private boolean isPublic;


    /**
     * 无参的
     */
    public GroupBean() {
    }

    /**
     * 基本信息
     *
     * @param groupName
     * @param desc
     * @param owner
     */
    public GroupBean(String groupName, String desc, String owner, boolean isPublic) {
        this.groupName = groupName;
        this.desc = desc;
        this.owner = owner;
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 保留
     *
     * @param groupName
     * @param desc
     * @param owner
     * @param members
     */
    public GroupBean(String groupName, String desc, String owner, boolean isPublic, List<String> members) {
        this.groupName = groupName;
        this.desc = desc;
        this.owner = owner;
        this.members = members;
        this.isPublic = isPublic;
    }
}
