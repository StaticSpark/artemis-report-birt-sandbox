<%@ page contentType="text/html;charset=UTF-8" %>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
     <%
	    String monId = request.getParameter("monId");//播控id
	    String areaId = request.getParameter("areaId");
	    String monDate = request.getParameter("monDate");
	    String path = request.getParameter("path");
	    String maxImpFwd = request.getParameter("maxImpFwd");
	    String x = request.getParameter("x");
	    String y = request.getParameter("y");
	    String column = request.getParameter("column");
	    String value = request.getParameter("value");
	    String columnDisplayName = request.getParameter("columnDisplayName");
	    String action = request.getParameter("action");
	    String hour = request.getParameter("hour");
	 %>
    <script type="text/javascript">
	    <% if(action != null && action.equals("onmouseout")) { %>
		  parent.parent.mouseOutAction();
       <% } else if(column == null || column.equals("")) { %>
          parent.parent.openSubReport("<%=monId%>", "<%=areaId%>", "<%=monDate%>", "<%=path%>", "<%=maxImpFwd%>","<%=hour%>", "<%=x%>", "<%=y%>");
       <% } else {%>
          parent.parent.showReportInfo("<%=monId%>", "<%=areaId%>", "<%=monDate%>", "<%=path%>", "<%=column%>","<%=columnDisplayName%>", "<%=value%>", "<%=hour%>", "<%=x%>", "<%=y%>"); 
       <%}%>
    </script>
</head>

<body>
<div>
</div>
</body>
</html>