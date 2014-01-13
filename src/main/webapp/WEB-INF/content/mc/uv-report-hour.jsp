<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>微距 - artemis</title>
<%@ include file="/common/taglibs.jsp"%>
<script	src="${ctx}/js/common/common.js"  type="text/javascript"></script>
<script src="${ctx}/js/mc/mc-uv-report-hour.js" type="text/javascript"></script>
</head>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>
<body>
	<%@ include file="/common/report_tip.jsp"%>
	<div id="frame">
		<div id="content">
			<textarea id="json" style="display: none">${json}</textarea>
			<input type="hidden" id="ctx" value="${ctx}" /><b> 
			
			<span><span	id="name"></span>&nbsp;&nbsp;---&nbsp;&nbsp;UV日期报告</b></span>
		     <br/><br/>
		 		<a target="_blank" id="view_imp" href="">查看播控日期报告</a>
			 <br/>
			 <div>
			 	<table>
			 		<tr><td>地域：</td><td><span id="areaName"></span></td></tr>
			 		<tr><td style="vertical-align: top">频次控制：</td>
			 		<td>序号：<span id="uvSn"></span><br/>时间段：<span id="period"></span><br/>方式及限次：<span id="type"></span>不超过<span id="uvLimit"></span>次</td></tr>
			 		<tr><td>日期：</td><td><span id="monDate"></span></td></tr>
			 	</table>
			 </div>
			
			<iframe class="iframe_class" frameborder="0" marginwidth="0"
				marginheight="0" scrolling="no" style="margin-bottom: 200px; width: 100%; height: 560px; border: none;" src=""
				id="report"></iframe>
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