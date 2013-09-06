package com.szo.module.demo.page;

import com.szo.module.demo.entity.test.SzoOrderCustomEntity;
import com.szo.module.demo.entity.test.SzoOrderProductEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangdaihao
 * @version V1.0
 * @Title: Entity
 * @Description: 订单信息 VO
 * @date 2013-03-19 22:01:34
 */
@SuppressWarnings("serial")
public class SzoOrderMainPage implements java.io.Serializable {
    /**
     * 订单客户明细
     */
    private List<SzoOrderCustomEntity> szoOrderCustomList = new ArrayList<SzoOrderCustomEntity>();

    public List<SzoOrderCustomEntity> getSzoOrderCustomList() {
        return szoOrderCustomList;
    }

    public void setSzoOrderCustomList(
            List<SzoOrderCustomEntity> szoOrderCustomList) {
        this.szoOrderCustomList = szoOrderCustomList;
    }

    /**
     * 订单产品明细
     */
    private List<SzoOrderProductEntity> szoOrderProductList = new ArrayList<SzoOrderProductEntity>();

    public List<SzoOrderProductEntity> getSzoOrderProductList() {
        return szoOrderProductList;
    }

    public void setSzoOrderProductList(
            List<SzoOrderProductEntity> szoOrderProductList) {
        this.szoOrderProductList = szoOrderProductList;
    }
}
