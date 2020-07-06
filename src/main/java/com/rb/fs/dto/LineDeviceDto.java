package com.rb.fs.dto;

import com.rb.fs.entity.Device;

import java.util.List;

/**
 * 封装生产线以及此生产线下面的所有设备
 */
public class LineDeviceDto {
    //生产线id
    private int lineId;
    //生产线名称
    private String lineName;
    //设备集合
    private List<Device> deviceList;

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public LineDeviceDto() {
    }

    public LineDeviceDto(int lineId, String lineName, List<Device> deviceList) {
        this.lineId = lineId;
        this.lineName = lineName;
        this.deviceList = deviceList;
    }
}
