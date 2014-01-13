//取消点击复选框效果
function cancelSelectCheckbox() {
	/* （1）条件：
	 * 1.国家、省份、城市、地域、题材、产地不可相互组合
	 * 2.题材、产地以及时段不可相互组合
	 * 3.最多选择2个分布
	 * （2）复选框此时执行的流程：
	 * step1:以国家、省份、城市、地域为条件，若取消某个复选框后，剩下的为上述4个分布其中的一个，则执行一个流程
	 * step2:以题材、产地为条件，剩下的为上述2个分布其中的一个时，执行一个流程
	 * step3:以时段为条件，执行一个流程
	 * step4:当上述三个流程都不满足时，执行最后一个流程
	 * （3）基本原则：
	 * 数组删除几个则最后添加几个，保持前后值一致；
	 * 每点击一次重新生成一次checkBoxIdArray数组;
	 * 每切换一次二级菜单，重新初始化复选框
	 * 每一次的点击与上一次的点击没有任何关系（使两个点击之间完全解耦）
	 */
	var currentNum = 0;
	var datePeriodCount= 0;
	var count = 0;
	var cacCount = 0;
	var catAndCountry = [52,53,13];
	var areaArray = [14,15,16,49,52,53];
	var timeArray = [10,21];
	//每点击一次，更新一次该数组，则该数组为最新的复选框集合
	var checkBoxIdArray = new Array();
	$("input[name='reports_class']:checkbox").each(function(){
		checkBoxIdArray.push($(this).val());
	});		
	//监测当前所选复选框的个数
	if ($("input[name='reports_class']:checkbox:checked").length == 1){						
		//当前所选复选框的个数
		currentNum = currentNum + 1;
		//若当前唯一选择的分布为时段分布，则将其计数器+1
		if ($("input[name='reports_class']:checkbox:checked").val() == 13) {
			datePeriodCount = datePeriodCount + 1;	
			catAndCountry.removeElement(13);
		}							
	}							
	//实现地域分布、国家分布、省份分布、城市分布、题材分布以及产地分布不可相互组合
	for (var i = 0; i< areaArray.length;i++) {
		$("input[name='reports_class']:checkbox:checked").each(function(){		
			if ($(this).val() == areaArray[i]) {															
				//若此时的复选框为题材分布或者产地分布时，要考虑到此时的时段分布不可选
				if(areaArray[i] == 52 || areaArray[i] == 53) {
					cacCount = cacCount + 1;
					catAndCountry.removeElement($(this).val());
					areaArray.removeElement($(this).val());
				}else {
					count = count +1;
					areaArray.removeElement($(this).val());
				}
				
			}
		});
	}
	//当前只选择一个复选框且含有地域相关的分布时
	if (currentNum == 1 && count == 1) {						
		for (var i = 0; i< areaArray.length;i++) {
			var currentValue = areaArray[i];
			$("#"+currentValue).attr("disabled",true);
			$("#"+currentValue).parent().css("color",'#A9A9A9');							
			checkBoxIdArray.removeElement(currentValue);
		}
		//除去与地域相关的分布不可选择外，其他的分布可选
		for (var i = 0;i<checkBoxIdArray.length;i++) {
			var currentValue = checkBoxIdArray[i];										
			$("#"+currentValue).attr("disabled",false);
			$("#"+currentValue).parent().removeAttr("style");
		}
		//此时的地域相关的数组没有当前所选的地域id，则将其加上
		areaArray.push($("input[name='reports_class']:checkbox:checked").val());
	}else if(currentNum == 1 && cacCount == 1){		//当前只剩下一个选择的复选框且该复选框是题材或者产地中的一个时					
		//实现与时段不可组合
		for (var i = 0; i< catAndCountry.length;i++) {
			var currentValue = catAndCountry[i];
			$("#"+currentValue).attr("disabled",true);
			$("#"+currentValue).parent().css("color",'#A9A9A9');
			checkBoxIdArray.removeElement(13);
		}
		//实现与地域相关的分布不可操作
		for (var i = 0; i< areaArray.length;i++) {
			var currentValue = areaArray[i];
			$("#"+currentValue).attr("disabled",true);
			$("#"+currentValue).parent().css("color",'#A9A9A9');
			checkBoxIdArray.removeElement(currentValue);
		}
		//除去与时段相关以及与地域相关后的其他分布的展现
		for (var i = 0;i<checkBoxIdArray.length;i++) {
			var currentValue = checkBoxIdArray[i];										
			$("#"+currentValue).attr("disabled",false);
			$("#"+currentValue).parent().removeAttr("style");
		}
		areaArray.push($("input[name='reports_class']:checkbox:checked").val());
		catAndCountry.push($("input[name='reports_class']:checkbox:checked").val());
	}else if(currentNum == 1 && datePeriodCount == 1){ //当前只剩下一个选择的复选框且其是时段分布
		//实现与题材、产地的限制
		for (var i = 0; i< catAndCountry.length;i++) {
			var currentValue = catAndCountry[i];
			$("#"+currentValue).attr("disabled",true);
			$("#"+currentValue).parent().css("color",'#A9A9A9');
			checkBoxIdArray.removeElement(currentValue);
		}
		//实现与日期、月份的限制
		for (var i = 0;i<timeArray.length;i++){
			var currentValue = timeArray[i];
			$("#"+currentValue).attr("disabled",true);
			$("#"+currentValue).parent().css("color",'#A9A9A9');
			checkBoxIdArray.removeElement(currentValue);
		}
		for (var i = 0;i<checkBoxIdArray.length;i++) {
			var currentValue = checkBoxIdArray[i];										
			$("#"+currentValue).attr("disabled",false);
			$("#"+currentValue).parent().removeAttr("style");
		}			
		catAndCountry.push($("input[name='reports_class']:checkbox:checked").val());
	}else{ //上述条件都不满足时
		$("input[name='reports_class']:checkbox:checked").each(function(){
			checkBoxIdArray.push($(this).val());
		});
		checkBoxIdArray = jQuery.unique(checkBoxIdArray);
		for (var i = 0;i<checkBoxIdArray.length;i++) {								
			var currentValue = checkBoxIdArray[i];										
			$("#"+currentValue).attr("disabled",false);
			$("#"+currentValue).parent().removeAttr("style");
		}
	}
}