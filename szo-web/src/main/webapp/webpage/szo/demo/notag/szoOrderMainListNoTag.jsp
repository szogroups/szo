<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$('#szoOrderMainListNoTag').datagrid({
			idField : 'id',
			title : '订单信息(页面未用自定义标签)',
			url : 'szoOrderMainNoTagController.do?datagrid&field=id,goOrderCode,goderType,usertype,goContactName,goTelphone,goOrderCount,goAllPrice,goReturnPrice,goContent,',
			fit : true,
			loadMsg : '数据加载中...',
			pageSize : 10,
			pagination : true,
			sortOrder : 'asc',
			rownumbers : true,
			singleSelect : true,
			fitColumns : true,
			showFooter : true,
			frozenColumns : [[{
				field : 'goOrderCode',
				title : '订单号',
				sortable : true
			}]],
			columns : [[
				{
					field : 'id',
					title : '编号',
					hidden : true,
					sortable : true
				},
				{
					field : 'goderType',
					title : '订单类型',
					sortable : true,
					formatter : function(value, rec, index) {
						if (value == '1') {
							return '优质订单';
						}
						if (value == '2') {
							return '普通订单';
						} else {
							return value;
						}
					}
				},
				{
					field : 'usertype',
					title : '顾客类型 ',
					sortable : true,
					formatter : function(value, rec, index) {
						if (value == '1') {
							return '签约客户';
						}
						if (value == '2') {
							return '普通客户';
						} else {
							return value;
						}
					}
				},
				{
					field : 'goContactName',
					title : '联系人',
					sortable : true
				},
				{
					field : 'goTelphone',
					title : '手机',
					sortable : true
				},
				{
					field : 'goOrderCount',
					title : '订单人数',
					sortable : true
				},
				{
					field : 'goAllPrice',
					title : '总价(不含返款)',
					sortable : true
				},
				{
					field : 'goReturnPrice',
					title : '返款',
					sortable : true
				},
				{
					field : 'goContent',
					title : '备注',
					sortable : true
				},
				{
					field : 'opt',
					title : '操作',
					width : 100,
					formatter : function(value, rec, index) {
						if (!rec.id) {
							return '';
						}
						var href = '';
						href += "[<a href='#' onclick=delObj('szoOrderMainNoTagController.do?del&id="
								+ rec.id
								+ "','szoOrderMainListNoTag')>";
						href += "删除</a>]";
						return href;
					}
				}]],
			onLoadSuccess : function(data) {
				$("#szoOrderMainListNoTag").datagrid("clearSelections");
			},
			onClickRow : function(rowIndex, rowData) {
				rowid = rowData.id;
				gridname = 'szoOrderMainList';
			}
		});
		
		$('#szoOrderMainListNoTag').datagrid('getPager').pagination({
			beforePageText : '',
			afterPageText : '/{pages}',
			displayMsg : '{from}-{to}共{total}条',
			showPageList : true,
			pageList : [ 10, 20, 30 ],
			showRefresh : true
		});
		
		$('#szoOrderMainListNoTag').datagrid('getPager').pagination({
			onBeforeRefresh : function(pageNumber, pageSize) {
				$(this).pagination('loading');
				$(this).pagination('loaded');
			}
		});
	});
	
	function reloadTable() {
		$('#' + gridname).datagrid('reload');
	}
	function reloadszoOrderMainList() {
		$('#szoOrderMainListNoTag').datagrid('reload');
	}
	function getszoOrderMainListSelected(field) {
		return getSelected(field);
	}
	function getSelected(field) {
		var row = $('#' + gridname).datagrid('getSelected');
		if (row != null) {
			value = row[field];
		} else {
			value = '';
		}
		return value;
	}
	function getszoOrderMainListSelections(field) {
		var ids = [];
		var rows = $('#szoOrderMainListNoTag').datagrid('getSelections');
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i][field]);
		}
		ids.join(',');
		return ids;
	};
	function szoOrderMainListsearch() {
		var queryParams = $('#szoOrderMainListNoTag').datagrid('options').queryParams;
		$('#szoOrderMainListNoTagtb').find('*').each(function() {
			queryParams[$(this).attr('name')] = $(this).val();
		});
		$('#szoOrderMainListNoTag').datagrid({
			url : 'szoOrderMainNoTagController.do?datagrid&field=id,goOrderCode,goderType,usertype,goContactName,goTelphone,goOrderCount,goAllPrice,goReturnPrice,goContent,'
		});
	}
	function dosearch(params) {
		var jsonparams = $.parseJSON(params);
		$('#szoOrderMainListNoTag').datagrid({
			url : 'szoOrderMainNoTagController.do?datagrid&field=id,goOrderCode,goderType,usertype,goContactName,goTelphone,goOrderCount,goAllPrice,goReturnPrice,goContent,',
			queryParams : jsonparams
		});
	}
	function szoOrderMainListsearchbox(value, name) {
		var queryParams = $('#szoOrderMainListNoTag').datagrid('options').queryParams;
		queryParams[name] = value;
		queryParams.searchfield = name;
		$('#szoOrderMainList').datagrid('reload');
	}
	$('#szoOrderMainListsearchbox').searchbox({
		searcher : function(value, name) {szoOrderMainListsearchbox(value, name);},
		menu : '#szoOrderMainListmm',
		prompt : '请输入查询关键字'
	});
	function searchReset(name) {
		$("#" + name + "tb").find(":input").val("");
		szoOrderMainListsearch();
	}
</script>

<table width="100%" id="szoOrderMainListNoTag" toolbar="#szoOrderMainListNoTagtb"></table>
<div id="szoOrderMainListNoTagtb" style="padding: 3px; height: auto">
	<div style="height: 30px;" class="datagrid-toolbar">
		<span style="float: left;">
			<a href="#"	class="easyui-linkbutton" plain="true" icon="icon-add"	onclick="add('录入(无标签)','szoOrderMainNoTagController.do?addorupdateNoTag','szoOrderMainListNoTag')">录入</a>
			<a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit"	onclick="update('编辑(无标签)','szoOrderMainNoTagController.do?addorupdateNoTag','szoOrderMainListNoTag')">编辑</a>
		</span>
	</div>
</div>