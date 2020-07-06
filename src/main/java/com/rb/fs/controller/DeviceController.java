package com.rb.fs.controller;


import com.rb.fs.dto.DeLineDto;
import com.rb.fs.dto.ResultDto;
import com.rb.fs.entity.Device;
import com.rb.fs.entity.ProductLine;
import com.rb.fs.entity.ReturnBean;
import com.rb.fs.service.DeviceService;
import com.rb.fs.service.ProductLineService;
import com.rb.fs.util.CodeMsg;
import com.rb.fs.util.JudgeIsNull;
import com.rb.fs.util.Result;
import com.rb.fs.util.ReturnCode;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * 设备控制类
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private ProductLineService productLineService;

    /**
     * 添加设备
     * @param productLineId
     * @param process_name
     * @param d_type
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/add")
    public Result add(int productLineId,String process_name,String d_type){
        return deviceService.add(productLineId,process_name,d_type);
    }

    /**
     *修改设备
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/update")
    public Result update(int deviceId,int productLineId,String process_name,String d_type){
        return deviceService.update(deviceId,productLineId,process_name,d_type);
    }

    /**
     * 查询所有设备
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/selectall")
    public ResultDto selectAll(int page,int limit,String processname,String devicename,String lineName){
       /* List<DeLineDto> devices=new ArrayList<>();
        int count=0;
        if((processname==null||processname.equals(""))&&(devicename==null||devicename.equals(""))) {
             devices = deviceService.selectAll(page, limit);
             count = deviceService.count();
            return new ResultDto(0,"",count,devices);
        }else{
            return findAllByProcessNameAndDeviceName(processname,devicename,limit,page);
        }*/
       List<DeLineDto> deLineDtoList=new ArrayList<>();
        Page<Device>  devices=deviceService.getPageUpgradeScheduleView(page,limit,processname,devicename,lineName);
        List<Device> deviceList=devices.getContent();
        for(Device device:deviceList){
            ProductLine productLine=productLineService.findById(device.getProline_id());
            DeLineDto deLineDto=new DeLineDto(device.getId(),device.getProcess_name(),device.getD_type(),device.getProline_id(),productLine.getName());
            deLineDtoList.add(deLineDto);
        }
        return new ResultDto(0,"",(int)devices.getTotalElements(),deLineDtoList);
    }

    /**
     * 根据线id查询所有
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/selectByLineId")
    public Result selectByLineId(int[] lineId){
        List<DeLineDto> devices=new ArrayList<>();
        List<DeLineDto> deviceList=new ArrayList<>();
            try {
                for(int i=0;i<lineId.length;i++) {
                    devices = deviceService.selectByLineId(lineId[i]);
                    deviceList.addAll(devices);
                }
                return Result.success(deviceList);
            } catch (Exception e) {
                return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG, e.getMessage()));

        }
    }

    /**
     * 批量添加设备
     * @return
     */
    @ResponseBody
    @RequiresRoles("管理员")
    @RequestMapping("/batchadd")
    public Result batchAdd( String processNames,String deviceNames,int lineid){
        processNames=JudgeIsNull.setStringToNull(processNames);
        deviceNames=JudgeIsNull.setStringToNull(deviceNames);
        String[] processNameArray=processNames.split(",");
        String[] deviceNameArray=deviceNames.split(",");
        if(processNameArray.length!=deviceNameArray.length){
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,"输入格式有误"));
        }
        try {
            deviceService.batchAdd(processNameArray,deviceNameArray,lineid);
           /* for(int i=0;i<processNameArray.length;i++){
                deviceService.batchAdd(new Device(JudgeIsNull.setStringToNull(processNameArray[i]),JudgeIsNull.setStringToNull(deviceNameArray[i]),lineid));
            }*/
            return Result.success();
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }
    }

    /**
     * 删除设备
     * @param id
     * @return
     */
   /* @RequestMapping("/delete")
    public Result delete(int id){
       return deviceService.delete(id);
    }*/

    /**
     * 批量删除
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/muldel")
    public Result multiDel(String ids){
       try {
           deviceService.delete(ids);
           return Result.success();
       }catch (Exception e){
           return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
       }
    }

    /**
     * 根据生产线id查询设备
     * @param lineId
     * @return
     */
    @RequestMapping("/getDeviceByLine")
    public ResultDto getDeviceByLine(int page,int limit,int lineId){
            ReturnBean returnBean=deviceService.getDeviceByLine(page,limit,lineId);
            return new ResultDto(0,"",returnBean.getCount(),returnBean.getObjectList());
    }

}
