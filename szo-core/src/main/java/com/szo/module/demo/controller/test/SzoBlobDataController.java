package com.szo.module.demo.controller.test;

import com.szo.core.common.controller.BaseController;
import com.szo.core.common.hibernate.qbc.CriteriaQuery;
import com.szo.core.common.model.json.AjaxJson;
import com.szo.core.common.model.json.DataGrid;
import com.szo.core.constant.Globals;
import com.szo.core.tag.core.easyui.TagUtil;
import com.szo.core.util.ExceptionUtil;
import com.szo.core.util.MyBeanUtils;
import com.szo.core.util.StringUtil;
import com.szo.module.demo.entity.test.SzoBlobDataEntity;
import com.szo.module.demo.service.test.SzoBlobDataServiceI;
import com.szo.module.system.service.SystemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Map;

/**
 * @author Quainty
 * @version V1.0
 * @Title: Controller
 * @Description: Blob型数据操作例子
 * @date 2013-06-07 14:46:08
 */
@Controller
@RequestMapping("/szoBlobDataController")
public class SzoBlobDataController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(SzoBlobDataController.class);

    @Autowired
    private SzoBlobDataServiceI szoBlobDataService;
    @Autowired
    private SystemService systemService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Blob型数据操作例子列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "szoBlobData")
    public ModelAndView szoBlobData(HttpServletRequest request) {
        return new ModelAndView("szo/demo/test/szoBlobDataList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(SzoBlobDataEntity szoBlobData,
                         HttpServletRequest request, HttpServletResponse response,
                         DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(SzoBlobDataEntity.class, dataGrid);
        // 查询条件组装器
        com.szo.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
                szoBlobData);
        this.szoBlobDataService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除Blob型数据操作例子
     *
     * @return
     */
    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson del(SzoBlobDataEntity szoBlobData,
                        HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        szoBlobData = systemService.getEntity(SzoBlobDataEntity.class,
                szoBlobData.getId());
        message = "删除成功";
        szoBlobDataService.delete(szoBlobData);
        systemService.addLog(message, Globals.Log_Type_DEL,
                Globals.Log_Leavel_INFO);

        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "download")
    public void exportXls(HttpServletRequest request, String fileId,
                          HttpServletResponse response) {
        // 从数据库取得数据
        SzoBlobDataEntity obj = systemService.getEntity(
                SzoBlobDataEntity.class, fileId);
        try {
            Blob attachment = obj.getAttachmentcontent();
            response.setContentType("application/x-msdownload;");
            response.setHeader(
                    "Content-disposition",
                    "attachment; filename="
                            + new String((obj.getAttachmenttitle() + "." + obj
                            .getExtend()).getBytes("GBK"), "ISO8859-1"));
            // 从数据库中读取出来 , 输出给下载用
            InputStream bis = attachment.getBinaryStream();
            BufferedOutputStream bos = new BufferedOutputStream(
                    response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            long lTotalLen = 0;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
                lTotalLen += bytesRead;
            }
            response.setHeader("Content-Length", String.valueOf(lTotalLen));
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(params = "upload")
    @ResponseBody
    public AjaxJson upload(HttpServletRequest request, String documentTitle,
                           HttpServletResponse response) {
        AjaxJson j = new AjaxJson();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            try {
                szoBlobDataService.saveObj(documentTitle, file);
                j.setMsg("文件导入成功！");
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                logger.error(ExceptionUtil.getExceptionMessage(e));
            }
            // break; // 不支持多个文件导入？
        }

        return j;
    }

    /**
     * 添加Blob型数据操作例子
     *
     * @param ids
     * @return
     */
    @RequestMapping(params = "save")
    @ResponseBody
    public AjaxJson save(SzoBlobDataEntity szoBlobData,
                         HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(szoBlobData.getId())) {
            message = "更新成功";
            SzoBlobDataEntity t = szoBlobDataService.get(
                    SzoBlobDataEntity.class, szoBlobData.getId());
            try {
                MyBeanUtils.copyBeanNotNull2Bean(szoBlobData, t);
                szoBlobDataService.saveOrUpdate(t);
                systemService.addLog(message, Globals.Log_Type_UPDATE,
                        Globals.Log_Leavel_INFO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            message = "添加成功";
            szoBlobDataService.save(szoBlobData);
            systemService.addLog(message, Globals.Log_Type_INSERT,
                    Globals.Log_Leavel_INFO);
        }

        return j;
    }

    /**
     * Blob型数据操作例子列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "addorupdate")
    public ModelAndView addorupdate(SzoBlobDataEntity szoBlobData,
                                    HttpServletRequest req) {
        if (StringUtil.isNotEmpty(szoBlobData.getId())) {
            szoBlobData = szoBlobDataService.getEntity(SzoBlobDataEntity.class,
                    szoBlobData.getId());
            req.setAttribute("szoBlobDataPage", szoBlobData);
        }
        return new ModelAndView("szo/demo/test/szoBlobData");
    }
}
