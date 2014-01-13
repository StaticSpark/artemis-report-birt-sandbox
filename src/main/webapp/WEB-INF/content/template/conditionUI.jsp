<%@ page contentType="text/html;charset=UTF-8"%>

<script id="adReportUITemplate" type="text/x-jquery-tmpl">
	<tr>
		<td width="10px"><input type="checkbox" mark="ad-c" id="ad-c-\${adId}" name="con-check"></input></td>
		<td width="95px"><span style="float: left"><font size=2>\${adId}</font></span></td>
		<td width="280px"><span style="float: left"><font size=2>\${adName}</font></span></td>
		<td width="180px"><span style="float: left"><font size=2 name="adpName">\${adpName}</font></span></td>
		<td width="180px"><span style="float: left"><font size=2>\${statusName}</font></span></td>
		<td width="180px"><span style="float: left"><font size=2>\${orderName}</font></span></td>
		<td width="180px"><span style="float: left"><img src="${ctx}/images/report/list_view.gif" alt="*" /></span></td>
	</tr>
</script>

<script id="matReportUITemplate" type="text/x-jquery-tmpl">
	<tr>
		<td width="10px"><input type="checkbox" mark="mat-c" id="mat-c-\${id}" name="con-check"></input></td>
		<td width="95px"><span style="float: left"><font size=2>\${id}</font></span></td>
		<td width="280px"><span style="float: left"><font size=2>\${name}</font></span></td>
		<td width="180px"><span style="float: left"><font size=2>\${type}</font></span></td>
		<td width="180px"><span style="float: left"><font size=2>\${width}*\${height}</font></span></td>
		<td width="180px"><span style="float: left"><font size=2>\${playDur}</font></span></td>
		<td width="180px"><span style="float: left"><img src="${ctx}/images/report/list_view.gif" alt="" /></span></td>
	</tr>
</script>

<script id="adpReportUITemplate" type="text/x-jquery-tmpl">
	<tr>
		<td width="10px"><input type="checkbox" id="adp-c-\${id}" mark="adp-c" name="con-check"></input></td>
		<td width="95px"><span style="float: left"><font size=2>\${id}</font></span></td>
		<td width="280px"><span style="float: left"><font size=2>\${name}</font></span></td>
		<td width="180px"><span style="float: left"><font size=2>\${code}</font></span></td>
		<td width="180px"><span style="float: left"><font size=2>\${adpSize}</font></span></td>
		<td width="180px"><span style="float: left"><font size=2></font></span></td>
		<td width="180px"><span style="float: left"><img src="${ctx}/images/report/list_view.gif" alt="" /></span></td>
	</tr>
</script>

<script id="orderReportUITemplate" type="text/x-jquery-tmpl">
	<tr>
		<td width="10px"><input type="checkbox" id="order-c-\${id}" mark="order-c" name="con-check"></input></td>
		<td width="95px"><span style="float: left"><font size=2>\${id}</font></span></td>
		<td width="280px"><span style="float: left"><font size=2>\${name}</font></span></td>
		<td width="180px"><span style="float: left"><font size=2>\${medUserName}</font></span></td>
		<td width="180px"><span style="float: left"><font size=2>\${saleName}</font></span></td>
		<td width="180px"><span style="float: left"><img src="${ctx}/images/report/list_view.gif" alt="" /></span></td>
	</tr>
</script>

<script id="accReportUITemplate" type="text/x-jquery-tmpl">
	<tr>
		<td width="10px"><input type="checkbox" id="acc-c-\${id}" mark="acc-c" name="con-check"></input></td>
		<td width="95px"><span style="float: left"><font size=2>\${id}</font></span></td>
		<td width="280px"><span style="float: left"><font size=2>\${name}</font></span></td>
		<td width="180px"><span style="float: left"><font size=2></font></span></td>
		<td width="180px"><span style="float: left"><img src="${ctx}/images/report/list_view.gif" alt="" /></span></td>
	</tr>
</script>

<script id="saleReportUITemplate" type="text/x-jquery-tmpl">
	<tr>
		<td width="10px"><input type="checkbox" id="sale-c-\${id}" mark="sale-c" name="con-check"></input></td>
		<td width="95px"><span style="float: left"><font size=2>\${id}</font></span></td>
		<td width="280px"><span style="float: left"><font size=2>\${name}</font></span></td>
		<td width="180px"><span style="float: left"><font size=2></font></span></td>
		<td width="180px"><span style="float: left"><img src="${ctx}/images/report/list_view.gif" alt="" /></span></td>
	</tr>
</script>

<script id="catReportUITemplate" type="text/x-jquery-tmpl">
	<tr>
		<td width="10px"><input type="checkbox" id="cat-c-\${id}" mark="cat-c" name="con-check"></input></td>
		<td width="95px" style="display:none;"><span style="float: left;"><font size=2>\${id}</font></span></td>
		<td width="280px"><span style="float: left"><font size=2>\${fullname}</font></span></td>
		<td width="180px"><span style="float: left"><img src="${ctx}/images/report/list_view.gif" alt="" /></span></td>
	</tr>
</script>

<script id="countryReportUITemplate" type="text/x-jquery-tmpl">
	<tr>
		<td width="10px"><input type="checkbox" id="country-c-\${id}" mark="country-c" name="con-check"></input></td>
		<td width="95px" style="display:none;"><span style="float: left;"><font size=2>\${id}</font></span></td>
		<td width="280px"><span style="float: left"><font size=2>\${fullname}</font></span></td>
		<td width="180px"><span style="float: left"><img src="${ctx}/images/report/list_view.gif" alt="" /></span></td>
	</tr>
</script>

