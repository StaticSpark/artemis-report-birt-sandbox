$(document).ready(function() {	
	$("body").click(function(e){
		e = e || window.event;
		var target = e.target || e.srcElement;
		if(target.parentNode.parentNode.parentNode != null && target.parentNode.parentNode.parentNode.parentNode != null) {
			var tableId = target.parentNode.parentNode.parentNode.id;
			var tableId2 = target.parentNode.parentNode.parentNode.parentNode.id;
		    if(tableId == "colTable" || tableId == "tipInfo" || tableId == "tableArea" || tableId == "areaListTip" 
		        	|| tableId2 == "colTable" || tableId2 == "tipInfo" || tableId2 == "areaListTip" || tableId2 == "tableArea"){
			} else if(target.id != "areaList"){
				hiddeInfo();
			}
		}
	});
});
//检验开始时间是否小于结束时间 
function checkEndTime(startTime, endTime){  
  var start=new Date(startTime.replace("-", "/").replace("-", "/"));  
  var end=new Date(endTime.replace("-", "/").replace("-", "/"));  
  if(end<start){  
      return false;  
  }  
  return true;  
}  

//打开子报表
function openSubReport(monId, areaId, monDate, path, maxImpFwd, hour, x, y) {
	y = parseInt(y) + 363;
	y = y + 'px';
	var width = document.body.clientWidth;
	var windowparam = 'height=100,width='+width+',top='+y+',left=20,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no,location=no,menubar=no,titlebar=no';
	var reportParam =  '&monId=' + monId + '&areaId=' + areaId + '&monDate=' + monDate + '&path=' + path + '&maxImpFwd=' + maxImpFwd;
	var url = ctx + '/frameset?__report=persistence/mc/mc_imp_sub_report.rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&__navigationbar=false&__toolbar=false' +
	           reportParam;
	if(hour > -1) { 
		url = ctx + '/frameset?__report=persistence/mc/mc_imp_hour_sub_report.rptdesign&__parameterpage=false&__showtitle=false&__toolbar=false&__navigationbar=false&__toolbar=false' +
        reportParam + '&hour=' + hour;
	}
	window.open (url,'播控子路径报告',windowparam);
}

//并发报告的悬浮窗信息
function showConcurrentReportInfo(uv1_plus, uv2_plus, x, y) 
{	
	
	if(uv1_plus == 0 || uv2_plus == 0)
		return;
	percent = uv2_plus / uv1_plus;
	percent = percent * 100;
	result = changeTwoDecimal_f(percent);
	data = "占比为："+result + "%";
	showConcurrentReportTipInfo(x, y, data);
}

//显示悬浮信息
function showConcurrentReportTipInfo(x, y, info)
{
	x += 37;
	//y += 260;//之前使用的方法
	y = $("#report").offset().top + y+20;//采用报表切换后的方法
	var tip = document.getElementById("tipInfo");
	tip.style.display = 'block';
	tip.style.margin = '1px';
	tip.style.top = y + 'px';
	tip.style.left = x + 'px';
	$("#tipInfo").html(info);
}

//小数点后保留2位
function changeTwoDecimal_f(x)  {  
	var f_x = parseFloat(x);   
	var f_x = Math.round(x*100)/100;  
	var s_x = f_x.toString();  
	var pos_decimal = s_x.indexOf('.');  
	
	if (pos_decimal < 0) {  
		pos_decimal = s_x.length;  
		s_x += '.';  
	}  
	while (s_x.length <= pos_decimal + 2) {  
		s_x += '0';  
	} 
	
	return s_x;  
} 
//隐藏悬浮窗
function mouseOutAction()
{
	$("#tipInfo").hide();
}

//显示悬浮信息
function showTipInfo(x, y, info, tipId) {
	if($("#" + tipId).length > 0) {
		return;
	}
	var tip = document.createElement("span");
	tip.id = tipId;
	tip.className = "tip";
	tip.style.display = 'block';
	tip.style.margin = '1px';
	tip.style.top = y + 'px';
	tip.style.left = x + 'px';
	document.body.appendChild(tip);
	$("#" + tipId).html(info);
	$("#" + tipId).css("background","#CAE8EA");
	return tipId;
}

//报告筛选列
function showColsSelectInfo(type, x, y) {
	var colList = $("input[name='col_list']");
	$("#tipInfo").show();
	var tip = document.getElementById("tipInfo");
	$(".tip").css("background","#CAE8EA");
	tip.style.display = 'block';
	tip.style.margin = '1px';
	if("" == xPox) {
		xPox = -60;
	}
	
	if("" == yPox) {
		yPox = 250;
	}
	x = parseInt(x);
	y = parseInt(y);
	x = x + parseInt(xPox);
	y = y + parseInt(yPox);
	
	if(type == "ad") {
		colArray = getReportColumnsInfo();
		y = $("#report").offset().top + 40;
	}
	
	//当进行报表切换时，重新获得该tips的位置
	if(type == "mc") {
		y = $("#report").offset().top + 40;
	}
	tip.style.top = y +'px';
	tip.style.left = (x - 30) + 'px';
	
	if(colList.length > 0 && reportName == $("#colTable").attr("reportName")) {
		return;
	}
		
	displayTipPage(colArray,x,y);
}

function displayTipPage(colArray, x, y) {
	var html = "<table id = 'colTable' reportName="+reportName+">";
	var isSelectAll = true;//是否全选
	for(var i = 0; i < colArray.length; i++) {
		if(colArray[i].columnDisplayName.length > 0) {
			var checkedStr = " checked=checked ";
			if(colArray[i].isShow == "0") {
				checkedStr = "  ";
				isSelectAll = false;
			}
			html += "<tr><td><input type='checkbox' " +checkedStr+"value='"+colArray[i].index+"' name='col_list'></input></td><td>" + colArray[i].columnDisplayName + "</td></tr>";
		}
	}
	var selectAllStr = "";
	if(isSelectAll) {
		selectAllStr = "checked=checked";
	}
	html += "<tr><td><input  type='checkbox' "+selectAllStr+" id='select_all_cols'></input></td><td><font color='red'>全选</font></td></tr>";
	html += "<tr><td colspan='2' style='text-align:center'><input type='button' id='changeCols' value='确定'/></td></tr>";
	html += "</table>";
	
	$("#tipInfo").html(html);
	tipEvent();
}

function tipEvent() {
	$("#select_all_cols").live("click", function(){
		if($(this).attr("checked") == "checked") {
			$("input[name='col_list']").each(function(i){
				$(this).attr("checked","checked");
			});
		} else {
			$("input[name='col_list']").each(function(i){
				$(this).attr("checked",false);
			});
		}
	});
	
	$("#changeCols").live("click", function() {
		var selectCols = "";
		var hideCounts = 0;//需要隐藏的列数
		$("input[name='col_list']").each(function(i) {
			if($(this).attr("checked") != "checked") {
				var columnName = $(this).attr("value");
				selectCols = selectCols + columnName + ",";
			} else {
				hideCounts++;
			}
		});
		
		if(hideCounts == 0) {
			alert("至少选择一个指标!");
			return;
		} else {
			selectCols = selectCols.substring(0, selectCols.length - 1);
			var iframe = document.getElementsByTagName("iframe")[0];
			var url = new Common().updateUrlParam(iframe.src, "hideColumns", selectCols);
			iframe.src = url;
			$("#tipInfo").hide();
		}
	});
}

/**
 * 获取报告列信息(并将某些列放到前面)
 */
function getReportColumnsInfo() {
	var columns = new Array();
	for(var i = 0; i < rptList.length; i++) {
		if(reportId == rptList[i].id) {
			columns = rptList[i].columnList;
		}
	}
		
	var a = ['请求量','浏览量','播放量','点击量'];//需要放在最前面的 几列
    var result = new Array();
	var pirotyCol = new Array();//
	var sn = new Array();
	//广告位报告，广告位分布及基础分布报表添加请求量指标，其余报表隐藏请求量
	var noReqLst = {'4':'', '10':'', '13':'', '21':'', '14':'', '15':'', '16':'', '49':'', '51':''};
	
	// 非广告位报告，不显示播放比、有效播放比、完整播放比
	if (conditonType != "adp" && conditonType != "area") {
		for(var i = 0; i < columns.length; i++) {
			if (columns[i].columnDisplayName == '播放比' || columns[i].columnDisplayName == '有效播放比' || columns[i].columnDisplayName == '完整播放比'){
				columns.remove(i);
			}
			if(noReqLst.hasOwnProperty(reportId) && columns[i].columnDisplayName == '请求量')
				columns.remove(i);
		}
	}else{
		if(conditonType == "area") {
			for(var i = 0; i < columns.length; i++) {
				if (columns[i].columnDisplayName == '播放比' || columns[i].columnDisplayName == '有效播放比' || columns[i].columnDisplayName == '完整播放比'){
					columns.remove(i);
				}
			}
		}
		
		//adp 二维报表，不包含广告位分布的二维报表，需要隐藏广告位请求量
		if(idGroup)
		{
			var cols = idGroup.split(",");
			var id_f = cols[0];
			var id_l = cols[1];
			var noReqTab = {"1":"", "2":"","3":"","52":"","53":""};
			
			if(noReqTab.hasOwnProperty(id_f) || noReqTab.hasOwnProperty(id_l))
			{
				for(var i = 0; i < columns.length; i++) 
				{
					if(columns[i].columnDisplayName == '请求量')
						columns.remove(i);
				}
			}
		}
	}

	for(var i = 0; i < columns.length; i++) {
		var isExist = "false";
		for(var j = 0; j < a.length; j++) {
			if(a[j] == columns[i].columnDisplayName) {
				isExist = "true";
				pirotyCol.push(columns[i]);
			};
		};
		
		if(isExist == "false" && columns[i].columnDisplayName.length > 0 && columns[i].isSift == '1') {
            if (columns[i].columnDisplayName !="序列号"){
            	result.push(columns[i]);
            }else {
            	sn.push(columns[i]);
            }
		};
				
	};
	result.push(sn[0]);
	pirotyCol.reverse();
	for(var i = 0; i < pirotyCol.length; i++) {
		result.unshift(pirotyCol[i]);
	} 	
	return result;
}


//隐藏提示信息
function hiddeInfo() {
	$(".tip").hide();
}

//显示广告报告标题信息
var timeout;
function showAdReportTitleTips(x, y, columnName) {

    timeout = setTimeout(function () {
        titleInfo = "";

        if (columnName == "日浏览指标") {
            titleInfo = "按日期设定的广告浏览量";
        } else if (columnName == "浏览量") {
            titleInfo = "请求广告位后成功返回广告的量";
        } else if (columnName == "浏览完成比") {
            titleInfo = "浏览量与日浏览指标的比值";
        } else if (columnName == "日播放指标") {
            titleInfo = "按日期设定的广告播放量";
        } else if (columnName == "播放量") {
            titleInfo = "广告实际播放量";
        } else if (columnName == "播放完成比") {
            titleInfo = "播放量与日播放指标的比值";
        } else if (columnName == "有效播放量") {
            titleInfo = "广告播放时间大于5s的播放量";
        } else if (columnName == "完整播放量") {
            titleInfo = "广告播放时长>=(物料时长-1)的播放量";
        } else if (columnName == "点击量") {
            titleInfo = "广告被点击的量";
        } else if (columnName == "点击率1") {
            titleInfo = "广告的点击量与播放量的比值";
        }else if (columnName == "请求量") {
            titleInfo = "请求广告位的总量";
        } else if (columnName == "播放比") {
            titleInfo = "播放量与浏览量的比值";
        } else if (columnName == "有效播放比") {
            titleInfo = "有效播放量与浏览量的比值";
        } else if (columnName == "完整播放比") {
            titleInfo = "完整播放量与浏览量的比值";
        } else if (columnName == "点击率2") {
            titleInfo = "广告的点击量与浏览量的比值";
        }

        if (titleInfo != "") {
            showConcurrentReportTipInfo(x, y, titleInfo);
        }
    }, 1000);
}

//隐藏广告报告标题信息，鼠标移出后提示消失
function hideAdReportTitleTips() {
    clearInterval(timeout);
    mouseOutAction();
}