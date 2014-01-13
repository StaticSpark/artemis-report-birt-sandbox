package com.funshion.artemis.custom.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: zhangyong
 * Date: 13-12-27
 * Time: 下午12:54
 * Description:      UV报告实体封装类 ,map的key为uvn，value为n天的uv数据。
 */
public class McUVData {
    Map<String, List<Long>> uvnData;

    public McUVData() {
        uvnData = new HashMap<String, List<Long>>();
    }

    public List<Long> getUVnData(String uvn) {
        return uvnData.get(uvn);
    }

    public void setUvnData(String uvn, List<Long> data) {
        uvnData.put(uvn, data);
    }

    public void addUVnData(String uvn, Long data) {
        if (uvnData.get(uvn) == null) {
            List<Long> tmpList = new ArrayList<Long>();
            uvnData.put(uvn, tmpList);
        }
        uvnData.get(uvn).add(data);
    }

    /**
     *  计算N+UV
     */
    public void computeUVNPlus() {
        List<Long> tmpList = null;
        for (int i = 10; i >= 1; i--) {
            String tmpKey = i + "+uv";
            List<Long> tmpData = uvnData.get(tmpKey);
            if (tmpData == null) {
                continue;
            }
            if (tmpList == null) {
                tmpList = new ArrayList<Long>();
                tmpList.addAll(tmpData);
            } else {
                for (int j = 0; j < tmpList.size(); j++) {
                    tmpList.set(j, tmpList.get(j) + tmpData.get(j));
                }
                uvnData.put(tmpKey, new ArrayList<Long>(tmpList));
            }
        }
    }

    public int getUvDataSize() {
        return uvnData.size();
    }


}

