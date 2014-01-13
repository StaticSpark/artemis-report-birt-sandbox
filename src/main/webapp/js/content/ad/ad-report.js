/* 当前菜单及次级菜单不能为空 */
var current_nav = 3;
var current_subnav = 8;
$(document).ready(function() {
   adReport = new AdReport();
});
//主界面
var AdReport = Common.extend({
	t_json : null,
	//当前查询的报告类型
	conditionType : null,
	conditionTypeText : null,
	constructor : function() {
		__report = this;
		this.base();
		this.dataPrepare();
		this.initUI();
		navComponent = new NavComponent();
		listComponent = new ListComponent();
		dataComponent = new DataComponent();
		this.initEvent();
	},
	// 数据准备
	dataPrepare : function() {
		t_json = eval(reportJson);
		conditionType = "ad";
		conditionTypeText = "广告报告";
	},
	//初始化UI
	initUI : function () {
		$("#submit_to").css("margin-left",$(".maintop").width() / 3);
		areaReady();//地域组件初始化
	},
	//初始化事件
	initEvent : function() {
		 //默认隐藏广告位频次分布
		   $("[name='reports_class']").each(function(i){
				  if($(this).html() == '广告位频次分布') {
					  $(this).parent().parent().hide();
				  }
		    });
		
			//默认隐藏客户分布及销售分布
		   $("[name='report_name_checkbox']").each(function(i)
		    {

		       if($(this).html() == '客户分布' || $(this).html() == '销售分布')
		    	   $(this).parent().parent().hide(); 
		    });
			//点击开始时间
			$("#s_datetime").click(function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd HH:mm:ss',
					alwaysUseStartDate : false
				});
			});
			
			$("#e_datetime").click(function() {
				WdatePicker({
					dateFmt : 'yyyy-MM-dd HH:mm:ss',
					alwaysUseStartDate : false
				});
			});
			
			$("#limnk").attr("checked", true);
			
			$("#in_time").find('input[type="radio"]').each(function(i) {
				if ($(this).val() == "yesterday") {
					this.checked = true;
				$("#s_datetime").val(genrateParam($(this).val())[0]);
				$("#e_datetime").val(
					genrateParam($(this).val())[1]);
				}

			});
			$("#in_time").find('input[type="radio"]').click(function() {
				$("#s_datetime").val(genrateParam($(this).val())[0]);
				$("#e_datetime").val(genrateParam($(this).val())[1]);
			});
			
			//定义复选框文本和值 such as: text 日期分布  value 10
			input_text = new Array();
			input_value = new Array();	
			//复选框点击选中
			$("input[name='reports_class']:checkbox").live("click",function() {				
				if (conditionType != 'ad' && conditionType != 'adp'){
					if($(this).attr("checked") == "checked") {						
						var checkBoxIdArray = new Array();
						$("input[name='reports_class']:checkbox").each(function(){
							checkBoxIdArray.push($(this).val());
						});			
						checkBoxIdArray = jQuery.unique(checkBoxIdArray);			
						if($("input[name='reports_class']:checkbox:checked").length = 1){
							checkBoxIdArray.removeElement($(this).val());
							for (var i = 0;i<checkBoxIdArray.length;i++) {
								var currentValue = checkBoxIdArray[i];										
								$("#"+currentValue).attr("disabled",true);
								$("#"+currentValue).parent().css("color",'#A9A9A9');
							}						
							input_text.push("<a href='#' class='regular' id="+$(this).val()+">"+$(this).next('span').html()+" ×"+"</a>");
							input_value.push($(this).val());	
							input_text = jQuery.unique(input_text);
							document.getElementById("sub_report_input").innerHTML = input_text;																																													
						}
					}else {
						var checkBoxIdArray = new Array();
						$("input[name='reports_class']:checkbox").each(function(){
							checkBoxIdArray.push($(this).val());
						});
						checkBoxIdArray = jQuery.unique(checkBoxIdArray);
						for (var i = 0;i<checkBoxIdArray.length;i++) {
							var currentValue = checkBoxIdArray[i];										
							$("#"+currentValue).attr("disabled",false);
							$("#"+currentValue).parent().removeAttr("style");
						}
						for (var i =0;i<input_text.length;i++) {
							var second = input_text[i].indexOf(">");
				    		var first = input_text[i].lastIndexOf("<");
				    		var input_text_value = input_text[i].substring(second+1,first);	
							if (input_text_value == ($(this).next('span').html()+" ×")) {
								input_text.remove(i);
								input_value.remove(i);
							}
						}
						document.getElementById("sub_report_input").innerHTML = input_text;
					}
				}else {
					//支持二维组合 目前只支持广告和广告位，后期会支持所有的报告
					if($(this).attr("checked") == "checked") {																					
						
						var checkBoxIdArray = new Array();
						$("input[name='reports_class']:checkbox").each(function(){
							checkBoxIdArray.push($(this).val());
						});
						
						if (input_text.length >=0 && input_text.length < 2 ){						
							var allFlag = 0;
							if ($("input[name='reports_class']:checkbox:checked").length == 2){
								$("input[name='reports_class']:checkbox:checked").each(function() {
									checkBoxIdArray.removeElement($(this).val());
								});
								for (var i = 0;i<checkBoxIdArray.length;i++) {
									var currentValue = checkBoxIdArray[i];										
									$("#"+currentValue).attr("disabled",true);
									$("#"+currentValue).parent().css("color",'#A9A9A9');
								}
								allFlag = 1;
							}														
							//allFlag作为控制显示、隐藏的开关，当为1时，其他的任何效果都将作废
							if(allFlag !=1){
								cancelSelectCheckbox();
								//满足特殊的分布组合，当选择日期分布时，不可选月份分布
								if ($("input[name='reports_class']:checkbox:checked").val() == 10) {
									$("#"+21).attr("disabled",true);
									$("#"+21).parent().css("color",'#A9A9A9');
								}
							}												
							//处理实际所选的分布																	
							tmp = "<a href='#' class='regular' id="+$(this).val()+">"+$(this).next('span').html()+" ×"+"</a>";
							input_text.push(tmp);										
							input_value.push($(this).val());
							tmpValue ='';
							input_text = jQuery.unique(input_text);
							input_value = jQuery.unique(input_value);
							for (var i = input_text.length-1;i>=0;i--) {
								tmpValue += input_text[i];
							}
							document.getElementById("sub_report_input").innerHTML = tmpValue;
						}
					}else {
						//当复选框取消选择时，触发该分支						
						cancelSelectCheckbox();
						//满足时段分布不可与日期和月份进行组合
						if($("input[name='reports_class']:checkbox:checked").val() == 13){
							if ($(this).val() == 10 || $(this).val() == 21) {
								$(this).attr("disabled",true);
								$(this).parent().css("color",'#A9A9A9');
							}
						}
						//满足日期不可与月份组合
						if ($("input[name='reports_class']:checkbox:checked").val() == 10) {
							$("#"+21).attr("disabled",true);
							$("#"+21).parent().css("color",'#A9A9A9');
						}
						//处理实际的数据
						for (var i =0;i<input_text.length;i++) {
							if (input_text[i] == "<a href='#' class='regular' id="+$(this).val()+">"+$(this).next('span').html()+" ×"+"</a>") {
								input_text.remove(i);
								input_value.remove(i);
							}
						}			
						tmpValue ='';
						input_text = jQuery.unique(input_text);
						input_value = jQuery.unique(input_value);
						for (var i = input_text.length-1;i>=0;i--) {
							tmpValue += input_text[i];
						}
						document.getElementById("sub_report_input").innerHTML = tmpValue;	
					}
				}					
			});
			
			//报表分布的点击时间
		    $(".regular").live("click",function(){    	
		    	tmpValue ='';
		    	for (var i =0;i<input_text.length;i++) {
		    		var second = input_text[i].indexOf(">");
		    		var first = input_text[i].lastIndexOf("<");
		    		var input_text_value = input_text[i].substring(second+1,first);		
		    		if (input_text_value == $(this).html()) {	    			
		    			input_text.remove(i);	
		    			input_value.remove(i);
		    			$("input[name='reports_class']:checkbox:checked").each(function(){
		    				if($(this).next("span").html()+" ×" == input_text_value){
		    					$(this).attr("checked",false);	    					
		    					//该分支与取消点击复选框出发的事件保持一致
		    					cancelSelectCheckbox();
		    					if($("input[name='reports_class']:checkbox:checked").val() == 13){
									if ($(this).val() == 10 || $(this).val() == 21) {
										$(this).attr("disabled",true);
										$(this).parent().css("color",'#A9A9A9');
									}
								}
		    					if ($("input[name='reports_class']:checkbox:checked").val() == 10) {
									$("#"+21).attr("disabled",true);
									$("#"+21).parent().css("color",'#A9A9A9');
								}
		    				}		    				
		    			});
					}
				}	
		    
		    	for (var i = input_text.length-1;i>=0;i--) {
					tmpValue += input_text[i];
				}
		    	document.getElementById("sub_report_input").innerHTML = tmpValue;	
		    });
			
			
			//点击查询
			$("#submit_to").click(function(){
				 var ids = "";
				 var names = "";
				 var areaIds = $("#dimarea").val();
			     if($("#all_con").length == 0) {//非全部的情况下
			    	$("input[name='selected_check']").each(function(i){
		        		$(this).parent().siblings().each(function(j){
		        			if(j == 0) {
		        				ids += $(this).html() + ",";
		        			} else if(j == 1) {
		        				names += $(this).html() + ",";
		        			}
		        		});
		    	    });
			    	
			    	if(conditionType == "area") {
			    		names = $("#area-cdt-input").val();
			    	}
			    	
		    	     if(ids.length > 0) {
		    	    	 ids = ids.substring(0, ids.length - 1);
		    	    	 names = names.substring(0, names.length - 1);
		    	     }
		    	     
		    	     if(conditionType == "area") {
		    	    	 if(areaIds.length == 0 && $("#all_con").length == 0){
		    	    		 hiAlert("请选择查询条件!");
		    	    		 return;
		    	    	 }
		    	     } else {
		    	    	 if(ids.length == 0) {
		    	    		 hiAlert("请选择查询条件!");
		    	    		 return;
			    	     } 
		    	     }
			     }
		    	 
		    	 var beginDate = $("#s_datetime").val();
		    	 //结束时间
		    	 var endDate = $("#e_datetime").val();
		    	 if(!checkEndTime(beginDate, endDate)) {
		    		 hiAlert("结束时间不得小于开始时间!");
			    	 return;
		    	 }
		    	 
		    	 //基础报表   资源分布报表(之前的逻辑)
		    	 //var t_reports =$(":checkbox[name='reports_class']:checked").val();
		    	 //遍历已选中的复选框
		    	 var t_reports='';    		    	
		    	 if (input_value.length == '') {
		    		 hiAlert("请选择报表分布");
		    		 return;
		    	 }else {
		    		 for (var i = input_value.length-1;i>=0;i--) {
		    			 t_reports += input_value[i]+",";
		    		 }
		    	 }
		    	 //去除后面的逗号  
		    	 var reg = /,$/gi; 
		    	 t_reports = t_reports.replace(reg,"");	
		    	 var freqPeriod = $(":radio[name='reports_freq']:checked").val();
		    	 //当选地域分布时，必须选择地域才能提交;选择频次是，必须清空地域才能提交
		    	 if(($(":checkbox[name='reports_class']:checked").parent().text() == "地域分布" || $(":checkbox[name='reports_class']:checked").parent().text().contains("地域分布")) && areaIds == "" && conditionType != "area"){
					 hiAlert("请选择地域");
				 } else if(($(":checkbox[name='reports_class']:checked").attr("freqtag") == 1 || $(":checkbox[name='reports_class']:checked").parent().text().contains("题材分布") || $(":checkbox[name='reports_class']:checked").parent().text().contains("产地分布"))&& areaIds != "") {
					 hiAlert("暂无数据，需清空地域！");
				 } else {
					 var paramObject = new Object();
		        	 paramObject.startDate = beginDate;
		        	 paramObject.endDate = endDate;
		        	 paramObject.reportId = t_reports;
		        	 paramObject.conditonType = conditionType;
		        	 paramObject.conditionNames = names;
		        	 paramObject.conditonTypeText = conditionTypeText;
		        	 paramObject.freq = freqPeriod;//媒体频次时用到
		        	 paramObject.ids = ids;
		        	 paramObject.areaIds = areaIds;
		        	 var paramJson = JSON.stringify(paramObject);
		        	 var url = $("#ctx").val()+"/ad-report-engine";
		        	 __report.openPostWindow(url,"paramJson",paramJson,"_blank");
				 }
		   });
	},
	//动态创建表单并以post激活，避免url中参数过长
	openPostWindow : function (url, paramName, paramValue, name){
		  //创建一个form
		  var tempForm = document.createElement("form");
		  tempForm.id="tempForm";
		  tempForm.method="post";
		  tempForm.action=url;
		  tempForm.target=name;
		  tempForm.style.display="none";
		  //创建一个隐藏input（传参用）
		  var hideInput = document.createElement("input");
		  hideInput.type="hidden";
		  hideInput.name=paramName;
		  hideInput.value=paramValue;
		  tempForm.appendChild(hideInput); 
		  //添加事件
		  tempForm.addEventListener("onsubmit",function(){ 
			  window.open(url,name,"directories=no,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no");
		  });
		  //激活事件
		  document.body.appendChild(tempForm);
		  tempForm.submit();
		  document.body.removeChild(tempForm);
	},
	showAreaTip : function (adObj, info) {
		var v = adObj.getBoundingClientRect();
		var tip = document.getElementById("tipInfo");
		tip.style.display = 'block';
		tip.style.margin = '100px';
		var y = v.top - 80;
		var x = v.left - 160;
		tip.style.top = y + 'px';
		tip.style.left = x + 'px';
		$("#tipInfo").html(info);
	},
    hiddenAreaTip : function (){
		var tip = document.getElementById("tipInfo");
		tip.style.display='none';
	}
});