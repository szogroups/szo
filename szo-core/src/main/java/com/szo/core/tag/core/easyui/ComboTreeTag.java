package com.szo.core.tag.core.easyui;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * 类描述：下拉树形菜单
 *
 * @version 1.0
 * @author: jeecg
 * @date： 日期：2012-12-7 时间：上午10:17:45
 */
public class ComboTreeTag extends TagSupport {
    protected String id;// ID
    protected String url;// 远程数据
    protected String name;// 控件名称
    protected String width;// 宽度
    protected String value;// 控件值
    private boolean multiple = false;//是否多选

    public int doStartTag() throws JspTagException {
        return EVAL_PAGE;
    }

    public int doEndTag() throws JspTagException {
        try {
            JspWriter out = this.pageContext.getOut();
            out.print(end().toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

    public StringBuffer end() {
        StringBuffer sb = new StringBuffer();
        width = (width == null) ? "140" : width;
        sb.append("<script type=\"text/javascript\">"
                + "$(function() { " + "$(\'#" + id + "\').combotree({		 "
                + "url :\'" + url + "\',"
                + "width :\'" + width + "\',"
                + "multiple:" + multiple + ""
                + "});		"
                + "});	"
                + "</script>");
        sb.append("<input  name=\"" + name + "\" id=\"" + id + "\" ");
        if (value != null) {
            sb.append("value=" + value + "");
        }
        sb.append(">");
        return sb;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }
}
