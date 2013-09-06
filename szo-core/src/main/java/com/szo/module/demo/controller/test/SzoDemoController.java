package com.szo.module.demo.controller.test;

import com.szo.core.common.controller.BaseController;
import com.szo.core.common.hibernate.qbc.CriteriaQuery;
import com.szo.core.common.model.json.AjaxJson;
import com.szo.core.common.model.json.DataGrid;
import com.szo.core.constant.Globals;
import com.szo.core.tag.core.easyui.TagUtil;
import com.szo.core.util.MyBeanUtils;
import com.szo.core.util.StringUtil;
import com.szo.module.demo.entity.test.CKEditorEntity;
import com.szo.module.demo.entity.test.SzoDemo;
import com.szo.module.demo.service.test.SzoDemoServiceI;
import com.szo.module.system.pojo.base.TSDepart;
import com.szo.module.system.service.SystemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangdaihao
 * @version V1.0
 * @Title: Controller
 * @Description: 单表模型（DEMO）
 * @date 2013-01-23 17:12:40
 */
@Controller
@RequestMapping("/szoDemoController")
public class SzoDemoController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(SzoDemoController.class);

    @Autowired
    private SzoDemoServiceI szoDemoService;
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
     * popup 例子
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "popup")
    public ModelAndView popup(HttpServletRequest request) {
        return new ModelAndView("szo/demo/szoDemo/popup");
    }

    /**
     * popup 例子
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "selectUserList")
    public ModelAndView selectUserList(HttpServletRequest request) {
        String departsReplace = "";
        List<TSDepart> departList = systemService.getList(TSDepart.class);
        for (TSDepart depart : departList) {
            if (departsReplace.length() > 0) {
                departsReplace += ",";
            }
            departsReplace += depart.getDepartname() + "_" + depart.getId();
        }
        request.setAttribute("departsReplace", departsReplace);
        return new ModelAndView("szo/demo/szoDemo/selectUserList");
    }

    /**
     * ckeditor 例子
     *
     * @return
     */
    @RequestMapping(params = "ckeditor")
    public ModelAndView ckeditor(HttpServletRequest request) {
        // CKEditorEntity t = szoDemoService.get(CKEditorEntity.class, "1");
        CKEditorEntity t = szoDemoService.loadAll(CKEditorEntity.class).get(0);
        request.setAttribute("cKEditorEntity", t);
        if (t.getContents() == null) {
            request.setAttribute("contents", "");
        } else {
            request.setAttribute("contents", new String(t.getContents()));
        }
        return new ModelAndView("szo/demo/szoDemo/ckeditor");
    }

    /**
     * ckeditor saveCkeditor
     *
     * @return
     */
    @RequestMapping(params = "saveCkeditor")
    @ResponseBody
    public AjaxJson saveCkeditor(HttpServletRequest request,
                                 CKEditorEntity cKEditor, String contents) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(cKEditor.getId())) {
            CKEditorEntity t = szoDemoService.get(CKEditorEntity.class,
                    cKEditor.getId());
            try {
                MyBeanUtils.copyBeanNotNull2Bean(cKEditor, t);
                t.setContents(contents.getBytes());
                szoDemoService.saveOrUpdate(t);
                j.setMsg("更新成功");
            } catch (Exception e) {
                e.printStackTrace();
                j.setMsg("更新失败");
            }
        } else {
            cKEditor.setContents(contents.getBytes());
            szoDemoService.save(cKEditor);
        }
        return j;
    }

    /**
     * SzoDemo例子列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "szoDemo")
    public ModelAndView szoDemo(HttpServletRequest request) {
        return new ModelAndView("szo/demo/szoDemo/szoDemoList");
    }

    /**
     * easyuiAJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(SzoDemo szoDemo, HttpServletRequest request,
                         HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(SzoDemo.class, dataGrid);
        // 查询条件组装器
        com.szo.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
                szoDemo, request.getParameterMap());
        this.szoDemoService.getDataGridReturn(cq, true);
        String total_salary = String.valueOf(szoDemoService.findOneForJdbc(
                "select sum(salary) as ssum from szo_demo").get("ssum"));
        /*
		 * 说明：格式为 字段名:值(可选，不写该值时为分页数据的合计) 多个合计 以 , 分割
		 */
        dataGrid.setFooter("salary:"
                + (total_salary.equalsIgnoreCase("null") ? "0.0" : total_salary)
                + ",age,email:合计");
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 权限列表
     */
    @RequestMapping(params = "combox")
    @ResponseBody
    public List<SzoDemo> combox(HttpServletRequest request, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(SzoDemo.class);
        List<SzoDemo> ls = this.szoDemoService
                .getListByCriteriaQuery(cq, false);
        return ls;
    }

    /**
     * 删除SzoDemo例子
     *
     * @return
     */
    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson del(SzoDemo szoDemo, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        szoDemo = systemService.getEntity(SzoDemo.class, szoDemo.getId());
        message = "SzoDemo例子: " + szoDemo.getUserName() + "被删除 成功";
        szoDemoService.delete(szoDemo);
        systemService.addLog(message, Globals.Log_Type_DEL,
                Globals.Log_Leavel_INFO);

        j.setMsg(message);
        return j;
    }

    /**
     * 添加SzoDemo例子
     *
     * @param ids
     * @return
     */
    @RequestMapping(params = "save")
    @ResponseBody
    public AjaxJson save(SzoDemo szoDemo, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(szoDemo.getId())) {
            message = "SzoDemo例子: " + szoDemo.getUserName() + "被更新成功";
            SzoDemo t = szoDemoService.get(SzoDemo.class, szoDemo.getId());
            try {
                MyBeanUtils.copyBeanNotNull2Bean(szoDemo, t);
                szoDemoService.saveOrUpdate(t);
                systemService.addLog(message, Globals.Log_Type_UPDATE,
                        Globals.Log_Leavel_INFO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            message = "SzoDemo例子: " + szoDemo.getUserName() + "被添加成功";
            szoDemo.setStatus("0");
            szoDemoService.save(szoDemo);
            systemService.addLog(message, Globals.Log_Type_INSERT,
                    Globals.Log_Leavel_INFO);
        }

        return j;
    }

    /**
     * 审核报错
     *
     * @param ids
     * @return
     */
    @RequestMapping(params = "saveAuthor")
    @ResponseBody
    public AjaxJson saveAuthor(SzoDemo szoDemo, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(szoDemo.getId())) {
            message = "测试-用户申请成功";
            SzoDemo t = szoDemoService.get(SzoDemo.class, szoDemo.getId());
            try {
                MyBeanUtils.copyBeanNotNull2Bean(szoDemo, t);
                t.setStatus("1");
                szoDemoService.saveOrUpdate(t);
                j.setMsg(message);
                systemService.addLog(message, Globals.Log_Type_UPDATE,
                        Globals.Log_Leavel_INFO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return j;
    }

    /**
     * SzoDemo例子列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "addorupdate")
    public ModelAndView addorupdate(SzoDemo szoDemo, HttpServletRequest req) {
        // 获取部门信息
        List<TSDepart> departList = systemService.getList(TSDepart.class);
        req.setAttribute("departList", departList);

        Map sexMap = new HashMap();
        sexMap.put(0, "男");
        sexMap.put(1, "女");
        req.setAttribute("sexMap", sexMap);

        if (StringUtil.isNotEmpty(szoDemo.getId())) {
            szoDemo = szoDemoService.getEntity(SzoDemo.class, szoDemo.getId());
            req.setAttribute("jgDemo", szoDemo);
        }
        return new ModelAndView("szo/demo/szoDemo/szoDemo");
    }

    /**
     * 设置签名跳转页面
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "doCheck")
    public ModelAndView doCheck(HttpServletRequest request) {
        String id = request.getParameter("id");
        request.setAttribute("id", id);
        if (StringUtil.isNotEmpty(id)) {
            SzoDemo szoDemo = szoDemoService.getEntity(SzoDemo.class, id);
            request.setAttribute("szoDemo", szoDemo);
        }
        return new ModelAndView("szo/demo/szoDemo/szoDemo-check");
    }

    /**
     * 全选删除Demo实例管理
     *
     * @return
     * @author tanghan
     * @date 2013-07-13 14:53:00
     */
    @RequestMapping(params = "doDeleteALLSelect")
    @ResponseBody
    public AjaxJson doDeleteALLSelect(SzoDemo szoDemo,
                                      HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        String[] entitys = ids.split(",");
        List<SzoDemo> list = new ArrayList<SzoDemo>();
        for (int i = 0; i < entitys.length; i++) {
            szoDemo = systemService.getEntity(SzoDemo.class, entitys[i]);
            list.add(szoDemo);
        }
        message = "删除成功";
        szoDemoService.deleteAllEntitie(list);
        systemService.addLog(message, Globals.Log_Type_DEL,
                Globals.Log_Leavel_INFO);
        j.setMsg(message);
        return j;
    }
}
