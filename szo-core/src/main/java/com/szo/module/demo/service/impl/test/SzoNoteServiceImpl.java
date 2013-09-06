package com.szo.module.demo.service.impl.test;

import com.szo.core.common.service.impl.CommonServiceImpl;
import com.szo.module.demo.service.test.SzoNoteServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("szoNoteService")
@Transactional
public class SzoNoteServiceImpl extends CommonServiceImpl implements SzoNoteServiceI {

}