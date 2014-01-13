<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>微距 - artemis </title>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<link href="${ctx}/css/report/ad-report-senior.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<%@ include file="/common/header.jsp"%>
<div id="layout">
    <%@ include file="/common/SideBar.jsp"%>
    <div id="frame">
        <div id="searchContent">
            <input type="text" name="search" value="快速搜索"/>
        </div>
        <div id="content">
            <%@ include file="/common/LeftMenu.jsp"%>
            <div > 
            <h1>列表</h1>
            </div>
        </div>
    </div>
    <%@ include file="/common/footer.jsp"%>
</div>
</body>
</html>