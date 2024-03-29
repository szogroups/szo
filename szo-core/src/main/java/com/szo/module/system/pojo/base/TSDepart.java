package com.szo.module.system.pojo.base;

import com.szo.core.common.entity.IdEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门机构表
 */
@Entity
@Table(name = "t_s_depart")
public class TSDepart extends IdEntity implements java.io.Serializable {
    private TSDepart TSPDepart;//上级部门
    private String departname;//部门名称
    private String description;//部门描述
    private List<TSDepart> TSDeparts = new ArrayList<TSDepart>();//下属部门

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentdepartid")
    public TSDepart getTSPDepart() {
        return this.TSPDepart;
    }

    public void setTSPDepart(TSDepart TSPDepart) {
        this.TSPDepart = TSPDepart;
    }

    @Column(name = "departname", nullable = false, length = 100)
    public String getDepartname() {
        return this.departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }

    @Column(name = "description", length = 500)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSPDepart")
    public List<TSDepart> getTSDeparts() {
        return TSDeparts;
    }

    public void setTSDeparts(List<TSDepart> tSDeparts) {
        TSDeparts = tSDeparts;
    }
}