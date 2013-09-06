package com.szo.module.demo.service.impl.test;

import com.szo.core.common.service.impl.CommonServiceImpl;
import com.szo.core.util.ContextHolderUtils;
import com.szo.core.util.FileUtils;
import com.szo.module.demo.entity.test.TFinanceEntity;
import com.szo.module.demo.entity.test.TFinanceFilesEntity;
import com.szo.module.demo.service.test.TFinanceServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("tFinanceService")
@Transactional
public class TFinanceServiceImpl extends CommonServiceImpl implements
        TFinanceServiceI {

    /**
     * 附件删除
     */
    public void deleteFile(TFinanceFilesEntity file) {

        // [1].删除附件
        String sql = "select * from t_s_attachment where id = ?";
        Map<String, Object> attachmentMap = commonDao.findOneForJdbc(sql,
                file.getId());
        // 附件相对路径
        String realpath = (String) attachmentMap.get("realpath");
        String fileName = FileUtils.getFilePrefix2(realpath);

        // 获取部署项目绝对地址
        String realPath = ContextHolderUtils.getSession().getServletContext()
                .getRealPath("/");
        FileUtils.delete(realPath + realpath);
        FileUtils.delete(realPath + fileName + ".pdf");
        FileUtils.delete(realPath + fileName + ".swf");
        // [2].删除数据
        commonDao.delete(file);
    }

    /**
     * 资金管理删除
     */
    public void deleteFinance(TFinanceEntity finance) {
        // [1].上传附件删除
        String attach_sql = "select * from t_s_attachment where id in (select id from t_finance_files where financeId = ?)";
        List<Map<String, Object>> attachmentList = commonDao.findForJdbc(
                attach_sql, finance.getId());
        for (Map<String, Object> map : attachmentList) {
            // 附件相对路径
            String realpath = (String) map.get("realpath");
            String fileName = FileUtils.getFilePrefix2(realpath);

            // 获取部署项目绝对地址
            String realPath = ContextHolderUtils.getSession()
                    .getServletContext().getRealPath("/");
            FileUtils.delete(realPath + realpath);
            FileUtils.delete(realPath + fileName + ".pdf");
            FileUtils.delete(realPath + fileName + ".swf");
        }
        // [2].删除资金管理
        commonDao.delete(finance);
    }

}