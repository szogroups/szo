package com.szo.module.demo.entity.test;

import com.szo.module.system.pojo.base.TSAttachment;

import javax.persistence.*;

/**
 * @author tanghong
 * @version V1.0
 * @Title: Entity
 * @Description: 资金管理附件
 * @date 2013-06-26 23:57:04
 */
@Entity
@Table(name = "t_finance_files", schema = "")
@PrimaryKeyJoinColumn(name = "id")
@SuppressWarnings("serial")
public class TFinanceFilesEntity extends TSAttachment implements
        java.io.Serializable {

    private TFinanceEntity finance;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "financeId")
    public TFinanceEntity getFinance() {
        return finance;
    }

    public void setFinance(TFinanceEntity finance) {
        this.finance = finance;
    }

}
