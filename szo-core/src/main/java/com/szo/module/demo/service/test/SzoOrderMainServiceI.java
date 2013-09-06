package com.szo.module.demo.service.test;

import com.szo.core.common.service.CommonService;
import com.szo.module.demo.entity.test.SzoOrderCustomEntity;
import com.szo.module.demo.entity.test.SzoOrderMainEntity;
import com.szo.module.demo.entity.test.SzoOrderProductEntity;

import java.util.List;


public interface SzoOrderMainServiceI extends CommonService {
    /**
     * 添加一对多
     */
    public void addMain(SzoOrderMainEntity szoOrderMain, List<SzoOrderProductEntity> szoOrderProducList, List<SzoOrderCustomEntity> szoOrderCustomList);

    /**
     * 修改一对多
     */
    public void updateMain(SzoOrderMainEntity szoOrderMain, List<SzoOrderProductEntity> szoOrderProducList, List<SzoOrderCustomEntity> szoOrderCustomList, boolean szoOrderCustomShow);

    public void delMain(SzoOrderMainEntity szoOrderMain);
}
