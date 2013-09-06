package com.szo.module.demo.service.impl.test;

import com.szo.core.common.service.impl.CommonServiceImpl;
import com.szo.module.demo.service.test.SzoMatterBomServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <li>类型名称：
 * <li>说明：物料Bom业务接口实现类
 * <li>创建人： 温俊
 * <li>创建日期：2013-8-12
 * <li>修改人：
 * <li>修改日期：
 */
@Service("szoMatterBomService")
@Transactional
public class SzoMatterBomServiceImpl extends CommonServiceImpl implements
        SzoMatterBomServiceI {

}
