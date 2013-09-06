package com.szo.core.common.model.common;

import com.szo.module.system.pojo.base.TSUser;

import java.io.Serializable;


public class SessionInfo implements Serializable {

    private TSUser user;

    public TSUser getUser() {
        return user;
    }

    public void setUser(TSUser user) {
        this.user = user;
    }

}
