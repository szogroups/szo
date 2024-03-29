<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>订单信息(无标签)</title>
	<link id="easyuiTheme" rel="stylesheet" href="plug-in/easyui/themes/default/easyui.css" type="text/css"/>
	<link rel="stylesheet" href="plug-in/easyui/themes/icon.css" type="text/css"/>
	<link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css">
	<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css" />
	<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css" />
	<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
	<script type="text/javascript" src="plug-in/easyui/jquery.easyui.min.1.3.2.js"></script>
	<script type="text/javascript" src="plug-in/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="plug-in/tools/syUtil.js"></script>
	<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
	<script type="text/javascript" src="plug-in/tools/curdtools.js"></script>
	<script type="text/javascript" src="plug-in/tools/easyuiextend.js"></script>
	<script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min.js"></script>
	<script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype.js"></script>
	<script type="text/javascript" src="plug-in/Validform/js/datatype.js"></script>
	<script type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></script>
</head>
<script type="text/javascript">
	function resetTrNum(tableId) {
		$tbody = $("#" + tableId + "");
		$tbody.find('>tr').each(function(i){
			$(':input, select', this).each(function() {
				var $this = $(this), name = $this.attr('name'), val = $this.val();
				if (name != null) {
					if (name.indexOf("#index#") >= 0) {
						$this.attr("name",name.replace('#index#',i));
					} else {
						var s = name.indexOf("[");
						var e = name.indexOf("]");
						var new_name = name.substring(s + 1,e);
						$this.attr("name",name.replace(new_name,i));
					}
				}
			});
		});
	}
	
	$(function() {
		$("#formobj").Validform({
			tiptype : 1,
			btnSubmit : "#btn_sub",
			btnReset : "#btn_reset",
			ajaxPost : true,
			usePlugin : {
				passwordstrength : {
					minLen : 6,
					maxLen : 18,
					trigger : function(obj, error) {
						if (error) {
							obj.parent().next().find(".Validform_checktip").show();
							obj.find(".passwordStrength").hide();
						} else {
							$(".passwordStrength").show();
							obj.parent().next().find(".Validform_checktip").hide();
						}
					}
				}
			},
			callback : function(data) {
				var win = frameElement.api.opener;
				if (data.success == true) {
					frameElement.api.close();
					win.tip(data.msg);
					
				} else {
					if (data.responseText == ''|| data.responseText == undefined){
						$("#formobj").html(data.msg);
					}else{
						$("#formobj").html(data.responseText);
					}
					return false;
				}
				win.reloadTable();
			}
		});
		
		$('#addBtn').linkbutton({   
		    iconCls: 'icon-add'  
		});  
		$('#delBtn').linkbutton({   
		    iconCls: 'icon-remove'  
		}); 
		$('#addBtn').bind('click', function(){   
	 		 var tr =  $("#add_szoOrderProduct_table_template tr").clone();
		 	 $("#add_szoOrderProduct_table").append(tr);
		 	 resetTrNum('add_szoOrderProduct_table');
	    });  
		$('#delBtn').bind('click', function(){   
	       $("#add_szoOrderProduct_table").find("input:checked").parent().parent().remove();   
	        resetTrNum('add_szoOrderProduct_table');
	    });
		
		$('#addCustomBtn').linkbutton({   
		    iconCls: 'icon-add'  
		});  
		$('#delCustomBtn').linkbutton({   
		    iconCls: 'icon-remove'  
		}); 
		$('#addCustomBtn').bind('click', function(){   
	 		 var tr =  $("#add_szoOrderCustom_table_template tr").clone();
		 	 $("#add_szoOrderCustom_table").append(tr);
		 	 resetTrNum('add_szoOrderCustom_table');
	    });  
		$('#delCustomBtn').bind('click', function(){   
	      	$("#add_szoOrderCustom_table").find("input:checked").parent().parent().remove();   
	        resetTrNum('add_szoOrderCustom_table'); 
	    }); 
	});
</script>
<body>
	<form id="formobj" action="szoOrderMainController.do?save" name="formobj" method="post">
		<input type="hidden" id="btn_sub" class="btn_sub" /> 
		<input id="id" name="id" type="hidden" value="${szoOrderMainPage.id }">
		<table cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">	订单号:</label>
				</td>
				<td class="value">
					<input class="inputxt" id="goOrderCode" name="goOrderCode" datatype="*" value="${szoOrderMainPage.goOrderCode}"/>
				</td>
				<td align="right">
					<label class="Validform_label">	订单类型:</label>
				</td>
				<td class="value">
					<t:dictSelect field="goderType" typeGroupCode="order" hasLabel="false" defaultVal="${szoOrderMainPage.goderType}"></t:dictSelect>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">顾客类型 :</label>
				</td>
				<td class="value">
					<t:dictSelect field="usertype" typeGroupCode="custom" hasLabel="false" defaultVal="${szoOrderMainPage.usertype}"></t:dictSelect>
				</td>
				<td align="right">
					<label class="Validform_label">联系人:</label>
				</td>
				<td class="value">
					<input nullmsg="联系人不能为空" errormsg="联系人格式不对" class="inputxt" id="goContactName" name="goContactName" 
						   value="${szoOrderMainPage.goContactName}" datatype="*">
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">手机:</label>
				</td>
				<td class="value">
					<input class="inputxt" id="goTelphone" name="goTelphone" 
						   value="${szoOrderMainPage.goTelphone}" datatype="m" errormsg="手机号码不正确!" 	ignore="ignore">
				</td>
				<td align="right">
					<label class="Validform_label">订单人数:</label>
				</td>
				<td class="value">
					<input nullmsg="订单人数不能为空" errormsg="订单人数必须为数字" class="inputxt" id="goOrderCount" name="goOrderCount" 
						   value="${szoOrderMainPage.goOrderCount}" datatype="n">
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">总价(不含返款):</label>
				</td>
				<td class="value">
					<input class="inputxt" id="goAllPrice" name="goAllPrice" value="${szoOrderMainPage.goAllPrice}" datatype="d">
				</td>
				<td align="right">
					<label class="Validform_label">返款:</label>
				</td>
				<td class="value">
					<input nullmsg="返款不能为空" errormsg="返款格式不对" class="inputxt" id="goReturnPrice" name="goReturnPrice" 
						   value="${szoOrderMainPage.goReturnPrice}" datatype="d">
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">备注:</label>
				</td>
				<td class="value" colspan="3">
					<input class="inputxt" id="goContent" name="goContent" value="${szoOrderMainPage.goContent}">
				</td>
			</tr>
		</table>
		<!-- //update-begin--Author:tanghong  Date:20130723 for：一对多无标签示例 -->
		<div style="width: auto; height: 200px;">
			<div style="width: 690px; height: 1px;"></div>
				<div id="tt" class="easyui-tabs" style="width:586px;height:250px;">  
				    <div title="产品明细" >
				    <div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
						<a id="addBtn" href="#">添加</a> <a id="delBtn" href="#">删除</a> 
					</div>
					<table border="0" cellpadding="2" cellspacing="0"
						id="szoOrderCustom_table">
						<tr bgcolor="#E6E6E6">
							<td align="center" bgcolor="#EEEEEE">序号</td>
							<td align="left" bgcolor="#EEEEEE">产品名称</td>
							<td align="left" bgcolor="#EEEEEE">个数</td>
							<td align="left" bgcolor="#EEEEEE">服务项目类型</td>
							<td align="left" bgcolor="#EEEEEE">单价</td>
							<td align="left" bgcolor="#EEEEEE">小计</td>
							<td align="left" bgcolor="#EEEEEE">备注</td>
						</tr>
						<tbody id="add_szoOrderProduct_table">
							<c:if test="${fn:length(szoOrderProductList)  <= 0 }">
								<tr>
									<td align="center"><input style="width: 20px;"
										type="checkbox" name="ck" /></td>
									<td align="left"><input nullmsg="请输入订单产品明细的产品名称！"
										datatype="s6-10" errormsg="订单产品明细的产品名称应该为6到10位"
										name="szoOrderProductList[0].gopProductName" maxlength="100"
										type="text" value=""></td>
									<td align="left"><input nullmsg="请输入订单产品明细的产品个数！" datatype="n"
                                        errormsg="订单产品明细的产品个数必须为数字" name="szoOrderProductList[0].gopCount" maxlength="10"
										type="text" value=""></td>
									<td align="left"><t:dictSelect
											field="szoOrderProductList[0].gopProductType"
											typeGroupCode="service" hasLabel="false"
											defaultVal="${poVal.gocSex}"></t:dictSelect></td>
									<td align="left"><input nullmsg="请输入订单产品明细的产品单价！" datatype="d"
                                        errormsg="订单产品明细的产品单价填写不正确" name="szoOrderProductList[0].gopOnePrice" maxlength="10"
										type="text" value=""></td>
									<td align="left"><input nullmsg="请输入订单产品明细的产品小计！" datatype="d"
                                        errormsg="订单产品明细的产品小计填写不正确" name="szoOrderProductList[0].gopSumPrice" maxlength="10"
										type="text" value=""></td>
									<td align="left"><input
										name="szoOrderProductList[0].gopContent" maxlength="200"
										type="text" value=""></td>
								</tr>
							</c:if>
							<c:if test="${fn:length(szoOrderProductList)  > 0 }">
								<c:forEach items="${szoOrderProductList}" var="poVal"
									varStatus="stuts">
									<tr>
										<td align="center"><input style="width: 20px;"
											type="checkbox" name="ck" /></td>
										<td align="left"><input nullmsg="请输入订单产品明细的产品名称！"
											datatype="s6-10" errormsg="订单产品明细的产品名称应该为6到10位"
											name="szoOrderProductList[${stuts.index }].gopProductName"
											maxlength="100" type="text" value="${poVal.gopProductName}"></td>
										<td align="left"><input nullmsg="请输入订单产品明细的产品个数！" datatype="n"
                                            errormsg="订单产品明细的产品个数必须为数字" name="szoOrderProductList[${stuts.index }].gopCount"
											maxlength="10" type="text" value="${poVal.gopCount }"></td>
										<td align="left"><t:dictSelect
												field="szoOrderProductList[${stuts.index }].gopProductType"
												typeGroupCode="service" hasLabel="false"
												defaultVal="${poVal.gopProductType}"></t:dictSelect></td>
										<td align="left"><input nullmsg="请输入订单产品明细的产品单价！" datatype="d"
                                            errormsg="订单产品明细的产品单价填写不正确" name="szoOrderProductList[${stuts.index }].gopOnePrice"
											maxlength="10" type="text" value="${poVal.gopOnePrice }"></td>
										<td align="left"><input nullmsg="请输入订单产品明细的产品小计！" datatype="d"
                                            errormsg="订单产品明细的产品小计填写不正确" name="szoOrderProductList[${stuts.index }].gopSumPrice"
											maxlength="10" type="text" value="${poVal.gopSumPrice }"></td>
										<td align="left"><input
											name="szoOrderProductList[${stuts.index }].gopContent"
											maxlength="200" type="text" value="${poVal.gopContent }"></td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>
				<div title="客户明细 ">
					<div style="padding: 3px; height: 25px; width: auto;"
						class="datagrid-toolbar">
						<a id="addCustomBtn" href="#">添加</a> <a id="delCustomBtn" href="#">删除</a>
					</div>
					<table border="0" cellpadding="2" cellspacing="0"
						id="szoOrderCustom_table">
						<tr bgcolor="#E6E6E6">
							<td align="center" bgcolor="#EEEEEE">序号</td>
							<td align="left" bgcolor="#EEEEEE">姓名</td>
							<td align="left" bgcolor="#EEEEEE">性别</td>
							<td align="left" bgcolor="#EEEEEE">身份证号</td>
							<td align="left" bgcolor="#EEEEEE">护照号</td>
							<td align="left" bgcolor="#EEEEEE">业务</td>
							<td align="left" bgcolor="#EEEEEE">备注</td>
						</tr>
						<tbody id="add_szoOrderCustom_table">
							<c:if test="${fn:length(szoOrderCustomList)  <= 0 }">
								<tr>
									<td align="center"><input style="width: 20px;"
										type="checkbox" name="ck" /></td>
									<td align="left"><input
										name="szoOrderCustomList[0].gocCusName" maxlength="50"
										type="text"></td>
									<td align="left"><t:dictSelect
											field="szoOrderCustomList[0].gocSex" typeGroupCode="sex"
											hasLabel="false" defaultVal="${jgDemo.sex}"></t:dictSelect></td>
									<td align="left"><input
										name="szoOrderCustomList[0].gocIdcard" maxlength="32"
										type="text"></td>
									<td align="left"><input
										name="szoOrderCustomList[0].gocPassportCode" maxlength="32"
										type="text"></td>
									<td align="left"><input
										name="szoOrderCustomList[0].gocBussContent" maxlength="100"
										type="text"></td>
									<td align="left"><input
										name="szoOrderCustomList[0].gocContent" maxlength="200"
										type="text"></td>
								</tr>
							</c:if>
							<c:if test="${fn:length(szoOrderCustomList)  > 0 }">
								<c:forEach items="${szoOrderCustomList}" var="poVal"
									varStatus="stuts">
									<tr>
										<td align="center"><input style="width: 20px;"
											type="checkbox" name="ck" /></td>
										<td align="left"><input
											name="szoOrderCustomList[${stuts.index }].gocCusName"
											maxlength="50" type="text" value="${poVal.gocCusName }"></td>
										<td align="left"><t:dictSelect
												field="szoOrderCustomList[${stuts.index }].gocSex"
												typeGroupCode="sex" hasLabel="false"
												defaultVal="${poVal.gocSex}"></t:dictSelect></td>
										<td align="left"><input
											name="szoOrderCustomList[${stuts.index }].gocIdcard"
											maxlength="32" type="text" value="${poVal.gocIdcard }"></td>
										<td align="left"><input
											name="szoOrderCustomList[${stuts.index }].gocPassportCode"
											maxlength="32" type="text" value="${poVal.gocPassportCode }"></td>
										<td align="left"><input
											name="szoOrderCustomList[${stuts.index }].gocBussContent"
											maxlength="100" type="text" value="${poVal.gocBussContent }"></td>
										<td align="left"><input
											name="szoOrderCustomList[${stuts.index }].gocContent"
											maxlength="200" type="text" value="${poVal.gocContent }"></td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
					<input type="hidden" name="szoOrderCustomShow" value="true" />
				</div>
			</div>  
			</div>
	</form>
	
	<table style="display: none">
		<tbody id="add_szoOrderProduct_table_template">
			<tr>
				<td align="center"><input style="width: 20px;" type="checkbox"	name="ck" /></td>
				<td align="left"><input nullmsg="请输入订单产品明细的产品名称！"datatype="s6-10" errormsg="订单产品明细的产品名称应该为6到10位"
					name="szoOrderProductList[#index#].gopProductName"
					maxlength="100" type="text" ></td>
				<td align="left">
					<input nullmsg="请输入订单产品明细的产品个数！" datatype="n"
                    errormsg="订单产品明细的产品个数必须为数字"  name="szoOrderProductList[#index#].gopCount" maxlength="10" type="text" >
				</td>
				<td align="left">
					<select	name="szoOrderProductList[#index#].gopProductType">
						<option value="1">特殊服务</option>
						<option value="2">普通服务</option>
					</select>
				</td>
				<td align="left">
					<input nullmsg="请输入订单产品明细的产品单价！" datatype="d"
                    errormsg="订单产品明细的产品单价填写不正确" name="szoOrderProductList[#index#].gopOnePrice" maxlength="10" type="text" >
				</td>
				<td align="left">
				    <input nullmsg="请输入订单产品明细的产品小计！" datatype="d"
                    errormsg="订单产品明细的产品小计填写不正确" name="szoOrderProductList[#index#].gopSumPrice" maxlength="10" type="text" >
				</td>
				<td align="left">
					<input	name="szoOrderProductList[#index#].gopContent" maxlength="200"type="text" >
				</td>
			</tr>
		</tbody>
		<tbody id="add_szoOrderCustom_table_template">
			<tr>
				<td align="center">
				<input style="width: 20px;" type="checkbox"	name="ck" /></td>
				<td align="left">
					<input name="szoOrderCustomList[#index#].gocCusName" maxlength="50" type="text" >
				</td>
				<td align="left">
					<select	name="szoOrderCustomList[#index#].gocSex">
						<option value="0">男性</option>
						<option value="1">女性</option>
					</select>
				</td>
				<td align="left">
					<input name="szoOrderCustomList[#index#].gocIdcard" maxlength="32" type="text" >
				</td>
				<td align="left">
					<input name="szoOrderCustomList[#index#].gocPassportCode" maxlength="32" type="text" >
				</td>
				<td align="left">
					<input name="szoOrderCustomList[#index#].gocBussContent" maxlength="100" type="text" >
				</td>
				<td align="left">
					<input	name="szoOrderCustomList[#index#].gocContent" maxlength="200"	type="text" >
				</td>
			</tr>
		</tbody>
	</table>
	<!-- //update-end--Author:tanghong  Date:20130723 for：一对多无标签示例 -->
</body>
</html>