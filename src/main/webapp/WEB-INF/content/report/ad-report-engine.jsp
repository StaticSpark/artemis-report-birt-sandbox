<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>微距 - artemis </title>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/report_tip.jsp"%>
<script src="${ctx}/js/util/date.format.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.tmpl.min.js"  type="text/javascript"></script>
<script type="text/javascript"  src="${ctx}/js/content/ad/ad-report-engine.js"></script>
<script src="${ctx}/js/highcharts/highcharts.js"></script>
<script src="${ctx}/js/highcharts/plugins/draggable-legend.js"></script>
<script src="${ctx}/js/highcharts/modules/exporting.js"></script>
<script type="text/javascript">
    dataJson = ${resultjson};
</script>
<style type="text/css" rel="stylesheet">
    .ui-orient-selector {
        position: absolute;
        width: 640px;
        background: none repeat scroll 0 0 #FFFFFF;
        right:1px;
    }
    
    .sub-title {
        text-align:center;
        height:32px;
        line-height:32px;
        /* MARGIN-RIGHT:auto;
        MARGIN-LEFT:auto; */
    }
    
    .chart_type_selected{
        background-color: #3C78B5;
        padding: 2px;
        border-radius: 2px 2px 2px 2px;
        color: #FFFFFF;
        cursor:pointer;
    }
    
    .chart_type {
       color:black;
       margin-right: 5px;
       margin-left: 5px;
       cursor: pointer;
    }
    
</style>
</head>
<%@ include file="/common/header.jsp"%>
<body>
<div id="layout">
    <%@ include file="/common/SideBar.jsp"%>
    <div id="frame" style="border: 1px">
       <div id="content">
        <span class="tip" id="tipInfo" style="width:auto"></span>
        <input type="hidden" id="ctx" value="${ctx}"/>
        <input type="hidden" name="dimarea" id="dimarea"/>
        <div>
            <div id="condition" style="margin-left:0px;margin-top:-10px; margin-bottom: 7px;">
            <table width="100%">
                <tr>
                    <td  width="30%">  <b>报表类型：</b>&nbsp;<span id="conditonTypeText"></span></td>
                    <td>  <b><span id="conditonTypeTextHeader"></span>名称：</b>&nbsp;<span id="conditionNames">   </span></td>
                </tr>
                <tr>
                    <td style="white-space: nowrap;width:30%"> 
                       <b>报表分布：</b>&nbsp;<span id="reportDim"></span>
                     </td>
                    <td><span id="choseTime"><b>选择时段：</b>&nbsp;开始时间：<input type="text" class="Wdate" size=12 style="width: 150px;" id='s_datetime' />
                        &nbsp;&nbsp;至&nbsp;&nbsp;结束时间：<input  type="text" class="Wdate" style="width: 150px;" size=12 id='e_datetime' /></span>
                        <span id="freqPeriod" style="display:none">
                        <b>选择周期：</b>&nbsp;
                        <input value="_uvfreq_l1" checked="checked" type="radio" name=reports_freq />昨天 
                        <input value="_uvfreq_l3" type="radio" name=reports_freq />前3天
                        <input value="_uvfreq_l7" type="radio" name=reports_freq />前7天
<!--                        <input value="_uvfreq_l15" type="radio" name=reports_freq />前15天  -->
<!--                        <input value="_uvfreq_l30" type="radio" name=reports_freq />前30天 -->
                        </span>
                        
                        <button style="margin-left: 20px;" name="search" id="search2">&nbsp;查&nbsp;询&nbsp;</button>
                    </td>       
                    <!-- <td> 
                        <span style="display:none;" id="area-cdt-td"><b>地域定向:</b>&nbsp;<input id="area-cdt-input" type="text" flag="true" name="area-cdt"/>                      
                        <div id='ui-orient-selector' class='ui-orient-selector ui-orient-selector-none'></div>   </span>                   
                    </td> -->                                               
                </tr>
                <tr>
                    <td style="white-space: nowrap;">
                        <span id="rpt_id" style="margin-top:3px;"><b>报表切换：</b><span id="rpt-switch"></span> 
                        <select id="rptList" style="margin-right:20px"></select></span>
                    </td>
                    <td>    
                        <input type="button" class="button" id="search" name="search" value="查询" />
                        <input type="button" class="button"  name="viewChart" value="查看分布图" id="switchLoction" firstElement="report"/> 
                    </td>
                </tr>           
            </table>
            </div>          
            <div style="margin-left:5px"> 
               <iframe id="report" class='iframe_class' frameborder='0' marginwidth='0' marginheight='0' scrolling='no' style="overflow:auto; width: 88%; height: 220px;border:none;"></iframe></div>
<!--               <iframe src="" id="ws-report" class='iframe_class' frameborder='0' marginwidth='0' marginheight='0' scrolling='no' style="margin-top:300px; overflow:auto; width: 97%; height: 220px;border:none;"></iframe> -->
               
               <%@ include file="/common/chart.jsp"%>
               <script type="text/javascript"   src="${ctx}/js/content/ad/ad-chart.js"></script>
        </div>
        </div>
        <%@ include file="/common/footer.jsp"%>
        <script type="text/javascript">
            //标题显示
            $("#ad_title_span").css("background","white");
            $("#ad_title_span").css("color","#4888B4");
        </script>
    </div>
</div>
</body>
</html>


