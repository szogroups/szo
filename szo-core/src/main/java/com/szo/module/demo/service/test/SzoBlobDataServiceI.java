package com.szo.module.demo.service.test;

import com.szo.core.common.service.CommonService;
import org.springframework.web.multipart.MultipartFile;

public interface SzoBlobDataServiceI extends CommonService {
    public void saveObj(String documentTitle, MultipartFile file);

}
