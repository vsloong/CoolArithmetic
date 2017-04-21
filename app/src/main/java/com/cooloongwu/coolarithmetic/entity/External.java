package com.cooloongwu.coolarithmetic.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * by y on 2016/12/12
 */
@Entity(nameInDb = "blacklist")
public class External {
    @Property(nameInDb = "id")
    private Integer id;
    @Property(nameInDb = "email")
    private String email;

    @Generated(hash = 589693698)
    public External(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    @Generated(hash = 773088188)
    public External() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
