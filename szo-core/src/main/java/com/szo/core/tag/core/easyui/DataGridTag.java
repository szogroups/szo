package com.szo.core.tag.core.easyui;

import com.szo.core.constant.Globals;
import com.szo.core.tag.vo.easyui.ColumnValue;
import com.szo.core.tag.vo.easyui.DateGridColumn;
import com.szo.core.tag.vo.easyui.DateGridUrl;
import com.szo.core.tag.vo.easyui.OptTypeDirection;
import com.szo.core.util.ResourceUtil;
import com.szo.core.util.StringUtil;
import com.szo.core.util.oConvertUtils;
import com.szo.module.system.pojo.base.TSType;
import com.szo.module.system.pojo.base.TSTypegroup;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * 类描述：DATAGRID标签处理类
 *
 * @version 1.0
 * @author: jeecg
 * @date： 日期：2012-12-7 时间：上午10:17:45
 */
public class DataGridTag extends TagSupport {
    protected String fields = "";// 显示字段
    protected String searchFields = "";// 查询字段 Author:qiulu Date:20130618
    // for：添加对区间查询的支持
    protected String name;// 表格标示
    protected String title;// 表格标示
    protected String idField = "id";// 主键字段
    protected boolean treegrid = false;// 是否是树形列表
    protected List<DateGridUrl> urlList = new ArrayList<DateGridUrl>();// 列表操作显示
    protected List<DateGridUrl> toolBarList = new ArrayList<DateGridUrl>();// 工具条列表
    protected List<DateGridColumn> columnList = new ArrayList<DateGridColumn>();// 列表操作显示
    protected List<ColumnValue> columnValueList = new ArrayList<ColumnValue>();// 值替换集合
    protected List<ColumnValue> columnStyleList = new ArrayList<ColumnValue>();// 颜色替换集合
    public Map<String, Object> map;// 封装查询条件
    private String actionUrl;// 分页提交路径
    public int allCount;
    public int curPageNo;
    public int pageSize = 10;
    public boolean pagination = true;// 是否显示分页
    private String width;
    private String height;
    private boolean checkbox = false;// 是否显示复选框
    private boolean showPageList = true;// 定义是否显示页面列表
    private boolean fit = true;// 是否允许表格自动缩放，以适应父容器
    private boolean fitColumns = true;// 当为true时，自动展开/合同列的大小，以适应的宽度，防止横向滚动.
    private String sortName;// 定义的列进行排序
    private String sortOrder = "asc";// 定义列的排序顺序，只能是"递增"或"降序".
    private boolean showRefresh = true;// 定义是否显示刷新按钮
    private boolean showText = true;// 定义是否显示刷新按钮
    private String style = "easyui";// 列表样式easyui,datatables
    private String onLoadSuccess;// 数据加载完成调用方法
    private String onClick;// 单击事件调用方法
    private String onDblClick;// 双击事件调用方法
    private String queryMode = "single";// 查询模式
    private boolean autoLoadData = true; // 列表是否自动加载数据
    // private boolean frozenColumn=false; // 是否是冰冻列 默认不是
    // json转换中的系统保留字
    protected static Map syscode = new HashMap<String, String>();

    static {
        syscode.put("class", "clazz");
    }

    public void setOnLoadSuccess(String onLoadSuccess) {
        this.onLoadSuccess = onLoadSuccess;
    }

    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    public void setOnDblClick(String onDblClick) {
        this.onDblClick = onDblClick;
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTreegrid(boolean treegrid) {
        this.treegrid = treegrid;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFit(boolean fit) {
        this.fit = fit;
    }

    public void setShowPageList(boolean showPageList) {
        this.showPageList = showPageList;
    }

    public void setShowRefresh(boolean showRefresh) {
        this.showRefresh = showRefresh;
    }

    /**
     * 设置询问操作URL
     */
    public void setConfUrl(String url, String title, String message,
                           String exp, String operationCode) {
        DateGridUrl dateGridUrl = new DateGridUrl();
        dateGridUrl.setTitle(title);
        dateGridUrl.setUrl(url);
        dateGridUrl.setType(OptTypeDirection.Confirm);
        dateGridUrl.setMessage(message);
        dateGridUrl.setExp(exp);
        installOperationCode(dateGridUrl, operationCode, urlList);
    }

    /**
     * 设置删除操作URL
     */
    public void setDelUrl(String url, String title, String message, String exp,
                          String funname, String operationCode) {
        DateGridUrl dateGridUrl = new DateGridUrl();
        dateGridUrl.setTitle(title);
        dateGridUrl.setUrl(url);
        dateGridUrl.setType(OptTypeDirection.Del);
        dateGridUrl.setMessage(message);
        dateGridUrl.setExp(exp);
        dateGridUrl.setFunname(funname);
        installOperationCode(dateGridUrl, operationCode, urlList);
    }

    /**
     * 设置默认操作URL
     */
    public void setDefUrl(String url, String title, String exp,
                          String operationCode) {
        DateGridUrl dateGridUrl = new DateGridUrl();
        dateGridUrl.setTitle(title);
        dateGridUrl.setUrl(url);
        dateGridUrl.setType(OptTypeDirection.Deff);
        dateGridUrl.setExp(exp);
        installOperationCode(dateGridUrl, operationCode, urlList);

    }

    /**
     * 设置工具条
     *
     * @param height2
     * @param width2
     */
    public void setToolbar(String url, String title, String icon, String exp,
                           String onclick, String funname, String operationCode,
                           String width2, String height2) {
        DateGridUrl dateGridUrl = new DateGridUrl();
        dateGridUrl.setTitle(title);
        dateGridUrl.setUrl(url);
        dateGridUrl.setType(OptTypeDirection.ToolBar);
        dateGridUrl.setIcon(icon);
        dateGridUrl.setOnclick(onclick);
        dateGridUrl.setExp(exp);
        dateGridUrl.setFunname(funname);
        dateGridUrl.setWidth(String.valueOf(width2));
        dateGridUrl.setHeight(String.valueOf(height2));
        installOperationCode(dateGridUrl, operationCode, toolBarList);

    }

    /**
     * 设置自定义函数操作URL
     */
    public void setFunUrl(String title, String exp, String funname,
                          String operationCode) {
        DateGridUrl dateGridUrl = new DateGridUrl();
        dateGridUrl.setTitle(title);
        dateGridUrl.setType(OptTypeDirection.Fun);
        dateGridUrl.setExp(exp);
        dateGridUrl.setFunname(funname);
        installOperationCode(dateGridUrl, operationCode, urlList);

    }

    /**
     * 设置自定义函数操作URL
     */
    public void setOpenUrl(String url, String title, String width,
                           String height, String exp, String operationCode, String openModel) {
        DateGridUrl dateGridUrl = new DateGridUrl();
        dateGridUrl.setTitle(title);
        dateGridUrl.setUrl(url);
        dateGridUrl.setWidth(width);
        dateGridUrl.setHeight(height);
        dateGridUrl.setType(OptTypeDirection.valueOf(openModel));
        dateGridUrl.setExp(exp);
        installOperationCode(dateGridUrl, operationCode, urlList);

    }

    /**
     * <b>Summary: </b> setColumn(设置字段)
     *
     * @param name
     * @param text
     * @param value
     */
    public void setColumn(String title, String field, Integer width,
                          String rowspan, String colspan, String align, boolean sortable,
                          boolean checkbox, String formatter, boolean hidden, String replace,
                          String treefield, boolean image, boolean query, String url,
                          String funname, String arg, String queryMode, String dictionary,
                          boolean frozenColumn, String extend, String style) {
        DateGridColumn dateGridColumn = new DateGridColumn();
        dateGridColumn.setAlign(align);
        dateGridColumn.setCheckbox(checkbox);
        dateGridColumn.setColspan(colspan);
        dateGridColumn.setField(field);
        dateGridColumn.setFormatter(formatter);
        dateGridColumn.setHidden(hidden);
        dateGridColumn.setRowspan(rowspan);
        dateGridColumn.setSortable(sortable);
        dateGridColumn.setTitle(title);
        dateGridColumn.setWidth(width);
        dateGridColumn.setTreefield(treefield);
        dateGridColumn.setImage(image);
        dateGridColumn.setReplace(replace);
        dateGridColumn.setQuery(query);
        dateGridColumn.setUrl(url);
        dateGridColumn.setFunname(funname);
        dateGridColumn.setArg(arg);
        dateGridColumn.setQueryMode(queryMode);
        dateGridColumn.setDictionary(dictionary);
        dateGridColumn.setFrozenColumn(frozenColumn);
        dateGridColumn.setExtend(extend);
        dateGridColumn.setStyle(style);
        columnList.add(dateGridColumn);
        if (field != "opt") {
            fields += field + ",";
            if ("group".equals(queryMode)) {
                searchFields += field + "," + field + "_begin," + field
                        + "_end,";
            } else {
                searchFields += field + ",";
            }
        }
        if (replace != null) {
            String[] test = replace.split(",");
            String text = "";
            String value = "";
            for (String string : test) {
                text += string.substring(0, string.indexOf("_")) + ",";
                value += string.substring(string.indexOf("_") + 1) + ",";
            }
            setColumn(field, text, value);

        }
        if (!StringUtils.isBlank(dictionary)) {
            String text = "";
            String value = "";
            List<TSType> typeList = TSTypegroup.allTypes.get(dictionary
                    .toLowerCase());
            if (typeList != null && !typeList.isEmpty()) {
                for (TSType type : typeList) {
                    text += type.getTypename() + ",";
                    value += type.getTypecode() + ",";
                }
                setColumn(field, text, value);
            }
        }
        if (StringUtil.isNotEmpty(style)) {
            String[] temp = style.split(",");
            String text = "";
            String value = "";
            if (temp.length == 1 && temp[0].indexOf("_") == -1) {
                text = temp[0];
            } else {
                for (String string : temp) {
                    text += string.substring(0, string.indexOf("_")) + ",";
                    value += string.substring(string.indexOf("_") + 1) + ",";
                }
            }
            setStyleColumn(field, text, value);
        }
    }

    /**
     * 设置 颜色替换值
     *
     * @param field
     * @param text
     * @param value
     */
    private void setStyleColumn(String field, String text, String value) {
        ColumnValue columnValue = new ColumnValue();
        columnValue.setName(field);
        columnValue.setText(text);
        columnValue.setValue(value);
        columnStyleList.add(columnValue);
    }

    /**
     * <b>Summary: </b> setColumn(设置字段替换值)
     *
     * @param name
     * @param text
     * @param value
     */
    public void setColumn(String name, String text, String value) {
        ColumnValue columnValue = new ColumnValue();
        columnValue.setName(name);
        columnValue.setText(text);
        columnValue.setValue(value);
        columnValueList.add(columnValue);
    }

    public int doStartTag() throws JspTagException {
        // 清空资源
        urlList.clear();
        toolBarList.clear();
        columnValueList.clear();
        columnStyleList.clear();
        columnList.clear();
        fields = "";
        searchFields = "";
        return EVAL_PAGE;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            JspWriter out = this.pageContext.getOut();
            if (style.equals("easyui")) {
                out.print(end().toString());
            } else {
                out.print(datatables().toString());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

    /**
     * datatables构造方法
     *
     * @return
     */
    public StringBuffer datatables() {
        StringBuffer sb = new StringBuffer();
        sb.append("<script type=\"text/javascript\">");
        sb.append("$(document).ready(function() {");
        sb.append("var oTable = $(\'#userList\').dataTable({");
        // sb.append(
        // "\"sDom\" : \"<\'row\'<\'span6\'l><\'span6\'f>r>t<\'row\'<\'span6\'i><\'span6\'p>>\",");
        sb.append("\"bProcessing\" : true,");// 当datatable获取数据时候是否显示正在处理提示信息"
        sb.append("\"bPaginate\" : true,"); // 是否分页"
        sb.append("\"sPaginationType\" : \"full_numbers\",");// 分页样式full_numbers,"
        sb.append("\"bFilter\" : true,");// 是否使用内置的过滤功能"
        sb.append("\"bSort\" : true, ");// 排序功能"
        sb.append("\"bAutoWidth\" : true,");// 自动宽度"
        sb.append("\"bLengthChange\" : true,");// 是否允许用户自定义每页显示条数"
        sb.append("\"bInfo\" : true,");// 页脚信息"
        sb.append("\"sAjaxSource\" : \"userController.do?test\",");
        sb.append("\"bServerSide\" : true,");// 指定从服务器端获取数据
        sb.append("\"oLanguage\" : {" + "\"sLengthMenu\" : \" _MENU_ 条记录\","
                + "\"sZeroRecords\" : \"没有检索到数据\","
                + "\"sInfo\" : \"第 _START_ 至 _END_ 条数据 共 _TOTAL_ 条\","
                + "\"sInfoEmtpy\" : \"没有数据\","
                + "\"sProcessing\" : \"正在加载数据...\"," + "\"sSearch\" : \"搜索\","
                + "\"oPaginate\" : {" + "\"sFirst\" : \"首页\","
                + "\"sPrevious\" : \"前页\", " + "\"sNext\" : \"后页\","
                + "\"sLast\" : \"尾页\"" + "}" + "},"); // 汉化
        // 获取数据的处理函数 \"data\" : {_dt_json : JSON.stringify(aoData)},
        sb.append("\"fnServerData\" : function(sSource, aoData, fnCallback, oSettings) {");
        // + "\"data\" : {_dt_json : JSON.stringify(aoData)},"
        sb.append("oSettings.jqXHR = $.ajax({" + "\"dataType\" : \'json\',"
                + "\"type\" : \"POST\"," + "\"url\" : sSource,"
                + "\"data\" : aoData," + "\"success\" : fnCallback" + "});},");
        sb.append("\"aoColumns\" : [ ");
        int i = 0;
        for (DateGridColumn column : columnList) {
            i++;
            sb.append("{");
            sb.append("\"sTitle\":\"" + column.getTitle() + "\"");
            if (column.getField().equals("opt")) {
                sb.append(",\"mData\":\"" + idField + "\"");
                sb.append(",\"sWidth\":\"20%\"");
                sb.append(",\"bSortable\":false");
                sb.append(",\"bSearchable\":false");
                sb.append(",\"mRender\" : function(data, type, rec) {");
                this.getOptUrl(sb);
                sb.append("}");
            } else {
                int colwidth = (column.getWidth() == null) ? column.getTitle()
                        .length() * 15 : column.getWidth();
                sb.append(",\"sName\":\"" + column.getField() + "\"");
                sb.append(",\"mDataProp\":\"" + column.getField() + "\"");
                sb.append(",\"mData\":\"" + column.getField() + "\"");
                sb.append(",\"sWidth\":\"" + colwidth + "\"");
                sb.append(",\"bSortable\":" + column.isSortable() + "");
                sb.append(",\"bVisible\":" + column.isHidden() + "");
                sb.append(",\"bSearchable\":" + column.isQuery() + "");
            }
            sb.append("}");
            if (i < columnList.size())
                sb.append(",");
        }

        sb.append("]" + "});" + "});" + "</script>");
        sb.append("<table width=\"100%\"  class=\"" + style + "\" id=\"" + name
                + "\" toolbar=\"#" + name + "tb\"></table>");
        return sb;

    }

    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * easyui构造方法
     *
     * @return
     */
    public StringBuffer end() {
        String grid = "";
        StringBuffer sb = new StringBuffer();
        width = (width == null) ? "auto" : width;
        height = (height == null) ? "auto" : height;
        sb.append("<script type=\"text/javascript\">");
        sb.append("$(function(){");
        sb.append(this.getNoAuthOperButton());
        if (treegrid) {
            grid = "treegrid";
            sb.append("$(\'#" + name + "\').treegrid({");
            sb.append("idField:'id',");
            sb.append("treeField:'text',");
        } else {
            grid = "datagrid";
            sb.append("$(\'#" + name + "\').datagrid({");
            sb.append("idField: '" + idField + "',");
        }
        if (title != null) {
            sb.append("title: \'" + title + "\',");
        }

        if (autoLoadData)
            sb.append("url:\'" + actionUrl + "&field=" + fields + "\',");
        else
            sb.append("url:\'',");

        if (fit) {
            sb.append("fit:true,");
        } else {
            sb.append("fit:false,");
        }
        sb.append("loadMsg: \'数据加载中...\',");
        sb.append("pageSize: " + pageSize + ",");
        sb.append("pagination:" + pagination + ",");
        sb.append("pageList:[" + pageSize * 1 + "," + pageSize * 2 + ","
                + pageSize * 3 + "],");
        if (StringUtils.isNotBlank(sortName)) {
            sb.append("sortName:'" + sortName + "',");
        }
        sb.append("sortOrder:'" + sortOrder + "',");
        sb.append("rownumbers:true,");
        sb.append("singleSelect:" + !checkbox + ",");
        if (fitColumns) {
            sb.append("fitColumns:true,");
        } else {
            sb.append("fitColumns:false,");
        }
        sb.append("showFooter:true,");
        sb.append("frozenColumns:[[");
        this.getField(sb, 0);
        sb.append("]],");

        sb.append("columns:[[");
        this.getField(sb);
        sb.append("]],");
        sb.append("onLoadSuccess:function(data){$(\"#" + name + "\")." + grid
                + "(\"clearSelections\");");
        if (StringUtil.isNotEmpty(onLoadSuccess)) {
            sb.append(onLoadSuccess + "(data);");
        }
        sb.append("},");
        if (StringUtil.isNotEmpty(onDblClick)) {
            sb.append("onDblClickRow:function(rowIndex,rowData){" + onDblClick
                    + "(rowIndex,rowData);},");
        }
        if (treegrid) {
            sb.append("onClickRow:function(rowData){");
        } else {
            sb.append("onClickRow:function(rowIndex,rowData){");
        }
        /** 行记录赋值 */
        sb.append("rowid=rowData.id;");
        sb.append("gridname=\'" + name + "\';");
        if (StringUtil.isNotEmpty(onClick)) {
            sb.append("" + onClick + "(rowIndex,rowData);");
        }
        sb.append("}");
        sb.append("});");
        this.setPager(sb, grid);
        // sb.append("alert($(\".datagrid-footer\").find(\"td[field='opt']\").length());");
        sb.append("});");
        sb.append("function reloadTable(){");
        sb.append("try{");
        sb.append("	$(\'#\'+gridname).datagrid(\'reload\');");
        sb.append("	$(\'#\'+gridname).treegrid(\'reload\');");
        sb.append("}catch(ex){}");
        sb.append("}");
        sb.append("function reload" + name + "(){" + "$(\'#" + name + "\')."
                + grid + "(\'reload\');" + "}");
        sb.append("function get" + name
                + "Selected(field){return getSelected(field);}");
        sb.append("function getSelected(field){"
                + "var row = $(\'#\'+gridname)." + grid + "(\'getSelected\');"
                + "if(row!=null)" + "{" + "value= row[field];" + "}" + "else"
                + "{" + "value=\'\';" + "}" + "return value;" + "}");
        sb.append("function get" + name + "Selections(field){"
                + "var ids = [];" + "var rows = $(\'#" + name + "\')." + grid
                + "(\'getSelections\');" + "for(var i=0;i<rows.length;i++){"
                + "ids.push(rows[i][field]);" + "}" + "ids.join(\',\');"
                + "return ids" + "};");
        if (columnList.size() > 0) {
            sb.append("function " + name + "search(){");
            sb.append("var queryParams=$(\'#" + name
                    + "\').datagrid('options').queryParams;");
            sb.append("$(\'#"
                    + name
                    + "tb\').find('*').each(function(){queryParams[$(this).attr('name')]=$(this).val();});");
            // sb.append("$(\'#" + name + "\')." + grid + "({url:'" + actionUrl
            // + "&field=" + fields + "'});" + "}");
            sb.append("$(\'#" + name + "\')." + grid + "({url:'" + actionUrl
                    + "&field=" + searchFields + "',pageNumber:1});" + "}");

            // 高级查询执行方法
            sb.append("function dosearch(params){");
            sb.append("var jsonparams=$.parseJSON(params);");
            // sb.append("$(\'#" + name + "\')." + grid + "({url:'" + actionUrl
            // + "&field=" + fields + "',queryParams:jsonparams});" + "}");
            sb.append("$(\'#" + name + "\')." + grid + "({url:'" + actionUrl
                    + "&field=" + searchFields + "',queryParams:jsonparams});"
                    + "}");

            if (toolBarList.size() > 0) {
                // searchbox框执行方法
                searchboxFun(sb, grid);
            }
            // 生成重置按钮功能js
            sb.append("function searchReset(name){");
            sb.append(" $(\"#\"+name+\"tb\").find(\":input\").val(\"\");");
            String func = name.trim() + "search();";
            sb.append(func);
            sb.append("}");
        }
        sb.append("</script>");
        sb.append("<table width=\"100%\"   id=\"" + name + "\" toolbar=\"#"
                + name + "tb\"></table>");
        sb.append("<div id=\"" + name
                + "tb\" style=\"padding:3px; height: auto\">");
        if (hasQueryColum(columnList)) {
            sb.append("<div name=\"searchColums\">");
            // 如果表单是组合查询
            if ("group".equals(getQueryMode())) {
                int count = 0;
                for (DateGridColumn col : columnList) {
                    if (col.isQuery()) {
                        sb.append("<span style=\"display:-moz-inline-box;display:inline-block;\">");
                        sb.append("<span style=\"display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; \" title=\""
                                + col.getTitle()
                                + "\">"
                                + col.getTitle()
                                + "：</span>");
                        if ("single".equals(col.getQueryMode())) {
                            if (!StringUtil.isEmpty(col.getReplace())) {
                                sb.append("<select name=\""
                                        + col.getField()
                                        + "\" WIDTH=\"100\" style=\"width: 104px\"> ");
                                sb.append("<option value =\"\" >---请选择---</option>");
                                String[] test = col.getReplace().split(",");
                                String text = "";
                                String value = "";
                                for (String string : test) {
                                    text = string.split("_")[0];
                                    value = string.split("_")[1];
                                    sb.append("<option value =\"" + value
                                            + "\">" + text + "</option>");
                                }
                                sb.append("</select>");
                            } else if (!StringUtil.isEmpty(col.getDictionary())) {
                                Map<String, List<TSType>> typedatas = TSTypegroup.allTypes;
                                List<TSType> types = typedatas.get(col
                                        .getDictionary().toLowerCase());
                                sb.append("<select name=\""
                                        + col.getField()
                                        + "\" WIDTH=\"100\" style=\"width: 104px\"> ");
                                sb.append("<option value =\"\" >---请选择---</option>");
                                for (TSType type : types) {
                                    sb.append(" <option value=\""
                                            + type.getTypecode() + "\">");
                                    sb.append(type.getTypename());
                                    sb.append(" </option>");
                                }
                                sb.append("</select>");
                            } else {
                                sb.append("<input type=\"text\" name=\""
                                        + col.getField() + "\"  "
                                        + extendAttribute(col.getExtend())
                                        + " style=\"width: 100px\" />");
                            }
                        } else if ("group".equals(col.getQueryMode())) {
                            sb.append("<input type=\"text\" name=\""
                                    + col.getField()
                                    + "_begin\"  style=\"width: 94px\" "
                                    + extendAttribute(col.getExtend()) + "/>");
                            sb.append("<span style=\"display:-moz-inline-box;display:inline-block;width: 8px;text-align:right;\">~</span>");
                            sb.append("<input type=\"text\" name=\""
                                    + col.getField()
                                    + "_end\"  style=\"width: 94px\" "
                                    + extendAttribute(col.getExtend()) + "/>");
                        }
                        sb.append("</span>");
                    }
                }
            }
            sb.append("</div>");
        }
        if (toolBarList.size() == 0 && !hasQueryColum(columnList)) {
            sb.append("<div style=\"height:0px;\" >");
        } else {
            sb.append("<div style=\"height:30px;\" class=\"datagrid-toolbar\">");
        }
        sb.append("<span style=\"float:left;\" >");
        if (toolBarList.size() > 0) {
            for (DateGridUrl toolBar : toolBarList) {
                sb.append("<a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" icon=\""
                        + toolBar.getIcon() + "\" ");
                if (StringUtil.isNotEmpty(toolBar.getOnclick())) {
                    sb.append("onclick=" + toolBar.getOnclick() + "");
                } else {
                    sb.append("onclick=\"" + toolBar.getFunname() + "(");
                    if (!toolBar.getFunname().equals("doSubmit")) {
                        sb.append("\'" + toolBar.getTitle() + "\',");
                    }
                    String width = toolBar.getWidth().contains("%") ? "'"
                            + toolBar.getWidth() + "'" : toolBar.getWidth();
                    String height = toolBar.getHeight().contains("%") ? "'"
                            + toolBar.getHeight() + "'" : toolBar.getHeight();
                    sb.append("\'" + toolBar.getUrl() + "\',\'" + name + "\',"
                            + width + "," + height + ")\"");
                }
                sb.append(">" + toolBar.getTitle() + "</a>");
            }
        }
        sb.append("</span>");
        if ("group".equals(getQueryMode()) && hasQueryColum(columnList)) {// 如果表单是组合查询
            sb.append("<span style=\"float:right\">");
            sb.append("<a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-search\" onclick=\""
                    + name + "search()\">查询</a>");
            sb.append("<a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\" onclick=\"searchReset('"
                    + name + "')\">重置</a>");
            sb.append("</span>");
        } else if ("single".equals(getQueryMode()) && hasQueryColum(columnList)) {// 如果表单是单查询
            sb.append("<span style=\"float:right\">");
            sb.append("<input id=\""
                    + name
                    + "searchbox\" class=\"easyui-searchbox\"  data-options=\"searcher:"
                    + name + "searchbox,prompt:\'请输入关键字\',menu:\'#" + name
                    + "mm\'\"></input>");
            sb.append("<div id=\"" + name + "mm\" style=\"width:120px\">");
            for (DateGridColumn col : columnList) {
                if (col.isQuery()) {
                    sb.append("<div data-options=\"name:\'" + col.getField()
                            + "\',iconCls:\'icon-ok\' "
                            + extendAttribute(col.getExtend()) + " \">"
                            + col.getTitle() + "</div>  ");
                }
            }
            sb.append("</div>");
            sb.append("</span>");
        }
        sb.append("</div>");
        return sb;
    }

    /**
     * 生成扩展属性
     *
     * @param field
     * @return
     */
    private String extendAttribute(String field) {
        if (StringUtil.isEmpty(field)) {
            return "";
        }
        field = dealSyscode(field, 1);
        StringBuilder re = new StringBuilder();
        try {
            JSONObject obj = JSONObject.fromObject(field);
            Iterator it = obj.keys();
            while (it.hasNext()) {
                String key = String.valueOf(it.next());
                JSONObject nextObj = ((JSONObject) obj.get(key));
                Iterator itvalue = nextObj.keys();
                re.append(key + "=" + "\"");
                if (nextObj.size() <= 1) {
                    String onlykey = String.valueOf(itvalue.next());
                    if ("value".equals(onlykey)) {
                        re.append(nextObj.get(onlykey) + "");
                    } else {
                        re.append(onlykey + ":" + nextObj.get(onlykey) + "");
                    }
                } else {
                    while (itvalue.hasNext()) {
                        String multkey = String.valueOf(itvalue.next());
                        String multvalue = nextObj.getString(multkey);
                        re.append(multkey + ":" + multvalue + ",");
                    }
                    re.deleteCharAt(re.length() - 1);
                }
                re.append("\" ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return dealSyscode(re.toString(), 2);
    }

    /**
     * 处理否含有json转换中的保留字
     *
     * @param field
     * @param flag  1:转换 2:还原
     * @return
     */
    private String dealSyscode(String field, int flag) {
        String change = field;
        Iterator it = syscode.keySet().iterator();
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            String value = String.valueOf(syscode.get(key));
            if (flag == 1) {
                change = field.replaceAll(key, value);
            } else if (flag == 2) {
                change = field.replaceAll(value, key);
            }
        }
        return change;
    }

    /**
     * 判断是否存在查询字段
     *
     * @return hasQuery true表示有查询字段,false表示没有
     */
    protected boolean hasQueryColum(List<DateGridColumn> columnList) {
        boolean hasQuery = false;
        for (DateGridColumn col : columnList) {
            if (col.isQuery()) {
                hasQuery = true;
            }
        }
        return hasQuery;
    }

    /**
     * 拼装操作地址
     *
     * @param sb
     */
    protected void getOptUrl(StringBuffer sb) {
        // 注：操作列表会带入合计列中去，故加此判断
        sb.append("if(!rec.id){return '';}");
        List<DateGridUrl> list = urlList;
        sb.append("var href='';");
        for (DateGridUrl dateGridUrl : list) {
            String url = dateGridUrl.getUrl();
            MessageFormat formatter = new MessageFormat("");
            if (dateGridUrl.getValue() != null) {
                String[] testvalue = dateGridUrl.getValue().split(",");
                List value = new ArrayList<Object>();
                for (String string : testvalue) {
                    value.add("\"+rec." + string + " +\"");
                }
                url = formatter.format(url, value.toArray());
            }
            if (url != null && dateGridUrl.getValue() == null) {

                url = formatUrl(url);
            }
            String exp = dateGridUrl.getExp();// 判断显示表达式
            if (StringUtil.isNotEmpty(exp)) {
                String[] ShowbyFields = exp.split("&&");
                for (String ShowbyField : ShowbyFields) {
                    int beginIndex = ShowbyField.indexOf("#");
                    int endIndex = ShowbyField.lastIndexOf("#");
                    String exptype = ShowbyField.substring(beginIndex + 1,
                            endIndex);// 表达式类型
                    String field = ShowbyField.substring(0, beginIndex);// 判断显示依据字段
                    String[] values = ShowbyField.substring(endIndex + 1,
                            ShowbyField.length()).split(",");// 传入字段值
                    String value = "";
                    for (int i = 0; i < values.length; i++) {
                        value += "'" + "" + values[i] + "" + "'";
                        if (i < values.length - 1) {
                            value += ",";
                        }
                    }
                    if ("eq".equals(exptype)) {
                        sb.append("if($.inArray(rec." + field + ",[" + value
                                + "])>=0){");
                    }
                    if ("ne".equals(exptype)) {
                        sb.append("if($.inArray(rec." + field + ",[" + value
                                + "])<0){");
                    }
                    if ("empty".equals(exptype) && value.equals("'true'")) {
                        sb.append("if(rec." + field + "==''){");
                    }
                    if ("empty".equals(exptype) && value.equals("'false'")) {
                        sb.append("if(rec." + field + "!=''){");
                    }
                }
            }

            if (OptTypeDirection.Confirm.equals(dateGridUrl.getType())) {
                sb.append("href+=\"[<a href=\'#\' onclick=confirm(\'" + url
                        + "\',\'" + dateGridUrl.getMessage() + "\',\'" + name
                        + "\')> \";");
            }
            if (OptTypeDirection.Del.equals(dateGridUrl.getType())) {
                sb.append("href+=\"[<a href=\'#\' onclick=delObj(\'" + url
                        + "\',\'" + name + "\')>\";");
            }
            if (OptTypeDirection.Fun.equals(dateGridUrl.getType())) {
                String name = TagUtil.getFunction(dateGridUrl.getFunname());
                String parmars = TagUtil.getFunParams(dateGridUrl.getFunname());
                sb.append("href+=\"[<a href=\'#\' onclick=" + name + "("
                        + parmars + ")>\";");
            }
            if (OptTypeDirection.OpenWin.equals(dateGridUrl.getType())) {
                sb.append("href+=\"[<a href=\'#\' onclick=openwindow('"
                        + dateGridUrl.getTitle() + "','" + url + "','" + name
                        + "','" + dateGridUrl.getWidth() + "','"
                        + dateGridUrl.getHeight() + "')>\";");
            }
            if (OptTypeDirection.Deff.equals(dateGridUrl.getType())) {
                sb.append("href+=\"[<a href=\'" + url + "' title=\'"
                        + dateGridUrl.getTitle() + "\'>\";");
            }
            if (OptTypeDirection.OpenTab.equals(dateGridUrl.getType())) {
                sb.append("href+=\"[<a href=\'#\' onclick=addOneTab('"
                        + dateGridUrl.getTitle() + "','" + url + "')>\";");
            }
            sb.append("href+=\"" + dateGridUrl.getTitle() + "</a>]\";");

            if (StringUtil.isNotEmpty(exp)) {
                for (int i = 0; i < exp.split("&&").length; i++) {
                    sb.append("}");
                }

            }
        }
        sb.append("return href;");
    }

    /**
     * 列自定义函数
     *
     * @param sb
     * @param column
     */
    protected void getFun(StringBuffer sb, DateGridColumn column) {
        String url = column.getUrl();
        url = formatUrl(url);
        sb.append("var href=\"<a style=\'color:red\' href=\'#\' onclick="
                + column.getFunname() + "('" + column.getTitle() + "','" + url
                + "')>\";");
        sb.append("return href+value+\'</a>\';");

    }

    /**
     * 格式化URL
     *
     * @return
     */
    protected String formatUrl(String url) {
        MessageFormat formatter = new MessageFormat("");
        String parurlvalue = "";
        if (url.indexOf("&") >= 0) {
            String beforeurl = url.substring(0, url.indexOf("&"));// 截取请求地址
            String parurl = url.substring(url.indexOf("&") + 1, url.length());// 截取参数
            String[] pras = parurl.split("&");
            List value = new ArrayList<Object>();
            int j = 0;
            for (int i = 0; i < pras.length; i++) {
                if (pras[i].indexOf("{") >= 0 || pras[i].indexOf("#") >= 0) {
                    String field = pras[i].substring(pras[i].indexOf("{") + 1,
                            pras[i].lastIndexOf("}"));
                    parurlvalue += "&"
                            + pras[i].replace("{" + field + "}", "{" + j + "}");
                    value.add("\"+rec." + field + " +\"");
                    j++;
                } else {
                    parurlvalue += "&" + pras[i];
                }
            }
            url = formatter.format(beforeurl + parurlvalue, value.toArray());
        }
        return url;

    }

    /**
     * 拼接字段 普通列
     *
     * @param sb
     */
    protected void getField(StringBuffer sb) {
        getField(sb, 1);
    }

    /**
     * 拼接字段
     *
     * @param sb
     * @frozen 0 冰冻列 1 普通列
     */
    protected void getField(StringBuffer sb, int frozen) {
        // 复选框
        if (checkbox && frozen == 0) {
            sb.append("{field:\'ck\',checkbox:\'true\'},");
        }
        int i = 0;
        for (DateGridColumn column : columnList) {
            i++;
            if ((column.isFrozenColumn() && frozen == 0)
                    || (!column.isFrozenColumn() && frozen == 1)) {
                String field;
                if (treegrid) {
                    field = column.getTreefield();
                } else {
                    field = column.getField();
                }
                sb.append("{field:\'" + field + "\',title:\'"
                        + column.getTitle() + "\'");
                // ----------------------------------------------------------------
                // update-begin--Author:Quainty Date:20130607 for：[079]优化列宽显示
                // int colwidth = (column.getWidth() == null) ?
                // column.getTitle().length() * 15 : column.getWidth();
                // sb.append("width:" + colwidth + "");
                if (column.getWidth() != null) {
                    sb.append(",width:" + column.getWidth() + "");
                }
                // update-end--Author:Quainty Date:20130607 for：[079]优化列宽显示
                // ----------------------------------------------------------------

                // 隐藏字段
                if (!column.isHidden()) {
                    sb.append(",hidden:true");
                }
                if (!treegrid) {
                    // 字段排序
                    if ((column.isSortable())
                            && (field.indexOf("_") <= 0 && field != "opt")) {
                        sb.append(",sortable:" + column.isSortable() + "");
                    }
                }
                // 显示图片
                if (column.isImage()) {
                    sb.append(",formatter:function(value,rec,index){");
                    sb.append(" return '<img border=\"0\" src=\"'+value+'\"/>'}");
                }
                // 自定义链接
                if (column.getUrl() != null) {
                    sb.append(",formatter:function(value,rec,index){");
                    this.getFun(sb, column);
                    sb.append("}");
                }
                if (column.getFormatter() != null) {
                    sb.append(",formatter:function(value,rec,index){");
                    sb.append(" return new Date().format('"
                            + column.getFormatter() + "',value);}");
                }
                // 加入操作
                if (column.getField().equals("opt")) {
                    sb.append(",formatter:function(value,rec,index){");
                    // sb.append("return \"");
                    this.getOptUrl(sb);
                    sb.append("}");
                }
                // 值替換
                if (columnValueList.size() > 0
                        && !column.getField().equals("opt")) {
                    String testString = "";
                    for (ColumnValue columnValue : columnValueList) {
                        if (columnValue.getName().equals(column.getField())) {
                            String[] value = columnValue.getValue().split(",");
                            String[] text = columnValue.getText().split(",");
                            sb.append(",formatter:function(value,rec,index){");
                            for (int j = 0; j < value.length; j++) {
                                testString += "if(value=='" + value[j]
                                        + "'){return \'" + text[j] + "\'}";
                            }
                            sb.append(testString);
                            sb.append("else{return value}");
                            sb.append("}");
                        }
                    }

                }
                // 背景设置
                if (columnStyleList.size() > 0
                        && !column.getField().equals("opt")) {
                    String testString = "";
                    for (ColumnValue columnValue : columnStyleList) {
                        if (columnValue.getName().equals(column.getField())) {
                            String[] value = columnValue.getValue().split(",");
                            String[] text = columnValue.getText().split(",");
                            sb.append(",styler:function(value,rec,index){");
                            if (value.length == 0 && text.length == 1) {
                                testString = " return \'" + text[0] + "\'";
                            } else {
                                for (int j = 0; j < value.length; j++) {
                                    testString += "if(value=='" + value[j]
                                            + "'){return \'" + text[j] + "\'}";
                                }
                            }
                            sb.append(testString);
                            sb.append("}");
                        }
                    }

                }
                sb.append("}");
                // 去除末尾,
                if (i < columnList.size()) {
                    sb.append(",");
                }
            }
        }
    }

    /**
     * 设置分页条信息
     *
     * @param sb
     */
    protected void setPager(StringBuffer sb, String grid) {
        sb.append("$(\'#" + name + "\')." + grid
                + "(\'getPager\').pagination({");
        sb.append("beforePageText:\'\'," + "afterPageText:\'/{pages}\',");
        if (showText) {
            sb.append("displayMsg:\'{from}-{to}共{total}条\',");
        } else {
            sb.append("displayMsg:\'\',");
        }
        if (showPageList == true) {
            sb.append("showPageList:true,");
        } else {
            sb.append("showPageList:false,");
        }
        sb.append("showRefresh:" + showRefresh + "");
        sb.append("});");// end getPager
        sb.append("$(\'#" + name + "\')." + grid
                + "(\'getPager\').pagination({");
        sb.append("onBeforeRefresh:function(pageNumber, pageSize){ $(this).pagination(\'loading\');$(this).pagination(\'loaded\'); }");
        sb.append("});");
    }

    // 列表查询框函数
    protected void searchboxFun(StringBuffer sb, String grid) {
        sb.append("function " + name + "searchbox(value,name){");
        sb.append("var queryParams=$(\'#" + name
                + "\').datagrid('options').queryParams;");
        sb.append("queryParams[name]=value;queryParams.searchfield=name;$(\'#"
                + name + "\')." + grid + "(\'reload\');}");
        sb.append("$(\'#" + name + "searchbox\').searchbox({");
        sb.append("searcher:function(value,name){");
        sb.append("" + name + "searchbox(value,name);");
        sb.append("},");
        sb.append("menu:\'#" + name + "mm\',");
        sb.append("prompt:\'请输入查询关键字\'");
        sb.append("});");
    }

    public boolean isFitColumns() {
        return fitColumns;
    }

    public void setFitColumns(boolean fitColumns) {
        this.fitColumns = fitColumns;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getQueryMode() {
        return queryMode;
    }

    public void setQueryMode(String queryMode) {
        this.queryMode = queryMode;
    }

    public boolean isAutoLoadData() {
        return autoLoadData;
    }

    public void setAutoLoadData(boolean autoLoadData) {
        this.autoLoadData = autoLoadData;
    }

    public String getNoAuthOperButton() {
        List<String> nolist = (List<String>) super.pageContext.getRequest()
                .getAttribute("noauto_operationCodes");
        StringBuffer sb = new StringBuffer();
        if (ResourceUtil.getSessionUserName().getUserName().equals("admin")
                || !Globals.BUTTON_AUTHORITY_CHECK) {
        } else {
            if (nolist != null && nolist.size() > 0) {
                for (String s : nolist) {
                    sb.append("$('#" + name + "tb\').find(\"#"
                            + s.replaceAll(" ", "") + "\").hide();");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 描述：组装菜单按钮操作权限 dateGridUrl：url operationCode：操作码 optList： 操作列表
     *
     * @version 1.0
     */
    private void installOperationCode(DateGridUrl dateGridUrl,
                                      String operationCode, List optList) {
        if (ResourceUtil.getSessionUserName().getUserName().equals("admin")
                || !Globals.BUTTON_AUTHORITY_CHECK) {
            optList.add(dateGridUrl);
        } else if (!oConvertUtils.isEmpty(operationCode)) {
            Set<String> operationCodes = (Set<String>) super.pageContext
                    .getRequest().getAttribute("operationCodes");
            if (null != operationCodes) {
                for (String MyoperationCode : operationCodes) {
                    if (MyoperationCode.equals(operationCode)) {
                        optList.add(dateGridUrl);
                    }
                }
            }
        } else {
            optList.add(dateGridUrl);
        }
    }
}
