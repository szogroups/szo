package com.szo.module.system.service.impl;

import com.szo.core.common.service.impl.CommonServiceImpl;
import com.szo.module.system.pojo.base.TSAttachment;
import com.szo.module.system.service.DeclareService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("declareService")
@Transactional
public class DeclareServiceImpl extends CommonServiceImpl implements DeclareService {

    public List<TSAttachment> getAttachmentsByCode(String businessKey, String description) {
        String hql = "from TSAttachment t where t.businessKey='" + businessKey + "' and t.description='" + description + "'";
        return commonDao.findByQueryString(hql);
    }

}
