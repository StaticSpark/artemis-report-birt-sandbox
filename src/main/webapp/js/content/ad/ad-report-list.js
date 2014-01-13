//报表列表
ListComponent = Common.extend({
	constructor : function() {
		__list = this;
		this.dataPrepare();
		this.initUI();
		this.initEvent();
	},
	// 数据准备
	dataPrepare : function() {
	},
	initUI : function() {
		//添加基础报表
		this.showBaseReport(t_json, '基础分布');
		// 添加资源分部报表
		this.showReports(t_json, '广告分布');
	},
	initEvent : function() {
		//点击某个报告
	    $("[name='reports_class']").live("click", function(){
	    	var freqTag = $(this).attr("freqTag");
	    	if(freqTag == "1") {
				   $("[name='chose-time']").each(function(i){
					   $(this).hide();
				   });
				   
				   $("[name='chose-freq']").each(function(i){
					   $(this).show();
				   });
			  } else {
				   $("[name='chose-time']").each(function(i){
					   $(this).show();
				   });
				   
				   $("[name='chose-freq']").each(function(i){
					   $(this).hide();
				   });
			   }

	     });
	},
	//报告展示控制
	reportDisplayControl : function () {
		
		//根据选择的二级报告类型显示不同的资源分布报表
		  if(conditionType == 'cat') 
		  {
			  listComponent.showBaseReport(t_json, '题材报告');
			  $("[name='report_name_checkbox']").each(function(i)
			  {
				  $(this).parent().parent().hide();  
			  });
			  
		  }else if(conditionType == 'country'){ 
			  listComponent.showBaseReport(t_json, '产地报告');
			  $("[name='report_name_checkbox']").each(function(i)
			  {
				  $(this).parent().parent().hide();  
			  });
		  } else if(conditionType == "area") {
			  listComponent.showBaseReport(t_json, '基础分布');
			  listComponent.showReports(t_json, '广告分布');
			  //隐藏题材分布和产地分布
			  $("[name='report_name_checkbox']").each(function(i)
			  {
				  if($(this).html() == '题材分布' || $(this).html() == '产地分布' 
					  || $(this).html() == '客户分布' || $(this).html() == '销售分布')
					  $(this).parent().parent().hide(); 
			  });
		  }else if(conditionType == "acc" || conditionType == "sale") {
			  listComponent.showBaseReport(t_json, '基础分布');
			  listComponent.showReports(t_json, '广告分布');
			  //隐藏题材分布和产地分布
			  $("[name='report_name_checkbox']").each(function(i)
			  {
				  if($(this).html() == '题材分布' || $(this).html() == '产地分布')
					  $(this).parent().parent().hide(); 
			  });
		  } else { 
			  listComponent.showBaseReport(t_json, '基础分布');
			  listComponent.showReports(t_json, '广告分布');
			  $("[name='report_name_checkbox']").each(function(i)
			  {
			       if($(this).html() == '客户分布' || $(this).html() == '销售分布')
			    	   $(this).parent().parent().hide(); 
		      });			
		  };
	},
	// 显示基础报告 列表
	showBaseReport : function(t_json, type) {
		// 先移出之前的基础分布
		$("#base_report").children().each(function(i) {
			$(this).remove();
		});

		var count = 0;
		for ( var i = 0; i < t_json.resource.length; i++) {
			if (t_json.resource[i].type == type) {
				var checkedStr = "";
				count = count + 1;
				var baseTable = "<td><label for=" + t_json.resource[i].value
						+ "><input id='" + t_json.resource[i].id + "' "
						+ checkedStr + "value=" + t_json.resource[i].id
						+ " type=checkbox name=reports_class /><span>"
						+ t_json.resource[i].name + "</span></label></td>";
				$("#base_report").append(baseTable);
			}
		}
	},
	//显示分布列表 
	showReports : function (t_json, types) {
		//先移出之前的基础分布
		$("#resource_rep").children().each(function(i){
			$(this).remove();
		});
		for ( var i = 0; i < t_json.resource.length; i++) {
			if (t_json.resource[i].type == types) {
				var tpl = "";
				if(t_json.resource[i].name == '广告位频次分布') {
					tpl = '<td style="display:none"><input value="' + t_json.resource[i].id
						+ '" type="checkbox" freqTag="1" name="reports_class" /><span name="report_name_checkbox">'
						+ t_json.resource[i].name + '</span></td>';
				} else if(t_json.resource[i].name == '广告频次分布'){
					tpl = '<td style="display:none"><label for="'+t_json.resource[i].value+'"><input value="' + t_json.resource[i].id
						+ '" type="checkbox"  freqTag="1" name="reports_class"  /><span name="report_name_checkbox">'
						+ t_json.resource[i].name + '</span></label></td>';
				} else {
				    tpl = '<td><label for="'+t_json.resource[i].value+'"><input id="'+ t_json.resource[i].id + '" value="' + t_json.resource[i].id
					+ '" type="checkbox"  name="reports_class"  /><span name="report_name_checkbox">'
					+ t_json.resource[i].name + '</span></label></td>';
				}
				
				$("#resource_rep").append(tpl);
			}
		}
	}
});