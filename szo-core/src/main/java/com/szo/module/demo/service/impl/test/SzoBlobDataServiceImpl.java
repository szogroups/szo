package com.szo.module.demo.service.impl.test;

import com.szo.core.common.service.impl.CommonServiceImpl;
import com.szo.module.demo.entity.test.SzoBlobDataEntity;
import com.szo.module.demo.service.test.SzoBlobDataServiceI;
import org.hibernate.LobHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;

@Service("szoBlobDataService")
@Transactional
public class SzoBlobDataServiceImpl extends CommonServiceImpl implements SzoBlobDataServiceI {
    @Override
    public void saveObj(String documentTitle, MultipartFile file) {
        SzoBlobDataEntity obj = new SzoBlobDataEntity();
        LobHelper lobHelper = commonDao.getSession().getLobHelper();
        Blob data;
        try {
            data = lobHelper.createBlob(file.getInputStream(), 0);
            obj.setAttachmentcontent(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        obj.setAttachmenttitle(documentTitle);
        String sFileName = file.getOriginalFilename();
        int iPos = sFileName.lastIndexOf('.');
        if (iPos >= 0) {
            obj.setExtend(sFileName.substring(iPos + 1));
        }
        super.save(obj);
    }
}