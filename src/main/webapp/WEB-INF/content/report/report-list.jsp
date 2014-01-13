<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微距- artemis</title>
<link href="${ctx}/css/global.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/custom-theme/jquery-ui-1.8.18.custom.css"
	type="text/css" rel="stylesheet" />
<link href="${ctx}/js/DataTables-1.9.1/media/css/jquery.dataTables.css"
	type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/js/jquery-1.7.1.min.js"></script>
<script src="${ctx}/js/jquery-ui-1.8.18.custom.min.js"
	type="text/javascript"></script>
<script
	src="${ctx}/js/DataTables-1.9.1/media/js/jquery.dataTables.min.js"
	type="text/javascript"></script>
<script src="${ctx}/js/jquery.tmpl.min.js" type="text/javascript"></script>
<script src="${ctx}/js/report/report-list.js" type="text/javascript"></script>
</head>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>
<body style="background-color:white">
	<textarea style="display: none" id="json" rows="" cols="">${json}</textarea>
	<input type="hidden" id="udpateName" value="${udpateName}"/>
	<input type="hidden" id="ctx" value="${ctx}"/>
	<script id="reportListUITemplate" type="text/x-jquery-tmpl">
	   <tr> 
		<td width="95px"><span style="float: left"><font size=2>\${id}</font></span></td>
		<td width="100px"><span style="float: left"><font size=2>\${rptName}</font></span></td>
        <td width="200px"><span style="float: left"><font size=2> <a href="ad-report-input?id=\${id}"> \${rptDisplayName}</a></font></span></td>
	    <td width="200px"><span style="float: left"><font size=2> <a name="delete-report" tag="\${id}" href="javascript:void(0);"> 删除</a></font></span></td>
	   </tr> 
	</script>
	<a style="float:left" href="${ctx}/report/ad-report-input">新建报告</a>
	<table width="500px" style="word-wrap: break-word;margin-left:5%" name="odd-even">
		<thead>
			<tr>
				<th width="95px"><span style="float: left">报告id</span></th>
				<th width="100px"><span style="float: left">报告标识</span></th>
				<th width="200px"><span style="float: left">报告名称</span></th>
				<th width="200px"><span style="float: left">操作</span></th>
			</tr>
		</thead>
		<tbody id="report-list">
		</tbody>
	</table>
</body>
<%@ include file="/common/footer.jsp"%>
</html>