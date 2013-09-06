package com.szo.module.demo.service.test;

import com.szo.core.common.service.CommonService;
import com.szo.module.demo.entity.test.WebOfficeEntity;
import org.springframework.web.multipart.MultipartFile;

public interface WebOfficeServiceI extends CommonService {
    public void saveObj(WebOfficeEntity docObj, MultipartFile file);
}
