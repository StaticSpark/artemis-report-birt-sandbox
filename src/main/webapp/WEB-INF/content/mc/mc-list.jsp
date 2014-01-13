<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/js/jqGrid/css/ui.jqgrid.css"/>
<script type="text/javascript" src="${ctx}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/mc/mc-list.js"></script>
<script src="${ctx}/js/jqGrid/js/i18n/grid.locale-cn.js" type="text/javascript"> </script>
<script src="${ctx}/js/jqGrid/js/jquery.jqGrid.min.js" type="text/javascript"> </script>
<script src="${ctx}/js/util/jquery.cookie.js" type="text/javascript"> </script>
<title>微距 - artemis </title>
<style type="text/css">
   .ui-jqgrid .ui-jqgrid-title{font-size:14px;}    /*修改grid标题的字体大小*/  
.ui-jqgrid-sortable {font-size:13px;}   /*修改列名的字体大小*/  
.ui-jqgrid tr.jqgrow td {font-size:13px; font-family:"宋体"} /*修改表格内容字体*/ 
.ui-th-subgrid {font-size:12px;}
.ui-subtblcell td {font-size:12px; font-family:"宋体"}
.ui-jqgrid-sortable {text-align: center} /*列表文字居中*/
	a:link{color: #0000FF;} /*连接颜色*/
</style>
</head>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>
<body style="background-color:white">
<input type="hidden" id="ctx" value="${ctx}">
       <div style="width:97%;margin-left:auto;margin-right:auto;margin-top:auto;text-align:center;">
		<table id="mc-list" width="100%" border="1" cellspacing="0" cellpadding="0" />
		<div id="mc-page" />
		</div>
</body>
<script type="text/javascript">
    //标题显示
    $("#mc_title_span").css("background","white");
	$("#mc_title_span").css("color","#4888B4");
</script>
<%-- <%@ include file="/common/footer.jsp"%> --%>
</html>