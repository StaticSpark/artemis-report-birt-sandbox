package com.funshion.artemis.mc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zhangyong
 * Date: 13-12-19
 * Time: 下午3:39
 * Description: 播控UV报告图表展示实体类
 * {
 * "UVNSeries":[{"name":"1+UV","data":"100,100,100"},{"name":"2+UV","data":"80,50,30"},{"name":"3+UV","data":"20,15,13"}],
 * "DateXAxis":["2013-12-01","2013-12-02","2013-12-03"]
 * }
 */
public class UVChartsData {

    //  存放日期的集合作为X轴的数据
    private List<String> dateXAxis = new ArrayList<String>();
    //  uvn的series数据
    private List<Series> uvnSeries = new ArrayList<Series>();

    public List<Series> getUvnSeries() {
        return uvnSeries;
    }

    public void setUvnSeries(List<Series> uvnSeries) {
        this.uvnSeries = uvnSeries;
    }

    public List<String> getDateXAxis() {
        return dateXAxis;
    }

    public void setDateXAxis(List<String> dateXAxis) {
        this.dateXAxis = dateXAxis;
    }

}
