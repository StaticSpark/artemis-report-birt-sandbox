<%@ page language="java" pageEncoding="UTF-8" %>
<style>
#mc-header {
    border:solid 1px  rgb(202, 209, 215);
    width:100%;
    margin-bottom:7px;
    padding:10px;
    border-collapse:collapse;
}
#mc-header tr:last-child{
    border-top:solid 1px rgb(202, 209, 215);
}
</style>
         <table id="mc-header">
               <tr  nowrap="nowrap">
                  <td><b>报表类型：</b></td> 
                  <td width="30%"><span id="reportType"></span><span id="reportTail"></span></td> 
                  <td><b>播控名称：</b></td>
                  <td><span  id="mcName"></span></span></td>
               </tr>
               <tr>
                 <td><b>地域：</b></td> 
                 <td><input type="text" flag="false"  id="areaList" value="选择地域" readonly="readonly"/>
                     <input type="hidden" id="areaIds"/>
                     <span id="allArea" style="display:none;">全部地域</span>
                 </td>
                 <td><b>选择日期：</b></td> 
                 <td>
                                   开始日期：<input type="text" class="Wdate" style="margin-right:10px;width:90px;" id='startDate'/> 
                              <span  style="margin-left:10px;margin-right:10px;">至</span> 结束日期：
                              <input type="text" class="Wdate"  style="width:90px;" id='endDate'/>
         
                 </td>
               </tr>
               <tr id="imp-day-tr" style="display:none;">
                 <td><span name='pathInfo'><b>path</b></span></td> 
                 <td>
                   <span name='pathInfo'><select id="pathList" style="width:30px; display:none;"></select>
                        <span id="pathStar" style="display:none;">*</span>
                   </span>
                 </td>
                 <td colspan="2">
                        <input type='button' class="button"  name="search" value='查询' id="path_search" style="margin-left:0px;" />
                        <input type="button" class="button"  name="viewChart" value="查看分布图" id="path_switch" /> </td>
                 </tr>
                 
                 <tr id="imp-btn" style="display:none;">
                 <td colspan="4">
                    <input type='button' class="button"  name="search" value='查询' id='search' style="margin-left:0px;" />
                    <input type="button" class="button"  name="viewChart" value="查看分布图" id="switchLoction" firstElement="report"/> 
                </td>
               </tr>
               <tr>
                   <td><b>链接到：</b></td>
                   <td colspan="3"><%@ include file="/common/McNav.jsp" %></td>
               </tr>
         </table> 