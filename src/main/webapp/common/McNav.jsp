<%@ page language="java" pageEncoding="UTF-8" %>
<style>
#mcNavBar a{
    text-decoration:none;
    cursor: pointer;
    display:inline-block;
    text-align:center;
    border:solid 1px #CCCCCC; /*框*/
    line-height:20px;
    color:#000000;
}

#mcNavBar, #mcNavBar ul{
    list-style:none;
    margin:0px;
    padding:0px;
    
}
#mcNavBar li{
    margin:0px;
    padding:0px;
}
#mcNavBar > li{
    display:inline-block;
    position:relative;
    vertical-align:top;
}
#mcNavBar > li > a{
    width:100px;
    background-color:#F2F2F2;
    border-radius:5px;
    margin-left:30px;
}

#mcNavBar ul{
    position:absolute;
    z-index:99;
    max-height:0px;
    overflow:hidden;
    
    -webkit-transition:max-height 0.6s linear;
    -moz-transition:max-height 0.6s linear;
    transition:max-height 0.6s linear;
}
#mcNavBar li:hover ul{
    max-height:120px;
}
#mcNavBar ul li{
    text-align:center;
}
#mcNavBar ul li a{
    background-color:#F2F2F2;
    display:inline-block;
    width:100px;
    border-left:solid 1px #AEAEAE; /*框*/
    border-right:solid 1px #AEAEAE; 
    border-top:none;
    border-bottom:none;
    margin-left:60px;
}
#mcNavBar ul li:first-child{
    margin-top:5px;
}
#mcNavBar ul li:first-child a{
    border-top-left-radius:5px;
    border-top-right-radius:5px;
    border-top:solid 1px #AEAEAE; /*框*/
}
#mcNavBar ul li:last-child a{
    border-bottom-left-radius:5px;
    border-bottom-right-radius:5px;
    border-bottom:solid 1px #AEAEAE; /*框*/
}
#mcNavBar ul li:hover a{
    background-color:#6495ED;
}
#mcNavBar li a div {
    display:inline-block;
    width:0px;
    height:0px;  
    border-width:6px;  
    border-style:solid;  
    border-color:transparent transparent transparent transparent;
    margin-left:7px;
}
#mcNavBar li a div:last-child{
    border-color:transparent transparent transparent #AEAEAE;
}
#mcNavBar li:hover > a{
    background-color:#6495ED;
    color:#FFFFFF;
}
#mcNavBar li:hover a div:last-child{
    border-color:transparent transparent transparent white;
}
</style>
<div>
    <ul id="mcNavBar">
    <li><a href="javascript:void(0);"><div></div>IMP报告<div></div></a>
        <ul><li><a href="javascript:void(0);" name="navBtn" reportName="mc_imp_report" id="impNormal">分天</a></li>
        <li><a href="javascript:void(0);" name="navBtn" reportName="mc_imp_sum_report" id="impSumDay">天小计</a></li>
        <li><a href="javascript:void(0);" name="navBtn" reportName="mc_imp_sum_date_report" id="impSumArea">地域小计</a></li></ul>
    </li>
    <li><a><div></div>UV报告<div></div></a>
    <ul><li><a href="javascript:void(0);" name="navBtn" id="uvPeriod">投放周期UV</a></li>
    <li><a href="javascript:void(0);" name="navBtn" id="uvDay">UV每日</a></li></ul>
    </li>
    </ul>
</div>