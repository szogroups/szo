package com.szo.module.system.service;

import com.szo.core.common.service.CommonService;
import com.szo.module.system.pojo.base.TSAttachment;

import java.util.List;


public interface DeclareService extends CommonService {

    public List<TSAttachment> getAttachmentsByCode(String businessKey, String description);

}
