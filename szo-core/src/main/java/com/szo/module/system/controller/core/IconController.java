package com.szo.module.system.controller.core;

import com.szo.core.common.controller.BaseController;
import com.szo.core.common.hibernate.qbc.CriteriaQuery;
import com.szo.core.common.model.common.UploadFile;
import com.szo.core.common.model.json.AjaxJson;
import com.szo.core.common.model.json.DataGrid;
import com.szo.core.constant.Globals;
import com.szo.core.tag.core.easyui.TagUtil;
import com.szo.core.util.StringUtil;
import com.szo.core.util.oConvertUtils;
import com.szo.module.system.pojo.base.TSFunction;
import com.szo.module.system.pojo.base.TSIcon;
import com.szo.module.system.pojo.base.TSOperation;
import com.szo.module.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;


/**
 * 图标信息处理类
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/iconController")
public class IconController extends BaseController {

    private SystemService systemService;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    /**
     * 图标列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "icon")
    public ModelAndView icon() {
        return new ModelAndView("system/icon/iconList");
    }

    /**
     * easyuiAJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(TSIcon icon, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSIcon.class, dataGrid);
        com.szo.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, icon);
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        IconImageUtil.convertDataGrid(dataGrid, request);//先把数据库的byte存成图片到临时目录，再给每个TsIcon设置目录路径
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 上传图标
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "saveOrUpdateIcon", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveOrUpdateIcon(HttpServletRequest request) throws Exception {
        AjaxJson j = new AjaxJson();

        TSIcon icon = new TSIcon();
        Short iconType = oConvertUtils.getShort(request.getParameter("iconType"));
        String iconName = oConvertUtils.getString(request.getParameter("iconName"));
        String id = request.getParameter("id");
        icon.setId(id);
        icon.setIconName(iconName);
        icon.setIconType(iconType);
        // uploadFile.setBasePath("images/accordion");
        UploadFile uploadFile = new UploadFile(request, icon);
        uploadFile.setCusPath("plug-in/accordion/images");
        uploadFile.setExtend("extend");
        uploadFile.setTitleField("iconclas");
        uploadFile.setRealPath("iconPath");
        uploadFile.setObject(icon);
        uploadFile.setByteField("iconContent");
        uploadFile.setRename(false);
        systemService.uploadFile(uploadFile);
        // 图标的css样式
        String css = "." + icon.getIconClas() + "{background:url('../images/" + icon.getIconClas() + "." + icon.getExtend() + "') no-repeat}";
        write(request, css);
        message = "上传成功";
        j.setMsg(message);
        return j;
    }


    /**
     * 没有上传文件时更新信息
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(params = "update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson update(HttpServletRequest request) throws Exception {
        AjaxJson j = new AjaxJson();
        Short iconType = oConvertUtils.getShort(request.getParameter("iconType"));
        String iconName = java.net.URLDecoder.decode(oConvertUtils.getString(request.getParameter("iconName")));
        String id = request.getParameter("id");
        TSIcon icon = systemService.get(TSIcon.class, id);
        icon.setId(id);
        icon.setIconName(iconName);
        icon.setIconType(iconType);
        systemService.saveOrUpdate(icon);
        // 图标的css样式
        String css = "." + icon.getIconClas() + "{background:url('../images/" + icon.getIconClas() + "." + icon.getExtend() + "') no-repeat}";
        write(request, css);
        message = "更新成功";
        j.setMsg(message);
        return j;
    }

    /**
     * 添加图标样式
     *
     * @param request
     * @param css
     */
    protected void write(HttpServletRequest request, String css) {
        try {
            String path = request.getSession().getServletContext().getRealPath("/plug-in/accordion/css/icons.css");
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter out = new FileWriter(file, true);
            out.write("\r\n");
            out.write(css);
            out.close();
        } catch (Exception e) {
        }
    }

    /**
     * 恢复图标（将数据库图标数据写入图标存放的路径下）
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "repair")
    @ResponseBody
    public AjaxJson repair(HttpServletRequest request) throws Exception {
        AjaxJson json = new AjaxJson();
        List<TSIcon> icons = systemService.loadAll(TSIcon.class);
        String rootpath = request.getSession().getServletContext().getRealPath("/");
        String csspath = request.getSession().getServletContext().getRealPath("/plug-in/accordion/css/icons.css");
        // 清空CSS文件内容
        clearFile(csspath);
        for (TSIcon c : icons) {
            File file = new File(rootpath + c.getIconPath());
            if (!file.exists()) {
                byte[] content = c.getIconContent();
                if (content != null) {
                    BufferedImage imag = ImageIO.read(new ByteArrayInputStream(content));
                    ImageIO.write(imag, "PNG", file);// 输出到 png 文件
                }
            }
            String css = "." + c.getIconClas() + "{background:url('../images/" + c.getIconClas() + "." + c.getExtend() + "') no-repeat}";
            write(request, css);
            json.setMsg("样式表创建成功");
        }
        json.setSuccess(true);
        return json;
    }

    /**
     * 清空文件内容
     *
     * @param request
     * @param css
     */
    protected void clearFile(String path) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(path));
            fos.write("".getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除图标
     *
     * @param icon
     * @param request
     * @return
     */
    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson del(TSIcon icon, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();

        icon = systemService.getEntity(TSIcon.class, icon.getId());

        boolean isPermit = isPermitDel(icon);

        if (isPermit) {
            systemService.delete(icon);

            message = "图标: " + icon.getIconName() + "被删除成功。";

            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

            return j;
        }

        message = "图标: " + icon.getIconName() + "正在使用，不允许删除。";

        j.setMsg(message);

        return j;
    }

    /**
     * 检查是否允许删除该图标。
     *
     * @param icon 图标。
     * @return true允许；false不允许；
     */
    private boolean isPermitDel(TSIcon icon) {
        List<TSFunction> functions = systemService.findByProperty(TSFunction.class, "TSIcon.id", icon.getId());
        if (functions == null || functions.isEmpty()) {
            return true;
        }
        return false;
    }

    public void upEntity(TSIcon icon) {
        List<TSFunction> functions = systemService.findByProperty(TSFunction.class, "TSIcon.id", icon.getId());
        if (functions.size() > 0) {
            for (TSFunction tsFunction : functions) {
                tsFunction.setTSIcon(null);
                systemService.saveOrUpdate(tsFunction);
            }
        }
        List<TSOperation> operations = systemService.findByProperty(TSOperation.class, "TSIcon.id", icon.getId());
        if (operations.size() > 0) {
            for (TSOperation tsOperation : operations) {
                tsOperation.setTSIcon(null);
                systemService.saveOrUpdate(tsOperation);
            }
        }
    }

    /**
     * 图标页面跳转
     *
     * @param icon
     * @param req
     * @return
     */
    @RequestMapping(params = "addorupdate")
    public ModelAndView addorupdate(TSIcon icon, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(icon.getId())) {
            icon = systemService.getEntity(TSIcon.class, icon.getId());
            req.setAttribute("icon", icon);
        }
        return new ModelAndView("system/icon/icons");
    }
}
