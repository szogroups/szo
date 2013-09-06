package com.szo.module.system.controller.core;

import com.szo.core.common.model.common.SessionInfo;
import com.szo.core.common.model.json.AjaxJson;
import com.szo.core.constant.Globals;
import com.szo.core.extend.datasource.DataSourceContextHolder;
import com.szo.core.extend.datasource.DataSourceType;
import com.szo.core.util.ContextHolderUtils;
import com.szo.core.util.NumberComparator;
import com.szo.core.util.ResourceUtil;
import com.szo.core.util.oConvertUtils;
import com.szo.module.system.pojo.base.*;
import com.szo.module.system.service.SystemService;
import com.szo.module.system.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 登陆初始化控制器
 */
@Controller
@RequestMapping("/loginController")
public class LoginController {
    private Logger log = Logger.getLogger(LoginController.class);
    private SystemService systemService;
    private UserService userService;
    private String message = null;

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    @Autowired
    public void setUserService(UserService userService) {

        this.userService = userService;
    }

    @RequestMapping(params = "goPwdInit")
    public String goPwdInit() {
        return "login/pwd_init";
    }

    /**
     * admin账户密码初始化
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "pwdInit")
    public ModelAndView pwdInit(HttpServletRequest request) {
        ModelAndView modelAndView = null;
        TSUser user = new TSUser();
        user.setUserName("admin");
        String newPwd = "123456";
        userService.pwdInit(user, newPwd);
        modelAndView = new ModelAndView(new RedirectView(
                "loginController.do?login"));
        return modelAndView;
    }

    /**
     * 检查用户名称
     *
     * @param user
     * @param req
     * @return
     */
    @RequestMapping(params = "checkuser")
    @ResponseBody
    public AjaxJson checkuser(TSUser user, HttpServletRequest req) {
        HttpSession session = ContextHolderUtils.getSession();
        DataSourceContextHolder
                .setDataSourceType(DataSourceType.dataSource_szo);
        AjaxJson j = new AjaxJson();
        TSUser u = userService.checkUserExits(user);
        if (u != null) {
            // date:20130318-------for:注释掉U盾的校验
            // if (user.getUserKey().equals(u.getUserKey())) {
            if (true) {
                // date:20130318-------for:注释掉U盾的校验
                message = "用户: " + user.getUserName() + "[" + u.getTSDepart().getDepartname() + "]" + "登录成功";
                SessionInfo sessionInfo = new SessionInfo();
                sessionInfo.setUser(u);
                session.setMaxInactiveInterval(60 * 30);
                session.setAttribute(Globals.USER_SESSION, sessionInfo);
                // 添加登陆日志
                systemService.addLog(message, Globals.Log_Type_LOGIN,
                        Globals.Log_Leavel_INFO);

            } else {
                j.setMsg("请检查U盾是否正确");
                j.setSuccess(false);
            }
        } else {
            j.setMsg("用户名或密码错误!");
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 用户登录
     *
     * @param user
     * @param request
     * @param session
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "login")
    public String login(HttpServletRequest request) {
        DataSourceContextHolder
                .setDataSourceType(DataSourceType.dataSource_szo);
        TSUser user = ResourceUtil.getSessionUserName();
        String roles = "";
        if (user != null) {
            List<TSRoleUser> rUsers = systemService.findByProperty(
                    TSRoleUser.class, "TSUser.id", user.getId());
            for (TSRoleUser ru : rUsers) {
                TSRole role = ru.getTSRole();
                roles += role.getRoleName() + ",";
            }
            if (roles.length() > 0) {
                roles = roles.substring(0, roles.length() - 1);
            }
            request.setAttribute("roleName", roles);
            request.setAttribute("userName", user.getRealName());
            //默认风格
            String indexStyle = "default";
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
                    continue;
                }
                if (cookie.getName().equalsIgnoreCase("SZOINDEXSTYLE")) {
                    indexStyle = cookie.getValue();
                }
            }
            //要添加自己的风格，复制下面三行即可
            if (StringUtils.isNotEmpty(indexStyle) && indexStyle.equalsIgnoreCase("bootstrap")) {
                return "main/bootstrap_main";
            }

            return "main/main";
        } else {
            return "login/login";
        }

    }

    /**
     * 退出系统
     *
     * @param user
     * @param req
     * @return
     */
    @RequestMapping(params = "logout")
    public ModelAndView logout(HttpServletRequest request) {
        ModelAndView modelAndView = null;

        HttpSession session = ContextHolderUtils.getSession();
        String versionCode = oConvertUtils.getString(request
                .getParameter("versionCode"));
        TSUser user = ResourceUtil.getSessionUserName();
        // 根据版本编码获取当前软件版本信息
        TSVersion version = systemService.findUniqueByProperty(TSVersion.class,
                "versionCode", versionCode);
        List<TSRoleUser> rUsers = systemService.findByProperty(
                TSRoleUser.class, "TSUser.id", user.getId());
        for (TSRoleUser ru : rUsers) {
            TSRole role = ru.getTSRole();
            session.removeAttribute(role.getId());
        }

        // 判断用户是否为空不为空则清空session中的用户object
        session.removeAttribute(Globals.USER_SESSION);// 注销该操作用户
        systemService.addLog("用户" + user.getUserName() + "已退出",
                Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO);
        modelAndView = new ModelAndView(new RedirectView(
                "loginController.do?login"));

        return modelAndView;
    }

    /**
     * 菜单跳转
     *
     * @return
     */
    @RequestMapping(params = "left")
    public ModelAndView left(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUserName();
        HttpSession session = ContextHolderUtils.getSession();
        // 登陆者的权限
        if (user.getId() == null) {
            session.removeAttribute(Globals.USER_SESSION);
            return new ModelAndView(
                    new RedirectView("loginController.do?login"));
        }

        request.setAttribute("menuMap", getFunctionMap(user));
        List<TSConfig> configs = userService.loadAll(TSConfig.class);
        for (TSConfig tsConfig : configs) {
            request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
        }
        return new ModelAndView("main/left");
    }

    /**
     * 获取权限的map
     *
     * @param user
     * @return
     */
    private Map<Integer, List<TSFunction>> getFunctionMap(TSUser user) {
        Map<Integer, List<TSFunction>> functionMap = new HashMap<Integer, List<TSFunction>>();
        Map<String, TSFunction> loginActionlist = getUserFunction(user);
        if (loginActionlist.size() > 0) {
            Collection<TSFunction> allFunctions = loginActionlist.values();
            for (TSFunction function : allFunctions) {
                if (!functionMap.containsKey(function.getFunctionLevel() + 0)) {
                    functionMap.put(function.getFunctionLevel() + 0,
                            new ArrayList<TSFunction>());
                }
                functionMap.get(function.getFunctionLevel() + 0).add(function);
            }
            // 菜单栏排序
            Collection<List<TSFunction>> c = functionMap.values();
            for (List<TSFunction> list : c) {
                Collections.sort(list, new NumberComparator());
            }
        }
        return functionMap;
    }

    /**
     * 获取用户菜单列表
     *
     * @param user
     * @return
     */
    private Map<String, TSFunction> getUserFunction(TSUser user) {
        HttpSession session = ContextHolderUtils.getSession();
        Map<String, TSFunction> loginActionlist = new HashMap<String, TSFunction>();
        List<TSRoleUser> rUsers = systemService.findByProperty(
                TSRoleUser.class, "TSUser.id", user.getId());
        for (TSRoleUser ru : rUsers) {
            TSRole role = ru.getTSRole();
            List<TSRoleFunction> roleFunctionList = ResourceUtil
                    .getSessionTSRoleFunction(role.getId());
            if (roleFunctionList == null) {
                session.setMaxInactiveInterval(60 * 30);
                roleFunctionList = systemService.findByProperty(
                        TSRoleFunction.class, "TSRole.id", role.getId());
                session.setAttribute(role.getId(), roleFunctionList);
            } else {
                if (roleFunctionList.get(0).getId() == null) {
                    roleFunctionList = systemService.findByProperty(
                            TSRoleFunction.class, "TSRole.id", role.getId());
                }
            }
            for (TSRoleFunction roleFunction : roleFunctionList) {
                TSFunction function = roleFunction.getTSFunction();
                loginActionlist.put(function.getId(), function);
            }
        }
        return loginActionlist;
    }

    /**
     * 首页跳转
     *
     * @return
     */
    @RequestMapping(params = "home")
    public ModelAndView home(HttpServletRequest request) {
        return new ModelAndView("main/home");
    }

    /**
     * 无权限页面提示跳转
     *
     * @return
     */
    @RequestMapping(params = "noAuth")
    public ModelAndView noAuth(HttpServletRequest request) {
        return new ModelAndView("common/noAuth");
    }

    /**
     * @param request
     * @return ModelAndView
     * @throws
     * @Title: top
     * @Description: bootstrap头部菜单请求
     */
    @RequestMapping(params = "top")
    public ModelAndView top(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUserName();
        HttpSession session = ContextHolderUtils.getSession();
        // 登陆者的权限
        if (user.getId() == null) {
            session.removeAttribute(Globals.USER_SESSION);
            return new ModelAndView(
                    new RedirectView("loginController.do?login"));
        }
        request.setAttribute("menuMap", getFunctionMap(user));
        List<TSConfig> configs = userService.loadAll(TSConfig.class);
        for (TSConfig tsConfig : configs) {
            request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
        }
        return new ModelAndView("main/bootstrap_top");
    }
}
