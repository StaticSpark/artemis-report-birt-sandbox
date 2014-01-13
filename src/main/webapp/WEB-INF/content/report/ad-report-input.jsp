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
<script src="${ctx}/js/report/ad-report-input.js" type="text/javascript"></script>
<script
	src="${ctx}/js/jquery-multi-open-accordion-1.5.3/jquery.multi-open-accordion-1.5.3.min.js"
	type="text/javascript"></script>
</head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>
<body>
	<div id="frame">
		<textarea style="display: none" id="json">${json}</textarea>
		<input type="hidden" id="ctx" value="${ctx }"/>
		<input type="hidden" value="${errorInfo}" id="errorInfo"/>
		<%-- <%@ include file="/common/header.jsp"%> --%>
		<div id="content">
			<form id="form" action="ad-report-save" method="post">
				<div id="multiOpenAccordion">
				    <input type="hidden" id="id" name="id"/>
					<h3>
						<a href="#">新建报表</a>
					</h3>
					<div>
						<table width="60%">
							<tr>
								<td width="5%">报表名称:</td>
								<td width="45%"><input type="text" name="reportDisplayName"
									id="reportDisplayName" value="${reportDisplayName }"/></td>
								<td width="5%">报表标识:</td>
								<td width="45%"><input type="text" name="reportName"
									id="reportName" value="${reportName }"/></td>
							</tr>
							<tr>
								<td>报表类型:</td>
								<td><select id="reportTypeList" name="rptType">
								</select></td>
								<td></td>
								<td></td>
							</tr>

							<tr>
								<td>报表结构:</td> 
								<td><select name="isCross" id="isCross">
										<option value="0">普通表</option>
										<option value="1">交叉表</option>
								</select></td>
							</tr>
							<tr>
								<td width="25%">报表列信息:</td>
								<td colspan="3"><textarea rows="10" cols="100"
										name="columnJson" id="columnJson">${column}</textarea></td>
							</tr>

							<tr>
								<td width="25%">报表sql:</td>
								<td colspan="3"><textarea rows="10" cols="100"
										name="reportSql" id="reportSql">${reportSql }</textarea></td>
							</tr>
							<tr>
								<td><input type="submit" value="提交" id="submit"></input></td>
							</tr>
						</table>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>