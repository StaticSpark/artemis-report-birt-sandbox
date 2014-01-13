<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>微距 - artemis</title>
<script type="text/javascript">
</script>
<%@ include file="/common/taglibs.jsp"%>
<link href="${ctx}/css/jquery.prompt.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/css/con-style.css" type="text/css" rel="stylesheet"/>
<script	src="${ctx}/js/mc/mc-common.js"  type="text/javascript"></script>
<script	src="${ctx}/js/content/mc/mc-common.js"  type="text/javascript"></script>
<script	src="${ctx}/js/content/mc/imp/mc-imp-chart.js"  type="text/javascript"></script>
<script src="${ctx}/js/component/switch-location.js"></script>
<script	src="${ctx}/js/component/mc_chart_multiple_area_select.js"  type="text/javascript"></script>
<script	src="${ctx}/js/component/mc_chart_single_area_select.js"  type="text/javascript"></script>
<script	src="${ctx}/js/content/mc/imp/mc_imp_chart_multiple_area_select.js"  type="text/javascript"></script>
<script	src="${ctx}/js/content/mc/imp/mc-imp-switch-location.js"  type="text/javascript"></script>
<script	src="${ctx}/js/content/mc/imp/mc_imp_chart_single_area_select.js"  type="text/javascript"></script>
<script	src="${ctx}/js/content/mc/imp/mc-imp-report-day-areagroup.js"  type="text/javascript"></script>
<script type="text/javascript"	src="${ctx}/js/component/artemis-chart-cookie.js"></script>
<script src="${ctx}/js/highcharts/highcharts.js"></script>
<script src="${ctx}/js/highcharts/plugins/draggable-legend.js"></script>
<script src="${ctx}/js/highcharts/modules/exporting.js"></script>
<script src="${ctx}/js/highcharts/plugins/grouped-categories.js"></script>
<script	src="${ctx}/js/jquery.prompt.js"  type="text/javascript"></script>
<script type="text/javascript">
   data = ${json};
   ctx = "${ctx}";
   
   $(document).ready(function() {
		new ImpAreaGroupReport();
   });
</script>
</head>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>
<body>
<div id="frame">
	<div id="content">
	 <table id="mc-header">
	     <tr>
	        <td><b>报表类型：</b></td> 
	        <td width="30%"><span id="reportType"></span></td> 
	        <td><b><span>播控名称</span></b></td>
	        <td><span  id="mcName"></span></span></td>
	     </tr>
	     <tr>
	        <td><b>地域：</b></td> 
	        <td><span id="areaName" style="margin-right:20px"></span></td>
	        <td><b>path</b> </td>
	        <td><span style="float:left;" id="path"></span>
	        </td>
	     </tr>
	     <tr>
	       <td colspan="4"> <input type="button" class="button"  name="viewChart" value="查看分布图" id="switchLoction" firstElement="report" style="margin-left:0px;" /></td>
	     </tr>
	 </table>
		<iframe class="iframe_class" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="width: 98%; height:350px; border: none;" src="" id="report"></iframe>
		<br />
		<div id="chart" style="margin:0 auto; display:none;width:98%">
	        <br/>
	        <div id="imp_container">
	        <div class="conchart">
		     <ul>
				<li><a id="area_chart_label" href="javascript:void(0)" rel="nofollow" >&nbsp;&nbsp; 日期 &nbsp;&nbsp;  </a></li>
				<li><a id="hour_chart_label" href="javascript:void(0)" rel="nofollow" >&nbsp;&nbsp; 时段 &nbsp;&nbsp;  </a></li>
			</ul>	   	        
	        </div>
			 
			<!-- tab "panes" -->
			<div id="chart_types" style="border:1px solid #7A991A">
			   <div id = "area_chart_area">
			      <div id="area_chart_container" style="height: 400px; width:97%"></div>
			      <table id="chart_multiple_area_list" style="margin:0 auto;">
	               </table>
	               <br />
			   </div>
			    <div id = "hour_chart_area">
			       <div id="hour_chart_container" style="height: 400px; width:97%"></div>
			       <table id="chart_single_area_list" style="margin:0 auto;">
	               </table>
	                <br />
			    </div>
			</div>
			</div>
			<div  style="display:none;border:1px solid #7A991A;padding:10px;">
			     <div id="day_chart_container" >
			</div>
	    </div>
        </div>
	</div>
</div>
</body>
<script type="text/javascript">
    //标题显示
    $("#mc_title_span").css("background","white");
	$("#mc_title_span").css("color","#4888B4");
</script>
<%@ include file="/common/footer.jsp"%>
</html>
