package com.szo.module.system.pojo.base;

import com.szo.core.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 角色表
 */
@Entity
@Table(name = "t_s_role")
public class TSRole extends IdEntity implements java.io.Serializable {
    private String roleName;//角色名称
    private String roleCode;//角色编码

    @Column(name = "rolename", nullable = false, length = 100)
    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column(name = "rolecode", length = 10)
    public String getRoleCode() {
        return this.roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}