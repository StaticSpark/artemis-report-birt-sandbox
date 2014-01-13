<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>微距 - artemis</title>
<%@ include file="/common/taglibs.jsp"%>
<script	src="${ctx}/js/mc/mc-common.js"  type="text/javascript"></script>
<script	src="${ctx}/js/mc/mc-uv-report-day-areagroup.js"  type="text/javascript"></script>
</head>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>
<body>
<%@ include file="/common/report_tip.jsp" %>
<div id="frame">
	<div id="content">
	    <textarea id="json" style="display: none">${json}</textarea>
	    <input type="hidden" id="ctx" value="${ctx}"/>
	    <span class="tip" id="tipInfo"></span>
	    <table id="mc-header">
	        <tr>
				<td><b>报表类型：</b></td>
				<td style="width: 20%"><span id="reportType"></span></td>
				<td><b>播控名称：</b></td>
				<td><span id="mcName"></span></span></td>
			</tr>
			<tr>
	        <td><b>地域 :</b></td> 
	        <td><span id="areaGroupName" style="margin-right:20px"></span></td>
	        <td> <b>日期：</b></td>
	        <td><span id="monDate"></span></td>
	     </tr>
	    </table>
		<iframe class="iframe_class" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="width: 100%; height:650px; border: none;" src="" id="report"></iframe>
	</div>
</div>
</body>
<script type="text/javascript">
    //标题显示
    $("#mc_title_span").css("background","white");
	$("#mc_title_span").css("color","#4888B4");
</script>
<%@ include file="/common/footer.jsp"%>
</html>
