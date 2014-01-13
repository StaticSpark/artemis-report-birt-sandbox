$(document).ready(function() {
	json = $("#json").html();
	ctx = $("#ctx").val();
	data = eval('(' + json + ')');
	
	$("a[name='mc-report-url']").live("click", function(){
		var mcId = $(this).attr("idTag");
		var reportTag = $(this).attr("reportTag");
		var url = ctx + "/mc/"+reportTag+"-report?id=" + mcId;
		window.open(url,"_blank");
	});
	initializeGrid();
});

function initializeGrid() {
	var mcListGrid = jQuery("#mc-list").jqGrid({
		url:'data-list', 
		datatype: "json", 
		autowidth: true,
		jsonReader: {
            repeatitems: false
        },
		height: "100%",
		colNames: ['ID','名称','所属订单','最后更新','状态','报告'],
		colModel:[ 
			{name:'id',index:'id',  align:"center",sortable:false}, 
			{name:'name',index:'name', align:"center",sortable:false},
			{name:'orderName',index:'orderName', align:"center",sortable:false},
			{name:'updateTime',index:'updateTime', align:"center",sortable:false},
			{name:'status',index:'status', align:"center",sortable:false},
			{name:'ad_operate',index:'ad_operate', align:'center',sortable:false,formatter:function(cellvalue, options, rowObject){
				var	returnStr =	'<a href="imp-report?id='+rowObject.id+'" target="_blank"  >IMP报告 |</a>'+
					'<a  href="uv-report?id='+rowObject.id+'" target="_blank"  >UV报告 |</a>'+'<a  href="concurrent-report?id='+rowObject.id+'" target="_blank"  >并发报告 </a>';
				return returnStr;
				}}
		], 
		rowList:[10,20,30,50], 
		pager: '#mc-page', 
		page:1, 
		pagerpos:'center',
		viewrecords: true, 
		multiselect: false,
		caption:'<input id="con-input" value="输入ID、名称及订单搜索"  type="text"></font><span style="margin-left:3px;"></span><input value="查 询" class="cls-button"  id="typeSearch"  type="button"> <input type="button" class="cls-button" value="并发报告" onclick="(function(){location.href=\'concurrent-report?id=-1\'})();"><input type="button" class="cls-button" value="稳定UV报告" onclick="(function(){location.href=\'stable-uv?monType=0\'})();">'
		}); 
	jQuery("#mc-list").jqGrid('navGrid','#mc-page',{edit:false,add:false,del:false,search:false});
	$("#typeSearch").click(function(){
		jQuery("#mc-list").jqGrid('setGridParam',{page:1});//默认搜到的为第一页
		var params = "search=" + $("#con-input").val() +"&page=" + jQuery('#mc-list').jqGrid('getGridParam','page') + "&rows=" + jQuery('#mc-list').jqGrid('getGridParam','rowNum');
		mcListGrid.setGridParam({postData:params}).trigger("reloadGrid");
		params= "";
	});
	
	$("#con-input").live("click",function(){
		if($(this).val() == '输入ID、名称及订单搜索') {
			$(this).val("");
		}
	});
}
