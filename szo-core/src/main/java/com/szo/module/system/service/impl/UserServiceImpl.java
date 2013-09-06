package com.szo.module.system.service.impl;

import com.szo.core.common.service.impl.CommonServiceImpl;
import com.szo.module.system.pojo.base.TSUser;
import com.szo.module.system.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userService")
@Transactional
public class UserServiceImpl extends CommonServiceImpl implements UserService {

    public TSUser checkUserExits(TSUser user) {
        return this.commonDao.getUserByUserIdAndUserNameExits(user);
    }

    public String getUserRole(TSUser user) {
        return this.commonDao.getUserRole(user);
    }

    @Override
    public void pwdInit(TSUser user, String newPwd) {
        this.commonDao.pwdInit(user, newPwd);
    }

}
