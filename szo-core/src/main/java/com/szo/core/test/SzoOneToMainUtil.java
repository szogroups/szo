package com.szo.core.test;

import org.jeecgframework.codegenerate.generate.onetomany.CodeGenerateOneToMany;
import org.jeecgframework.codegenerate.pojo.onetomany.CodeParamEntity;
import org.jeecgframework.codegenerate.pojo.onetomany.SubTableEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器入口【一对多】
 *
 * @author zhangdaihao
 */
public class SzoOneToMainUtil {

    /**
     * 一对多(父子表)数据模型，生成方法
     *
     * @param args
     */
    public static void main(String[] args) {
        // 第一步：设置主表
        CodeParamEntity codeParamEntityIn = new CodeParamEntity();
        codeParamEntityIn.setTableName("szo_order_main");// 主表[表名]
        codeParamEntityIn.setEntityName("OrderMain"); // 主表[实体名]
        codeParamEntityIn.setEntityPackage("szo"); // 主表[包名]
        codeParamEntityIn.setFtlDescription("订单主数据"); // 主表[描述]

        // 第二步：设置子表集合
        List<SubTableEntity> subTabParamIn = new ArrayList<SubTableEntity>();
        // [1].子表一
        SubTableEntity po = new SubTableEntity();
        po.setTableName("szo_order_custom");// 子表[表名]
        po.setEntityName("OrderCustoms"); // 子表[实体名]
        po.setEntityPackage("szo"); // 子表[包]
        po.setFtlDescription("订单客户明细"); // 子表[描述]
        // 子表[外键:与主表关联外键]
        // 说明：这里面的外键是子表的外键字段,非主表和子表的对应关系
        po.setForeignKeys(new String[]{"GORDER_ID", "GO_ORDER_CODE"});
        subTabParamIn.add(po);
        // [2].子表二
        SubTableEntity po2 = new SubTableEntity();
        po2.setTableName("szo_order_product"); // 子表[表名]
        po2.setEntityName("OrderProduct"); // 子表[实体名]
        po2.setEntityPackage("szo"); // 子表[包]
        po2.setFtlDescription("订单产品明细"); // 子表[描述]
        // 子表[外键:与主表关联外键]
        // 说明：这里面的外键是子表的外键字段,非主表和子表的对应关系
        po2.setForeignKeys(new String[]{"GORDER_ID", "GO_ORDER_CODE"});
        subTabParamIn.add(po2);
        codeParamEntityIn.setSubTabParam(subTabParamIn);

        // 第三步：一对多(父子表)数据模型,代码生成
        CodeGenerateOneToMany.oneToManyCreate(subTabParamIn, codeParamEntityIn);
    }
}
