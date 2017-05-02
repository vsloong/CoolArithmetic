package com.zxxxy.coolarithmetic.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 闯关等级的实体类
 * Created by CooLoongWu on 2017-4-19 16:33.
 */
@Entity
public class Advance {

    @Id
    private Long id;
    private int grade;      //年级
    private int advance;    //闯关等级

    @Generated(hash = 855832688)
    public Advance(Long id, int grade, int advance) {
        this.id = id;
        this.grade = grade;
        this.advance = advance;
    }

    @Generated(hash = 810441784)
    public Advance() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGrade() {
        return this.grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getAdvance() {
        return this.advance;
    }

    public void setAdvance(int advance) {
        this.advance = advance;
    }
}
