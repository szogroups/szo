package com.szo.module.system.pojo.base;
// default package

import com.szo.core.common.entity.IdEntity;

import javax.persistence.*;

/**
 * TConfig entity. @author MyEclipse Persistence Tools
 * 系统配置类
 */
@Entity
@Table(name = "t_s_config")
public class TSConfig extends IdEntity implements java.io.Serializable {

    private TSUser TSUser;
    private String code;
    private String name;
    private String contents;
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    public TSUser getTSUser() {
        return this.TSUser;
    }

    public void setTSUser(TSUser TSUser) {
        this.TSUser = TSUser;
    }

    @Column(name = "code", length = 100)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "content", length = 300)
    public String getContents() {
        return this.contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Column(name = "note", length = 300)
    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}