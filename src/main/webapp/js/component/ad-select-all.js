/**
 * 用于当选择全选时在首页展现数据的操作
 */
var orderoTable = null;
var adpoTable = null;
var accoTable = null;
var saleoTable = null;
var catoTable = null;
var countryoTable = null;
/**
 * 当为广告报告以及物料报告时，去后端请求数据
 * @param type
 * @param param
 */
function callAdAllSelect(type,param) {
	$.ajax({
		type: "post",
		async:false,
		dataType:'json',
		url: $(ctx).val()+"/ad/"+type+"-checkall-report?param="+param,
		success:function(data) {
			if (data == null || data == "") {
			}else {
				if (type == "ad") {
					//若为广告的话则去调用conditionData.jsp中的展现广告数据的方法
					adDataDisplay(data);
				}
				if (type == "mat") {
					 matDataDisplay(data)
				}
			}
		}
	});
}
/**
 * 物料报告的首先展示
 * @param data
 */
function matDataDisplay(data) {		
	$("#linkTabel").find("tr[name='selected_con']").each(function(){
		//截取特定的id
		var tmpValue = $(this).attr("id");
		var tmpIndex = tmpValue.lastIndexOf("_");
		var subTmpValue = tmpValue.substring(tmpIndex+1,tmpValue.length);
		$("#" + conditionType + "_id_" + subTmpValue).parent().find("tr[name='selected_con']").remove();
	});
	for (var i = 0; i< data.length; i++) {
		var mat_id =  data[i].id;		    
		var mat_name = data[i].name;
		var mat_type = data[i].type;
		var mat_size = data[i].width + "*" + data[i].height;
		var mat_playDur = data[i].playDur;	
		var str = "<td>"+mat_id+"</td>"+"<td>"+mat_name+"</td>"+"<td>"+mat_type+"</td>"+"<td>"+mat_size+"</td>"+"<td>"+mat_playDur+"</td>";//页面动态增加选中复选框的记录
		var tabelTpl = '<tr bgcolor=#f0f3f4 width="100%" id="'+'mat_id_'+mat_id+'" name="selected_con">'
			+ '<td width=5 align=left ><input  '
			+ 'type="checkbox" name="selected_check"></td>';
		str = tabelTpl + str + "</tr>";
		if($("#" + conditionType + "_id_" + mat_id).length == 0) {
		    $("#linkTabel").append(str);	
		}
	}				
}
/**
 * 当非广告报告以及物料报告时，对于全选的操作从前端进行处理
 * @param conditionType
 * @param searchParam
 */
function handleClientAllSelect(conditionType,searchParam) {
	if (conditionType == "order") {
		orderoTable.fnFilter(searchParam);
	    var data = orderoTable._('tr', {"filter": "applied"});
	    displayData(data,conditionType);
	}   
	if (conditionType == "adp") {
		adpoTable.fnFilter(searchParam);
	    var data = adpoTable._('tr', {"filter": "applied"}); 
	    displayData(data,conditionType);
	}
	
	if (conditionType == "acc") {
		accoTable.fnFilter(searchParam);
	    var data = accoTable._('tr', {"filter": "applied"});
	    displayData(data,conditionType);
	}   
	
	if (conditionType == "sale") {
		saleoTable.fnFilter(searchParam);
	    var data = saleoTable._('tr', {"filter": "applied"});
	    displayData(data,conditionType);
	}   
	
	if (conditionType == "cat") {
		catoTable.fnFilter(searchParam);
	    var data = catoTable._('tr', {"filter": "applied"});
	    displayData(data,conditionType);
	}   
	
	if (conditionType == "country") {
		countryoTable.fnFilter(searchParam);
	    var data = countryoTable._('tr', {"filter": "applied"});
	    displayData(data,conditionType);
	}   
}

function displayData(data,conditionType) {
	for (var i=0;i<data.length;i++) { 
    	var str= "";
 	    var id = "";
    	var column = data[i];
    	for (var j = 1;j<column.length-1;j++) {
    		var currentValue = null;
    		if (j == 1) {
	    		id = $(column[j]).find("font").html();
	    	}
    		currentValue = $(column[j]).find("font").html();
    		if (conditionType=="cat" || conditionType == "country") {
    			j = j+1;
    			currentValue = $(column[j]).find("font").html();
    		}
    		str += "<td>" + currentValue + "</td>";
    	}
    	var tabelTpl = '<tr bgcolor=#f0f3f4 width="100%" id="'+conditionType+'_id_'+id+'" name="selected_con">'
		+ '<td width=5 align=left ><input  '
		+ 'type="checkbox" name="selected_check"></td>';
	    str = tabelTpl + str + "</tr>";
	    if($("#" + conditionType + "_id_" + id).length == 0) {
	    	$("#linkTabel").append(str);
	    }
    }  
}