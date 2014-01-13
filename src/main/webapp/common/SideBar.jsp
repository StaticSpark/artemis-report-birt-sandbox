<%@ page language="java" pageEncoding="UTF-8" %>
<style>
#sideBar {
    float:right;
    width:10%;
    height:100%;
    min-height:726px;
    background: #4888B4; /* fallback for older/unsupporting browsers */  
    FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#4888B4,endColorStr=#D2D3D4);
    background: -moz-linear-gradient(top, #4888B4, #D2D3D4);   
    background: -webkit-gradient(linear, 0 0, 0 100%, from(#4888B4), to(#D2D3D4));   
    display:inline-block;
    z-index:100;
    margin-top:-38px;
    overflow:hidden;
}
#sideBar * {
    margin:0px;
    padding:0px;
}
#sideBar ul{
    border-top:solid 1px #88C8B4;
    border-right:solid 1px #4888B4;
}
#sideBar li{
    list-style:none;
    text-align:center;
}
#sideBar a{
    cursor:pointer;
    text-decoration:none;
    line-height:35px;
    width:100%;
    display:inline-block;
    border-top-right-radius:5px;
    border-bottom-right-radius:5px;
    font-size:16px;
    color:#FFFFFF;
    text-align:center;
}
.act{
    color:#000000 !important;
    background:url("${ctx}/images/add-li/split_panel.png");
}
#frame{
    display:inline-block;
    width:90%;
}
#layout{
    width:100%;
}
.maintop{
    width:100%;
}
.main_ {
    /*border-right:0px;*/
}
.main_ > table{
    width:100%;
}
#add_second{
    width:89%  !important;
}
#subnav-strips{
    border:0px !important;
}
.sub-title{
    margin-left:-15%;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	
	$("#essReport").click(function(){window.location.href="${ctx}";});
	$("#senReport").click(function(){window.location.href="${ctx}" + "/ad-report-senior";});
	var hrf = window.location.href;
    var path = hrf.substring(hrf.lastIndexOf("/") + 1);
    if(path == "ad-report-condition" || path == "ad-report-engine")
    {
        $("#essReport").attr("class", "act");
        $("#senReport").attr("class", "");
    }
    else
    {
    	 $("#essReport").attr("class", "");
    	 $("#senReport").attr("class", "act");
    }
});
</script>
<div id="sideBar">
<ul>
    <li><a href="javascript:void(0);" id="essReport" >基础报告</a></li>
    <li><a href="javascript:void(0);" id="senReport" >固定报告</a></li>
</ul>
</div>




