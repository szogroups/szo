<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="szoBlobDataList" title="Blob型数据操作例子" actionUrl="szoBlobDataController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="标题" field="attachmenttitle" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt title="下载" funname="szoBlobData_funcDownloadFile(id)" />
   <t:dgDelOpt title="删除" url="szoBlobDataController.do?del&id={id}" />
   <t:dgToolBar title="上传" icon="icon-add" url="szoBlobDataController.do?addorupdate" funname="add"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
function szoBlobData_funcDownloadFile(id) {
	window.location.href = "szoBlobDataController.do?download&fileId="+id; 
}
 </script>