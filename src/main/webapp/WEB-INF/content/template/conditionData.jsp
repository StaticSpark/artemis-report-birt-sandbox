<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript">
	function reportDataReady() {
		//callAjax('ad');
	}
	
	function callAjax(type) {//根据不同的type表示异步加载对应的报表数据
		var markArray = new Array();
	 	markArray =	$("input[mark='"+type+"-c']");
		if(markArray.length == 0) {//说明该列表没有加载过
			$.ajax({
				type: "post",
				async:false,
				dataType:'json',
				url: "${ctx}/ad/"+type+"-report",
				success:function(data) {
					$("#"+type+"ReportUITemplate").tmpl(data).appendTo("#"+type+"_report_list");
					dataTableShow(type);
				}
			});
		} 
	}
	

	//点击编辑后出发的事件
	function callAdAdpSelectAjax(type,param) {

		$.ajax({
			type: "post",
			async:false,
			dataType:'json',
			url: "${ctx}/ad/"+type+"-precise-report?param="+param,
			success:function(data) {										
				if (data =="" || data == null){						
					//hiAlert(param +" 不存在,请重新输入!");
					if (param.indexOf(",") > -1) {
						var col = param.split(",");
						var params = "";
						for (var i = 0;i<col.length;i++) {
							params = params +col[i]+"<br>";							
						}
						document.getElementById("display_textarea_error").innerHTML ="<span>以下查询记录不存在:</span><li>"+params+"</li>";
					}else{
						document.getElementById("display_textarea_error").innerHTML ="<span>以下查询记录不存在:</span><p>"+param+"</p>";
					}
								
				}else {						
					var columns = param.trim().split(",");
					var neArray = new Array();				
					if (type == "ad") {
						for(var i=0;i<columns.length;i++) {
							var count = 0;
							for (var j= 0;j<data.length;j++) {
								//判断是否含有<s></s>
								var adname = data[j].adName;
								if (adname.contains("<s>")) {
									fid = adname.indexOf(">");
									sid = adname.lastIndexOf("<");
									adname = adname.substring(fid+1,sid);
								}
								//需增加大小写敏感的判断
								if (columns[i].toLowerCase() != data[j].adId.toLowerCase() && columns[i].toLowerCase() != adname.toLowerCase()) {
									count = count +1;
								}
							}							
							if (count == data.length) {
								neArray.push(columns[i]);
							}
						}
						
						if(neArray.length>0) {
							var params = "";
							for (var j = 0;j<neArray.length;j++){						
								params = params +neArray[j] +"<br>";								
							}
							document.getElementById("display_textarea_error").innerHTML = "<span>以下查询记录不存在:</span><p>"+params+"</p>";
						}
						adDataDisplay(data);
					}
					
					if (type == "adp") {
						
						for(var i=0;i<columns.length;i++) {
							var count = 0;
							for (var j= 0;j<data.length;j++) {								
								//判断是否含有<s></s>
								var adpname = data[j].name;
								if (adpname.contains("<s>")) {
									fid = adpname.indexOf(">");
									sid = adpname.lastIndexOf("<");
									adpname = adpname.substring(fid+1,sid);
								}
								//需增加大小写敏感的判断
								if (columns[i].toLowerCase() != data[j].id && columns[i].toLowerCase() != adpname.toLowerCase()) {
									count = count +1;
								}
							}							
							if (count == data.length) {
								neArray.push(columns[i]);
							}
						}
						
						if(neArray.length>0) {
							//hiAlert(neArray+" 不存在");
							var params = "";
							for (var j = 0;j<neArray.length;j++){
								params = params +neArray[j] +"<br>";
							}
							document.getElementById("display_textarea_error").innerHTML = "<span>以下查询记录不存在:</span><p>"+params+"</p>";
						}
						
						adpDataDisplay(data);
					}
				}																					
			}
		});		
	}

	
	function adDataDisplay(data) {		
		$("#linkTabel").find("tr[name='selected_con']").each(function(){
			//截取特定的id
			var tmpValue = $(this).attr("id");
			var tmpIndex = tmpValue.lastIndexOf("_");
			var subTmpValue = tmpValue.substring(tmpIndex+1,tmpValue.length);
			$("#" + conditionType + "_id_" + subTmpValue).parent().find("tr[name='selected_con']").remove();
		});
		for (var i = 0; i< data.length; i++) {
			var ad_id =  data[i].adId;		    
			var ad_name = data[i].adName;
			//var adp_name = data[i].adpName;
			var status = data[i].statusName;
			var order_name = data[i].orderName;	
			var pub_date = data[i].pubDate;
			var str = "<td>"+ad_id+"</td>"+"<td>"+ad_name+"</td>"+"<td>"+order_name+"</td>"+"<td>"+status+"</td>"+"<td>"+pub_date+"</td>";//页面动态增加选中复选框的记录
			var tabelTpl = '<tr bgcolor=#f0f3f4 width="100%" id="'+'ad_id_'+ad_id+'" name="selected_con">'
				+ '<td width=5 align=left ><input  '
				+ 'type="checkbox" name="selected_check"></td>';
			str = tabelTpl + str + "</tr>";
			if($("#" + conditionType + "_id_" + ad_id).length == 0) {
			    $("#linkTabel").append(str);	
			}
		}				
	}
		
	function adpDataDisplay(data) {		
		$("#linkTabel").find("tr[name='selected_con']").each(function(){
			//截取特定的id
			var tmpValue = $(this).attr("id");
			var tmpIndex = tmpValue.lastIndexOf("_");
			var subTmpValue = tmpValue.substring(tmpIndex+1,tmpValue.length);
			$("#" + conditionType + "_id_" + subTmpValue).parent().find("tr[name='selected_con']").remove();
		});
		for (var i = 0; i< data.length; i++) {
			var adp_id =  data[i].id;		    
			var adp_name = data[i].name;
			var adp_code = data[i].code;
			var adp_size = data[i].adpSize;
			var group = "";			
			var str = "<td>"+adp_id+"</td>"+"<td>"+adp_name+"</td>"+"<td>"+adp_code+"</td>"+"<td>"+adp_size+"</td>"+"<td>"+group+"</td>";//页面动态增加选中复选框的记录
			var tabelTpl = '<tr bgcolor=#f0f3f4 width="100%" id="'+'adp_id_'+adp_id+'" name="selected_con">'
				+ '<td width=5 align=left ><input  '
				+ 'type="checkbox" name="selected_check"></td>';
			str = tabelTpl + str + "</tr>";
			if($("#" + conditionType + "_id_" + adp_id).length == 0) {
				$("#linkTabel").append(str);	
			}
		}				
	}		
	
	//通过ajax 去刷新广告列表
	function dataAjaxTable(type) {
		var markArray = new Array();
		markArray =	$("input[mark='"+type+"-c']");
		if(markArray.length == 0) {//说明该列表没有加载过
			$("[name='odd-even'] tr:odd").addClass("alt");
			var ctx = $("#ctx").val();
			var oLanguage = {
					"sProcessing" : "正在加载中......",
					"sLengthMenu" : " | 每页显示 _MENU_ 条记录",
					"sZeroRecords" : "对不起，查询不到相关数据！",
					"sEmptyTable" : "表中无数据存在！",
					"sInfo" : "_START_ 到 _END_ 条（共 _TOTAL_ 条）",
					"sInfoEmpty" : " 0到0条（共0条）",
					"sInfoFiltered" : "数据表中共为 _MAX_ 条记录",
					"sSearch" : "<span><font color='blue'>搜索:</font></span>",
	
					"oPaginate" : {
						"sFirst" : "首页",
						"sPrevious" : "上一页",
						"sNext" : "下一页",
						"sLast" : "末页"
					}
				};
				options = {
					"bProcessing": true,
					"bServerSide": true,
					"sAjaxSource": ctx +"/ad/"+type+"-report-page",
					"iDisplayLength" : 15,
					"bSort": false, 
					"oLanguage" : oLanguage,
					//"sPaginationType": "full_numbers",  //翻页界面类型
					//"sDom" : "<'H'f>rt<'F'ip>",
					 "sDom": '<"top"<"allselect"><"search"f>>rt<"F"ip<"clear">>',
					"fnServerData": function ( sSource, aoData, fnCallback ) {
						$.ajax( {
						"dataType": 'json',
						"type": "POST",
						"url": sSource,
						"data": aoData,
						"success": fnCallback
						} );
					}
				};
			
			$("#"+type+"_table").dataTable(options);
			$(".allselect").html("<label class='select_label'>&nbsp;&nbsp;&nbsp<input id='"+type+"_all_select' type='checkbox'></input><span>&nbsp;全部页 </span></label>");
			$(".display-div").show();
			$(".mainpanel").dialog({
				autoOpen: false,
				resizable: false,
				modal: true,
				width: 900,
				minHeight: 430,
				title: "",
				buttons: {
					"返回": function() {
						$(this).dialog("close");
					}
				}
			});
		}
	}
	
	function dataTableShow(type) {
		$("[name='odd-even'] tr:odd").addClass("alt");
		var oLanguage = {
			"sProcessing" : "正在加载中......",
			"sLengthMenu" : " | 每页显示 _MENU_ 条记录",
			"sZeroRecords" : "对不起，查询不到相关数据！",
			"sEmptyTable" : "表中无数据存在！",
			"sInfo" : "_START_ 到 _END_ 条（共 _TOTAL_ 条）",
			"sInfoEmpty" : " 0到0条（共0条）",
			"sInfoFiltered" : "数据表中共为 _MAX_ 条记录",
			"sSearch" : "<span><font color='blue'>搜索:</font></span>",

			"oPaginate" : {
				"sFirst" : "首页",
				"sPrevious" : "上一页",
				"sNext" : "下一页",
				"sLast" : "末页"
			}
		};
		options = {
			"bSort" : false,
			"bAutoWidth" : false,
			"bJQueryUI" : false,
			"aoColumnDefs" : [
			// bSearchable：是否可搜索；bVisible：是否可见；aTargets：哪一列
			{
				"bSearchable" : true,
				"bVisible" : true,
				"aTargets" : [ 0 ]
			}, {
				"bVisible" : true,
				"aTargets" : [ 1 ]
			} //
			],
			"iDisplayLength" : 15,
			"oLanguage" : oLanguage,
			"sDom": '<"top"<"allselect"><"search"f>>rt<"F"ip<"clear">>',
		};
		
		//下面采取逐个判断的方式原因：当dataTable第一次填充后，由于之前的逻辑的限制 ,就不再进行填充，如果不分别填充的话，会导致错乱
		if (type == "adp") {
			adpoTable = $("#"+type+"_table").dataTable(options);	
		}
		if (type == "acc") {
			accoTable = $("#"+type+"_table").dataTable(options);	
		}
		if (type == "order") {
			orderoTable = $("#"+type+"_table").dataTable(options);	
		}
		if (type == "sale") {
			saleoTable = $("#"+type+"_table").dataTable(options);	
		}
		if (type == "cat") {
			catoTable = $("#"+type+"_table").dataTable(options);
		}
		if (type == "country") {
			countryoTable = $("#"+type+"_table").dataTable(options);	
		}
		//oTable = $("#"+type+"_table").dataTable(options);		
		$(".allselect").html("<label class='select_label'>&nbsp;&nbsp;&nbsp<input id='"+type+"_all_select' type='checkbox'></input><span>&nbsp;全部页 </span></label>");
		$(".display-div").show();
		$(".mainpanel").dialog({
			autoOpen: false,
			resizable: false,
			modal: true,
			width: 900,
			minHeight: 430,
			title: "",
			buttons: {
				"返回": function() {
					$(this).dialog("close");
				}
			}
		});
	}

</script>