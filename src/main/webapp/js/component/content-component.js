var contentHtml = "";
//function contentReady() {
$(function(){
	//全部 checkbox
	$(":checkbox[name=all_cat]").live("click",function(){
		var checkFlag = $(this).attr("checked");  
		if(checkFlag=='checked'){
			//题材、产地复选框清空
			$(":checkbox[medTypeName='"+$(this).attr("medTypeName")+"'][name!=all_cat]").attr("checked",false);
			//题材、产地 复选框置灰
			$(":checkbox[medTypeName='"+$(this).attr("medTypeName")+"'][name!=all_cat]").attr("disabled",true);
		}else {
			$(":checkbox[medTypeName='"+$(this).attr("medTypeName")+"'][name!=all_cat]").attr("disabled",false);
		}
	})
	//选择
	$("#select_btn").live("click",function() {
		var re = select_cat_country(false);
		if(re){
			//将版权置灰
			$("input[name =copyright]").attr("disabled",true);
		}
	});	
	//重置
	$("#resetMed").live("click",function(){
		//重置复选框
		$("#cont-cdt-right-content_1 :checkbox").attr("checked",false);
		$("#cont-cdt-right-content_1 :checkbox").attr("disabled",false);
		//版权
		$("input[name =copyright]").attr("disabled",false);
		//清空题材隐藏输入内容
		$("#selected-med-cat-input").val('');
		//自选节目隐藏输入
		$("#slct-med-ipt").val('');
		$("#selected-med").html('');
		//已选 清空
		$("#selected-med-cat").html('');
		medList = new Array();
	})
	
	//确定
	$("#addMedCatAndCountry").live("click",function(){
		//验证 唯一性
		var a = $("#selected-med-cat:contains(以下)").length;
		var comfrimflag = false;
		var select_med = $("#selected-med").html();
		if((select_med==' '||select_med==''||select_med==null)&&a<=0){
			comfrimflag = false;
		} else {
			comfrimflag = true;
		}
		select_med = select_med.replace(/\s/g,"").replace('：','：\n').replace('</li><li>','</li>,<li>');//去除文章中间空格
		//选择数据成功 返回 true 否则 为false
		var res = select_cat_country(comfrimflag);
		//将选择内容存入输入textarea
		if(res||a>0){
			var html = $("#selected-med-cat").html();
			html = html+"\r\n"+select_med;
			html = replaceHtml(html);
			html = html.replace(/^\s+/,'');//去除开头的换行符
			$("#cont-cdt-input").text(html);
			$("#cont-cdt-frame").hide();
			$("#cont-cdt-input").attr("flag","true");
		}
	})
	//取消
	$("#cancel_ad").live("click",function(){
		$("#cont-cdt-frame").hide();
		$("#cont-cdt-input").attr("flag","true");
	})

})	

function replaceHtml(html){
	html = html.replaceAll('<li><font style="font-weight: bold;">',"");
	html = html.replaceAll("<li><font style='font-weight: bold;'>","");
	html = html.replaceAll("</font>","");
	html = html.replaceAll("</li>","");
	html = html.replaceAll("<li>","");
	html = html.replaceAll('<LI><FONT style="FONT-WEIGHT: bold">','');
	html = html.replaceAll('</FONT>','');
	html = html.replaceAll('<LI>','');
	html = html.replaceAll('</LI>','');
	return html;
}
	//选择操作方法
 function select_cat_country(comfrimflag){
	   // alert(comfrimflag);
		var type1=""; 
		var type2=""; 
		var type3=""; 
		var type4=""; 
		var type1_name = "电影";
		var type2_name = "电视剧";
		var type3_name = "动漫";
		var type4_name = "综艺";
		var all_country = true;
		var all_mat = true;
		var copyright = $("input[name =copyright]:checked");
		var copyrightChecked = copyright.length;
		//题材
		var medCats = $("input[name='medCat']:checked");
		var catsChecked = medCats.length;
		//产地
		var medCountry = $("input[name='medCountry']:checked");
		var countryChecked = medCountry.length;
		
		var copyHtml = "";
		if(copyrightChecked==1){
			 var v = $(copyright).val();
			 if(v=='1'){
				 copyHtml= "您已选择以下正版节目： ";
			 }else if(v=='2'){
				 copyHtml= "您已选择以下非版节目： ";
			 }
		}else if(copyrightChecked==2){
			copyHtml= "您已选择以下所有版权节目： ";
		}
		//comfrimflag：false 选择按钮、确认没有选中数据
		if(copyrightChecked<=0&&!comfrimflag&&(catsChecked>0||countryChecked>0)){
			alert("请选择版权");
			return  false;
		}
		//题材
		var arrayMedCat = new Array(); //已选择媒体
		var arrayMedCatId = new Array(); //已选择媒体Id
		//产地、媒体类型、题材，组成json数据格式 返回后台处理
		var jsonCatCountryId = $("#selected-med-cat-input").val();
		  //初始值为空
		if(jsonCatCountryId=='[]'||jsonCatCountryId==''||jsonCatCountryId==null)
		{
				jsonCatCountryId = "[";
		}else  //有初始值
		{
		    	jsonCatCountryId = jsonCatCountryId.substring(0,jsonCatCountryId.length-1)+",";
		}
		for(var i = 0; i < catsChecked; i++) {
				//题材
				var medCatId = $(medCats[i]).attr("medCatId");
				var medCatName = $(medCats[i]).attr("medCatName");
				//题材类型
				var medTypeName = $(medCats[i]).attr("medTypeName");
				var medTypeId = $(medCats[i]).attr("medTypeId");
				//题材名称、题材Id 放到数组 
				arrayMedCat.push(medTypeId+":"+medTypeName+":"+medCatName);
				arrayMedCatId.push("{typeId:"+medTypeId+",catId:"+medCatId);
		}
		//产地
		var showContent = '';
		var countryFlag = true;
		//产地
		for(var i = 0; i < countryChecked; i++) {
			   //产地名称、id
			   var medCountryId = $(medCountry[i]).attr("medCountryId");
			   var medCountryName = $(medCountry[i]).attr("medCountryName");
			   //产地的媒体类型、id
			   var medTypeName = $(medCountry[i]).attr("medTypeName");
			   var medTypeId = $(medCountry[i]).attr("medTypeId");
			   for(var h=0; h<catsChecked; h++){
					//题材的媒体类型
					var med = arrayMedCat[h].split(":"); 
					//产地和题材是同一个媒体类型
					if(med[1]==medTypeName){
					   if(medTypeId=='1'){
						   type1+= medCountryName+med[2]+",";
					   }
					   if(medTypeId=='2'){
						   type2+= medCountryName+med[2]+",";
					   }
					   if(medTypeId=='3'){
						   type3+= medCountryName+med[2]+",";
					   }
					   if(medTypeId=='4'){
						   type4+= medCountryName+med[2]+",";
					   }
					   jsonCatCountryId += arrayMedCatId[h]+",countryId:"+medCountryId+"},";
					   //验证 唯一性  选中全部，不能再选择下面选项
					   var all = $("#selected-med-cat li:contains(全部"+medTypeName+")").length;
					   //没有选中全部，不能选中相同的内容
					   $("#selected-med-cat li:contains("+medTypeName+")").each(function(){
						   var cat_country = $(this).filter(":contains("+medCountryName+med[2]+")").length;
						   var check_cat = $(this).filter(":contains(全部"+med[2]+")").length;
						   var check_country = $(this).filter(":contains("+medCountryName+"全部)").length;
						   if(all>0||cat_country>0||check_country>0||check_cat>0){
								countryFlag = false;
								return ;
							}
					   })
					 }
				}
				 //选中了产地，但是没有选中题材
				if(catsChecked<=0)
				{
					//验证 唯一性  选中全部，不能再选择下面选项
					   var all = $("#selected-med-cat li:contains(全部"+medTypeName+")").length;
					   //没有选中全部，不能选中相同的内容
					   $("#selected-med-cat li:contains("+medTypeName+")").each(function(){
						   var check_country = $(this).filter(":contains("+medCountryName+"全部)").length;
						   if(all>0||check_country>0){
								countryFlag = false;
								return ;
							}
					   })
					if(medTypeId=='1'){
						   type1+= medCountryName+"全部,";
					   }
					   if(medTypeId=='2'){
						   type2+= medCountryName+"全部,";
					   }
					   if(medTypeId=='3'){
						   type3+= medCountryName+"全部,";
					   }
					   if(medTypeId=='4'){
						   type4+= medCountryName+"全部,";
					   }
					jsonCatCountryId +="{typeId:"+medTypeId+",catId:'',countryId:"+medCountryId+"},";
				}
		}
		//没有产地,但是选中了题材
		if(countryChecked<=0)
		{	
			for(var i=0; i<catsChecked; i++){
				//题材的媒体类型
				var med = arrayMedCat[i].split(":"); 
				//showContent += med[2]+",";
				var medTypeId = med[0];
				if(medTypeId=='1'){
					   type1+= "全部"+med[2]+",";
				   }
				   if(medTypeId=='2'){
					   type2+= "全部"+med[2]+",";
				   }
				   if(medTypeId=='3'){
					   type3+= "全部"+med[2]+",";
				   }
				   if(medTypeId=='4'){
					   type4+= "全部"+med[2]+",";
				   }
				jsonCatCountryId += arrayMedCatId[i]+",countryId:''},";
				var medTypeName = med[1]; //题材类型
				//验证 唯一性  选中全部，不能再选择下面选项
				var all = $("#selected-med-cat li:contains(全部"+medTypeName+")").length;
				var check_cat = $("#selected-med-cat li:contains(全部"+med[2]+")").length;
				$("#selected-med-cat li:contains("+medTypeName+")").each(function(){
					   var check_cat = $(this).filter(":contains(全部"+med[2]+")").length;
					   if(all>0||check_cat>0){
							countryFlag = false;
							return ;
						}
				  })
			}
		}
		if(!countryFlag){
			 alert("不能重复选择!");	
			 return false;
		}
		//全选 复选框
		var selectall = $("input[name=all_cat]:checked");
		var selectallChecked = selectall.length;
		var allShow = "全部"; //全部
		var selected_cat_country = $("#selected-med-cat").html();
		var type_flag = true;
		for(var i=0; i<selectallChecked;i++){
			//题材类型
			var medTypeName = $(selectall[i]).attr("medTypeName");
			var medTypeId = $(selectall[i]).attr("medTypeId");
			jsonCatCountryId +="{typeId:"+medTypeId+",catId:'',countryId:''},";
			if(medTypeId=='1'){
				   type1+= allShow + medTypeName+" ";
			   }
			   if(medTypeId=='2'){
				   type2+= allShow + medTypeName+" ";
			   }
			   if(medTypeId=='3'){
				   type3+= allShow + medTypeName+" ";
			   }
			   if(medTypeId=='4'){
				   type4+= allShow + medTypeName+" ";
			   }
			//验证 唯一性
			var a = $("#selected-med-cat:contains("+medTypeName+")").length;
			if(a>0){
				type_flag = false;
				break;
			}
		}
		if(!type_flag){
			 alert("不能重复选择!");	
			 return false;
		}
		//alert("result="+copyrightChecked+"="+catsChecked+"="+countryChecked+"="+selectallChecked+"="+comfrimflag);
		//没有选择任何 全选、题材、产地 复选框  comfrimflag：false 选择按钮、确认没有选中数据
		if(copyrightChecked>0&&catsChecked<=0&&countryChecked<=0&&selectallChecked<=0&&!comfrimflag)
		{
			 alert("请选择节目!");	
			 //重置复选框
			 resetInputChecked();
			 return false;
		} 
		//补全json数据格式
		jsonCatCountryId = jsonCatCountryId.substring(0,jsonCatCountryId.length-1)+"]";
		//json 数据存入隐藏表单
		if(jsonCatCountryId==']'||jsonCatCountryId==''||jsonCatCountryId==null){
		}else {
			$("#selected-med-cat-input").val(jsonCatCountryId);
		}
		//版权
		var copy_disabled = $("input[name =copyright]").attr("disabled");
		if(copy_disabled!='disabled'){
			var html = $("#selected-med-cat").html();
			var html2 = '';
			if((catsChecked>0||countryChecked>0||selectallChecked>0)){
				if(html==''||html==null){
					$("#selected-med-cat").append(copyHtml);
				}else {
					var ht = html.split('节目');
					html2 = html.replaceAll(ht[0]+"节目：",copyHtml);
					$("#selected-med-cat").html('');
					$("#selected-med-cat").append(html2);
				}
			}
		}
		
		var movie = $("#selected-med-cat li:contains("+type1_name+")").length;
		var tv = $("#selected-med-cat li:contains("+type2_name+")").length;
		var cartoon = $("#selected-med-cat li:contains("+type3_name+")").length;
		var arts = $("#selected-med-cat li:contains("+type4_name+")").length;
		if(movie>0){
			$("#selected-med-cat li:contains("+type1_name+")").append(type1);
		}else if(type1!=''){
			type1= "<li><font style='font-weight: bold;'>*"+type1_name+":</font>"+type1+"</li>"
			$("#selected-med-cat").append(type1);
		}
		if(tv>0){
			$("#selected-med-cat li:contains('"+type2_name+"')").append(type2);
		}else if(type2!=''){
			type2= "<li><font style='font-weight: bold;'>*"+type2_name+":</font>"+type2+"</li>"
			$("#selected-med-cat").append(type2);
		}
		if(cartoon>0){
			$("#selected-med-cat li:contains('"+type3_name+"')").append(type3);
		}else if(type3!=''){
			type3= "<li><font style='font-weight: bold;'>*"+type3_name+":</font>"+type3+"</li>"
			$("#selected-med-cat").append(type3);
		}
		if(arts>0){
			$("#selected-med-cat li:contains('"+type4_name+"')").append(type4);
		}else if(type4!=''){
			type4= "<li><font style='font-weight: bold;'>*"+type4_name+":</font>"+type4+"</li>"
			$("#selected-med-cat").append(type4);
		}
		//重置复选框
		resetInputChecked();
		return true;
	}
	
	function resetInputChecked(){
		//重置复选框
		$("#cont-cdt-right-content_1 :checkbox[name!='copyright']").attr("checked",false);
		$("#cont-cdt-right-content_1 :checkbox").filter("[name!=all_cat]").attr("disabled",false);
	}

