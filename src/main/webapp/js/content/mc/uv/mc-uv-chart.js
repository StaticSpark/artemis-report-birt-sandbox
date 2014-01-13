/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 13-12-25
 * Time: 下午1:42
 * uv 报告chart展现相关的js
 */
uvChart = McReport.extend({
    __uvChart: null,
    areaIds: null,
    all_areas: null,
    all_area_ids: null,
    currentArea: null,
    uvData: null,
    uvSeries: null,
    datesCatagories: null,
    chartType: null,

    constructor: function (chartType) {
        __uvChart = this;
        this.chartType = chartType;
        this.uvSeries = new Array();
        this.datesCatagories = new Array();
        this.uvData = new Array();
        this.all_areas = this.extractAreas(areaInfo, 'name');
        this.all_area_ids = this.extractAreas(areaInfo, 'id');
        this.prepareData();
        this.renderUVChart();
        new UvChartSingleAreaSelect("chart_single_area_list", this.all_areas);
    },
//    从AreaInfo中抽取地域信息，type字段为id则返回id数组，name返回地域名称数组
    extractAreas: function (areaInfo, type) {
        var retAreaInfo = new Array();
        for (var i = 0; i < areaInfo.length; i++) {
            if (type == "id") {
                retAreaInfo.push(areaInfo[i].id);
            } else if (type == 'name') {
                retAreaInfo.push(areaInfo[i].name);
            }
        }
        return retAreaInfo;
    },
//    处理空结果集的操作，隐藏图标区域，并把查看分布图按钮变灰。
    handleEmptyResult: function () {
        $("#chart").hide();
        $("#switchLoction").attr('disabled', "disabled");
    },
    prepareData: function () {
        var startDate = $('#startDate').val();
        var endDate = $('#endDate').val();
        var monId = mcBase.id;
        var areaIds = this.extractAreas(areaInfo, 'id');
        //              一次性获取全部地域UV数据
        var param = "monId=" + monId + "&monDateFrom=" + startDate + "&monDateTo=" + endDate + "&area_id=" + areaIds.join(",");
        $.ajax({
            type: "post",
            async: false,
            dataType: 'json',
            url: ctx + "/mc/" + this.chartType + "-report-chart?" + param,
//            beforeSend方法解决firefox中报错"not well-formed"
            beforeSend: function (xhr) {
                xhr.overrideMimeType("application/json");
            },
            success: function (data) {
                __uvChart.uvData = data;
                if (__uvChart.uvData == null) {
                    __uvChart.handleEmptyResult();
                }
            }
        });
    },
//  根据选择的地域中文，映射为地域ID
    mapSelectedAreaId: function (selectedAreaName) {
        this.currentArea = this.all_area_ids[this.all_areas.indexOf(selectedAreaName)];
    },
//  根据选择的地域Id，映射为地域中文
    mapSelectedAreaName: function (selectedAreaId) {
       return this.all_areas[this.all_area_ids.indexOf(selectedAreaId)];
    },


    renderUVChart: function (selectedAreaName) {
        if (selectedAreaName != undefined) {
            this.mapSelectedAreaId(selectedAreaName);
        } else {
            this.currentArea = this.all_area_ids[0];
        }
        if(this.uvData[this.currentArea]==undefined){
            var noDataOptions = {
                chart:{
                    renderTo: 'uv_area'
                },
                title: {
                    text: "投放周期UV分布图"
                },
                subtitle:{
                    text: "无数据"
                },
                xAxis: {
                    categories: ""
                },
                series: ""
            };
            new Highcharts.Chart(noDataOptions);
            return ;
        }
        var chartWidth = $("#report").width();
        var chartObj = {
            chart: {
                renderTo: "uv_area",
                type: "column",
                zoomType: "xy",
                width: chartWidth
            },
            title: {
                text: this.mapSelectedAreaName(this.currentArea) + "-" + (this.chartType == "uv" ? "周期UV报告 " : "每日UV报告")
            },
            series: this.uvData[this.currentArea].uvnSeries,
            yAxis: [
                {
                    min: 0,
                    labels: {
                        formatter: function () {
                            var num = Highcharts.numberFormat(this.value, 0, ".", ",");
                            return num;
                        }
                    },
                    title: {
                        text: ""
                    }
                }
            ],
            xAxis: {
                categories: this.uvData[this.currentArea].dateXAxis
            }
        };
        new ChartCookie("mc_uv_cookie", "1+uv,2+uv,3+uv,4+uv,5+uv,6+uv,7+uv,8+uv,9+uv,10+uv", chartObj);
        this.formateChart(chartObj);
        new Highcharts.Chart(chartObj);
    }

})
;



