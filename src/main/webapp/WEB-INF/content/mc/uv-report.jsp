<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>微距 - artemis</title>
    <%@ include file="/common/taglibs.jsp" %>
    <link href="${ctx}/css/con-style.css" type="text/css" rel="stylesheet"/>
    <script src="${ctx}/js/mc/mc-common.js" type="text/javascript"></script>
    <script src="${ctx}/js/content/mc/mc-common.js" type="text/javascript"></script>
    <script src="${ctx}/js/util/date.format.js" type="text/javascript"></script>
    <script src="${ctx}/js/mc/mc-uv-report.js" type="text/javascript"></script>
    <script src="${ctx}/js/component/simple-area-component.js" type="text/javascript"></script>
    <script src="${ctx}/js/highcharts/highcharts.js"></script>
    <script src="${ctx}/js/component/mc_chart_single_area_select.js" type="text/javascript"></script>
    <script src="${ctx}/js/content/mc/uv/mc_uv_chart_single_area_select.js"></script>
    <script src="${ctx}/js/content/mc/uv/mc_uv_overlock_single_area_select.js" type="text/javascript"></script>
    <script src="${ctx}/js/mc/mc_uv_overlock_chart.js"></script>
    <script src="${ctx}/js/component/artemis-chart-cookie.js" type="text/javascript"></script>
    <script src="${ctx}/js/component/switch-location.js"></script>
    <script src="${ctx}/js/content/mc/uv/mc-uv-switchlocation.js"></script>
    <script src="${ctx}/js/content/mc/uv/mc-uv-chart.js"></script>

    <style>
        .NavImg {

        }

        .NavImg a {
            display: inline-block;
            cursor: pointer;
            height: 16px;
            width: 16px;
        }

        .NavImgl a {
            background: transparent url(../images/img.gif) no-repeat scroll -16px 0;
        }

        .NavImgr a {
            background: transparent url(../images/img.gif) no-repeat scroll -32px 0;
        }
    </style>
</head>
<%@ include file="/common/meta.jsp" %>
<%@ include file="/common/header.jsp" %>

<body>
<div id="frame">
    <div id="content">
        <textarea id="json" style="display: none">${json}</textarea>
        <input type="hidden" id="ctx" value="${ctx}"/> <span class="tip" id="tipInfo"></span>
        <%@ include file="/common/McHeader.jsp" %>
        <iframe class="iframe_class" frameborder="0" marginwidth="0"
                marginheight="0" scrolling="no"
                style="width: 100%; height: 658px; border: none;" src="" id="report"></iframe>
        <div id="chart">
            <!-- <p><div style="border-top:1px dashed #7A991A;height: 1px;overflow:hidden"></div></p> -->
            <div class="conchart">
                <ul>
                    <li><a id="uvswich_id" href="javascript:void(0)" rel="nofollow">&nbsp;&nbsp; UV &nbsp;&nbsp; </a></li>
                    <li><a id="uvlockswitch_id" href="javascript:void(0)" rel="nofollow">&nbsp;&nbsp; 超频
                        &nbsp;&nbsp;  </a></li>
                </ul>
            </div>
            <div style="border:1px solid #7A991A">
	             <!-- uv超频分布 -->
	            <div id="overlock_chart">
	                <div id="convert_id">
	                    <select id="rpt_id" style="margin-left:60px;margin-top:20px">
	                        <option id="area_id" value="1">地域分布</option>
	                        <option id="day_id" value="2">日期分布</option>
	                    </select>
	                </div>
	                <div id="overlock_area_chart">
	                    <div id="overlock_area" style="height: 400px; margin: 0 auto"></div>
	                    <div style="margin-left:60px;margin-bottom:10px"><span>日期选择：<span class="NavImg NavImgl"><a
	                            href="javascript:void(0)" id="timePre" rel="nofollow"></a></span>
	                             <input id="chartDate" class="Wdate" type="text" style="width:90px"/><span
	                                class="NavImg NavImgr"><a href="javascript:void(0)" id="timeLater"
	                                                          rel="nofollow"></a></span></span>
	                    </div>
	                </div>
	                <div id="overlock_date_chart" style="display:none">
	                    <div id="overlock_date" style="height: 400px; margin: 0 auto;"></div>
	                    <div style="margin-left:40px;margin-bottom:5px">
	                        <table id="area_table"></table>
	                    </div>
	                </div>
	            </div>
	            <!-- uv -->
	            <div id="uv_chart">
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
</div>
</body>
<script type="text/javascript">
    //标题显示
    $("#mc_title_span").css("background", "white");
    $("#mc_title_span").css("color", "#4888B4");
</script>
<%@ include file="/common/footer.jsp" %>
</html>
