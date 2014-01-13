<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>hello</title>
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
<script type="text/javascript">
	$(document).ready(function() {
		var oLanguage = {
				"sProcessing" : "正在加载中......",
				"sLengthMenu" : "每页显示 _MENU_ 条记录",
				"sZeroRecords" : "对不起，查询不到相关数据！",
				"sEmptyTable" : "表中无数据存在！",
				"sInfo" : "_START_ 到 _END_ 条（共 _TOTAL_ 条）",
				"sInfoEmpty" : " 0到0条（共0条）",
				"sInfoFiltered" : "数据表中共为 _MAX_ 条记录",
				"sSearch" : "<span><font color='blue'>搜索:</font></span>",

				"oPaginate" : {
					"sFirst" : "首页",
					"sPrevious" : "上一页",
					"sNext" : "下一页",
					"sLast" : "末页"
				}
			};
			options = {
				"bProcessing": true,
				"bServerSide": true,
				"sPaginationType": "full_numbers",
				"sAjaxSource": "http://localhost:8080/artemis/userInfo",
				"sPaginationType": "full_numbers",
				"iDisplayLength" : 15,
				"oLanguage" : oLanguage,
				"sDom" : "<'H'f>rt<'F'ip><'clear'>",
				"fnServerData": function ( sSource, aoData, fnCallback ) {
					$.ajax( {
					"dataType": 'json',
					"type": "POST",
					"url": sSource,
					"data": aoData,
					"success": fnCallback
					} );
				}
			};
			$("[name='odd-even']").dataTable(options);
	});
</script>
</head>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>
<body style="background-color:white">
	<div
		style="width: 800px; border-width: 0px; border-style: solid; margin-left: 30px; margin-top: 30px;" name="show-hide">
		<table width="800px" style="word-wrap: break-word;" name="odd-even">
			<thead>
				<tr>
					<th width="95px"><span style="float: left">播控id</span></th>
					<th width="480px"><span style="float: left">播控名称</span></th>
				</tr>
			</thead>
			<tbody>
				<tr>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>