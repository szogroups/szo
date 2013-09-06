package com.szo.module.demo.controller.test;

import com.szo.core.common.controller.BaseController;
import com.szo.core.common.model.json.AjaxJson;
import com.szo.core.common.model.json.DataGrid;
import com.szo.core.constant.Globals;
import com.szo.core.tag.core.easyui.TagUtil;
import com.szo.core.util.MyBeanUtils;
import com.szo.core.util.StringUtil;
import com.szo.module.demo.entity.test.SzoJdbcEntity;
import com.szo.module.demo.service.test.SzoJdbcServiceI;
import com.szo.module.system.service.SystemService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Quainty
 * @version V1.0
 * @Title: Controller
 * @Description: 通过JDBC访问数据库
 * @date 2013-05-20 13:18:38
 */
@Controller
@RequestMapping("/szoJdbcController")
public class SzoJdbcController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(SzoJdbcController.class);

    @Autowired
    private SzoJdbcServiceI szoJdbcService;
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
     * 通过JDBC访问数据库列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "szoJdbc")
    public ModelAndView szoJdbc(HttpServletRequest request) {
        return new ModelAndView("szo/demo/test/szoJdbcList");
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
    public void datagrid(SzoJdbcEntity szoJdbc, HttpServletRequest request,
                         HttpServletResponse response, DataGrid dataGrid) {
        // 方式1, 用底层自带的方式往对象中设值 -------------------
        /*
		 * this.szoJdbcService.getDatagrid1(szoJdbc, dataGrid);
		 * TagUtil.datagrid(response, dataGrid); // end of 方式1
		 * =========================================
		 */

        // 方式2, 取值自己处理(代码量多一些，但执行效率应该会稍高一些) -------------------------------
		/*
		 * this.szoJdbcService.getDatagrid2(szoJdbc, dataGrid);
		 * TagUtil.datagrid(response, dataGrid); // end of 方式2
		 * =========================================
		 */

        // 方式3, 取值进一步自己处理(直接转换成easyUI的datagrid需要的东西，执行效率最高，最自由)
        // -------------------------------
        // *
        JSONObject jObject = this.szoJdbcService
                .getDatagrid3(szoJdbc, dataGrid);
        responseDatagrid(response, jObject);
        // end of 方式3 ========================================= */
    }

    /**
     * 删除通过JDBC访问数据库
     *
     * @return
     */
    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson del(SzoJdbcEntity szoJdbc, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();

        String sql = "delete from szo_demo where id='" + szoJdbc.getId() + "'";
        szoJdbcService.executeSql(sql);

        message = "删除成功";
        systemService.addLog(message, Globals.Log_Type_DEL,
                Globals.Log_Leavel_INFO);

        j.setMsg(message);
        return j;
    }

    /**
     * 添加通过JDBC访问数据库
     *
     * @param ids
     * @return
     */
    @RequestMapping(params = "save")
    @ResponseBody
    public AjaxJson save(SzoJdbcEntity szoJdbc, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(szoJdbc.getId())) {
            message = "更新成功";
            SzoJdbcEntity t = szoJdbcService.get(SzoJdbcEntity.class,
                    szoJdbc.getId());
            try {
                MyBeanUtils.copyBeanNotNull2Bean(szoJdbc, t);
                szoJdbcService.saveOrUpdate(t);
                systemService.addLog(message, Globals.Log_Type_UPDATE,
                        Globals.Log_Leavel_INFO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            message = "添加成功";
            szoJdbcService.save(szoJdbc);
            systemService.addLog(message, Globals.Log_Type_INSERT,
                    Globals.Log_Leavel_INFO);
        }

        return j;
    }

    /**
     * 通过JDBC访问数据库列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "addorupdate")
    public ModelAndView addorupdate(SzoJdbcEntity szoJdbc,
                                    HttpServletRequest req) {
        if (StringUtil.isNotEmpty(szoJdbc.getId())) {
            szoJdbc = szoJdbcService.getEntity(SzoJdbcEntity.class,
                    szoJdbc.getId());
            req.setAttribute("szoJdbcPage", szoJdbc);
        }
        return new ModelAndView("szo/demo/test/szoJdbc");
    }

    // -----------------------------------------------------------------------------------
    // 以下各函数可以提成共用部件 (Add by Quainty)
    // -----------------------------------------------------------------------------------
    public void responseDatagrid(HttpServletResponse response,
                                 JSONObject jObject) {
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        try {
            PrintWriter pw = response.getWriter();
            pw.write(jObject.toString());
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(params = "dictParameter")
    public String dictParameter() {
        return "demo/jdbc/jdbc-list";
    }

    /**
     * JDBC DEMO 显示列表
     *
     * @return
     */
    @RequestMapping(params = "listAllDictParaByJdbc")
    public void listAllDictParaByJdbc(HttpServletRequest request,
                                      HttpServletResponse response, DataGrid dataGrid) {

        this.szoJdbcService.listAllByJdbc(dataGrid);
        TagUtil.datagrid(response, dataGrid);
    }
}
