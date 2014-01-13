<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>微距 - artemis</title>
<%@ include file="/common/taglibs.jsp"%>
<script src="${ctx}/js/content/mc/mc-common.js" type="text/javascript"></script>
<script src="${ctx}/js/mc/mc-imp-report-sum-hour.js" type="text/javascript"></script>
<style type="text/css" rel="stylesheet">
	.sub-title {
		text-align:center;
		height:32px;
		line-height:32px;
	}
</style>
</head>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>
<body>
	<div id="frame" style="border: 1px">
		<div id="content" style="margin-top: -10px;">
			<textarea id="json" style="display: none">${json}</textarea>
			<input type="hidden" id="ctx" value="${ctx}" /> 
			<table id="mc-header">
				<tr>
					<td> <b>报表类型:</b> <td> 用户小时报告（小计）</td></td>
					<td> <b>播控名称:</b></td> <td> <span id="name"></span></td>
				</tr>				
				<tr>
					<td> <b>地域:</b></td> <td><span id="areaName"></span></td>
					<td> <b>日期:</b> </td><td><span id="startDate"></span> 至 <span id="endDate"></span></td>					
				</tr>
				<tr>
					<td> <b>path：</b> </td><td><span id="path"></span></td>
					<td> <b>设定投放量：</b> </td><td> <span id="impLimit"></span></td>
				</tr>			
			</table>
			<div style="margin-top:5px">
				<iframe class="iframe_class" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="width: 100%; height: 600px;"src="" id="report"></iframe>		
			</div>			
		</div>		
	</div>
</body>
<script type="text/javascript">
	//标题显示
	$("#mc_title_span").css("background", "white");
	$("#mc_title_span").css("color", "#4888B4");
	$("#subnav-strips").css("margin-left","2%");
	$("#subnav-strips").css("margin-right","0%");
</script>
</script>
<%@ include file="/common/footer.jsp"%>
</html>