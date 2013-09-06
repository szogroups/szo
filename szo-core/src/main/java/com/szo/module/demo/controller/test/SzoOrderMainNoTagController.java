package com.szo.module.demo.controller.test;

import com.szo.core.common.controller.BaseController;
import com.szo.core.common.hibernate.qbc.CriteriaQuery;
import com.szo.core.common.model.json.AjaxJson;
import com.szo.core.common.model.json.DataGrid;
import com.szo.core.constant.Globals;
import com.szo.core.tag.core.easyui.TagUtil;
import com.szo.core.util.StringUtil;
import com.szo.module.demo.entity.test.SzoOrderCustomEntity;
import com.szo.module.demo.entity.test.SzoOrderMainEntity;
import com.szo.module.demo.entity.test.SzoOrderProductEntity;
import com.szo.module.demo.page.SzoOrderMainPage;
import com.szo.module.demo.service.test.SzoOrderMainServiceI;
import com.szo.module.system.service.SystemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zhangdaihao
 * @version V1.0
 * @Title: Controller
 * @Description: 订单信息
 * @date 2013-03-19 22:01:34
 */
@Controller
@RequestMapping("/szoOrderMainNoTagController")
public class SzoOrderMainNoTagController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(SzoOrderMainController.class);

    @Autowired
    private SzoOrderMainServiceI szoOrderMainService;
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
     * 订单信息列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "szoOrderMainNoTag")
    public ModelAndView szoOrderMain(HttpServletRequest request) {
        return new ModelAndView("szo/demo/notag/szoOrderMainListNoTag");
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
    public void datagrid(HttpServletRequest request,
                         HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(SzoOrderMainEntity.class, dataGrid);
        this.szoOrderMainService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除订单信息
     *
     * @return
     */
    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson del(SzoOrderMainEntity szoOrderMain,
                        HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        szoOrderMain = systemService.getEntity(SzoOrderMainEntity.class,
                szoOrderMain.getId());
        message = "删除成功";
        szoOrderMainService.delMain(szoOrderMain);
        systemService.addLog(message, Globals.Log_Type_DEL,
                Globals.Log_Leavel_INFO);
        j.setMsg(message);
        return j;
    }

    /**
     * 添加订单及明细信息
     *
     * @param ids
     * @return
     */
    @RequestMapping(params = "save")
    @ResponseBody
    public AjaxJson save(SzoOrderMainEntity szoOrderMain,
                         SzoOrderMainPage szoOrderMainPage, HttpServletRequest request) {
        List<SzoOrderProductEntity> szoOrderProducList = szoOrderMainPage
                .getSzoOrderProductList();
        List<SzoOrderCustomEntity> szoOrderCustomList = szoOrderMainPage
                .getSzoOrderCustomList();
        Boolean szoOrderCustomShow = "true".equals(request
                .getParameter("szoOrderCustomShow"));
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(szoOrderMain.getId())) {
            message = "更新成功";
            szoOrderMainService.updateMain(szoOrderMain, szoOrderProducList,
                    szoOrderCustomList, szoOrderCustomShow);
            systemService.addLog(message, Globals.Log_Type_UPDATE,
                    Globals.Log_Leavel_INFO);
        } else {
            message = "添加成功";
            szoOrderMainService.addMain(szoOrderMain, szoOrderProducList,
                    szoOrderCustomList);
            systemService.addLog(message, Globals.Log_Type_INSERT,
                    Globals.Log_Leavel_INFO);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 订单信息列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "addorupdateNoTag")
    public ModelAndView addorupdate(SzoOrderMainEntity szoOrderMain,
                                    HttpServletRequest req) {
        if (StringUtil.isNotEmpty(szoOrderMain.getId())) {
            szoOrderMain = szoOrderMainService.getEntity(
                    SzoOrderMainEntity.class, szoOrderMain.getId());
            req.setAttribute("szoOrderMainPage", szoOrderMain);
        }
        if (StringUtil.isNotEmpty(szoOrderMain.getGoOrderCode())) {
            List<SzoOrderProductEntity> szoOrderProductEntityList = szoOrderMainService
                    .findByProperty(SzoOrderProductEntity.class, "goOrderCode",
                            szoOrderMain.getGoOrderCode());
            req.setAttribute("szoOrderProductList", szoOrderProductEntityList);
        }
        if (StringUtil.isNotEmpty(szoOrderMain.getGoOrderCode())) {
            List<SzoOrderCustomEntity> szoSzoOrderCustomEntityList = szoOrderMainService
                    .findByProperty(SzoOrderCustomEntity.class, "goOrderCode",
                            szoOrderMain.getGoOrderCode());
            req.setAttribute("szoOrderCustomList", szoSzoOrderCustomEntityList);
        }
        return new ModelAndView("szo/demo/notag/szoOrderMainNoTag");
    }
}
