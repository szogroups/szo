package com.szo.module.demo.controller.test;

import com.szo.core.common.controller.BaseController;
import com.szo.core.common.hibernate.qbc.CriteriaQuery;
import com.szo.core.common.model.json.AjaxJson;
import com.szo.core.common.model.json.DataGrid;
import com.szo.core.constant.Globals;
import com.szo.core.tag.core.easyui.TagUtil;
import com.szo.core.util.StringUtil;
import com.szo.module.demo.entity.test.SzoNoteEntity;
import com.szo.module.demo.service.test.SzoNoteServiceI;
import com.szo.module.system.service.SystemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangdaihao
 * @version V1.0
 * @Title: Controller
 * @Description: 公告
 * @date 2013-03-12 14:06:34
 */
@Controller
@RequestMapping("/szoNoteController")
public class SzoNoteController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(SzoNoteController.class);

    @Autowired
    private SzoNoteServiceI szoNoteService;
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
     * 公告列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "szoNote")
    public ModelAndView szoNote(HttpServletRequest request) {
        return new ModelAndView("szo/demo/test/szoNoteList");
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
    public void datagrid(SzoNoteEntity szoNote, HttpServletRequest request,
                         HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(SzoNoteEntity.class, dataGrid);
        // 查询条件组装器
        com.szo.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
                szoNote);
        this.szoNoteService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除公告
     *
     * @return
     */
    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson del(SzoNoteEntity szoNote, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        szoNote = systemService.getEntity(SzoNoteEntity.class, szoNote.getId());
        message = "删除成功";
        szoNoteService.delete(szoNote);
        systemService.addLog(message, Globals.Log_Type_DEL,
                Globals.Log_Leavel_INFO);

        j.setMsg(message);
        return j;
    }

    /**
     * 添加公告
     *
     * @param ids
     * @return
     */
    @RequestMapping(params = "save")
    @ResponseBody
    public AjaxJson save(SzoNoteEntity szoNote, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(szoNote.getId())) {
            message = "更新成功";
            szoNoteService.saveOrUpdate(szoNote);
            systemService.addLog(message, Globals.Log_Type_UPDATE,
                    Globals.Log_Leavel_INFO);
        } else {
            message = "添加成功";
            szoNoteService.save(szoNote);
            systemService.addLog(message, Globals.Log_Type_INSERT,
                    Globals.Log_Leavel_INFO);
        }

        return j;
    }

    /**
     * 公告列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "addorupdate")
    public ModelAndView addorupdate(SzoNoteEntity szoNote,
                                    HttpServletRequest req) {
        if (StringUtil.isNotEmpty(szoNote.getId())) {
            szoNote = szoNoteService.getEntity(SzoNoteEntity.class,
                    szoNote.getId());
            req.setAttribute("szoNotePage", szoNote);
        }
        return new ModelAndView("szo/demo/test/szoNote");
    }
}
