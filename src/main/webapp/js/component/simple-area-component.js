$(document).ready(function() {
	__simpleArea1 = new SimpleAreaComponent();
});


var SimpleAreaComponent =  Common.extend({
	areaIdArray : null,
	areaNameArray : null,
	areaInfo : null,
	constructor:function(){
		__simpleArea = this; 
		areaInfo = data.areaInfo;
		areaIdArray = new Array();
		areaNameArray = new Array();
		this.base();
		this.initliazeAreaTable();
		this.initEvent();
	},
	initEvent : function () {
		//点击地域框
	    $("#areaList").click(function() {
	    	if(areaInfo.length == 0) {
	    		hiAlert("没有可选地域!");
	    		return;
	    	}
	    	//点击地域框，如果已经弹出了，就隐藏，如果已经隐藏了，就弹出
	    	if($(this).attr("flag") == "true") {
	    		$("#areaListTip").hide();
	    		$(this).attr("flag", "false");
	    	}  else {
	    		$(this).attr("flag", "true");
		    	if($("#tableArea").length == 0) {//地域组件第一次初始化
		    		var height = $("#areaList").position().top + $("#areaList").height();
		    		showTipInfo($("#areaList").position().left ,height, tableArea, "areaListTip");
		    	} else {
		    		$("#areaListTip").show();
		    	}
	    	}
	    });
	    
	    $("#areaList").blur(function() {//失去焦点
	    	$(this).attr("flag", "false");
	    });
	    
	    //点击全选
	    $("#select_all_area").live("click", function(){
			if($(this).attr("checked") == "checked") {
				$("input[name='area_select']").each(function(i){
					$(this).attr("checked","checked");
					var areaId = $(this).attr("areaId");
					__simpleArea.updateArea(areaId);
				});
			} else {
				$("input[name='area_select']").each(function(i){
					$(this).attr("checked",false);
					var areaId = $(this).attr("areaId");
					__simpleArea.updateArea(areaId);
				});
			}
		});
	    
	    //点击地域
	    $("[name='area_select']").live("click", function(){
	    	var areaId = $(this).attr("areaId");
	    	__simpleArea.updateArea(areaId);
	    });
	    
	    //鼠标滑过地域框
	    $("#areaList").mouseover(function(){
	    	var selectedAreaInfo = $("#areaList").val();
	    	if(selectedAreaInfo.length < 10 || $("#areaList").attr("flag") == "true") {//地域过少或者 地域框已弹出的情况下 都不会有悬浮提示
	    		return;
	    	}
	    	
	    	var areaInfoLength = selectedAreaInfo.length;//已选择的地域信息长度
	    	var newTip = "";
	    	var lineWidth = 50;//悬浮提示框 每行最大字符数
	    	
	    	for(var i = 0; i < (areaInfoLength / lineWidth + 1); i++) {//将过长的地域信息分行显示
	    		var start = i * lineWidth;
	    		var end = (i+1) * lineWidth; 
	    		if(end > areaInfoLength) {
	    			end = areaInfoLength;
	    		}
	    		newTip += selectedAreaInfo.substring(start, end) + "<br/>";
	    	}
	    	var lineNum = areaInfoLength / lineWidth + 1;
	    	
	    	var height = $("#areaList").position().top - $("#areaList").height() - 15 - lineNum * 15;
	    	showTipInfo($("#areaList").position().left, height, newTip, "selectedAreaInfo");
	    });
	    //鼠标离开地域框
	    $("#areaList").mouseout(function(){
	    	$("#selectedAreaInfo").remove();
	    });
	},
	initliazeAreaTable : function() {
		if(areaInfo.length == 0) {
			return;
		}
		
		tableArea = "<table id='tableArea'>";
	    tableArea += "<tr><td><input type='checkbox' id='select_all_area'></td><td><font color='red'>全选</font></td></tr>";
	
		if(areaInfo.length < 12) {
			for(var i = 0; i < areaInfo.length;  i++) {//添加地域信息到下拉列表
			    tableArea += "<tr><td><input type='checkbox' areaName='"+areaInfo[i].name+"' areaId="+ areaInfo[i].id +" name='area_select'></td><td>" + areaInfo[i].name + "</td></tr>";    	
			}
		} else {
			for(var i = 0; i < areaInfo.length;  i=i+4) {//添加地域信息到下拉列表
				
				var trStr = "";
				
				for(var j = i; j < i+4 && j < areaInfo.length;j++) {
					trStr += "<td><input type='checkbox' areaName='"+areaInfo[j].name+"' areaId="+ areaInfo[j].id +" name='area_select'></td><td>" + areaInfo[j].name + "</td>";
				}
				
				tableArea += "<tr>"+trStr+"</tr>"; 
		    }
		}		
	    tableArea += "</table>";
	},
	updateArea : function (areaId) {
		if($("#areaList").val() == "选择地域") {
			$("#areaList").val("");
		}
		var areaName = $("input[areaId="+areaId+"]").attr("areaName");
		if($("input[areaId="+areaId+"]").attr("checked") == "checked") {//选择地域
			areaIdArray.push(areaId);
			areaNameArray.push(areaName);
		} else {//反选
			var deleteIndex = -1;
			$.each(areaIdArray, function(key, val) {
				if(val == areaId) {
					deleteIndex = key;
				}
			});
			areaIdArray.splice(deleteIndex, 1);//删除那个地域
			areaNameArray.splice(deleteIndex, 1);
		}
		jQuery.unique(areaIdArray);//排重
		jQuery.unique(areaNameArray);
		$("#areaIds").val(areaIdArray.toString());
		$("#areaList").val(areaNameArray.toString());//将所选信息更新到页面上
	}
});