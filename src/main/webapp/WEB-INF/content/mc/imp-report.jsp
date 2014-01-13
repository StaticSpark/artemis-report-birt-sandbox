<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>微距 - artemis</title>
<%@ include file="/common/taglibs.jsp"%>
<link href="${ctx}/css/con-style.css" type="text/css" rel="stylesheet"/>
<script src="${ctx}/js/util/date.format.js" type="text/javascript"></script>
<script	src="${ctx}/js/content/mc/mc-common.js"  type="text/javascript"></script>
<script	src="${ctx}/js/content/mc/imp/mc-imp-report.js"  type="text/javascript"></script>
<script	src="${ctx}/js/content/mc/imp/mc-imp-chart.js"  type="text/javascript"></script>
<script src="${ctx}/js/content/mc/imp/mc-imp-sum-date-chart.js"  type="text/javascript"></script>
<script	src="${ctx}/test/content/mc/imp/mc-imp-report-test.js"  type="text/javascript"></script>
<script	src="${ctx}/js/component/simple-area-component.js"  type="text/javascript"></script>
<script src="${ctx}/js/component/switch-location.js"></script>
<script src="${ctx}/js/content/mc/imp/mc-imp-switch-location.js"  type="text/javascript"></script>
<script	src="${ctx}/js/component/mc_chart_multiple_area_select.js"  type="text/javascript"></script>
<script	src="${ctx}/js/component/mc_chart_single_area_select.js"  type="text/javascript"></script>
<script	src="${ctx}/js/content/mc/imp/mc_imp_chart_multiple_area_select.js"  type="text/javascript"></script>
<script	src="${ctx}/js/content/mc/imp/mc_imp_chart_single_area_select.js"  type="text/javascript"></script>
<script src="${ctx}/js/highcharts/highcharts.js"></script>
<script src="${ctx}/js/highcharts/plugins/draggable-legend.js"></script>
<script src="${ctx}/js/highcharts/modules/exporting.js"></script>
<script src="${ctx}/js/highcharts/plugins/grouped-categories.js"></script>
<script type="text/javascript"	src="${ctx}/js/component/artemis-chart-cookie.js"></script>

<script type="text/javascript">
   data = ${json};
   ctx = "${ctx}";
   $(document).ready(function() {
		__test = new ImpReport();
		new ImpReportTest();
   });
</script>
</head>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>

<body>
<div id="frame">
	<div id="content">
         <%@ include file="/common/McHeader.jsp" %>
    
		<iframe class="iframe_class" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="width: 98%; height:300px; border: none;" src="" id="report"></iframe>
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
			     <div id="day_chart_container" style="height: 400px; width:97%" >
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
