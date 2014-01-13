<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>微距 - artemis</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/common/meta.jsp"%>
<link href="${ctx}/css/global.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/smoothness/jquery-ui-1.8.18.custom.css"
	type="text/css" rel="stylesheet" />
<link href="${ctx}/css/custom-theme/jquery-ui-1.8.18.custom.css"
	type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/js/hiAlert/css/alert.css" />
<script src="${ctx}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery-ui-1.8.18.custom.min.js"
	type="text/javascript"></script>
<script
	src="${ctx}/js/jquery-multi-open-accordion-1.5.3/jquery.multi-open-accordion-1.5.3.min.js"
	type="text/javascript"></script>

<style type="text/css">
</style>
<script type="text/javascript">
	/* 当前菜单及次级菜单不能为空 */
	var current_nav = 3;
	var current_subnav = 8;
	var medList = new Array();
	$(document).ready(function() {
	});
	
	function deleteReport(id) {
		if(confirm('确认要删除该报告么？')){
			location = "report/ad-report!deleteReport.action?reportId="+id;
		}
	}
</script>

</head>

<body>
<%-- <%@ include file="/common/header.jsp"%> --%>
	<div id="frame">
		<div id="content">
                          <a href="ad-report!input.action">新建报告</a>
                          <br />
		    <iframe class="iframe_class" frameborder="0" style="width: 95%; height: 500px; border: none;" src="${ctx}/ShowReport.wx?PAGEID=report_manager" id="chart"></iframe>
		</div>
	</div>
<%-- <%@ include file="/common/footer.jsp"%> --%>
</body>
</html>