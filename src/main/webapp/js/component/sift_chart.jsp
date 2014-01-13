<%@ page contentType="text/html;charset=UTF-8"%>
<script	src="${ctx}/js/component/sift_chart.js"  type="text/javascript"></script>
<img id="close_chart" tag="show" style="cursor: pointer;display:none;position:absolute;" width="20" height="20" src="${ctx}/images/report/close_chart.jpg"/>
<span id="sift_icon" tag="hide" isClick="false" style="float:left;display:none;border:1px solid gray;cursor: pointer">
   <img height="30px" width="120px" src="${ctx}/images/report/sift_icon.jpg"/>
</span>
<div id="sift_chart" style="border: solid #87CEFA 1px; width: 200px; height: 290px; padding: 10px;display:none;background-color:#FFFFFF; border-radius:10px;">
	<span id="sift_switch" style="margin-bottom: 10px; margin-left: 15px;">
	</span>
	<div id="sift_tables"
		style="border: solid #C6E2FF 1px; margin-top: 10px; width: 180px; height: 180px; padding: 10px;">
	</div>

	<div
		style="margin-left: auto;vertical-align:bottom; margin-top: 10px; margin-right: auto; text-align: center;">
		<img id="first_page" style="cursor: pointer"
			src="${ctx}/webcontent/birt/images/FirstPage.gif" alt="" /> <img
			id="previous_page" style="cursor: pointer"
			src="${ctx}/webcontent/birt/images/PreviousPage.gif" alt="" /> <input
			type="text" style="width: 15px; margin-top: -5px;" readonly
			id="pageNum" /> <img id="next_page"
			style="cursor: pointer; margin-left: 15px;"
			src="${ctx}/webcontent/birt/images/NextPage.gif" alt="" /> <img
			id="last_page" style="cursor: pointer"
			src="${ctx}/webcontent/birt/images/LastPage.gif" alt="" />
	</div>

	<table style="maring-left: 15px;">
		<tr>
			<td><input type="button" style="border:1px solid #52A652;display: block;-webkit-appearance: button" value="应用" id="refresh_chart" /></td>
		</tr>
	</table>
</div>
