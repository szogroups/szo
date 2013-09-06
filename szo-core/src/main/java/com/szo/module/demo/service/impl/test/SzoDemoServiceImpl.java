package com.szo.module.demo.service.impl.test;

import com.szo.core.common.service.impl.CommonServiceImpl;
import com.szo.module.demo.service.test.SzoDemoServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("szoDemoService")
@Transactional
public class SzoDemoServiceImpl extends CommonServiceImpl implements SzoDemoServiceI {

}
