<%@ page contentType="text/html;charset=UTF-8"%>
<link href="${ctx}/css/area_component.css" type="text/css" rel="stylesheet"/>
<script	src="${ctx}/js/jquery.tmpl.min.js"  type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/js/component/area-component.js"></script>
<!-- 地域选择组件 -->
<script id="areaUITemplate" type="text/x-jquery-tmpl">
<div class='ui-orient-selector-head list-table-head'>  
	<span>
		<div style="float:left">请选择地域：<input type='radio' name='areasType' id='china-country' checked='checked' value='china-country'/>中国 &nbsp;</div>
		<div style="float:left"><input type='radio' id='other-country' name='areasType' value='other-country'/>其他国家  &nbsp;</div>
		<div id="area_group_show_div" name="area_group_show_div" style="float:left"><input type='radio' id='area_group' name='areasType' value='area_group'/>地域组  &nbsp;&nbsp;&nbsp;&nbsp;</div>
	</span>
	<span>
		<input type="text" id="auto_area_search" name="auto_area_search" autocomplete="off">
		<span><label id="error_info" style="color:red"></label></span>
		<input type="hidden" id="auto_area_search_hidden" name="auto_area_search_hidden">
		<div style="z-index: 1000; border: 1px solid; width: 200px; position:absolute; background-color:#FFF" id="search_area_tip"></div>
		<input type="button" id="auto_area_search_cmd" name="auto_area_search_cmd" value="添加">
	</span>
</div>  
<div id='ctrlorientorientselectorBody' class='ui-orient-selector-body'>  
	<div id='ctrlorientorientselectorModuleregion' class='ui-orient-selector-module'>  
		<div id='ctrlorientorientselectorLRListregion' class='ui-orient-sel-region'>  
			<div id='ctrlorientorientselectorLListregion' class='ui-orient-sel-llist'>  
				<div>  
					<div class='ui-table-head'>  
						<div class='ui-table-head-row'>  
							<table width='349' cellspacing='0' cellpadding='0' border='0'>  
								<tbody>  
									<tr>  
										<th style='width: 244px;padding:0;'>  
											<div class='ui-table-thcntr'>  
												<select id="select-ui-type">
													<option value="省市列表" selected="true">省市列表</option>
													<option value="一线城市">一线城市</option>
													<option value="省会城市">省会城市</option>
												</select>
											</div>  
										</th>  
										<th style='width: 103px;padding:0;'>  
											<div class='ui-table-thcntr'>  
												<div class='ui-table-thtext'>  
													<span class='ui-orient-sel-listentry' id='orient-sel-all'>全部添加</span>
													<span class='ui-orient-sel-listentry' id='orient-sel-invert'>反选</span>  
												</div>  
											</div>  
										</th>  
									</tr>  
								</tbody>  
							</table>  
						</div>  
					</div>  
					<div class='ui-orient-sel-regionlb'>  
						<table id='area-data-table'>  
							<tbody id="area-data-table-body">  
							</tbody>  
						</table>  
					</div>  
				</div>  
			</div>  
			<div class='ui-orient-gticon'></div>  
			<div id='ctrlorientorientselectorRListregion' class='ui-orient-sel-rlist'>  
				<div>  
					<div class='ui-table-head'>  
						<div class='ui-table-head-row'>  
						  
						<table width='250' cellspacing='0' cellpadding='0' border='0'>  
							<tbody>  
								<tr>  
									<th style='width: 154px;padding:0;'>  
										<div class='ui-table-thcntr'>  
											<div class='ui-table-thtext'>已选地理位置</div>  
										</div>  
									</th>  
									<th style='width: 93px;padding:0;'>  
										<div class='ui-table-thcntr'>  
											<div class='ui-table-thtext'>  
												<span id='orient-disel-all' class='ui-orient-sel-listentry' onclick=''>清空</span>  
											</div>  
										</div>  
									</th>  
								</tr>  
							</tbody>  
						</table>  
						</div>  
					</div>  
					<div id='ctrlorientorientselectorRListBodyregion' class='ui-orient-sel-regionrb'>  

					</div>  
				</div>  
			</div>  
		<div id='ctrlorientorientSelectorCityLayer' class='ui-orient-sel-citylayer' style='left: -10000px;' province='21' left='58'>  		  			  
		</div> 
		</div>  
	</div>  
</div>  
<div class='ui-orient-selector-foot'>  
	<div id='ctrlbuttonctrlorientorientselectOK' class='ui-button' ui='' control='ctrlorientorientselectOK' refer=''>  
		<div class='ui-button-bg-left'></div>  
		<div id='ctrlbuttonctrlorientorientselectOKlabel' class='ui-button-label' style='width: 40px;'>完成</div>  
	</div>  
	  
	<div id='ctrlbuttonctrlorientorientselectCancel' class='ui-button' ui='' control='ctrlorientorientselectCancel' refer=''>  
		<div class='ui-button-bg-left'></div>  
		<div id='ctrlbuttonctrlorientorientselectCancellabel' class='ui-button-label' style='width: 40px;'>取消</div>  
	</div>  
</div>
</script>
<script id="areaDataRowUITemplate" type="text/x-jquery-tmpl">
	<tr id="areaDataRow_\${rowNum}"></tr>
</script>
<script id="areaDataRowContentUITemplate" type="text/x-jquery-tmpl">
<td id="ctrlorientorientselector_\${id}" class="ui-orient-sel-regioncol">
	<div id="areaDataRow_div_\${id}" style="overflow:hidden;height:28px;width:77px;">
		<input id="areaDataRow_input_\${id}" class="area-second-level-radio" type="checkbox" name="id\${id}">
		<label id="areaDataRow_label_\${id}" name="id\${id}">\${name}</label>
	</div>
</td>
</script>
<script id="areaDataCityUITemplate" type="text/x-jquery-tmpl">
<p class="ui-orient-sel-city" onmouseout="this.style.background='#fff';" onmouseover="this.style.background='#feeecc';">
	<input id="areaDataRow_city_input_\${id}" type="checkbox" cityname="\${name}" cityid="\${id}" secondLevelAreaName="\${pname}" secondLevelAreaId="\${pid}">
	<label for="areaDataRow_city_label_\${id}">\${name}</label>
</p>
</script>
<script id="areaSelectedDataRowUITemplate" type="text/x-jquery-tmpl">
<div class='ui-table-row' province='\${id}' id='new-row-\${id}' areasType="\${areasType}"> 
	<table width='300' cellspacing='0' cellpadding='0' border='0'>  
		<tbody>  
			<tr>  
				<td style='width: 187px;padding:0px;'>  
					<table width='100%' border='0' collpadding='0' collspacing='0'>  
						<tbody>  
							<tr>  
								<td width='5' style='padding:0px;'><label id='new-row-slideToggle-\${id}'><font size="3">&nbsp;&nbsp;\${slideToggle}</font></label></td>  
								<td width='5' style='padding:0px;'></td>  
								<td style='padding:0px;'>  
									<div class='ui-table-tdcntr ui-orient-sel-province'>  
										<nobr class='city-name-label'>\${name}</nobr>  
									</div>  
								</td>  
							</tr>  
						</tbody>  
					</table>  
				</td>  
				<td style='width: 110px;' >  
					<div class='ui-table-tdcntr'>  
						<span id="deleteAction_\${id}" class='ui-orient-sel-listentry' onclick="">删除</span>  
					</div>  
				</td>  
			</tr>  
		</tbody>  
	</table> 
</div>
</script>
