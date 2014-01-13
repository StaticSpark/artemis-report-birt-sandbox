namespace("common.ui.area");
/*
 * 地域选择
 * */
common.ui.area.AreaUI = Base.extend({
	areaData :null,	
	selectedSecondLevelArea:null,//已选择省份ID
	selectedSecondLevelAreaUI:null,//记录UI中是否被选中
	selectedCities : null,
	selectAreaGroup :null,
	currentSecondAreaArray:null,
	
	secondLevelAreaBuf:null,	//二级区域缓存
	cityBuf:null,				//城市缓存
	firstTierCity:null,			//一线城市缓存2
	capitalCity:null,			//省会城市缓存4
	selectUIType:null,			//当前选择UI类型
	constructor: function() {
		this.firstTierCity=new Array();
		this.capitalCity=new Array();
		var url_path=null;
		if($("#area-component-type").val()=='simple'){
			url_path=atlasHome + "/areagroup/dim-area-group!allarea.action";
		}else{
			url_path=atlasHome + "/dim/dim-area!allarea.action";
		}
		var _this=this;
		//加载数据
		$.ajax({
			type: "POST",
			async:true,
			url: url_path, 
			dataType:"jsonp",
			jsonp: "jsoncallback",
			jsonpCallback:"success_jsonpCallback",
			success:function(data) {
				if($("#area-component-type").val()=='simple'){
					$("#area_group_show_div").attr("style","display:none");					
				}
				_this.areaData = data;
				_this.secondLevelAreaBuf={};
				_this.cityBuf={};
				for(var i=0;i<data.country.length;i++){
					var secondAreaArray= data.country[i].province;
					for(var l = 0; l < secondAreaArray.length; l++){
						var secondAreaObj=secondAreaArray[l];
						_this.secondLevelAreaBuf[secondAreaObj.id]=secondAreaObj;
						if(secondAreaObj.areaType){
							_this.addCityByType(secondAreaObj,2);
						}
						var cityArray = secondAreaObj.city;
						if(!cityArray || cityArray.length == 0){
							continue;
						}
						else{
							for(var k=0; k < cityArray.length;k++){
								_this.cityBuf[cityArray[k].id]=cityArray[k];
								_this.cityBuf[cityArray[k].id].secondLevelAreaId=secondAreaObj.id;
								if(cityArray[k].areaType){
									_this.addCityByType(cityArray[k],3);
								}
							}	
						}
					}
				}
			}
		});
		this.initUIEvent();
	},
	addCityByType:function(city,level){
		var type=city.areaType;
		city.level=level; 
		if((type&2)==2){
			this.firstTierCity.push(city);
		}
		if((type&4)==4){
			this.capitalCity.push(city);
		}
	},
	initUIEvent:function (){
		var _this=this;
	   	$('#areaUITemplate').tmpl('').appendTo("#ui-orient-selector");    	    		
		//中国
		$("#china-country").click(function(){
			_this.createChinaProvince();
		});
		
		//其他国家
		$("#other-country").click(function(){ 
			_this.createOhterCountry();
		}); 
		
		//地域组
		$("#area_group").click(function(){ 
			_this.createAreaGroup();
		}); 
		//全部清空
		 $("#orient-disel-all").click(function(){
			 _this.clearAllArea();	
			 _this.showAreaDataToUI();
		 });
		//全选
		 $("#orient-sel-all").click(function(){ 
			 _this.selectAll();
		});
//		 反选
		 $("#orient-sel-invert").click(function(){
			 _this.invertSelection();
		 });
		 //确定按钮事件
		$("#ctrlbuttonctrlorientorientselectOKlabel").click(function(){
			_this.clickOK();			
		});
		//取消按钮事件
		$("#ctrlbuttonctrlorientorientselectCancel").click(function(){
			_this.clickCancel();			
		});	
		//下拉菜单鼠标离开事件，鼠标离开，取消下拉菜单
		$("#ctrlorientorientSelectorCityLayer").mouseleave(function(){
			 $("#ctrlorientorientSelectorCityLayer").attr("name","");
			 $(this).css("left","-10000px"); 
		});
		//下拉菜单鼠标进入，鼠标进入，设置鼠标已进入标志
		$("#ctrlorientorientSelectorCityLayer").mouseenter(function(){
			$("#ctrlorientorientSelectorCityLayer").attr("name","areadyInSub");
		});
		$("#area-cdt-input").live("mouseup",function() {
			//点击展开收回下拉框
			if($(this).attr("flag")=="true") {				
				_this.clearAllArea();
				_this.queryOldData(); 	
				_this.showAreaDataToUI(); 
				_this.showAreaComponent();
			}else { 
				_this.hideAreaComponent();
			}
		});
		//全选
		 $("#select-ui-type").change(function(){ 
			 _this.changeSelectUIType();
		}); 
		 $("#auto_area_search_cmd").attr("disabled",true);
		 $("#search_area_tip").hide();
			$("#auto_area_search").keyup(function() {
				$("#auto_area_search_hidden").val("");
				if($("#auto_area_search").val()==""){
					$("#auto_area_search_cmd").attr("disabled",true);
				}else{
					$("#auto_area_search_cmd").removeAttr("disabled");
				}
				var name = $("#auto_area_search").val();
				name=name.replace("%","-");
				name=name.replace("'","-");
				$.ajax({
					type : "POST",
					dataType : 'jsonp',
					jsonp: "jsoncallback",
					jsonpCallback:"success_jsonpCallback",
					url : atlasHome + "/dim/dim-area!autoSearchArea.action?name=" + name + "&type=" + $("#area-component-type").val(),
					success : function(data) {
						$("#search_area_tip").empty();
						var isShow = false;
						$.each(data, function(index, content) {
							var div = "<div onmouseover='this.style.backgroundColor=\"#D7DFF6\"' onmouseout='this.style.backgroundColor=\"#FFFFFF\"' onclick='itemSelected(\""+content[0]+"\",\""+content[1]+"\")'>" + content[0] + "</div>";					
							$("#search_area_tip").append(div);
							isShow = true;
						});
						if(isShow){
							$("#search_area_tip").show();
						}else{
							$("#search_area_tip").hide();
						}
					}
				});
			});
			$("#auto_area_search_cmd").click(function() {
				var name = $("#auto_area_search").val();
				name=name.replace("%","-");
				name=name.replace("'","-");				
				$.ajax({
					type : "POST",
					dataType : 'jsonp',
					jsonp: "jsoncallback",
					jsonpCallback:"success_jsonpCallback",
					url : atlasHome + "/dim/dim-area!checkSearchArea.action?name=" + encodeURIComponent(name) + "&type=" + $("#area-component-type").val(),
					success : function(data) {
						if(isNull(data)){
							alert("搜索地域不存在！");
							return;
						}
						if(data.split("-")[1] != 8){
							data=data.split("-")[0];
							if(_this.selectedCities[data] || _this.selectedSecondLevelArea[data]){
								alert("该地域已被添加！");
							}
							for(var secondLevelAreaId in _this.selectedSecondLevelArea){
								var cityList=_this.findCityList(secondLevelAreaId);
								if(isNull(cityList) || !_this.selectedSecondLevelArea[secondLevelAreaId]){
									continue;
								}
								for(var i=0;i<cityList.length;i++){
									if(cityList[i].id==data){
										alert("该地域包含在已选中的二级地域里！");
									}
								}							
							}
							for(var cityId in _this.selectedCities){
								var cityList=_this.findCityList(data);
								if(isNull(cityList)){
									continue;
								}
								for(var i=0;i<cityList.length;i++){
									if(cityId==cityList[i].id){
										_this.clickCityArea(cityList[i].id,false);
									}
								}
							}
							_this.clickCityArea(data,true);	
						}else{
							var areas=data.split("-")[2].split(",");
							for(var n=0;n<areas.length;n++){
								if(!isNull(areas[n])){
									for(var cityId in _this.selectedCities){
										var cityList=_this.findCityList(areas[n]);
										if(isNull(cityList)){
											continue;
										}
										for(var i=0;i<cityList.length;i++){
											if(cityId==cityList[i].id){
												_this.clickCityArea(cityList[i].id,false);
											}
										}
									}
									_this.clickCityArea(areas[n],true);	
								}
							}
							$("#areaDataRow_input_"+data.split("-")[0]).attr("checked",true);
							if($("#area_group").attr("checked")){
								_this.selectAreaGroup[data.split("-")[0]]=areas;
							}
						}					
						$("#auto_area_search").val("");
						$("#auto_area_search_hidden").val("");
						$("#search_area_tip").hide();
					}
				});
			});
 	},
 	//选择UI类型变化：省市列表、一线城市、省会城市
 	changeSelectUIType:function(){
		var checked=$("#china-country").attr("checked");	
		if(checked){ 
			//默认初始化中国
			this.createChinaProvince(); 
		}
 	},
 	updateSelectUIType:function(){
 		this.selectUIType=$("#select-ui-type").val(); 
 	},
 	//确定按钮事件
 	clickOK:function (){
 		var selectedInfoArray=this.queryAllSelectedInfo();
 		var names="";
 		for(var i=0;i<selectedInfoArray.length;i++){
 			names+=","+selectedInfoArray[i].name;
 		}
 		names=names==""?"":names.slice(1);
 		$("#area-cdt-input").html('');
 		$("#area-cdt-input").val(names);
 		
 		var ids="";
 		for(var i=0;i<selectedInfoArray.length;i++){
 			ids+=","+selectedInfoArray[i].id;
 		}
 		ids=ids==""?"":ids.slice(1);
 		$("#dimarea").val(ids);
 		
 		this.hideAreaComponent();
 	},
 	//取消按钮事件
 	clickCancel:function (){
 		this.hideAreaComponent();
 	},
 	//获取原区域数据，并更新到UI
 	queryOldData:function (){
 		var oldIds = $("#dimarea").val();
		this.syncSelectedAreaToBuf(oldIds);
		this.syncSelectedAreaToUI();
 	},
 	//显示区域操作界面
	showAreaComponent:function (){
		$("#ui-orient-selector").removeClass("ui-orient-selector-none");
		$("#ui-orient-selector").addClass("ui-orient-selector-show");
		$("#area-cdt-input").attr("flag","false");
	},
	//隐藏区域操作界面
	hideAreaComponent:function (){
		$("#ui-orient-selector").addClass("ui-orient-selector-none");
		$("#ui-orient-selector").removeClass("ui-orient-selector-show");
		$("#area-cdt-input").attr("flag","true");
	},
	//填充中国的地域信息函数
	createChinaProvince:function (){
		$("#area-data-table-body").html('');	
		$("#select-ui-type").show();
		$("#orient-sel-all").show();
		$("#orient-sel-invert").show();	
		this.updateSelectUIType();//省市列表、一线城市、省会城市
		if(this.selectUIType=='省市列表'){
			var chinaProvince = this.areaData.country[0].province;
			this.currentSecondAreaArray=chinaProvince;
			this.showSecondLevelArea();	
		}
		else if(this.selectUIType=='一线城市'){
			this.showCityToUI(this.firstTierCity);
		}
		else if(this.selectUIType=='省会城市'){
			this.showCityToUI(this.capitalCity);
		}
	},	
	 //填充其他国家数据函数
	createOhterCountry:function (){ 
		$("#area-data-table-body").html(''); 	
		$("#select-ui-type").hide();
		$("#orient-sel-all").show();
		$("#orient-sel-invert").show();
		var otherCountry = this.areaData.country[1].province; 
		this.currentSecondAreaArray=otherCountry;
		this.showSecondLevelArea();
	},
	createAreaGroup:function (){ 
		$("#area-data-table-body").html('');
		var _this=this;
		this.selectAreaGroup=new Object();
		$("#select-ui-type").hide();
		$("#orient-sel-all").hide();
		$("#orient-sel-invert").hide();
		$.ajax({
			type : "POST",
			dataType : 'jsonp',
			jsonp: "jsoncallback",
			jsonpCallback:"success_jsonpCallback",
			url : atlasHome + "/dim/dim-area!findAllAreaGroup.action",
			success : function(data) {
				var groups = eval(data);
				for(var i=0; i<groups.length; i++){
					var tr=$("<tr></tr>");
					var td=$("<td style=\"border-bottom:1px solid #CCCCCC;height:28px;width:320px;\"></td>");					
					var checkbox=$("<input name=\"id"+groups[i].id+"\" id=\"areaDataRow_input_"+groups[i].id+"\" type=\"checkbox\">");
					var label=$("<label>&nbsp;&nbsp;"+groups[i].name+"</label>");
					tr.append(td);
					td.append(checkbox);
					td.append(label);
					$('#area-data-table-body').append(tr);
					$('#areaDataRow_input_'+groups[i].id).attr("arealist",groups[i].arealist);
					$('#areaDataRow_input_'+groups[i].id).attr("groupid",groups[i].id);
					$('#areaDataRow_input_'+groups[i].id).live('click',function(){
						var areas=$(this).attr("arealist").split(',');
						var groupId=$(this).attr("groupid");
						if($(this).attr("checked")){
							for(var i=0; i<areas.length; i++){
								for(var cityId in _this.selectedCities){
									var cityList=_this.findCityList(areas[i].split('-')[0]);
									if(isNull(cityList)){
										continue;
									}
									for(var j=0;j<cityList.length;j++){
										if(cityId==cityList[j].id){
											_this.clickCityArea(cityList[j].id,false);
										}
									}
								}
								_this.clickCityArea(areas[i].split('-')[0],true);
								_this.selectAreaGroup[groupId]=areas;
							}							
						}else{
							var otherSelectedAreaGroup=new Object();
							for(var areaGroup in _this.selectAreaGroup){
								if(_this.selectAreaGroup[areaGroup] && groupId != areaGroup){
									otherSelectedAreaGroup[areaGroup]=_this.selectAreaGroup[areaGroup];
								}
							}
							for(var areaGroup in _this.selectAreaGroup){
								var areaGroupStr=_this.selectAreaGroup[areaGroup]+"";
								var arealist=areaGroupStr.split(',');
								for(var i=0;i<arealist.length;i++){
									_this.clickCityArea(arealist[i].split('-')[0],false);
								}
							}
							for(var areaGroup in otherSelectedAreaGroup){
								for(var i=0; i<otherSelectedAreaGroup[areaGroup].length; i++){
									for(var cityId in _this.selectedCities){
										var cityList=_this.findCityList(otherSelectedAreaGroup[areaGroup][i].split('-')[0]);
										if(isNull(cityList)){
											continue;
										}
										for(var j=0;j<cityList.length;j++){
											if(cityId==cityList[j].id){
												_this.clickCityArea(cityList[j].id,false);
											}
										}
									}
									_this.clickCityArea(otherSelectedAreaGroup[areaGroup][i].split('-')[0],true);
								}
							}
							_this.selectAreaGroup[groupId]=null;							
						}
					});
				}			
			}
		});
	},
	showSecondLevelArea:function (){
//		设置左侧列表框中显示二级地域选择框，并设置选择框中的事件
		this.showSecondLevelAreaTarget(this.currentSecondAreaArray);
//		设置左侧列表框中二级地域的状态，选中还是没选中
		this.syncSelectedAreaTarget(this.currentSecondAreaArray);	
	},
	//在UI中显示城市区域信息：一级城市、省会城市
	showCityToUI:function (cityArray){
		var _this=this;
		this.showArrayToUI(cityArray);
		for(var i = 0; i<cityArray.length; i++){
			var city = cityArray[i];
			$("#areaDataRow_input_"+city.id).click(function(){
				var cityId=$(this).attr("name").substring(2);
				var checked=$(this).attr("checked");
				_this.clickCityArea(cityId,checked);
			});
			if(this.secondLevelAreaBuf[city.id]
				&&this.selectedSecondLevelAreaUI[city.id]){
				$("#areaDataRow_input_"+city.id).attr("checked","checked");
			}
			else if ( this.cityBuf[city.id] ){
				if(this.selectedCities[city.id]){
					$("#areaDataRow_input_"+city.id).attr("checked","checked");	
				}
				else{
					var secondLevelAreaId=city.secondLevelAreaId;
					if(this.selectedSecondLevelArea[secondLevelAreaId]){
						$("#areaDataRow_input_"+city.id).attr("checked","checked");
					} 
				}
			}
		}
	},
	//城市被选中
	clickCityArea:function(cityId,checked){
		if(this.secondLevelAreaBuf[cityId]){
			this.clickSecondLevelArea(cityId,checked);	
		}
		else{
			var city=this.cityBuf[cityId];
			if(!isNull(city)){
				this.clickCity(city.id,city.name,city.secondLevelAreaId,checked); 
			}
		}
	},
	showArrayToUI:function (areaArray){
		for(var i = 0; i<areaArray.length; i++){			
			if(i%4 == 0){ 		
				var rowNum=i/4; 
				$('#areaDataRowUITemplate').tmpl({rowNum:rowNum}).appendTo("#area-data-table-body");
			}			
		}
		var tempRowDataList = new Array();
		for(var i = 0; i<areaArray.length; i++){	
			var rowNum=parseInt(i/4);  
			var city = areaArray[i];
			tempRowDataList.push(city);
			if(i%4 == 3||i==areaArray.length-1){
				var htmlId="#areaDataRow_"+rowNum;
				$('#areaDataRowContentUITemplate').tmpl(tempRowDataList).appendTo(htmlId);
				tempRowDataList=new Array();
			}			
		}
	},
	//在UI中显示第二层区域信息：中国/国外
	showSecondLevelAreaTarget:function (secondAreaArray){
		var _this=this;
		this.showArrayToUI(secondAreaArray);
		for(var i = 0; i<secondAreaArray.length; i++){
			var secondAreaObj = secondAreaArray[i];
			$("#areaDataRow_input_"+secondAreaObj.id).mouseenter(function(){
				_this.hideThirdLevelLayer();
			});
			$("#areaDataRow_input_"+secondAreaObj.id).click(function(){
				var secondLevelAreaId=$(this).attr("name").substring(2);
				var checked=$(this).attr("checked");
				_this.clickSecondLevelArea(secondLevelAreaId,checked);
			});
			
			$("#areaDataRow_label_"+secondAreaObj.id).mouseenter(function(event){ 
				var secondLevelAreaId=$(this).attr("name").substring(2);
				_this.showThirdLevelLayer(event,secondLevelAreaId);
			});
			if(this.findCityList(secondAreaObj.id)){
				$("#areaDataRow_div_"+secondAreaObj.id).mouseleave(function(){			
					setTimeout(function(){				
						var flag = $("#ctrlorientorientSelectorCityLayer").attr("name");
						if("areadyInSub" != flag){
							_this.hideThirdLevelLayer();
						};
					},100);
				});	
			}
		}		
	},
	syncSelectedAreaTarget:function(secondAreaArray){
		for(var i = 0; i<secondAreaArray.length; i++){
			var secondAreaObj = secondAreaArray[i];
			if(this.selectedSecondLevelAreaUI[secondAreaObj.id]){
				this.makeSecondLevelAreaChecked(secondAreaObj.id);
			}
		} 
	},
	//第二层区域信息被点击
	clickSecondLevelArea:function (secondLevelAreaId,checked){
		var secondAreaObj=this.findSecondArea(secondLevelAreaId);
		if(checked){
			this.selectSecondArea(secondAreaObj);				
		}
		else{
			this.unSelectSecondArea(secondAreaObj);	
		}		
	},
	//隐藏第三层区域
	hideThirdLevelLayer:function (){
		$("#ctrlorientorientSelectorCityLayer").css("left","-10000px");
	},
	//显示第三层区域
	showThirdLevelLayer:function (event,secondAreaId){
		var _this=this;
	    $("#ctrlorientorientSelectorCityLayer").html("");				
	    var tempCityList=this.findCityList(secondAreaId);
	    if(!tempCityList){
	    	return false;
	    }
		$('#areaDataCityUITemplate').tmpl(tempCityList).appendTo("#ctrlorientorientSelectorCityLayer");
		if(!event) var event = window.Event;
		var target = event.srcElement ? event.srcElement : event.target;
		var object = document.getElementById("ctrlorientorientselectorBody");
		target.parentNode; 
		var x = this.getX(object);
		var px = this.getX(target.parentNode);
		$("#ctrlorientorientSelectorCityLayer").css("left",px - x +60+"px");
		for(var i=0;i<tempCityList.length;i++){
			var cityId=tempCityList[i].id;
			var cityInputHtmlId='#areaDataRow_city_input_'+cityId;
			if(this.selectedSecondLevelArea[secondAreaId]) {
				$(cityInputHtmlId).attr("checked",true);
			} else if(this.selectedCities[cityId]){
				$(cityInputHtmlId).attr("checked",true);	
			}
			
			$(cityInputHtmlId).click(function(){
				var checked=$(this).attr("checked");
				var cityName  = $(this).attr("cityName");
				var cityId = $(this).attr("cityId");
				var secondLevelAreaId = $(this).attr("secondLevelAreaId");
				_this.clickCity(cityId,cityName,secondLevelAreaId,checked); 
			});
		}
	},
	//第三层信息被点击：城市
	clickCity:function (cityId,cityName,secondLevelAreaId,checked){
		if(checked){
			this.selectCity(cityId);				
		}
		else{
			this.unSelectCity(cityId);	 
		}	
	},
	//选择城市
	selectCity:function (cityId){
		if ( this.selectedCities[cityId] ){
			return ;
		}
		var cityInfo=this.findCityInfo(cityId);
		if ( this.selectedSecondLevelArea[cityInfo.secondLevelAreaId]){
			return ;
		}
		if(!$("#new-row-"+cityInfo.secondLevelAreaId).size() ){
			var secondAreaObj=this.findSecondArea(cityInfo.secondLevelAreaId);
			this.addProvinceToSelected(secondAreaObj);	
		}
		this.makeSecondLevelAreaChecked(cityInfo.secondLevelAreaId);
		this.selectedCities[cityInfo.id]=true;
		this.addCityToSelected(cityInfo);
		if ( this.isAllChildSelected(cityInfo.secondLevelAreaId) ){
			this.selectedSecondLevelArea[cityInfo.secondLevelAreaId]=true;
			var childList = this.findCityList(cityInfo.secondLevelAreaId);
			if (null != childList){
				for (var i=0; i < childList.length; ++i){
					this.selectedCities[childList[i].id]=false;
					$("#new-row-"+childList[i].id).remove();
				}
			}
			this.hideThirdLevelLayer();
		}
	}, 
	//取消选择城市
	unSelectCity:function (cityId){ 
		var cityInfo=this.findCityInfo(cityId);
		if(this.selectedSecondLevelArea[cityInfo.secondLevelAreaId] ){
			this.unSelectCityInCompleteProvince(cityInfo);
		}else{
			this.selectedCities[cityId]=false;
			$("#new-row-"+cityId).remove();
			$("#areaDataRow_input_"+cityId).attr("checked",false);	
			if(!this.isHaveOtherCity(cityId)){
				 $("#new-row-"+cityInfo.secondLevelAreaId).remove();
				 this.makeSecondLevelAreaUnChecked(cityInfo.secondLevelAreaId);
			 } 
		}
	},
	unSelectCityInCompleteProvince:function(cityInfo){
		this.selectedSecondLevelArea[cityInfo.secondLevelAreaId]=false;
		$("#areaDataRow_input_"+cityInfo.id).attr("checked",false);
		var childList = this.findCityList( cityInfo.secondLevelAreaId );
//		判断省下面的市是否被展开
		var childUIExist = 	$("#new-row-"+cityInfo.secondLevelAreaId).children("div").size();
		if( null!=childList ){
			if( 0==childUIExist ){
				for(var i=0; i<childList.length; ++i){
					if(cityInfo.id == childList[i].id){
						continue;
					}else{
						this.addCityToSelected(this.findCityInfo(childList[i].id) );
						this.selectedCities[childList[i].id]=true;	
					}
				}
			}else{
				for(var i=0; i<childList.length; ++i){
					if(cityInfo.id == childList[i].id ){
						$("#new-row-"+cityInfo.id).remove();
					}else{
						this.selectedCities[childList[i].id]=true;	
					}
				}
			}
		}
	},
	addCityToSelected:function(cityInfo){
		var _this=this; 
		var htmlId = "#"+"new-row-"+cityInfo.secondLevelAreaId;
		var data={}; 
		data.areaType='china';
		data.id=cityInfo.id;
		data.name=cityInfo.name;
		data.slideToggle="-";
		$('#areaSelectedDataRowUITemplate').tmpl(data).appendTo(htmlId);
		$('#deleteAction_'+cityInfo.id).click(function(){
			_this.deleteAreaEle(cityInfo.id);
		});		
		$("#areaDataRow_input_"+cityInfo.id).attr("checked","checked");
		//​​​​​​​​
	}, 
	//是否有其它兄弟节点
	isHaveOtherCity:function (cityId){
		var cityInfo=this.findCityInfo(cityId);
		var cityList=this.findCityList(cityInfo.secondLevelAreaId);
		if(!cityList){
			return false;
		}
		for(var i = 0; i < cityList.length; i++){
			var tempId = cityList[i].id;
			if(this.selectedCities[tempId]){
				return true;
			} 
		}
		return false;
	},
	isAllChildSelected:function(secondLevelAreaId){
		var childList = this.findCityList(secondLevelAreaId);
		for (var i=0; i < childList.length; ++i){
			if ( !this.selectedCities[childList[i].id] ){
				return false;
			}
		}
		return true;
	},
	//第二层取消选中
	makeSecondLevelAreaUnChecked:function (secondLevelAreaId){
		$("#areaDataRow_input_"+secondLevelAreaId).attr("checked",false);
		this.selectedSecondLevelAreaUI[secondLevelAreaId]=false;
	},
	//第二层选中
	makeSecondLevelAreaChecked:function (secondLevelAreaId){
		$("#areaDataRow_input_"+secondLevelAreaId).attr("checked","checked");
		this.selectedSecondLevelAreaUI[secondLevelAreaId]=true;
	},
	//取消选择第二层区域
	unSelectSecondArea:function (secondAreaObj){
		this.selectedSecondLevelArea[secondAreaObj.id] = false;
		this.selectedSecondLevelAreaUI[secondAreaObj.id] = false;
		this.makeSecondLevelAreaUnChecked(secondAreaObj.id);
		$("#new-row-"+secondAreaObj.id).remove();
		var cityList=this.findCityList(secondAreaObj.id); 
		if(!cityList){
			return;
		} 
		for(var i = 0; i < cityList.length; i++){
			var tempId = cityList[i].id;
			this.selectedCities[tempId]=false;
//			不知道加了的作用
//			$("#areaDataRow_input_"+tempId).attr("checked",null);
//			$("#areaDataRow_input_"+tempId).removeAttr('disabled'); 
		}
	},	
	//选择第二层区域对象
	selectSecondArea:function (secondAreaObj){
		if(!secondAreaObj){
			return;			
		}	
		this.makeSecondLevelAreaChecked(secondAreaObj.id);
		this.selectedSecondLevelArea[secondAreaObj.id] = true;
		this.selectedSecondLevelAreaUI[secondAreaObj.id] = true;
		this.addProvinceToSelected(secondAreaObj);
	},
	//全部清空
	clearAllArea:function (){
		this.selectedSecondLevelAreaUI=new Object();
		this.selectedSecondLevelArea=new Object();
		this.selectedCities=new Object();
		$("#ctrlorientorientselectorRListBodyregion").html('');  
	},
	showAreaDataToUI:function(){
		if($("#china-country").attr("checked")){ 
			//默认初始化中国
			this.createChinaProvince(); 
		}else if($("#other-country").attr("checked")){
			this.createOhterCountry();
		}else{
			this.createAreaGroup(); 
		} 		
	},
	unSelectAllSecondArea:function (secondAreaArray){
		for(var l = 0; l < secondAreaArray.length; l++){
			var secondAreaObj=secondAreaArray[l];
			this.unSelectSecondArea(secondAreaObj);
		}
	},
	selectAllArea:function(){
		this.unSelectAllSecondArea(this.currentSecondAreaArray);
		for(var l = 0; l < this.currentSecondAreaArray.length; l++){
			var secondAreaObj=this.currentSecondAreaArray[l];
			this.selectSecondArea(secondAreaObj);
		}
	},
	//全选
	selectAll:function (){		
		var areaType=$('input:radio[name="areasType"]:checked').val();
		if('other-country'==areaType){
			this.selectAllArea(); 
			return;
		} 
		if(this.selectUIType=='省市列表'){
			this.selectAllArea();
		}
		else if(this.selectUIType=='一线城市'){
			for(var i=0;i<this.firstTierCity.length;i++){
				var city=this.firstTierCity[i];
				var state=$("#areaDataRow_input_"+city.id).attr("checked");
				if( undefined == state ){
					this.clickCityArea(city.id,"checked");
					$("#areaDataRow_input_"+city.id).attr("checked","checked");	
				}			
			}			
		}
		else if(this.selectUIType=='省会城市'){
			for(var i=0;i<this.capitalCity.length;i++){
				var city=this.capitalCity[i];
				var state=$("#areaDataRow_input_"+city.id).attr("checked");
				if( undefined==state ){
					this.clickCityArea(city.id,"checked");	
					$("#areaDataRow_input_"+city.id).attr("checked","checked");
				}
			}
		}	
	},
	invertSelection:function(){
		var list = $('input[id^="areaDataRow_input_"]');
		for (var i=0;i<list.size();++i){
			if (list[i].disabled){
				continue ;
			}
			var id = list[i].id.substring(18);
			if ( this.findSecondArea(id) ){
				this.invertSelectionSecondLevelArea(id);
			}else if ( this.findCityInfo(id) ){
				this.invertSelectionCityLevelArea(id);
			}
		}
	},
	invertSelectionSecondLevelArea:function(id){
		if ( !this.selectedSecondLevelArea[id] && this.selectedSecondLevelAreaUI[id] ){
			var cityList = this.findCityList(id);
			if (null!=cityList){
				for (var j=0;j<cityList.length;++j){
					if ( this.selectedCities[cityList[j].id] || this.selectedSecondLevelArea[id]){
						this.clickCityArea(cityList[j].id,false);
					}else{
						this.clickCityArea(cityList[j].id,true);
					}
				}
			}
		}else if(this.selectedSecondLevelArea[id]){
			this.clickSecondLevelArea(id,false);
		}else{
			this.clickSecondLevelArea(id,true);
		}
	},
	invertSelectionCityLevelArea:function(id){
		var state=$("#areaDataRow_input_"+id).attr("checked");
		if ( undefined != state ){
			this.clickCityArea(id,false);
		}else{
			this.clickCityArea(id,true);
		}
	},
	//选择城市
	selectCityArray:function (cityArray,secondLevelAreaId){
		if(!$("#new-row-"+secondLevelAreaId).size()){
			var secondAreaObj=this.findSecondArea(secondLevelAreaId);
			this.addProvinceToSelected(secondAreaObj);	
		}		
		this.makeSecondLevelAreaChecked(secondLevelAreaId);
		for(var i=0;i<cityArray.length;i++){
			this.selectedCities[cityArray[i].id]=true;	
		}		
		this.addCityArrayToSelected(cityArray,secondLevelAreaId);		
	}, 	 
	addCityArrayToSelected:function(cityArray,secondLevelAreaId){
		var _this=this; 
		var htmlId = "#"+"new-row-"+secondLevelAreaId;
		var dataArray=new Array();
		for(var i=0;i<cityArray.length;i++){
			var data={}; 
			data.areaType='china';
			data.id=cityArray[i].id;
			data.name = cityArray[i].name;
			data.slideToggle="-";
			dataArray.push(data);
		}		
		$('#areaSelectedDataRowUITemplate').tmpl(dataArray).appendTo(htmlId);		
		for(var i=0;i<cityArray.length;i++){
			$('#deleteAction_'+cityArray[i].id).click(function(){
				_this.deleteAreaEle(cityArray[i].id); 
			});	
		}
	}, 
	addProvinceToSelected:function (secondAreaObj){
		var _this=this;
		if(!secondAreaObj){
			return;
		}
		if(!$("#new-row-"+secondAreaObj.id).size()){
			var data={};
			data.areaType='china';
			data.id=secondAreaObj.id;
			data.name=secondAreaObj.name;
			data.slideToggle= "+";
			$('#areaSelectedDataRowUITemplate').tmpl(data).appendTo("#ctrlorientorientselectorRListBodyregion");
			$("#new-row-slideToggle-"+secondAreaObj.id).click(function(){
				if ( _this.selectedSecondLevelArea[secondAreaObj.id] && 0 == $("#new-row-"+secondAreaObj.id).children("div").size() ){
					var childList = _this.findCityList(secondAreaObj.id);
					if (null != childList){
						for(var i=0;i<childList.length;i++){
							_this.addCityToSelected( _this.findCityInfo(childList[i].id) );
						}
					}
				}else{
					$("#new-row-"+secondAreaObj.id).children("div").slideToggle("slow");
				}
			});		
			$('#deleteAction_'+secondAreaObj.id).click(function(){
				_this.deleteSecondProvince(secondAreaObj.id); 
			});
		} 
	},
	findCityInfo:function (cityId){
		return this.cityBuf[cityId];
	}, 
	findSecondArea:function (secondAreaId){
		return this.secondLevelAreaBuf[secondAreaId];
	},
	findCityList:function (secondAreaId){
		var secondAreaObj=this.findSecondArea(secondAreaId);
		if(!secondAreaObj){
			return null;
		}
		var city = secondAreaObj.city;
		if(!city || city.length == 0){
			return null; 
		}
		var tempCityList=new Array();
		for(var k=0; k < city.length;k++){
			var data={};
			data.id=city[k].id; 
			data.name=city[k].name;
			data.pid=secondAreaObj.id;
			data.pname=secondAreaObj.name;
			tempCityList.push(data);
		}		
		return tempCityList; 
	},
	getX:function (obj){  
        var parObj=obj;    
        var left=obj.offsetLeft;  
        while(parObj=parObj.offsetParent){   
            left+=parObj.offsetLeft;    
        }    
        return left;    
    },
	 //删除省级地域
	deleteSecondProvince:function (secondLevelAreaId){
		var secondAreaObj=this.findSecondArea(secondLevelAreaId);
		this.unSelectSecondArea(secondAreaObj); 
	 },
	 // 删除市级地域
    deleteAreaEle:function (cityId){  
		this.unSelectCity(cityId);
	},
	queryAllSelectedInfo:function (){
		var infoArray=new Array();		
		this.querySecondAreaSelectedInfoTarget(this.areaData.country[0].province,infoArray);
		this.queryCitySelectedInfoTarget(this.areaData.country[0].province,infoArray);

		this.querySecondAreaSelectedInfoTarget(this.areaData.country[1].province,infoArray);
		this.queryCitySelectedInfoTarget(this.areaData.country[1].province,infoArray);
 
		return infoArray;
	},
	queryCitySelectedInfoTarget:function (secondAreaArray,infoArray){
		for(var l = 0; l < secondAreaArray.length; l++){
			var secondAreaObj = secondAreaArray[l];
			if(!secondAreaObj){
				return null;
			} 
			var cityArray = secondAreaObj.city;
			for(var k=0; k < cityArray.length;k++){
				var cityId=cityArray[k].id;
				if(this.selectedCities[cityId]){
					var info={};
					info.id=cityArray[k].id; 
					info.name=cityArray[k].name;
					infoArray.push(info);
				}
			}		
		}
	},
	querySecondAreaSelectedInfoTarget:function (secondAreaArray,infoArray){
		for(var l = 0; l < secondAreaArray.length; l++){
			var secondAreaId = secondAreaArray[l].id;			
			if(this.selectedSecondLevelArea[secondAreaId]){
				var info={};
				info.id=secondAreaArray[l].id;
				info.name=secondAreaArray[l].name;
				infoArray.push(info);
			}
		}
	},
	//同步已经选择的地域到UI
	syncSelectedAreaToUI:function(){
		for(var id in this.selectedSecondLevelAreaUI){	
			if(id&&this.selectedSecondLevelAreaUI[id]){
				this.makeSecondLevelAreaChecked(id);
				this.addProvinceToSelected(this.findSecondArea(id));
//				this.selectSecondArea(this.findSecondArea(id) );
			}
		}
		for(var id in this.selectedCities){
			this.addCityToSelected(this.findCityInfo(id));
		}
	}, 
	//同步已经选择的地域到buf
	syncSelectedAreaToBuf:function (areaIds) {
		if(areaIds==null||areaIds==''){
			return;
		} 
		var idArray=areaIds.split(",");
		for(var i=0;i<idArray.length;i++){
			var targetId=idArray[i];
			var secondAreaObj=this.findSecondArea(targetId);
			if(secondAreaObj){
				this.selectedSecondLevelArea[targetId]=true;
				this.selectedSecondLevelAreaUI[targetId]=true;
			}
			else{
				var cityInfo=this.findCityInfo(targetId);
				if(cityInfo){
					this.selectedCities[cityInfo.id]=true;
					var pid=cityInfo.secondLevelAreaId;
					this.selectedSecondLevelAreaUI[pid]=true;
				}
			}
		}
	}
});

function areaReady() {		
	areaUI=new common.ui.area.AreaUI();
}

function itemSelected(name,id) {
	$("#auto_area_search").val(name);
	$("#auto_area_search_hidden").val(id);
	$("#search_area_tip").hide();
}