<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
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
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    });
</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addCustomBtn" href="#">添加</a> <a id="delCustomBtn" href="#">删除</a> 
</div>
<div style="width: auto;height: 300px;overflow-y:auto;overflow-x:scroll;">
<table border="0" cellpadding="2" cellspacing="0" id="szoOrderCustom_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="left" bgcolor="#EEEEEE">姓名</td>
		<td align="left" bgcolor="#EEEEEE">性别</td>
		<td align="left" bgcolor="#EEEEEE">身份证号 </td>
		<td align="left" bgcolor="#EEEEEE">护照号</td>
		<td align="left" bgcolor="#EEEEEE">业务</td>
		<td align="left" bgcolor="#EEEEEE">备注</td>
	</tr>
	<tbody id="add_szoOrderCustom_table">	
	<c:if test="${fn:length(szoOrderCustomList)  <= 0 }">
			<tr>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>
				<td align="left"><input name="szoOrderCustomList[0].gocCusName" maxlength="50" type="text"  style="width:220px;" ></td>
				<td align="left">
				 <t:dictSelect field="szoOrderCustomList[0].gocSex" typeGroupCode="sex" hasLabel="false" defaultVal="${jgDemo.sex}"></t:dictSelect>
				</td>
	            <td align="left"><input name="szoOrderCustomList[0].gocIdcard" maxlength="32" type="text"  style="width:120px;" ></td>
				<td align="left"><input name="szoOrderCustomList[0].gocPassportCode" maxlength="32" type="text"  style="width:120px;" ></td>
				<td align="left"><input name="szoOrderCustomList[0].gocBussContent" maxlength="100" type="text"  style="width:120px;" ></td>
				<td align="left"><input name="szoOrderCustomList[0].gocContent" maxlength="200" type="text"  style="width:120px;" ></td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(szoOrderCustomList)  > 0 }">
		<c:forEach items="${szoOrderCustomList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>
				<td align="left"><input name="szoOrderCustomList[${stuts.index }].gocCusName" maxlength="50" type="text" value="${poVal.gocCusName }" style="width:220px;" ></td>
				<td align="left">
				<t:dictSelect field="szoOrderCustomList[${stuts.index }].gocSex" typeGroupCode="sex" hasLabel="false" defaultVal="${poVal.gocSex}"></t:dictSelect>
				</td>
	            <td align="left"><input name="szoOrderCustomList[${stuts.index }].gocIdcard" maxlength="32" type="text"  value="${poVal.gocIdcard }" style="width:120px;" ></td>
				<td align="left"><input name="szoOrderCustomList[${stuts.index }].gocPassportCode" maxlength="32" type="text" value="${poVal.gocPassportCode }"   style="width:120px;" ></td>
				<td align="left"><input name="szoOrderCustomList[${stuts.index }].gocBussContent" maxlength="100" type="text" value="${poVal.gocBussContent }" style="width:120px;" ></td>
				<td align="left"><input name="szoOrderCustomList[${stuts.index }].gocContent" maxlength="200" type="text"  value="${poVal.gocContent }" style="width:120px;" ></td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
<!-- //update-begin--Author:tanghong  Date:20130618 for：[ #149 ]一对多页面问题 -->
<input type="hidden" name="szoOrderCustomShow" value="true"/>
<!-- //update-end--Author:tanghong  Date:20130618 for：[ #149 ]一对多页面问题 -->
</div>