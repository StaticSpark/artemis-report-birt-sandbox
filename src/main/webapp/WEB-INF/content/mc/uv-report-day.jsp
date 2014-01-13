<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>微距 - artemis</title>
    <%@ include file="/common/taglibs.jsp" %>
    <script src="${ctx}/js/mc/mc-common.js" type="text/javascript"></script>
    <script src="${ctx}/js/content/mc/mc-common.js" type="text/javascript"></script>
    <script src="${ctx}/js/util/date.format.js" type="text/javascript"></script>
    <script src="${ctx}/js/component/mc_chart_single_area_select.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/js/content/mc/uv/mc_uv_chart_single_area_select.js"></script>
    <script src="${ctx}/js/mc/mc-uv-report-day.js" type="text/javascript"></script>
    <script src="${ctx}/js/component/simple-area-component.js" type="text/javascript"></script>
    <script src="${ctx}/js/highcharts/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/js/component/artemis-chart-cookie.js"></script>
    <script type="text/javascript" src="${ctx}/js/component/switch-location.js"></script>
    <script src="${ctx}/js/content/mc/uv/mc-uv-chart.js"></script>  
</head>
<%@ include file="/common/meta.jsp" %>
<%@ include file="/common/header.jsp" %>
<body>
<div id="frame">
    <div id="content">
        <textarea id="json" style="display: none">${json}</textarea>
        <input type="hidden" id="ctx" value="${ctx}"/>
        <span class="tip" id="tipInfo"></span>
        <%@ include file="/common/McHeader.jsp" %>
        <iframe class="iframe_class" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"
                style="width: 100%; height:650px" src="" id="report"></iframe>
        <div id="chart">
            <div class="conchart">
                <ul>
                    <li><a id="periodswich_id" href="javascript:void(0)" rel="nofollow"
                           style=""> 每日UV数据</a></li>
                </ul>
            </div>
            <div id="uv_chart" style="border:1px solid #7A991A">
                <div id="uv_area_chart">
                    <div id="uv_area" style="height: 400px; margin: 0 auto">
                    </div>
                    <table id="chart_single_area_list" style="margin:0 auto;">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    //标题显示
    $("#mc_title_span").css("background", "white");
    $("#mc_title_span").css("color", "#4888B4");
</script>
<%@ include file="/common/footer.jsp" %>
</html>
