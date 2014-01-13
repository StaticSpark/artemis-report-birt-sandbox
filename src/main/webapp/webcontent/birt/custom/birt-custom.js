function birtCustom() {
}

/**
 * 鼠标悬停行事件
 */
function rowMouseOverEvent() {
	var trList = tableChild.getElementsByTagName("tr");
	for(var i = 1; i < trList.length;  i++) {
		var rowBackGroundColor = trList[i].style.backgroundColor;
		trList[i].addEventListener("mouseover", function () {this.style.backgroundColor = "#F0F0F0";}, false);
		trList[i].addEventListener("click", function () {
			for(var j =1; j < trList.length; j++) {
				trList[j].isClick = "false";
			}; 
			this.isClick = "true"; 
			this.style.backgroundColor = "#FBEC88";
			}, false
		);
		trList[i].addEventListener("mouseout", function ()  {this.style.backgroundColor = rowBackGroundColor;}, false);
	}
}

