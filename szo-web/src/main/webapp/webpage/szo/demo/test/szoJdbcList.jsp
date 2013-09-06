<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="szoJdbcList" title="通过JDBC访问数据库" actionUrl="szoJdbcController.do?datagrid" idField="id" fit="true"
   onDblClick="szoJdbcList_edit">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="姓名" field="userName" query="true"></t:dgCol>
   <t:dgCol title="部门" field="depId" ></t:dgCol>
   <t:dgCol title="性别" field="sex" ></t:dgCol>
   <t:dgCol title="年龄" field="age" ></t:dgCol>
   <t:dgCol title="生日" field="birthday" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="E-Mail" field="email" ></t:dgCol>
   <t:dgCol title="手机" field="mobilePhone" query="true"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="szoJdbcController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="szoJdbcController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="szoJdbcController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="szoJdbcController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
  
 <script type="text/javascript">
	function szoJdbcList_edit(rowIndex,rowData) {
		createwindow("XXX编辑",'szoJdbcController.do?addorupdate&id='+rowData.id);
	}
</script>