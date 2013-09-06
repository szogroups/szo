package com.szo.module.demo.service.impl.test;

import com.szo.core.common.service.impl.CommonServiceImpl;
import com.szo.module.demo.entity.test.SzoOrderCustomEntity;
import com.szo.module.demo.entity.test.SzoOrderMainEntity;
import com.szo.module.demo.entity.test.SzoOrderProductEntity;
import com.szo.module.demo.service.test.SzoOrderMainServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("szoOrderMainService")
@Transactional
public class SzoOrderMainServiceImpl extends CommonServiceImpl implements
        SzoOrderMainServiceI {

    @Override
    public void addMain(SzoOrderMainEntity szoOrderMain,
                        List<SzoOrderProductEntity> szoOrderProducList,
                        List<SzoOrderCustomEntity> szoOrderCustomList) {
        // 保存订单主信息
        this.save(szoOrderMain);
        // 保存订单产品明细
        for (SzoOrderProductEntity product : szoOrderProducList) {
            // 外键设置
            product.setGoOrderCode(szoOrderMain.getGoOrderCode());
            this.save(product);
        }
        // 保存订单客户明细
        for (SzoOrderCustomEntity custom : szoOrderCustomList) {
            // 外键设置
            custom.setGoOrderCode(szoOrderMain.getGoOrderCode());
            this.save(custom);
        }
    }

    @Override
    public void updateMain(SzoOrderMainEntity szoOrderMain,
                           List<SzoOrderProductEntity> szoOrderProducList,
                           List<SzoOrderCustomEntity> szoOrderCustomList,
                           boolean szoOrderCustomShow) {
        // 保存订单主信息
        this.saveOrUpdate(szoOrderMain);
        // 删除订单产品明细
        this.commonDao.deleteAllEntitie(this.findByProperty(
                SzoOrderProductEntity.class, "goOrderCode",
                szoOrderMain.getGoOrderCode()));
        // 保存订单产品明细
        for (SzoOrderProductEntity product : szoOrderProducList) {
            // 外键设置
            product.setGoOrderCode(szoOrderMain.getGoOrderCode());
            this.save(product);
        }
        if (szoOrderCustomShow) {
            // 删除订单客户明细
            this.commonDao.deleteAllEntitie(this.findByProperty(
                    SzoOrderCustomEntity.class, "goOrderCode",
                    szoOrderMain.getGoOrderCode()));
            // 保存订单客户明细
            for (SzoOrderCustomEntity custom : szoOrderCustomList) {
                // 外键设置
                custom.setGoOrderCode(szoOrderMain.getGoOrderCode());
                this.save(custom);
            }
        }
    }

    @Override
    public void delMain(SzoOrderMainEntity szoOrderMain) {
        // 删除主表信息
        this.delete(szoOrderMain);
        // 删除订单产品明细
        this.deleteAllEntitie(this.findByProperty(SzoOrderProductEntity.class,
                "goOrderCode", szoOrderMain.getGoOrderCode()));
        // 删除订单客户明细
        this.commonDao.deleteAllEntitie(this.findByProperty(
                SzoOrderCustomEntity.class, "goOrderCode",
                szoOrderMain.getGoOrderCode()));
    }
}