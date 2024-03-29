<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="szoMatterBomList" title="物料Bom列表" actionUrl="szoMatterBomController.do?doTreeGrid" treegrid="true" idField="id" pagination="false">
			<t:dgCol title="编号" field="id" treefield="id" hidden="false" />
		 	<t:dgCol title="名称" field="name" treefield="text" />
		 	<t:dgCol title="编码" field="code" treefield="src" />
		 	<%--
		 	<t:dgCol title="单位" field="unit" treefield="text" />
		 	<t:dgCol title="大小" field="weight" treefield="text" />
		 	<t:dgCol title="价格" field="price" treefield="text" />
		 	<t:dgCol title="库存" field="stock" treefield="text" />
			<t:dgCol title="产地" field="address" treefield="text" />
			<t:dgCol title="生产日期" field="productionDate" treefield="text" />
			<t:dgCol title="数量" field="quantity" treefield="text" />
			 --%>
			<t:dgCol title="操作" field="opt" width="100" />
		   	<t:dgDelOpt title="删除" url="szoMatterBomController.do?doDelete&id={id}" />
		   	<t:dgToolBar title="录入" icon="icon-add" url="szoMatterBomController.do?goEdit" funname="add" operationCode="add" />
		   	<t:dgToolBar title="编辑" icon="icon-edit" url="szoMatterBomController.do?goEdit" funname="update" operationCode="update" />
		</t:datagrid>
	</div>
</div>