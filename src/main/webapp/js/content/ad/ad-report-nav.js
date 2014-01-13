//报告分类
NavComponent = Common.extend({
	constructor : function() {
		__nav = this;
		this.base();
		this.dataPrepare();
		this.initUI();
		this.initEvent();
	},
	// 数据准备
	dataPrepare : function() {
		this.navDataPrepare();
	},
	initUI : function () {
		var tpl = '<div class="point_to" ></div><div class="maintop" id="add_second"  style="margin-top:0px;position:absolute;z-index:101;margin-left:16px;" > <ul id="maintop_ul_s"> </ul></div>';
		$('#miansin').after(tpl);
		// 添加一级菜单
		for ( var i = 0; i < t_json.adds.length; i++) {
			var tpl = "<li>" + t_json.adds[i].name + "</li>";
			$("#maintop_ul").append(tpl);
		}
		//$("#maintop_ul").css("margin-left", "-180px"); 
	},
	initEvent : function () {
		/*
		 * Method: 浏览器改变大小 进行页面调整
		 */
		window.onresize = __nav.winResize;
		// 一级菜单鼠标滑动的效果展示
		$("#maintop_ul >li").hover(function() {
			$(this).css("color", 'black');
			$("#add_second").show();
			$(".point_to").css("left", $(this).offset().left);
			__nav.showSecondAdd($(this).html());
		}, function(e) {
			$(this).css("background-image", 'none');
			$(this).css("color", '#3571BF');
		});
		
		$("#add_second").hide();
		$("#add_second").hover(function() {
		}, function() {
			$("#add_second").hide();
			$(".point_to").css("left", -200);
		});
		
		//选择条件类型
		$("span[name='secondNav']").live("click", function(){
			   conditionType = $(this).attr("tag");
			   conditionTypeText = $(this).html();
			   dataComponent.deleteSelected(0);		
				   
			   $("#report_input").val("");//清除报表选择					   
			   //每次改变二级菜单的选项，重新初始化数组
			   input_text = new Array();
			   input_value = new Array();
			   document.getElementById("sub_report_input").innerHTML="";
			   //每次改变二级菜单的选择项，重新初始化复选框，以免收到前面操作的影响
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
		   	   //初始化记录数为0
			   $("#calNum").html(0);
			   
			   $("#dimarea").val("");//清除地域信息
			   $("#area-cdt-input").val("");
			   areaUI.hideAreaComponent();
				
			   $("input[name='con-check']").each(function(i){//清除之前所选的复选框
			        var checkObj = $(this);
			        checkObj[0].checked = false;
			        checkObj.parent().parent().children().removeClass("highlight");
			   });
			   
			   $("tr[name='selected_con']").each(function(i){
			        $(this).remove();//清除已经选择的选项
			    });
			   $("td[name='con_show_title']").each(function(i) {
			        $(this).remove();//清除之前的 列名信息
			   });
			   var titleShow = "";
			   $("span[name='"+conditionType+"_title']").each(function(i) {//添加新的列名信息
				   var title = $(this).html();
				   if(title != "操作") {
					   titleShow += "<td bgcolor='#e5e5e5' align='center' name='con_show_title'>"+title+"</td>";
				   }
			   });
			   
			   if(conditionType != "area") {
				   titleShow += "<td width='1px'></td>";
			   }
			   
			  $("#con_show_check").after(titleShow);
			  $("[name='con-check-list']").each(function(i){
				  var checkObj = $(this);
				  checkObj[0].checked = "false";
				  checkObj[0].checked = false;
			  });
			  
			  dataComponent.selectedCheckAll();//刷新全选按钮
			  
			  var typeText = $(this).html();
			  typeText = typeText.substring(0, typeText.length -2);
			  $("#selected-condition-type").html("&nbsp;" + typeText + "&nbsp;&nbsp;");
			  $("#condition-type-choose").html("&nbsp;" + typeText + "选择&nbsp;&nbsp;");
			  
			  __list.reportDisplayControl();
			  __data.clearSelectFrame();
			  __nav.areaReportChange();
			  
			  $("[name='chose-time']").each(function(i){
				   $(this).show();
			   });
			   
			   $("[name='chose-freq']").each(function(i){
				   $(this).hide();
			   });
		   });
	},
	//二级菜单展示效果
	showSecondAdd : function (text) {
		for ( var i = 0; i < t_json.adds.length; i++) {
			if (t_json.adds[i].name == text) {
				var tpl = "";
				for ( var j = 0; j < t_json.adds[i].value.length; j++) {
					tpl += "<li><span name='secondNav' tag='"+t_json.adds[i].value[j].tag+"'>" + t_json.adds[i].value[j].name
							+ "</span></li>";
				}
				$("#maintop_ul_s").html(tpl);
				$("#maintop_ul_s >li").click(
						function() {
							$("#add_second").hide();
							$(".point_to").css("left", -200);
						});
				$("#add_second").find('li').hover(function() {
					$(this).css("color", 'black');
				}, function() {
					$(this).css("color", '#3571BF');
				});
			}
		}
	},
	winResize : function () {
		$("#add_second").css("margin-left",$("#miansin").offset().left);
	},
	//点击地域报告相关操作
	areaReportChange : function () {
		if(conditionType == "area" ) {
			$("#area_types").show();
			$("#con_city").parent().parent().hide();
			var titleShow = "<td bgcolor='#e5e5e5' style='text-align:center;' align='center' name='con_show_title'>地域名称</td>";
			$("#con_show_check").after(titleShow);
			$("#area-cdt-input").hide();
			$("#area_label").hide();
			$("#select_group").hide();
			$("#result_operation").hide();
			$("#select_area").hide();
			$("#select_all_checkbox").parent().hide();
			$("#area-cdt-input").parent().attr("bgcolor", "#ffffff");
			$("#select_con_all").hide();
			$("#blank_space_show").show();
			$("[name='area_type']").each(function(i){
				$(this).attr("checked", false);
			});			
			$("#select_precise").hide();
		}
		else if(conditionType == "cat" || conditionType == "country") 
		{
			$("#area-cdt-input").hide();
			$("#area_label").hide();
			$("#select_area").hide();
			$("#area-cdt-input").parent().attr("bgcolor", "#ffffff");
			$("#select_all_checkbox").parent().show();
			$("#select_con_all").hide();
			$("#blank_space_show").show();
			$("#select_group").show();
			$("#result_operation").show();
			$("#select_precise").hide(); 
		} else {
			$("#area_types").hide();
			$("#area-cdt-input").show();
			$("#area_label").show();
			$("#select_group").show();
			$("#select_area").show();
			$("#select_con_all").hide();
			$("#select_all_checkbox").parent().show();
			$("#area-cdt-input").parent().attr("bgcolor", "");
			$("#result_operation").show();
			//$("#blank_space_show").hide();
			$("#select_precise").hide();
			if (conditionType == "ad" || conditionType =="adp") {
				$("#select_precise").show();
			}
		}
	},
	navDataPrepare : function () {
		t_json.adds = [
		    {
		        "name": "广告报告", 
		        "value": [
		            {
		                "name": "广告报告", 
		                "tag": "ad"
		            }, 
		            {
		                "name": "订单报告", 
		                "tag": "order"
		            }, 
		            {
		                "name": "广告物料报告", 
		                "tag": "mat"
		            }
		        ]
		    }, 
		    {
		        "name": "广告位报告", 
		        "value": [
		            {
		                "name": "广告位报告", 
		                "tag": "adp"
		            }
		        ]
		    }, 
		    {
		        "name": "销售报告", 
		        "value": [
		            {
		                "name": "广告客户报告", 
		                "tag": "acc"
		            }, 
		            {
		                "name": "销售人员报告", 
		                "tag": "sale"
		            }
		        ]
		    }, 
		    {
		        "name": "媒体报告", 
		        "value": [
		            {
		                "name": "题材报告", 
		                "tag": "cat"
		            }, 
		            {
		                "name": "产地报告", 
		                "tag": "country"
		            }
		        ]
		    }, 
		    {
		        "name": "地域报告", 
		        "value": [
		            {
		                "name": "地域报告", 
		                "tag": "area"
		            }
		        ]
		    }
		]
	}
});