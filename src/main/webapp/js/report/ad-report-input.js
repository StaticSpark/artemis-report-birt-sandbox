$(document).ready(function() {
	errorInfo = $("#errorInfo").val();
	if(errorInfo.length > 0) {
		alert(errorInfo);
	}
	
	json = $("#json").html();
	data = eval('(' + json + ')');
	ctx = $("#ctx").val();
	typeList = new Array();
	typeList = data.typeList;
	columnInfo = new Array();
	columnInfo = data.columnInfo;
	report = data.report;
	
	initializeBasicInfo(report);
});

function formateJson(json, options) {
    var reg = null,
        formatted = '',
        pad = 0,
        PADDING = '    '; // one can also use '\t' or a different number of spaces

    // optional settings
    options = options || {};
    // remove newline where '{' or '[' follows ':'
    options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
    // use a space after a colon
    options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;

    // begin formatting...
    if (typeof json !== 'string') {
        // make sure we start with the JSON as a string
        json = JSON.stringify(json);
    } else {
        // is already a string, so parse and re-stringify in order to remove extra whitespace
        json = JSON.parse(json);
        json = JSON.stringify(json);
    }

    // add newline before and after curly braces
    reg = /([\{\}])/g;
    json = json.replace(reg, '\r\n$1\r\n');

    // add newline before and after square brackets
    reg = /([\[\]])/g;
    json = json.replace(reg, '\r\n$1\r\n');

    // add newline after comma
    reg = /(\,)/g;
    json = json.replace(reg, '$1\r\n');

    // remove multiple newlines
    reg = /(\r\n\r\n)/g;
    json = json.replace(reg, '\r\n');

    // remove newlines before commas
    reg = /\r\n\,/g;
    json = json.replace(reg, ',');

    // optional formatting...
    if (!options.newlineAfterColonIfBeforeBraceOrBracket) {         
        reg = /\:\r\n\{/g;
        json = json.replace(reg, ':{');
        reg = /\:\r\n\[/g;
        json = json.replace(reg, ':[');
    }
    if (options.spaceAfterColon) {          
        reg = /\:/g;
        json = json.replace(reg, ': ');
    }

    $.each(json.split('\r\n'), function(index, node) {
        var i = 0,
            indent = 0,
            padding = '';

        if (node.match(/\{$/) || node.match(/\[$/)) {
            indent = 1;
        } else if (node.match(/\}/) || node.match(/\]/)) {
            if (pad !== 0) {
                pad -= 1;
            }
        } else {
            indent = 0;
        }

        for (i = 0; i < pad; i++) {
            padding += PADDING;
        }

        formatted += padding + node + '\r\n';
        pad += indent;
    });

    return formatted;
};

//初始化基础信息
function initializeBasicInfo() {
	 var rptType;
	if(report) {
	    $("#reportDisplayName").val(report.rptDisplayName);
	    $("#reportName").val(report.rptName);
	    $("#reportSql").html(report.rptSql);
	    $("#id").val(report.id);
	    rptType = report.rptType;
	}
    for(var i = 0; i < typeList.length; i++) {
    	var optionStr = "";
    	if(rptType == typeList[i].id) {
    		optionStr = "<option selected='selected' value='"+typeList[i].id+"'>"+typeList[i].name+"</option>";
    	} else {
    		optionStr = "<option value='"+typeList[i].id+"'>"+typeList[i].name+"</option>";
    	}
    		
    	$("#reportTypeList").append(optionStr);
    }
	
	$('#multiOpenAccordion').multiOpenAccordion({
		active : [ 0 ],
		click : function(event, ui) {
		},
		init : function(event, ui) {
		},
		tabShown : function(event, ui) {
		},
		tabHidden : function(event, ui) {
		}
	});
	
	$("#submit").click(function(e) {
		var reportDisplayName = $("#reportDisplayName").val();
		var reportName = $("#reportName").val();
		if(reportName.length == 0) {
			alert("报表标识不能为空!");
			e.preventDefault();
			return;
		} else if(/.*[\u4e00-\u9fa5]+.*$/.test(reportName)) {
			alert("报表标识不能含有中文!");
			e.preventDefault();
			return;
		}
		
		if(reportDisplayName.length == 0) {
			alert("报表名称不能为空!");
			e.preventDefault();
			return;
		}
	});
}
