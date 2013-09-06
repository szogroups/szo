package com.szo.module.system.service;

import com.szo.core.common.service.CommonService;
import com.szo.module.system.pojo.base.TSUser;

public interface UserService extends CommonService {

    public TSUser checkUserExits(TSUser user);

    public String getUserRole(TSUser user);

    public void pwdInit(TSUser user, String newPwd);
}
