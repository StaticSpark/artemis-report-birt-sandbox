$(document).ready(function() {
	json = $("#json").text();
	data = eval('(' + json + ')');
	ctx = $("#ctx").val();
	$("#reportListUITemplate").tmpl(data).appendTo("#report-list");
	
	$("a[name='delete-report']").live("click", function(){
		var id = $(this).attr("tag");
		var url = ctx + "/report/ad-report-delete?id=" + id;
		if(confirm('确认要删除该报告么？')){
			location = url;
		}
	});
	
	var udpateName = $("#udpateName").val();
	
	if(udpateName.length > 0) {
		$.ajax({
			type: "post",
			dataType:'text',
			url: ctx + "/update-report?reportName=" + udpateName,
			success:function(data) {
				$("#"+type+"ReportUITemplate").tmpl(data).appendTo("#"+type+"_report_list");
				dataTableShow(type);
			}
		});
	}
});