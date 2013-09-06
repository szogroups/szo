package com.szo.module.system.listener;

import com.szo.module.system.service.SystemService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;


/**
 * 系统初始化监听器,在系统启动时运行,进行一些初始化工作
 *
 * @author laien
 */
public class InitListener implements javax.servlet.ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        SystemService systemService = (SystemService) webApplicationContext.getBean("systemService");
        //对数据字典进行缓存
        systemService.initAllTypeGroups();
    }

}
