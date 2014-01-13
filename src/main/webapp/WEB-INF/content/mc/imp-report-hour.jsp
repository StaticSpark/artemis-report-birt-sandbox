<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>微距 - artemis</title>
<%@ include file="/common/taglibs.jsp"%>
<script src="${ctx}/js/mc/mc-common.js" type="text/javascript"></script>
<script src="${ctx}/js/mc/mc-imp-report-hour.js" type="text/javascript"></script>
</head>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>
<body>

<style>
.NavImg {
 
}
.NavImg a{
    display:inline-block;
    cursor:pointer;
    height:16px;
    width:16px;
}

.NavImgl a{
    background:transparent url(../images/img.gif) no-repeat scroll -16px 0;
}

 .NavImgr a{
    background:transparent url(../images/img.gif) no-repeat scroll -32px 0;
}

.timeTbl{
    border:0px none;   
    padding:0px;
    margin:0px;
}

.timeTbl td{
    padding:0px;
    margin:0px;
}
.timeUp{
    display:inline-block;
    height:10px;
    width:13px;
    background:url(../images/img.gif) no-repeat -32px -16px;
    cursor:pointer;
    margin-left:2px;
}
.timeDown{
    display:inline-block;
    height:10px;
    width:13px;
    background:url(../images/img.gif) no-repeat -48px -16px;
    cursor:pointer;
    margin-left:2px;
}
</style>

	<div id="frame">
		<div id="content">
			<textarea id="json" style="display: none">${json}</textarea>
			<table id="mc-header">
			    <tr>
			        <td><b>报表类型：</b></td> 
	                <td width="30%"><span id="reportType"></span> <span id="sourceType"></span></td>
			        <td><b>播控名称：</b></td>
			        <td><span id="name"></span></td>
			    </tr>
			    <tr>
			        <td><b>地域 ：</b></td>
			        <td>
			            <span id="areaName"></span>
				        <select id="sltAreaName"></select>
			        </td>
			        <td><b>日期：</b></td>
			        <td><div>
			          <span class="NavImg NavImgl"><a href="#" id="timePre"></a></span>
                             <input id="date" class="Wdate" type="text" style="width:90px;" />
                      <span class="NavImg NavImgr"><a href="#" id="timeLater"></a></span>
			        </div>
			        
			      <!--  <table class="timeTbl" cellspacing="0px" cellpadding="0px">
			         <tr>   
			             <td rowspan="2">
			                 <span class="NavImg NavImgl"><a href="#"></a></span>
			                 <input id="date" class="Wdate" type="text" style="width:90px;" />
			                 <span class="NavImg NavImgr"><a href="#"></a></span>
			             </td>
	                     <td><a class="timeUp" ></a></td>		             
			         </tr>
			         <tr><td><a class="timeDown" ></a></td></tr>
			             </table>   -->
			        </td>
			    </tr>
			    <tr>
			        <td> <b>path：</b></td>
			        <td>
			             <select id="sltPath"></select>
			        </td>
			        <td><b>设定投放量：</b> </td>
			        <td>
			             <span id="impLimit"></span>  <span id="clkCtrl" style="margin-left:40px;"></span> 
			        </td>
			    </tr>
			    <tr><td colspan="4"> <input id="search_btn" class="button" type="button" value="查询" name="search" style="margin-left: 0px;" /> </td></tr>
			</table>
			<input type="hidden" id="ctx" value="${ctx}" /> 
			<iframe class="iframe_class" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="width: 100%; height: 600px;"
				src="" id="report"></iframe>
		</div>
	</div>
</body>
<script type="text/javascript">
	//标题显示
	$("#mc_title_span").css("background", "white");
	$("#mc_title_span").css("color", "#4888B4");
</script>
<%@ include file="/common/footer.jsp"%>
</html>