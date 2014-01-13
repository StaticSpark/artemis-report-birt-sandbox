<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>微距 - artemis </title>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<link href="${ctx}/css/report/ad-report-condition.css"  type="text/css"	rel="stylesheet" />
<link href="${ctx}/js/DataTables-1.9.1/media/css/jquery.dataTables.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/css/checkbox-button.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/css/report/ad-report-dataTable-check.css"  type="text/css"	rel="stylesheet" />
<script type="text/javascript">
	reportJson = ${reportJson};
</script>
<script src="${ctx}/js/DataTables-1.9.1/media/js/jquery.dataTables.min.js" type="text/javascript"></script>
<%-- <script src="${ctx}/js/DataTables-1.9.1/extras/TableTools/media/js/TableTools.min.js" type="text/javascript"></script> --%>
<script src="${ctx}/js/util/date.format.js" type="text/javascript"></script>
<script	src="${ctx}/js/jquery.tmpl.min.js"  type="text/javascript"></script>	
<script type="text/javascript"  src="${ctx}/js/content/ad/ad-report.js"></script>
<script type="text/javascript"  src="${ctx}/js/content/ad/ad-report-nav.js"></script>
<script type="text/javascript"  src="${ctx}/js/content/ad/ad-report-list.js"></script>
<script type="text/javascript"  src="${ctx}/js/content/ad/ad-report-data.js"></script>
<script type="text/javascript"  src="${ctx}/js/component/ad-select-all.js"></script>
<script type="text/javascript"  src="${ctx}/js/component/checkbox-select-component.js"></script>
<%@ include file="/js/component/area-component.jsp"%>
<style type="text/css" rel="stylesheet">
.maintop {
	margin: 0 auto;
	float: none;
	border: 0px solid #ADC1D0;
	margin-top: -57px;
	position: relative;
	width: 98%;
	background-image: url("${ctx}/images/add-li/split_panel.png");
	background-repeat: repeat-x;
	height: 35px;
}

.display-div {
	display: none;
}

.point_to {
	width: 37px;
	height: 10px;
	left: -200px;
	margin-top: -8px;
	position: absolute;
	background-image: url("${ctx}/images/add-li/tishipic.png");
}

.ui-orient-selector {
    position: absolute;
    width: 640px;
    margin-left:180px;
    background: none repeat scroll 0 0 #FFFFFF;
}

.tip { 
		color: #000000;
		background:#D7DFF6;
		border:1px #C0C0C0 solid;
		display:none; /*--Hides by default--*/
		padding:10px;
		position:absolute;	z-index:1000;
		-webkit-border-radius: 3px;
		-moz-border-radius: 3px;
		border-radius: 3px;
		top: 300;
	    left: 200
}
 .validateTips { font-family:"Times New Roman,Georgia,Serif";font-weight: bold;font-size:1.0em;color:#6699CC}
 .validateTips1 { font-family:"Times New Roman",Georgia,Serif;font-style:italic;font-weight: bold;font-size:1.0em;}
 .divCss{ 
width: 200px;
height:19px;
background-color: White;
border-style:solid;
border-width:1pt; 
border-color:#AAAAAA;
}
.cal_num {   
    font-family:"Lucida Grande", Tahoma, Arial, Verdana, sans-serif;
    font-size:12px;
    line-height:130%;
    color:#565656;
    padding:0px 0px 0px 0px;
}
</style>
<%@ include file="/WEB-INF/content/template/conditionUI.jsp"%>
<%@ include file="/WEB-INF/content/template/conditionData.jsp" %>
</head>
<body>
     <div class="themain" ></div>
     <input id="ctx" type="hidden" value="${ctx }"/>
     <input type="hidden" id="conditionType" value="ad"/>
     <input type="hidden" id="conditionTypeText" value="广告报告"/>
     <input type="hidden" name="dimarea" id="dimarea"/>
     <span class="tip" id="tipInfo" style="width:auto"></span>
  
	 <div id="ad_condition" class="mainpanel">
			     <div class="display-div" style="border-width:1px;border-style:solid;border-color:#ff0e3;" name="show-hide">
				    	<table id="ad_table" width="100%" style="word-wrap:break-word;" name="odd-even">
							<thead>
								<tr>
								    <th width="10px"><input type="checkbox" name="con-check-list"></input></th>
									<th width="140px" style="text-align: center;"><span style="float: left" id="test1"  name="ad_title">广告id</span></th>
									<th width="180px"><span style="float: left" name="ad_title">广告名称</span></th>
									<th width="180px"><span style="float: left" name="ad_title">订单名称</span></th>
									<th width="160px"><span style="float: left" name="ad_title">广告状态</span></th>
									<th width="180px"><span style="float: left ;text-align:center" name="ad_title">投放周期</span></th>
									<th width="80px"><span style="float: left" name="ad_title">操作</span></th>
								</tr>
							</thead>
							<tbody id="ad_report_list" class="report-list-td">
							
							</tbody>
						</table>
			   </div>
	</div>
	<div id="mat_condition" class="mainpanel">
     <!-- 物料查询条件 -->
			     <div class="display-div" style="border-width:1px;border-style:solid;border-color:#ff0e3;" id="mat-div" name="show-hide">
				    	<table id="mat_table" width="100%" style="word-wrap:break-word" name="odd-even">
							<thead>
								<tr>
								    <th width="10px"><input type="checkbox" name="con-check-list"></input></th>
									<th width="95px"><span style="float: left" name="mat_title">物料id</span></th>
									<th width="380px"><span style="float: left" name="mat_title">物料名称</span></th>
									<th width="180px"><span style="float: left" name="mat_title">物料类型</span></th>
									<th width="180px"><span style="float: left" name="mat_title">物料尺寸</span></th>
									<th width="180px"><span style="float: left" name="mat_title">播放时长</span></th>
									<th width="180px"><span style="float: left" name="mat_title">操作</span></th>
								</tr>
							</thead>
							<tbody id="mat_report_list" class="report-list-td">
							</tbody>
						</table>
			   </div>
	</div>
	
	<div id="adp_condition" class="mainpanel">
     <!-- 广告位查询条件 -->
			     <div class="display-div" style="border-width:1px;border-style:solid;border-color:#ff0e3;" id="adp-div" name="show-hide">
				    	<table id="adp_table" width="100%" style="word-wrap:break-word" name="odd-even">
							<thead>
								<tr>
								    <th width="10px"><input type="checkbox"  name="con-check-list"></input></th>
									<th width="95px"><span style="float: left" name="adp_title">广告位id</span></th>
									<th width="280px"><span style="float: left" name="adp_title">广告位名称</span></th>
									<th width="180px"><span style="float: left" name="adp_title">广告位编码</span></th>
									<th width="180px"><span style="float: left" name="adp_title">尺寸</span></th>
									<th width="180px"><span style="float: left" name="adp_title">分组</span></th>
									<th width="180px"><span style="float: left" name="adp_title">操作</span></th>
								</tr>
							</thead>
							<tbody id="adp_report_list" class="report-list-td">
							</tbody>
						</table>
			   </div>
	</div>
	
	<div id="order_condition" class="mainpanel">
     <!-- 订单查询条件 -->
			     <div class="display-div" style="border-width:1px;border-style:solid;border-color:#ff0e3;" id="order-div" name="show-hide">
				    	<table id="order_table" width="95%" style="word-wrap:break-word" name="odd-even">
							<thead>
								<tr>
								    <th width="10px"><input type="checkbox" name="con-check-list"></input></th>
									<th width="95px"><span style="float: left" name="order_title">订单id</span></th>
									<th width="280px"><span style="float: left" name="order_title">订单名称</span></th>
									<th width="180px"><span style="float: left" name="order_title">媒介人员</span></th>
									<th width="180px"><span style="float: left" name="order_title">销售人员</span></th>
<!-- 									<th width="180px"><span style="float: left" name="order_title">订单金额</span></th> -->
									<th width="180px"><span style="float: left" name="order_title">操作</span></th>
								</tr>
							</thead>
							<tbody id="order_report_list" class="report-list-td">
							</tbody>
						</table>
			   </div>
	</div>
	
	<div id="acc_condition" class="mainpanel">
     <!-- 广告客户查询条件 -->
			     <div class="display-div" style="border-width:1px;border-style:solid;border-color:#ff0e3;" id="acc-div" name="show-hide">
				    	<table id="acc_table" width="95%" style="word-wrap:break-word" name="odd-even">
							<thead>
								<tr>
								    <th width="10px"><input type="checkbox" name="con-check-list"></input></th>
									<th width="95px"><span style="float: left" name="acc_title">客户id</span></th>
									<th width="280px"><span style="float: left" name="acc_title">客户名称</span></th>
									<th width="180px"><span style="float: left" name="acc_title">分组</span></th>
									<th width="180px"><span style="float: left" name="acc_title">操作</span></th>
								</tr>
							</thead>
							<tbody id="acc_report_list" class="report-list-td">
							</tbody>
						</table>
			   </div>
	</div>
	
	
	<div id="sale_condition" class="mainpanel">
     <!-- 销售查询条件 -->
			     <div class="display-div" style="border-width:1px;border-style:solid;border-color:#ff0e3;" id="sale-div" name="show-hide">
				    	<table id="sale_table" width="95%" style="word-wrap:break-word" name="odd-even">
							<thead>
								<tr>
								    <th width="10px"><input type="checkbox" name="con-check-list"></input></th>
									<th width="95px"><span style="float: left" name="sale_title">销售id</span></th>
									<th width="280px"><span style="float: left" name="sale_title">销售名称</span></th>
									<th width="180px"><span style="float: left" name="sale_title">分组</span></th>
									<th width="180px"><span style="float: left" name="sale_title">操作</span></th>
								</tr>
							</thead>
							<tbody id="sale_report_list" class="report-list-td">
							</tbody>
						</table>
			   </div>
	</div>
	
	<div id="cat_condition" class="mainpanel">
     <!-- 题材查询条件 -->
			     <div class="display-div" style="border-width:1px;border-style:solid;border-color:#ff0e3;" id="cat-div" name="show-hide">
				    	<table id="cat_table" width="95%" style="word-wrap:break-word" name="odd-even">
							<thead>
								<tr>
								    <th width="10px"><input type="checkbox" name="con-check-list"></input></th>
								    <th width="95px" style="display:none;"><span style="float: left;" name="cat_title_hidden">题材id</span></th>
									<th width="280px"><span style="float: left" name="cat_title">题材名称</span></th>
									<th width="180px"><span style="float: left" name="cat_title">操作</span></th>
								</tr>
							</thead>
							<tbody id="cat_report_list" class="report-list-td">
							</tbody>
						</table>
			   </div>
	</div>
	
	<div id="country_condition" class="mainpanel">
     <!-- 产地查询条件 -->
			     <div class="display-div" style="border-width:1px;border-style:solid;border-color:#ff0e3;" id="country-div" name="show-hide">
				    	<table id="country_table" width="95%" style="word-wrap:break-word" name="odd-even">
							<thead>
								<tr>
								    <th width="10px"><input type="checkbox" name="con-check-list"></input></th>
								    <th width="95px" style="display:none;"><span style="float: left;" name="country_title_hidden">产地id</span></th>
									<th width="280px"><span style="float: left" name="country_title">产地名称</span></th>
									<th width="180px"><span style="float: left" name="country_title">操作</span></th>
								</tr>
							</thead>
							<tbody id="country_report_list" class="report-list-td">
							</tbody>
						</table>
			   </div>
	</div>
	
    <%@ include file="/common/header.jsp"%>
    <div id="layout">
    <%@ include file="/common/SideBar.jsp"%>
	<div id="frame">
		<div id="miansin" class="maintop" > 
			<ul id="maintop_ul">
			</ul>

		</div>
		<div class="main_">
			<table style="position: relative;" height="50"
				bgcolor="#ffffff" border="0" cellspacing="1" cellpadding="1">
				<tbody>
					<tr bgcolor=#ffffff width="100%">
						<td bgcolor='#ffffff' align='left'>&nbsp;<b id="condition-type-choose">广告选择</b></td>
					</tr>
					<tr bgcolor=#f0f3f4 width="70%">
						<td width=130 id="selected-condition-type" align=left>&nbsp;广告&nbsp;&nbsp;</td>
						<td align=right >
							<div style="overflow-x: hidden; overflow-y: scroll; width: 100%; background: #ffffff; HEIGHT: 150px"  	id=afp_reports_ad_select_layer>
								<center>
									<table id="linkTabel" border=0 cellspacing=0 bordercolor="#949293" bordercolorLight=white cellpadding=0 width="100%">
									        <tr bgcolor="#f0f3f4"  id="area_types" style="display:none"><td colspan="4" >
									        <table width="100%">
									          <tr>
									            <td style="text-align:center" width="25%"><span><input type="radio" id = "con_country" name='area_type'/> <span>国家</span></span></td>
									            <td style="text-align:center" width="25%"><span><input type="radio" id="con_province" name='area_type'/> <span>省份</span></span></td>
									            <td style="text-align:center" width="25%"><span><input type="radio" id="con_city" name='area_type'/> <span>城市</span></span></td>
									            <td style="text-align:center" width="25%"><span><input type="radio" id="con_custom" name='area_type'/> <span>自选地域</span></span></td>
									          </tr>
									        </table>
									        </td>
									        </tr>
									
											<tr id="display-header" bgcolor="#e5e5e5" width="100%">
												<td bgcolor="#e5e5e5" width=5 id="con_show_check">
												    <input type="checkbox" id="select_all_checkbox"/>
												</td>
												<td bgcolor="#e5e5e5" align="center" name="con_show_title">广告id</td>
												<td bgcolor="#e5e5e5" align="center" name="con_show_title">广告名称</td>
												<td bgcolor="#e5e5e5" align="center" name="con_show_title">订单名称</td>
												<td bgcolor="#e5e5e5" align="center" name="con_show_title">广告状态</td>
												<td bgcolor="#e5e5e5" align="center" name="con_show_title">投放周期</td>
											</tr>
									
									</table>
								</center>
							</div>
							<span id="blank_space_show" class="cal_num" style="color: gray;">&nbsp;共&nbsp;<span id="calNum">0</span>&nbsp;条记录</span>
							<span style="CURSOR: pointer; float: right;" id="result_operation"> <a id="select_con_all" style="display:none">全部</a>
								&nbsp;<a id="select_con">选择</a> &nbsp;<a id="select_precise">编辑</a>
								&nbsp;<a style="color: gray;" href="javascript:void(0);" id="select_group">选择分组</a> &nbsp;<a id="delete_con">去除</a>&nbsp;
						</span>
						</td>
					</tr>
					<tr bgcolor=#ffffff width="100%" name="chose-time">
						<td colspan=2 align=left>&nbsp;<b>快捷查询:</b></td>
					</tr>
					<tr bgcolor=#f0f3f4 width="100%" name="chose-time">
						<td width=110 align=left>&nbsp;快捷查询:&nbsp;</td>
						<td id="in_time" class="leftIclass"><input value="today"
							type="radio" name=reports_day />今天 <input value="yesterday"
							checked="checked" type="radio" name=reports_day />昨天 <input
							value="lastweek" type="radio" name=reports_day />上周 <input
							value="thisweek" type="radio" name=reports_day />本周 <input
							value="for7day" type="radio" name=reports_day />前七天 <input
							value="lastmonth" type="radio" name=reports_day />上月 <input
							value="thismonth" type="radio" name=reports_day />本月</td>
					</tr>

					<tr name="chose-time"><td bgcolor=#ffffff colspan=2 align=left>&nbsp;<b>报表时间设置:</b></td></tr>
					<tr bgcolor=#f0f3f4 name="chose-time">
						<td width=110 align=left>&nbsp;时间:&nbsp;&nbsp;</td>
						<td align=left>开始时间: <input type="text" class="Wdate" size=12
							style="width: 150px;" id="s_datetime" />
							&nbsp;&nbsp;至&nbsp;&nbsp;结束时间: <input type="text" class="Wdate"
							style="width: 150px;" size=12 id="e_datetime" /></td>

					</tr>
					
					<tr bgcolor="#ffffff" width="100%" name="chose-freq" style="display:none">
						<td colspan=2 align=left>&nbsp;<b>周期选择:</b></td>
					</tr>
					
					<tr bgcolor=#f0f3f4 width="100%" name="chose-freq" style="display:none">
						<td width=110 align=left>&nbsp;周期选择:&nbsp;</td>
						<td id="in_time" class="leftIclass">
						    <input value="_uvfreq_l1" checked="checked" type="radio" name=reports_freq />昨天 
						    <input value="_uvfreq_l3" type="radio" name=reports_freq />前3天
						    <input value="_uvfreq_l7" type="radio" name=reports_freq />前7天
<!-- 						    <input value="_uvfreq_l15" type="radio" name=reports_freq />前15天  -->
<!-- 						    <input value="_uvfreq_l30" type="radio" name=reports_freq />前30天 -->
					</tr>
					<tr bgcolor=#ffffff width="100%" id="select_area">
						<td colspan=2 align=left>&nbsp;<b>地域选择:</b></td>
					</tr>
					<tr  width="100%" bgcolor=#f0f3f4 >
						<td colspan=1 id='area_label' align=left>&nbsp;地域:</td>
						<td><input id="area-cdt-input" style="width:180px;" type="text" flag="true" name="area-cdt" readonly="true"/>
						<div id='ui-orient-selector' class='ui-orient-selector ui-orient-selector-none'></div></td>
					</tr>
					<tr bgcolor=#ffffff width="100%">
						<td colspan=2 align=left>&nbsp;<b>报表选择:</b></td>
					</tr>
					<tr bgcolor=#ffffff width="100%" style="display:none">
						<td bgcolor=#ffffff colspan=1 align=left>&nbsp;<b>报表选择:</b></td><td><table><tr id="rpt_type" class="leftIclass"></tr></table></td>
					</tr>
					<tr bgcolor=#ffffff width="100%"></tr>
				</tbody>
				<tbody>
					<tr bgcolor=#f0f3f4>
						<td width=110 align=left>&nbsp;报表:</td>
						<!-- <td><input id="report_input" style="width:300px;" type="text" flag="true" name="report_input" readonly="true"/></td> -->
						<td><div id="report_input" class="divCss"><div id="sub_report_input" class="buttons"></div></div></td>
					</tr>
					<tr bgcolor=#f0f3f4>
						<td></td>
						<td width=680 align=left>
							<table>
								<tbody>
									<tr class="leftIclass" id="base_report">
									</tr>
									<tr>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr bgcolor=#f0f3f4 >
						<!-- <td width=110 align=left><div style="width: 90px;">&nbsp;资源分布报表:</div></td> -->
						<td></td>
						<td width=680 align=left><table><tr id="resource_rep" class="leftIclass"></tr></table></td>
					</tr>
					<tr style="display:none"> 
						<td bgcolor=#f0f3f4 width=110 align=left>&nbsp;<font color='gray'>图表(暂不可用):</font></td>
						<td bgcolor=#f0f3f4 width=680 align=left>
							<table>
									<tr class="leftIclass">
										<td><input value="line" type="radio" name="chart" disabled="disabled"/><font color='gray'>折线图</font></td>
										<td><input value="columnar" type="radio" name="chart" disabled="disabled"/><font color='gray'>柱形图</font></td>
										<td><input value="pie" type="radio" name="chart" disabled="disabled"/><font color='gray'>饼图</font></td>
										<td><input value="bar" type="radio" name="chart" disabled="disabled"/><font color='gray'>条形图</font></td>
									</tr>
							</table>
						</td>
						
					</tr>
					<tr>
					<td></td>
					<td align="center" ><input type="button"  id="submit_to" value="提交" style="margin-top: 30px;" /></td>
					</tr>
				</tbody>
				
			</table>	
		</div>
		
<script type="text/javascript">
      //标题显示
      $("#ad_title_span").css("background","white");
	  $("#ad_title_span").css("color","#4888B4");
</script>
		<%@ include file="/common/footer.jsp"%>
	   </div>
    </div>
	<div id="dialog_form">
			<form> 
				<textarea type="text" id="rpt_value" name ="rpt_value" value="" style="width:400px;height:200px;"></textarea>
			</form>
			<div id="display_textarea_error" class="validateTips1" style="color:red;">
			</div> 							
    </div>
</body>
</html>
