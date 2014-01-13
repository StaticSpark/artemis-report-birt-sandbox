<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page import="com.funshion.artemis.common.config.PropertiesConfigParse" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="atlas_home" value="<%=PropertiesConfigParse.getAtlasHome()%>"/>
<script type="text/javascript">
   atlasHome = "${atlas_home}";
</script>
<link href="${ctx}/css/global.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/css/zocial/zocial.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/css/custom-theme/jquery-ui-1.8.18.custom.css" type="text/css" rel="stylesheet"/>

<link rel="stylesheet" type="text/css" href="${ctx}/js/hiAlert/css/alert.css"/>
<link href="${ctx}/css/qunit-1.12.0.css" type="text/css" rel="stylesheet"/>
<link rel="shortcut icon" href="${ctx}/images/artemis_icon.jpg" type="image/x-icon">

<%
   String isTest = request.getParameter("isTest");
   if(isTest != null && !"".equals(isTest)) {
	   session.setAttribute("isTest", isTest);
   }
%>
<script type="text/javascript">
	<%
		String isTestVal = (String) session.getAttribute("isTest");
	    if(isTestVal == null || isTestVal.length() == 0) {
	    	isTestVal = "false";
	    }
	%>
	isTest = <%=isTestVal%>
</script>
<script src="${ctx}/js/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery-ui-1.8.18.custom.min.js"	type="text/javascript"></script>
<script src="${ctx}/js/jquery.tools.min.js"	type="text/javascript"></script>
<script src="${ctx}/js/hiAlert/jquery.alert.js" type="text/javascript"></script>
<script	src="${ctx}/js/My97DatePicker/WdatePicker.js"  type="text/javascript"></script>
<script	src="${ctx}/js/My97DatePicker/config.js"  type="text/javascript"></script>
<script	src="${ctx}/js/Base.js"  type="text/javascript"></script>
<script	src="${ctx}/js/common/common.js"  type="text/javascript"></script>
<script	src="${ctx}/js/content/common/common.js"  type="text/javascript"></script>
<script	src="${ctx}/js/component/mc-report-header.js"  type="text/javascript"></script>
<script	src="${ctx}/js/content/common/common-test.js"  type="text/javascript"></script>
<script	src="${ctx}/js/qunit-1.12.0.js"  type="text/javascript"></script>
<script	src="${ctx}/js/jquery.cookie.js"  type="text/javascript"></script>	
<%@ include file="/common/report_tip.jsp"%>