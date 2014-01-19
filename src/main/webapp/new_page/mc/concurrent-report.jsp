<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" ng-app>
<head>
    <title>微距 - artemis</title>
    <%@ include file="/common/taglibs.jsp" %>
    <link href="${ctx}/css/con-style.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="../js/angular.min.js"></script>
    <script type="text/javascript" src="/artemis/js/component/switch-location.js"></script>
    <script type="text/javascript" src="partial/js/search_controler.js"></script>
</head>
<style>
    .tip {
        font-size: 12px;
    }

    .Wdate {
        width: 90px;
    }

</style>
<%@ include file="/common/meta.jsp" %>
<%@ include file="/common/header.jsp" %>
<body>
<%@ include file="/common/report_tip.jsp" %>
<div id="frame">
    <div id="content" ng-controller="search_controler">
        <textarea id="json" style="display: none">${json}</textarea>
        <input type="hidden" id="ctx" value="${ctx}"/>
        <span class="tip" id="tipInfo"></span>
        <input id="rptType" type="hidden" value="uv"/>
        <div ng-include src="'partial/search.html'"></div>
        <iframe class="iframe_class" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"
                style="width: 100%; height:655px; border: none;" src="" id="report"></iframe>
        <!-- 图表div层 -->
        <div id="chart" ng-include src="'partial/chart.html'">
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    //标题显示
    $("#mc_title_span").css("background", "white");
    $("#mc_title_span").css("color", "#4888B4");
</script>
<div ng-include src="'../../common/footer.jsp'">
</div>
</html>
