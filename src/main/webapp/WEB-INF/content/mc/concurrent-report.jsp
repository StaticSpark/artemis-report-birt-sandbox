<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>微距 - artemis</title>
<%@ include file="/common/taglibs.jsp"%>
<link href="${ctx}/css/con-style.css" type="text/css" rel="stylesheet"/>
<script	src="${ctx}/js/common/report_tool.js"  type="text/javascript"></script>
<script src="${ctx}/js/component/switch-location.js"></script>
<script src="${ctx}/js/content/mc/concurrent/mc-concurrent-switchlocation.js"></script>
<script	src="${ctx}/js/mc/mc-concurrent-report.js"  type="text/javascript"></script>
<script src="${ctx}/js/mc/mc-common.js" type="text/javascript"></script>
<script src="${ctx}/js/util/date.format.js" type="text/javascript"></script>
<script src="${ctx}/js/content/common/common.js"  type="text/javascript"></script>
<script src="${ctx}/js/highcharts/highcharts.js"></script>
<script type="text/javascript"	src="${ctx}/js/component/artemis-chart-cookie.js"></script>
</head>
<style>
.tip{
    font-size: 12px;
}
.Wdate {
	width:90px;
}

</style>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>
<body>
<%@ include file="/common/report_tip.jsp" %>
<div id="frame">
	<div id="content">
	    <textarea id="json" style="display: none">${json}</textarea>
	    <input type="hidden" id="ctx" value="${ctx}"/>
	    <span class="tip" id="tipInfo"></span>
	     <input id="rptType" type="hidden" value="uv" />
	    <table id="mc-header">
	       <tr>
	           <td><b>报表类型：</b></td>
	           <td style="width:30%"><span id="reportType"></span>
	           (<a id="uvRef" href="javascript:void(0);" >UV</a>
              |&nbsp;<a id="ipRef" href="javascript:void(0);" >IP</a>)
	           </td>
	           <td><b>播控名称：</b></td>
	           <td><span id="mcName"></span>
              </td>
	       </tr>
	        <tr>
	           <td><b>选择日期：</b> </td>
	           <td><input class="Wdate" id='startDate' type="text" /> 
	           </td>
	           <td colspan="2"><input type='button' class="button" value='查询' id='search'/>
	           <input type="button" class="button"  name="viewChart" value="查看分布图" id="switchLoction" firstElement="report"/> </td>
	       </tr>
	    </table>
		<iframe class="iframe_class" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="width: 100%; height:655px; border: none;" src="" id="report"></iframe>
		<!-- 图表div层 -->
		<div id="chart">
			<p><div style="border-top:1px dashed #7A991A;height: 1px;overflow:hidden"></div></p>
			<div class="conchart">
		    <ul>
				<li><a id="periodswich_id" href="javascript:void(0)" rel="nofollow" >指定时段 </a></li>
				<li><a id="timeswitch_id" href="javascript:void(0)" rel="nofollow" >&nbsp;&nbsp; 小时 &nbsp;&nbsp;  </a></li>
			</ul>	        
	        </div>
			<div style="border:1px solid #7A991A">
				<div id="periodchart" style="height: 400px; margin: 0 auto"></div>
			    <div id="timechart" style="height: 400px; margin: 0 auto"></div>
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
