<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>微距 - artemis </title>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<link href="${ctx}/css/report/ad-report-senior.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/css/report/ad-report-senior-brw.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/js/content/ad/ad-report-senior-brw.js" ></script>
</head>
<body>
<script type="text/javascript">
    ctx="${ctx}";
</script>
<%@ include file="/common/header.jsp"%>
<div id="layout">
    <%@ include file="/common/SideBar.jsp"%>
    <div id="frame">
        <div id="searchContent">
            <input type="text" name="search" value="快速搜索" style="visibility:hidden;"/>
        </div>
        <div id="content">
            <%@ include file="/common/LeftMenu.jsp"%>
            <div > 
                <div class="subBread">>><span>非优化前贴片总库存</span></div>
               <iframe id="report" class='iframe_class' frameborder='0' marginwidth='0' marginheight='0' scrolling='no' style="overflow:auto; width: 87%; min-height: 220px;border:none;"></iframe>
            </div>
        </div>
    </div>
    <%@ include file="/common/footer.jsp"%>
</div>
</body>
</html>