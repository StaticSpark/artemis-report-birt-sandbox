<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>微距 - artemis</title>
<%@ include file="/common/taglibs.jsp"%>
<script src="${ctx}/js/mc/mc-stable-uv.js"  type="text/javascript"></script>
<script src="${ctx}/js/mc/mc-common.js"  type="text/javascript"></script>
<script type="text/javascript">
   json = ${json};
   ctx = "${ctx}";
</script>
</head>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>
<body>
<div id="frame">
    <div id="content">
         <table  id="mc-header">
            <tr>
                <td><b>报表类型：</b></td> 
                <td width="30%"><span id="reportType"></span>
     ( <a id="reqRef" href="javascript:void(0);">请求</a> | <a id="fwdRef" href="javascript:void(0);">转发</a> ) 
                </td> 
                <td><b> 播控名称：</b></td>
                <td><span  id="mcName"></span></span></td>
            </tr>
            <tr>
                <td><b> UV总数：</span></b></td> 
                <td><span id="uvSum" style="margin-right:20px"></span></td>
                <td><b id="time_tag"> 月份：</b></td>
                <td>
                    <span id="tNormal"><input id="month" class="Wdate" type="text" style="width:70px;" /></span>
                </td>
            </tr>
            <tr><td colspan="4"><input type="button" class="button" style="margin-left:0px;" id="search" value="查询" /></td></tr>
         </table>
        <div>
        <iframe  frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="width: 100%; height:510px; " src="" id="report"></iframe>
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
