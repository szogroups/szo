package com.szo.module.system.pojo.base;
// default package

import com.szo.core.common.entity.IdEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TTypegroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_s_typegroup")
public class TSTypegroup extends IdEntity implements java.io.Serializable {
    public static Map<String, TSTypegroup> allTypeGroups = new HashMap<String, TSTypegroup>();
    public static Map<String, List<TSType>> allTypes = new HashMap<String, List<TSType>>();


    private String typegroupname;
    private String typegroupcode;
    private List<TSType> TSTypes = new ArrayList<TSType>();

    @Column(name = "typegroupname", length = 50)
    public String getTypegroupname() {
        return this.typegroupname;
    }

    public void setTypegroupname(String typegroupname) {
        this.typegroupname = typegroupname;
    }

    @Column(name = "typegroupcode", length = 50)
    public String getTypegroupcode() {
        return this.typegroupcode;
    }

    public void setTypegroupcode(String typegroupcode) {
        this.typegroupcode = typegroupcode;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSTypegroup")
    public List<TSType> getTSTypes() {
        return this.TSTypes;
    }

    public void setTSTypes(List<TSType> TSTypes) {
        this.TSTypes = TSTypes;
    }

}