package com.szo.core.tag.core.easyui;

import com.szo.core.constant.Globals;
import com.szo.core.util.ResourceUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

/**
 * @author 赵俊夫
 * @version V1.0
 * @Title:AuthFilterTag
 * @description:列表按钮权限过滤
 * @date Aug 24, 2013 7:46:57 PM
 */
public class AuthFilterTag extends TagSupport {
    /**
     * 列表容器的ID
     */
    protected String name;

    @Override
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            JspWriter out = this.pageContext.getOut();
            out.print(end().toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;

    }

    protected Object end() {
        StringBuilder out = new StringBuilder();
        getAuthFilter(out);
        return out;
    }

    /**
     * 获取隐藏按钮的JS代码
     *
     * @param out
     */
    @SuppressWarnings("unchecked")
    protected void getAuthFilter(StringBuilder out) {
        out.append("<script type=\"text/javascript\">");
        out.append("$(document).ready(function(){");
        List<String> nolist = (List<String>) super.pageContext.getRequest().getAttribute("noauto_operationCodes");
        if (ResourceUtil.getSessionUserName().getUserName().equals("admin") || !Globals.BUTTON_AUTHORITY_CHECK) {
        } else {
            if (nolist != null && nolist.size() > 0) {
                for (String s : nolist) {
                    out.append("$(\"#" + name + "\").find(\"#" + s.replaceAll(" ", "") + "\").hide();");
                }
            }
        }
        out.append("});");
        out.append("</script>");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
