/**
 * 该组件主要涵盖对广告报告相关数据列表的操作，点击选择后出现的table以及在首页中的table
 */
DataComponent = Common.extend({
	constructor : function() {
		__data = this;
		this.dataPrepare();
		this.initUI();
		this.initEvent();
	},
	// 数据准备
	dataPrepare : function() {
	},
	initUI : function() {	

	},	
	initEvent : function() {				
		
		__data.clickHeaderCheckBox();
		__data.clickBodyCheckBox();		
		__data.deleteCon();
		__data.selectConAll();
		__data.selectCon();
		__data.selectPrecise();	
		__data.selectAllCheckBox();
		__data.clickBlankSelect();
		
		//点击返回
		$("a[name='panelBack']").click(function() {
			closePanel();
		});

		$("#delete_ad").click(function() {
			deleteRows();
		});

		$("a[class^='paginate_']").live("click", function() {
			 //当为广告位时，实现选择框与编辑框同步 (后延伸至当非广告以及物料时)   
			if (conditionType != "ad" && conditionType != "mat") {
				__data.synSelect(conditionType);
				
				//修补全选按钮引起的颜色变化不一致
				//同步不选,下了内容是为了弥补点击（下一页|上一页）产生的问题
				if($("tr[name='selected_con']").length == 0){
					$("input[name='con-check']").each(function(){
						if($(this).parent().hasClass("highlight")) {
							$(this).parent().parent().children().removeClass("highlight");
						}
					});
				}	
		    }
			 __data.freshSelectAll();
			 __data.selectedCheck();
			 __data.adpNameHide();
		});
		
		$("[name='selected_check']").live("click",function(){
			__data.selectedCheckAll();
		});
		
		$("#ctrlbuttonctrlorientorientselectOKlabel").live("click", function() {
			if(conditionType == "area") {
				setTimeout(__data.addAreaToFrame, 300);
				__list.reportDisplayControl();
			}
		});
		
		$("input[name='area_type']").live("click", function() {
			__data.clearSelectFrame();
			//此时让所有的处于未选状态
			__data.initialCheckBox();
			
			if($(this).attr("id") != "con_custom") {
				areaUI.hideAreaComponent();
			} 
			
			$(this).attr("checked", true);
			
			var areaTypeName = "";
			$(this).siblings().each(function(i){
				areaTypeName = $(this).html();
			});
			
			conditionTypeText = areaTypeName + "报告";
			
			var a = ['国家分布', '省份分布', '城市分布', '地域分布'];
			
			//隐藏此分布之外的其他的分布，比如选择了国家，那么 省份、城市、地域分布都要隐藏
			for(var n = 0; n < a.length; n++) {
				if(a[n].indexOf(areaTypeName) > -1) {
					for(var k = 0; k < a.length;  k++) {
						if(n != k) {
							$("span:contains(" + a[k] + ")").parent().parent().hide();
						} else {
							$("span:contains(" + a[k] + ")").parent().parent().show();
						}
					}
				}
			}
			
			if(areaTypeName == "自选地域") {
				conditionTypeText = "地域报告";
				$("#blank_space_show").show();
				$("#calNum").html(0);
				$("#select_con").show();
				$("#delete_con").show();
				$("#result_operation").show();
				__list.reportDisplayControl();
					
			} else {
				$("#result_operation").hide();
				$("#blank_space_show").hide();
			}
			
			var conditionType = conditionTypeText;
			conditionType = conditionType.substring(0, conditionType.length - 2);
			var allCon = "<tr id='all_con'><td colspan='6' style='text-align:center;margin-top:30px;'><font size='6'> 你已经选择了全部"+areaTypeName+"</font></td></tr>";
			if($("#all_con").length == 0 && areaTypeName != "自选地域") {
				$("#display-header").after(allCon);
			}
			
		});	
		__data.cancelAllCheck();
	},
	/**
	 * 点击选中的全选按钮
	 */
	clickAllSelect:function() {
		$("#"+conditionType+"_all_select").live("click",function() {		
			$("#all_con").remove();	
			var isCheck = false;
			var markName = conditionType + "-c";
			var searchParam = "";
			// 判断是否选中，若选中，则请求数据展现，若取消选中，则清空数据展现
			if ($(this).attr("checked") == "checked") {
				isCheck = true;
				searchParam = $("input[aria-controls='"+conditionType+"_table']").val();
				//广告报告以及物料报告是在服务器端进行处理数据的，则对于全选的操作，仍然是通过来途径获得，其他的
				//报告是在客户端进行数据处理的，则利用dataTable的api在前端进行处理
				if (conditionType == "ad" || conditionType == "mat") {
					callAdAllSelect(conditionType,searchParam);
				}else{
					handleClientAllSelect(conditionType,searchParam);
				}
			}else {
				isCheck = false;
				$("tr[name=selected_con]").each(function(){
					$(this).remove();
				});	
			}
			//当点击全选时，dataTable中的复选框选中状态进行变化以及颜色也进行相应的变化
			$("input[mark='" + markName + "']").each(function(i) {
				var checkObj = $(this);
				if (isCheck) {
					checkObj[0].checked = "true";
					checkObj.parent().parent().children().addClass("highlight");
				} else {
					checkObj[0].checked = "false";
					checkObj[0].checked = false;
					checkObj.parent().parent().children().removeClass("highlight");
				}
			});
			__data.freshSelectAll();
			//统计当前的记录数
			__data.calCurrentNum();
		});
	},	
	/**
	 * 保证全选复选框动态变化已选以及未选
	 * 采用的逻辑是根据当前的记录数与页面上的记录数的对比结果
	 */
	cancelAllCheck:function() {
		var value =$.trim($("#"+conditionType+"_table_info").text());
		var countNum = $.trim($("#calNum").text());
		if(value != "") {
			var array = value.trim().split(" ");
			if (countNum != array[4]) {
				$("#"+conditionType+"_condition").find("input[id='"+conditionType+"_all_select']").attr("checked",false);		
			}else{		
				$("#"+conditionType+"_condition").find("input[id='"+conditionType+"_all_select']").attr("checked",true);		
			}
		}
	},
	/**
	 * 点击选择后的table中的全选复选框
	 */
	clickHeaderCheckBox : function() {
		$("[name='con-check-list']").live("click", function() {
			$("#all_con").remove();
			var markName = conditionType + "-c";
			var isCheck = false;			
			if ($(this).attr("checked") == "checked") {
				isCheck = true;
			}
			$("input[mark='" + markName + "']").each(function(i) {
				var checkObj = $(this);
				if (isCheck) {
					checkObj[0].checked = "true";
					checkObj.parent().parent().children().addClass("highlight");
				} else {
					checkObj[0].checked = "false";
					checkObj[0].checked = false;
					checkObj.parent().parent().children().removeClass("highlight");
				}
				__data.clickCheckOption(checkObj, isCheck);
			});
			__data.freshSelectAll();
		});
	},
	/**
	 * 点击选择后的table单个复选框事件
	 */
	clickBodyCheckBox:function() {
		// 点击复选框
		$("input[name='con-check']").live("click", function() {
			$("#all_con").remove();
			var obj = $(this);
			var isCheck = false;
			if ($(this).attr("checked") == "checked") {
				isCheck = true;
			}
			__data.clickCheckOption(obj, isCheck);
			__data.freshSelectAll();
		});
	},
	
	/**
	 * 点击一条记录的空白处即为选择该条记录,再点击则为反选该已选记录。
	 * 当点击dataTable一行中任意位置的元素时，该元素选中，采用颜色的变化来判别相关的值
	 */
	clickBlankSelect :function() {			
		var type = ["ad",'order','mat','adp','acc','sale','country','cat'];
		for (var i = 0;i<type.length;i++) {
			$("#"+type[i]+"_report_list").on("click",'tr',function(e) {				
				if (e.target.tagName == "A") {
				//当点击<a></a>标签时，不变色，只进行跳转链接
				}else {
					$("#all_con").remove();
					var isCheck = false;
					var id = $(this).find("input").attr("id");
					var obj = $("#"+id);
					$(this).children().toggleClass("highlight");
					if($(this).children().hasClass("highlight")) {
						$(this).find("input:checkbox").attr("checked","checked");
						isCheck = true;
					}else {
						$(this).find("input:checkbox").removeAttr("checked");
						isCheck = false;
					}
					__data.clickCheckOption(obj, isCheck);
					__data.freshSelectAll();
				}
				
				__data.cancelAllCheck();	
			});			
		}		
	},
	
	/**
	 * 取消已选择的,对应于首页的“去除”按钮
	 */
	deleteCon : function() {
		$("#delete_con").click(function(){
			$("input[name='selected_check']").each(function(i){
				if($(this).attr("checked") == "checked") {
					//将选择框 已经选择的 设为 未选状态
					var id = $(this).parent().parent().attr("id");
					id = id.substring(conditionType.length + 4);
					var sObj = $("#" + conditionType + "-c-" + id);
					if(sObj[0]) {
						sObj[0].checked = false;
						sObj.parent().parent().children().removeClass("highlight");
					} else if(sObj){
						sObj.checked = false;
						sObj.parent().parent().children().removeClass("highlight");
					}
					
					$(this).parent().parent().remove();
				}
			});
			
			$("#all_con").remove();
			__data.selectedCheckAll();
			
			
			if($("#con_custom").attr("checked") == "checked"  && conditionType == "area") {		
				__data.areaReportOperation();
			}
			//统计当前的记录数
			if (conditionType == 'area') {
				__data.calCurrentAreaNum();
			}else {
				__data.calCurrentNum();	
			}						
		});
	},
	
	/**
	 * 选择全部，对应首页的“全选”按钮，目前该功能已去掉
	 */
	selectConAll : function() {
		$("#select_con_all").click(function(){
			__data.clearSelectFrame();
			areaUI.clearAllArea();
			__data.deleteSelected(0);
		    $("input[name='con-check']").each(function(i){//清除之前所选的复选框
		        var checkObj = $(this);
		        checkObj[0].checked = false;
		    });
		    
			$("tr[name='selected_con']").each(function(i){
			     $(this).remove();//清除已经选择的选项
			});
			
			var conditionType = conditionTypeText;
			conditionType = conditionType.substring(0, conditionType.length - 2);
			var allCon = "<tr id='all_con'><td colspan='6' style='text-align:center;margin-top:30px;'><font size='6'> 你已经选择了全部"+conditionType+"</font></td></tr>";
			if($("#all_con").length == 0) {
				$("#display-header").after(allCon);
			}
		    __data.selectedCheckAll();
			__data.freshSelectAll();
		});
	},
	/**
	 * 点击首页中的“选择”按钮时，触发的事件
	 */
	selectCon : function() {
		$("#select_con").click(function() {
			$(".ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix").find("span[id='note']").remove();
		    var allCon = "<tr id='load_data'><td colspan='6' style='text-align:center;margin-top:30px;'><font size='6'> 正在加载数据，请稍后....</font></td></tr>";				
		    if($("#all_con").length == 0) {
				$("#display-header").after(allCon);
			}
		    if(conditionType == "ad" || conditionType == "mat") {
				dataAjaxTable(conditionType);
			} else {
				callAjax(conditionType);
			}	
			$("#load_data").remove();

			$("#"+conditionType+"_condition").dialog("open");
			
			//初始化全选点击事件
			__data.clickAllSelect();
		    //当为广告位时，实现选择框与编辑框同步					
			if (conditionType == "adp") {
				__data.synSelect(conditionType);				
		    }
			//当为广告报告时，实现选择与编辑同步
			if (conditionType == "ad") {
				setTimeout('__data.synSelect(conditionType)', 100);
			}	
			dataComponent.adpNameHide();
			//地域报告所需要的操作
			if($("#con_custom").attr("checked") == "checked" && conditionType == "area") {
				__data.areaReportOperation();
			}
			__data.freshSelectAll();
			__data.cancelAllCheck();
			
		});
	},
	/**
	 * 点击“编辑”按钮时触发的动作
	 * 
	 */
	selectPrecise : function() {
		$("#select_precise").click(function() {					
			if (conditionType == "ad" || conditionType == "adp" ) {		
				$("#all_con").remove();
				$(".ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix").find("span[id='note']").remove();
				$(".ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix").append("<span id='note' class='validateTips'> &nbsp;<多条记录查询用Enter分割></span>");				
				if (conditionType == "ad") {
					$("#dialog_form").dialog("option","title", "编辑 >> 请输入广告id或者广告名称").dialog("open");
					//将table中的具体值转储在编辑的文本框中
					__data.synTextArea();	
				}else if (conditionType == "adp") {
					$("#dialog_form").dialog("option","title", "编辑 >> 请输入广告位id或者广告位名称").dialog("open");
					//将table中的具体值转储在编辑的文本框中
					__data.synTextArea();				
				}
			}else {
				hiAlert("编辑功能仅广告报告和广告位报告可用！");
			};
		});	
		
		$("#dialog_form").dialog({
			autoOpen: false,
			resizable: false,
			modal: true,
			width: 430,
			minHeight: 200,
			title: "编辑",
			buttons: {				
				"确认": function(){
					$("#display_textarea_error").html("");
					bValide = true;
					if (bValide) {
						var params = $.trim($("#rpt_value").val());						
						if ( params.trim() =="" || params == null) {
							hiAlert("请输入查询条件!");
						}else{
							var type = conditionType;													
							if (type == "ad" || type == "adp") {
								
								if (params.trim().indexOf("\n") >= 0){
									var param = params.trim().split("\n");
									var paramGroup = "";
									for (var i = 0; i< param.length; i++) {
										if (param[i].trim().indexOf(" ") > -1) {
											hiAlert("请用Enter分割字符串");
										}else {
											if (param[i] != ""){
												paramGroup += param[i].trim()+",";
											}											
										}										
									}
									var reg = /,$/gi; 
									paramGroup = paramGroup.replace(reg,""); 	
									callAdAdpSelectAjax(type,paramGroup);	
								}else {
									
									if (params.trim().indexOf(" ") > -1) {
										hiAlert("请用Enter分割字符串");
									}else{
										callAdAdpSelectAjax(type,params.trim());	
									}	
								}
							}												    
							
							if ($("#display_textarea_error").html().length <=0 ){
								$("#rpt_value").val("");
								$(this).dialog("close");
							}
							//$("#rpt_value").val("");
							//$(this).dialog("close");
							//统计当前的记录数
							__data.calCurrentNum();
						}						
					}
				},
				"取消": function() {
					$("#rpt_value").val("");
					$("#display_textarea_error").html("");
					$(this).dialog("close");
				}
			}
		});
	},
	/**
	 * 首页中的全选复选框；即table的表头复选框
	 */
	selectAllCheckBox : function() {
		//全选 已选择的 该方法与下面的方法重复，则此处去除
		/*$("#select_all_checkbox").click(function(){
			$("input[name='selected_check']").each(function(i){
				var checkObj = $(this);
				checkObj[0].checked = "true";
			});
		});*/
		//全选复选框
		$("#select_all_checkbox").click(function() {
			if ($("#select_all_checkbox").attr("checked") == 'checked') {
				$("#linkTabel").find("input[type='checkbox']").not("#select_all_checkbox").each(function(i) {
					this.checked = true;
				});
			} else {
				$("#linkTabel").find("input[type='checkbox']").not("#select_all_checkbox").each(function(i) {
					this.checked = false;
				});
			}
		});
	},
	
	
	addAreaToFrame : function () {
		__data.clearSelectFrame();
		if($("#area-cdt-input").val().length > 0) {
			var areaNames = new Array();
			var areaIds = new Array();
			areaNames = $("#area-cdt-input").val().split(",");
			areaIds = $("#dimarea").val().split(",");
			var tabelTpl = "";
			if(areaNames.length < 3) {
				$("td:contains(地域名称)").attr("colspan", 2);
				for(var i = 0; i < areaIds.length; i++) {
					tabelTpl += '<tr width="100%"  areaConTag="areaTag"><td width=5 align=left >' +
						'</td><td style="text-align:center" id= "area_' + areaIds[i] + '" name="selected_con">' + areaNames[i] + '</td></tr>';
				}
			} else {
				$("td:contains(地域名称)").attr("colspan", 3);
				for(var i = 0; i < areaNames.length; i = i+3) {
					tabelTpl += '<tr  width="100%" areaConTag="areaTag" >';
					for(var j = i; j < i+3 && j < areaNames.length; j++) {
					    tabelTpl += '<td style="text-align:center" width="33%" id= "area_' + areaIds[j] + '" name="selected_con">' + areaNames[j] + '</td>';
					}
				}
				tabelTpl += "</tr>";
			}
			$("#linkTabel").append(tabelTpl);
			__data.calCurrentAreaNum();
		}		
	},
	//地域报告操作
	areaReportOperation : function () {
		if(areaUI.areaData == null){
			hiAlert("正在努力为你加载地域数据...");
			return;
		};
		areaUI.clearAllArea();
		areaUI.queryOldData(); 	
		areaUI.showAreaDataToUI(); 
		areaUI.showAreaComponent();
		$("#ui-orient-selector").css("left", $("#linkTabel").offset().left);
		$("#ui-orient-selector").css("top", "60px");
		
		//同步 显示框与地域组件里面的内容。 因为showAreaDataToUI 这个方法只有在 地域组件框弹出来的时候才能生效
		if($("tr[areacontag='areaTag']").length == 0) {
			areaUI.clearAllArea();
			areaUI.showAreaDataToUI(); 
		}
	},
	//清除选择框
	clearSelectFrame : function () {
		$("#all_con").remove();
		
		if($("#con_custom").attr("checked") != "checked") {
			$("#dimarea").val("");
			$("#area-cdt-input").val("");
		}
		
		$("#display-header").siblings().each(function(i){
			if($(this).attr("id") == "area_types") {
				if(conditionType != "area") {
					$(this).hide();
				}
			} else {
				$(this).remove();
			}
		});		
		__data.calCurrentAreaNum();
	},
	//点击复选框 效果
	clickCheckOption : function (obj, type) {
		if(type) {//选中
			var count = 0;
			var id = "";
			var str = "";//页面动态增加选中复选框的记录
			
			$(obj).parent().siblings().each(function(i) {
				count++;
				var childHtml =$(this).children().children().html();
				if(count > 0) {
					str += "<td align=middle ";
					if(count == 1) {
						id = childHtml;
						if(conditionType == 'cat' || conditionType == 'country') {
							str += "style='display:none;'";//题材报告隐藏id列
						}
					}
					//如果childHtml中含有<a></a>则只显示数值
					if (childHtml.contains("_blank") ){
						childHtml = $(childHtml).html();
					}
					
					str += ">"+childHtml+"</td>";
				}
			});
			
			var tabelTpl = '<tr bgcolor=#f0f3f4 width="100%" id="'+conditionType+'_id_'+id+'" name="selected_con">'
				+ '<td width=5 align=left ><input  '
				+ 'type="checkbox" name="selected_check"></td>';
			
			str = tabelTpl + str + "</tr>";
			if($("#" + conditionType + "_id_" + id).length == 0) {
				$("#linkTabel").append(str);
			}
		} else {//取消选中
			var id = "";
			var count = 0;
			$(obj).parent().siblings().each(function(i) {
				count++;
				if(count == 1) {
					id = $(this).children().children().html();
				}
			});
			$("#" + conditionType + "_id_" + id).remove();
		};
		//统计当前的记录数
		__data.calCurrentNum();
		//__data.cancelAllCheck();
	},
	//刷新全选按钮，如果都被选中了，那么全选按钮也得选中
	freshSelectAll: function (){
		setTimeout(__data.timeDelayFreshSelectAll,500);
	},
	timeDelayFreshSelectAll : function() {
		var mark = conditionType + "-c";
		var checkedStr = "1";
		$("[mark='"+mark+"']").each(function(i){
			var forthis = $(this);
			if(forthis[0].checked == false) {
				checkedStr = "0";
			}
			if(forthis.checked == true) {
				checkedStr = "1";
			}
		});
		
		if(checkedStr == "0") {
			$("[name='con-check-list']").each(function(i) {
				$(this).attr("checked",false);
			});
		} else {
			$("[name='con-check-list']").each(function(i) {
				$(this).attr("checked",true);
			});
		}
	},
	
	timeDelaySelectedCheck : function () {
		var prefxi = conditionType + "_id_";
		$("tr[name='selected_con']").each(function(i){
			var id = $(this).attr("id");
			id = id.substring(prefxi.length, id.length);
			var checkObj = $("#" + conditionType +"-c-" + id);
			if(checkObj[0]) {
				checkObj[0].checked = true;
				checkObj.parent().parent().children().addClass("highlight");
			} else {
				checkObj.checked = true;
				checkObj.parent().parent().children().addClass("highlight");
			}
		});
	},
	//已经选择的 条件中，如果全部选中，则全选按钮选中
	selectedCheckAll : function () {
		var checkedStr = "1";
		$("[name='selected_check']").each(function(i){
			var forthis = $(this);
			if(forthis[0].checked == false) {
				checkedStr = "0";
			}
		});
		
		var length = $("[name='selected_check']").length;
		if(length == 0) {
			checkedStr = "0";
		}
		
		var checkObj = $("#select_all_checkbox");
		if(checkedStr == "0") {
			checkObj[0].checked = false;
		} else {
			checkObj[0].checked = true;
		}
	},
	//如果显示框也需要选中，那么弹出框选中
	selectedCheck : function () {
		setTimeout(__data.timeDelaySelectedCheck,400);
	},
	//将过长的名字隐藏一部分
	adpNameHide : function () {
		$("[name='adpName']").each(function(i){
			var adpName= $(this).html();
			if(adpName.length > 20) {
				adpName = adpName.substring(0, 20) + "<span style='display:none'>"+adpName.substring(25, adpName.length)+"</span>";
				$(this).html(adpName);
			}
		});
	},
	showPanel : function() {
		$("#"+conditionType+"_condition").css("left",$(".maintop").offset().left + ($(".maintop").width() / 4));
		$("#"+conditionType+"_condition").css("top",$(".maintop").offset().top + ($(".maintop").height() / 2));
	},
	closePanel : function() {
		$("#"+conditionType+"_condition").css("left", "-2000px");
		$("#"+conditionType+"_condition").css("top", "-2000px");
	},
	deleteRows : function() {
		$("#linkTabel").find("input[type='checkbox']").not("#select_all_checkbox").each(function(i) {
			var forthis = $(this);
			if (this.checked == true) {
				forthis.parent().parent().remove();
			}
		});
	},
	//删除已选的 0全部删除 1 只删除已选中的
	deleteSelected : function (type) {
		$("input[name='selected_check']").each(function(i){
			if(type ==0 || (type == 1 && $(this).attr("checked") == "checked")) {
				$(this).parent().parent().remove();
			}
		});
	} ,
	deleteAdd : function () {
		$("#maintop_ul_s").html("");
	},
	
	//实现广告位选择与编辑保持同步
	synSelect:function (conditionType) {
		var array = new Array();
		$("input[name='con-check']").each(function(){
			array.push($(this).attr("id"));
			this.checked = false;
		});
		$("input[name='selected_check']").each(function(i){				
			var id = $(this).parent().parent().attr("id");
			id = id.substring(conditionType.length + 4);
			var tmpValue = conditionType + "-c-" + id;				
			$("#"+tmpValue).attr("checked",true);
			$("#"+tmpValue).parent().parent().children().addClass("highlight");
			for (var i=0;i<array.length;i++) {
				if (tmpValue == array[i]) {
					array.removeElement(tmpValue);
				}
			}				
			for (var i = 0;i<array.length;i++) {
				$("#"+array[i]).attr("checked",false);
				$("#"+array[i]).parent().parent().children().removeClass("highlight");
			}
		
		});
	},
	//实现tbody中的数据与textarea保持一致
	synTextArea:function() {
		var allVal = "";
		$("#linkTabel").find("tr[name='selected_con']").each(function(){
			//截取特定的id
			var tmpValue = $(this).attr("id");
			var tmpIndex = tmpValue.lastIndexOf("_");
			var subTmpValue = tmpValue.substring(tmpIndex+1,tmpValue.length);
			allVal += subTmpValue + "\n";
		});
		$("#rpt_value").val(allVal);
	},
	//统计当前的记录数
	calCurrentNum:function() {
		var count = 0;
		$("tr[name='selected_con']").each(function(){
			count = count + 1;
		});
		$("#calNum").html(count);
	},
	calCurrentAreaNum:function() {
		var count = 0;
		$("td[name='selected_con']").each(function(){
			count = count + 1;
		});
		$("#calNum").html(count);
	},
	initialCheckBox:function() {
		$("#sub_report_input").html("");
		input_text = new Array();
		input_value = new Array();
	    var checkBoxIdArray = new Array();
	    $("input[name='reports_class']:checkbox").each(function(){
	    	if ($(this).attr("checked") == "checked") {
	    		$(this).removeAttr("checked");
	    	}
	    	checkBoxIdArray.push($(this).val());
	    });
	    for (var i = 0;i<checkBoxIdArray.length;i++) {
		  var currentValue = checkBoxIdArray[i];										
		  $("#"+currentValue).attr("disabled",false);
		  $("#"+currentValue).parent().removeAttr("style");
	    }
	}
});