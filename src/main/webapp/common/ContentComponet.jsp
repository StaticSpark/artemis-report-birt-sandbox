<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> --%>
<div id="cont-cdt-frame" class="popup">
						<a id="close-cont-cdt" class="close" href="javascript: void(0);"></a>
						<div id="cont-cdt-content" class="clearfix">
							<div id="cont-cdt-left-nav">
								<a class="left-nav-item" id="left-nav-item_1" href="javascript: void(0);">按条件组合选择</a>
								<!-- <a class="left-nav-item" id="left-nav-item_2" href="javascript: void(0);">选择内容包</a> -->
								<a class="left-nav-item" id="left-nav-item_3" href="javascript: void(0);">自选节目</a>
							</div>
							
							
							<!-- 按条件组合选择 -->
							<div class="cont-cdt-right-content" id="cont-cdt-right-content_1">
								<div>
									版权&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="copyright" value="1" checked="checked" />&nbsp;正版&nbsp;&nbsp;
									<input type="checkbox" name="copyright" value="2" checked="checked"/>&nbsp;非版&nbsp;&nbsp;
									
									<div id="cont-cdt-tabs">
										<ul>
											<s:iterator value="medTypeVos" var="medTypeVo" status="st">
												<li><a href="#cont-cdt-tabs-<s:property value="#st.index + 1"/>">${medType.name}</a></li>
											</s:iterator>
										</ul>
										
										<s:iterator value="medTypeVos" var="medTypeVo" status="st">
											<div id="cont-cdt-tabs-<s:property value="#st.index + 1"/>">
												<div>题材&nbsp;&nbsp;<input type="checkbox"  name="all_cat" medTypeName="${medType.name}" medTypeId="${medType.id}"  value = "${medType.id}"/>全部${medType.name}</div>
												<!-- <ul class="clearfix"> -->
												<s:iterator value="#medTypeVo.medCats" var="medCat" status="stc">
													
													<!--<li class="med-cat-li">-->
														<input type="checkbox" name="medCat" medTypeName="${medType.name}" medTypeId="${medType.id}" medCatId="${id}" medCatName="${name}"/> ${name}
														<s:if test="#stc.index%6==5">
														</BR>
														</s:if>
													<!-- </li>  -->
												</s:iterator>
												<!-- </ul> -->
												<div>产地</div>
												<!-- <ul class="clearfix"> -->
												<s:iterator value="#medTypeVo.medCountries" var="medCountry" status="sto">
													
													<!--<li class="med-country-li">-->
														<input type="checkbox" name="medCountry" medTypeName="${medType.name}" medTypeId="${medType.id}" medCountryId="${id}" medCountryName="${name}"/> ${name}
														<s:if test="#sto.index%6==5">
														</BR>
														</s:if>
													<!-- </li>  -->
												</s:iterator>
												<!-- </ul> -->												
											</div>
										</s:iterator>
									</div>
								</div>
							</div>
							<!-- 自选节目 -->
							<div class="cont-cdt-right-content" id="cont-cdt-right-content_3">
								<table>
									<tr>
										<td>请输入影片Mid :</td>
										<td id="med-td">
											<input type="text" id="med-input" name="med-input"></input>
											<span id="med-error" style="color: red;"></span>
										</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>
											<a id="add-med_pro" class="add" href="javascript: void(0);"></a>
										</td>
									</tr>
								</table>
							</div>
							
							<script>
								
								$("#med-input").focus(function() { $("#med-error").html(""); });
								
								$("#add-med_pro").live("click",function() {
									$.ajax({
										type: "GET",
										url: "${ctx}/med/med-base!get.action",
										data: {
											"medId": $("#med-input").val()
										},
										success:function(data) {
											if(data.status == 'isDelete'){
												$("#med-error").html("该影片已被删除！");
											}else if(data.status == 'notExist'){
												$("#med-error").html("未找到该影片。");
											}else if(!contains(medList,data.id)) {
												medList.push(data.id);
												var medHtml = $("#selected-med").html();
												if(medHtml==' '||medHtml==''||medHtml==null){
													$("#selected-med").append("您已选择自选节目：<li>" + data.id + "-" + data.name + "</li>");
												} else {
													$("#selected-med").append("<li>" + data.id + "-" + data.name + "</li>");
												}
												var slctMedIpt = $("#slct-med-ipt").val();
												if(slctMedIpt == "") 
													slctMedIpt += data.id;
												 else 
													 slctMedIpt += "," + data.id;
												$("#slct-med-ipt").val(slctMedIpt);
											}
										},
										error:function(data) {
											$("#med-error").html("未找到该影片。");
										}
									});
								});
							</script>
							 <div class="select_item" >
								<span>
								<c:set var="vEnter" value="\n" scope="request"/>
								<ul id="selected-med-cat">${fn:replace(fn:substringBefore(adBaseShow.adContent,"您已选择自选节目"),vEnter,"")}</ul> 
									<ul id="selected-med"><c:if test="${fn:length(medBaseList)>0}">您已选择自选节目：</c:if>
									<s:iterator value="medBaseList" var="medBase">
									<li>${medBase.id}_${medBase.name}</li></s:iterator></ul>
									<!-- selected-med-input -->
									<input type="hidden" id="slct-med-ipt" name="slctMedIpt" value="${adBaseShow.adMedValue}"></input>
									<input type="button" class="button" id="select_btn" value="选择"/>
									<input type="button" class="button" id="resetMed" value="重置"/>
									<input type="button" class="button" id="addMedCatAndCountry" value="确认"/>
									<input type="button" class="button" id="cancel_ad" value="取消"/>
									<input type="hidden" id="selected-med-cat-input" name="selectedMedCatInput" value="${adBaseShow.catCountryValue}"></input>
								</span>
							</div>
						</div>
					</div>