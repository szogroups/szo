package com.szo.module.demo.service.impl.test;

import com.szo.core.common.service.impl.CommonServiceImpl;
import com.szo.module.demo.service.test.JpPersonServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jpPersonService")
@Transactional
public class JpPersonServiceImpl extends CommonServiceImpl implements
        JpPersonServiceI {

}