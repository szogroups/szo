package com.szo.module.demo.service.test;

import com.szo.core.common.service.CommonService;
import com.szo.module.demo.entity.test.TFinanceEntity;
import com.szo.module.demo.entity.test.TFinanceFilesEntity;

public interface TFinanceServiceI extends CommonService {

    void deleteFile(TFinanceFilesEntity file);

    void deleteFinance(TFinanceEntity finance);

}
