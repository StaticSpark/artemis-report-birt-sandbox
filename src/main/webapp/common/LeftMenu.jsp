<%@ page language="java" pageEncoding="UTF-8" %>
<style>
.clear{
    clear:none;
}
#leftMenu{
    width:10%;
    height:100%;
    min-height:480px;
    float:left;
    margin-right:20px;
    margin-top:20px;
    margin-left:10px;
    background: #4888B4; /* fallback for older/unsupporting browsers */  
    FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#4488AA,endColorStr=#55CCCC);
    background: -moz-linear-gradient(top, #4488AA, #55CCCC);   
    background: -webkit-gradient(linear, 0 0, 0 100%, from(#4488AA), to(#55CCCC));   
    
    font-family: Arial;
    font-weight: normal;
    font-style: normal;
    text-decoration: none;
    color: #FFFFFF;
    
}
#leftMenu a{
    color: #FFFFFF !important;
    margin-left:5px;
}
.menuHeader{
    margin:10px;
    padding-bottom:10px;
    border:none;
    border-bottom:solid 1px #C0C0C0;
	font-size: 18px;
}

.imgDown{
    display:inline-block;
    width:21px;
    height:18px;
    background:url(${ctx}/images/left-menu/left_menu_tri_down.png)  no-repeat;
    background-position:center center;
}
.imgRight{
    display:inline-block;
    width:21px;
    height:18px;
    background:url(${ctx}/images/left-menu/left_menu_tri_right.png) no-repeat;
    background-position:center center;
}

#leftMenu > ul > li{
    margin-left:20px;
	font-size: 16px;
    vertical-align:text-top;
}
#leftMenu > ul > li > ul{
    display:none;
}
#leftMenu ul ul li{
    margin-left:30%;
}
#leftMenu ul ul a{
    font-size:14px;
    width:75%;
    padding:3px;
    display:inline-block;
}
.cur{
    border-radius:3px;
    background:#FFCC33;    
}
</style>

<%  
    String navId = request.getParameter("navId"); 
    String menuId = request.getParameter("menuId");
%>
<script type="text/javascript">
$(document).ready(function(){
	var navId = "${navId}";
	var menuId = "${menuId}";
	//默认值
	if(navId == "")
		 navId = "rStock";
	if(menuId == "")
		 menuId = "frontPatch";

	$("#" + navId + " > span").attr("class", "imgDown");
	$("#" + navId + " > ul").css("display", "block");
	$("#" + menuId).attr("class", "cur");
	
	$("#frontPatch").click(function(){
		window.location.href=ctx+"/ad-report-senior?navId=rStock&menuId=frontPatch";
	});
	
	$(".mTitle").click(function(){
		var imgClz = $(this).prev().attr("class");
		if(imgClz == "imgDown")
		{
			$(this).prev().attr("class", "imgRight");
			$(this).next().hide();
		}
		else
		{
	         $(this).prev().attr("class", "imgDown");
	         $(this).next().show();
		}
	});
});
</script>

<div id="leftMenu">
    <div class="menuHeader">
        <img src="${ctx}/images/left-menu/left_menu_img.png" />
        <span>报告类型</span>
    </div>
    <ul>
        <li id="rStock">
            <span class="imgRight"></span><a href="javascript:void(0)" class="mTitle">资源位库存</a>
            <ul>
                <li><a href="javascript:void(0);"  id="frontPatch">前贴片</a></li>
            </ul>
        </li>
        <!-- <li id="rABC">
            <span class="imgRight"></span><a href="javascript:void(0)" class="mTitle">资源位库存</a>
            <ul>
                <li><a href="javascript:void(0);" id="wsadc">前贴片</a></li>
            </ul>
            </li>
        -->
    </ul>
</div>
<div class="clear"></div>