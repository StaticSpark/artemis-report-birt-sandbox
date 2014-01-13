<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.funshion.artemis.common.config.PropertiesConfigParse" %>
<style type="text/css" rel="stylesheet">
#nav-strip ul {
   float:left;
}
.title-span{
	width:90px;
	line-height:29px;
	text-align:center;
	font-size:16px;
	height:29px;
	color:white;
	background-color:#4888B4;
	cursor:pointer;
	float:left;
	display:block;
	overflow:hidden;
	border-radius: 3px 3px 0px 0px;
}

</style>

<div id="header">
	<a href="${ctx}/ad-report-condition">
	<img id="title" alt="title"	style="width: 100%; heigth: 60px; margin: 0px 0px 0px 0px;"	src="${ctx}/images/header/header3.png" /></a>
     <span style="margin: 1em; float: right;">
        <font color="#ffffff" id="userName">
                <security:authentication property="principal.username" />
            </font>
             <a id="to_atlas" style="color: white;" target="_blank" href="<%=PropertiesConfigParse.getAtlasHome()%>">| 去往 Atlas </a>
		  <a href="${ctx}/j_spring_cas_security_logout"><font color="#ffffff"> | 退出</font></a>
	</span>
	<div id="nav-controler"> 
		<div id="nav-strip">
          <ul style="margin-left:20px">
           <li class="title-span" src="${ctx}/ad-report-condition" id="ad_title_span">广告</li>
           <li class="title-span" src="${atlas_home}/mc/mc.action" id="mc_title_span">播控</li>
          </ul>
		</div>
		<div id="subnav-strips" style="margin-left:20px">
		</div>
	</div>
</div>

<script type="text/javascript">
		$("#nav-strip ul li").live("click", function() {
			var url = $(this).attr("src");
			window.location.href = url;
		});
</script>

