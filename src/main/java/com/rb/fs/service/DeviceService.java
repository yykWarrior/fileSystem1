package com.rb.fs.service;

import com.rb.fs.dto.DeLineDto;
import com.rb.fs.entity.Device;
import com.rb.fs.entity.ReturnBean;
import com.rb.fs.util.Result;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DeviceService {
    /**
     * 添加设备
     * @param productLineId
     * @param process_name
     * @param d_type
     * @return
     */
    Result add(int productLineId, String process_name, String d_type);

    /**
     * 修改设备
     * @return
     */
    Result update(int deviceId,int productLineId,String process_name,String d_type);

    /**
     * 查询所有设备
     * @return
     */
    //List<DeLineDto> selectAll(int page,int limit);

    /**
     * 批量添加设备

     * @return
     */
    void batchAdd(String[] processNameArray, String[] deviceNameArray, int lineId);


    /**
     * 删除设备
     * @param id
     * @return
     */
    void delete(String id);

    /**
     * 查询总条数
     * @return
     */
    //int count();

    /**
     * 根据设备名称和工序名称查询
     * @param processname
     * @param devicename
     * @param limit
     * @param page
     * @return
     */
   // List<DeLineDto> findAllByProcessNameAndDeviceName(String processname, String devicename, int limit, int page);

    /**
     * 查询符合条件的总条数
     * @param processname
     * @param devicename
     * @return
     */
   // int countByName(String processname, String devicename);

    /**
     * 根据线id查询所有
     * @return
     * @param line
     */
    List<DeLineDto> selectByLineId(int line);

    /**
     * 根据id查找
     * @param i
     * @return
     */
    Device findById(int i);

    /**
     * 多条件查询
     * @param page
     * @param limit
     * @param processname
     * @param devicename
     * @param lineName
     * @return
     */
     Page<Device> getPageUpgradeScheduleView(int page, int limit, String processname, String devicename, String lineName) ;

    /**
     * 根据设备名称模糊查询
     * @param dename
     * @return
     */
    List<Device> findByDtype(String dename);

    /**
     * 根据设备id查询
     *
     * @param page
     * @param limit
     * @param lineId
     * @return
     */
    ReturnBean getDeviceByLine(int page, int limit, int lineId);
}
