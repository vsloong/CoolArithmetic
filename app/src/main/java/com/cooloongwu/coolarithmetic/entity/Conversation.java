package com.cooloongwu.coolarithmetic.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 会话的实体类
 * Created by CooLoongWu on 2017-4-26 17:02.
 */
@Entity
public class Conversation {

    @Id
    private Long id;

    private int unReadNum;          //未读消息数
    private String name;            //用户名或系统名
    private String content;         //内容
    private String type;            //消息的类型（system//friend//group）
    private String avatar;          //头像
    private String remark;          //备注，系统消息用来备注url
    private long time;            //消息的时间

    @Generated(hash = 782059669)
    public Conversation(Long id, int unReadNum, String name, String content,
                        String type, String avatar, String remark, long time) {
        this.id = id;
        this.unReadNum = unReadNum;
        this.name = name;
        this.content = content;
        this.type = type;
        this.avatar = avatar;
        this.remark = remark;
        this.time = time;
    }
    @Generated(hash = 1893991898)
    public Conversation() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getUnReadNum() {
        return this.unReadNum;
    }
    public void setUnReadNum(int unReadNum) {
        this.unReadNum = unReadNum;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }


}
