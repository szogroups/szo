package com.szo.module.system.listener;

import com.szo.core.common.model.common.SessionInfo;
import com.szo.core.constant.Globals;
import com.szo.core.util.IpUtil;
import com.szo.module.system.service.SystemService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * 监听在线用户上线下线  add by duanql 2013-06-07
 */
public class OnlineListener implements ServletContextListener, ServletContextAttributeListener, HttpSessionListener, HttpSessionAttributeListener, HttpSessionActivationListener, HttpSessionBindingListener, ServletRequestListener, ServletRequestAttributeListener {

    private static ApplicationContext ctx = null;
    private static HttpServletRequest req = null;

    public OnlineListener() {
    }

    public void requestDestroyed(ServletRequestEvent arg0) {
    }

    /**
     * 向session里增加 user_session属性时调用
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent evt) {
        String name = evt.getName();
        if (Globals.USER_SESSION.equals(name)) {// 如果存入的属性是user_session
            HttpSession session = evt.getSession();
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(name);
            if (sessionInfo != null) {
                SystemService systemService = (SystemService) ctx.getBean("systemService");
                systemService.addOnline(IpUtil.getIpAddr(req), sessionInfo.getUser());
            }
        }
    }

    /**
     * 服务器初始化
     */
    public void contextInitialized(ServletContextEvent evt) {
        ctx = WebApplicationContextUtils.getWebApplicationContext(evt.getServletContext());
    }

    public void sessionDidActivate(HttpSessionEvent arg0) {
    }

    public void valueBound(HttpSessionBindingEvent arg0) {
    }

    public void attributeAdded(ServletContextAttributeEvent arg0) {
    }

    public void attributeRemoved(ServletContextAttributeEvent arg0) {
    }

    /**
     * session销毁
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent evt) {
        HttpSession session = evt.getSession();
        if (session != null) {
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Globals.USER_SESSION);
            if (sessionInfo != null) {
                SystemService systemService = (SystemService) ctx.getBean("systemService");
                systemService.deleteOnline(IpUtil.getIpAddr(req), sessionInfo.getUser());
            }
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent evt) {
        HttpSession session = evt.getSession();
        if (session != null) {
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Globals.USER_SESSION);
            if (sessionInfo != null) {
                SystemService systemService = (SystemService) ctx.getBean("systemService");
                systemService.deleteOnline(IpUtil.getIpAddr(req), sessionInfo.getUser());
            }
        }
    }

    public void attributeAdded(ServletRequestAttributeEvent evt) {
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent evt) {
        HttpSession session = evt.getSession();
        if (session != null) {
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Globals.USER_SESSION);
            if (sessionInfo != null) {
                SystemService systemService = (SystemService) ctx.getBean("systemService");
                systemService.deleteOnline(IpUtil.getIpAddr(req), sessionInfo.getUser());
            }
        }
    }

    public void sessionWillPassivate(HttpSessionEvent arg0) {
    }

    public void sessionCreated(HttpSessionEvent arg0) {
    }

    public void attributeReplaced(HttpSessionBindingEvent arg0) {
    }

    public void attributeReplaced(ServletContextAttributeEvent arg0) {
    }

    public void attributeRemoved(ServletRequestAttributeEvent arg0) {
    }

    public void contextDestroyed(ServletContextEvent evt) {
    }

    public void attributeReplaced(ServletRequestAttributeEvent arg0) {
    }

    /**
     * 服务器初始化
     */
    public void requestInitialized(ServletRequestEvent evt) {
        req = (HttpServletRequest) evt.getServletRequest();
    }

}
