package com.rb.fs.controller;

import com.rb.fs.dao.DeviceDao;
import com.rb.fs.dao.FileDao;
import com.rb.fs.dao.ModelDao;
import com.rb.fs.dao.ProductLineDao;
import com.rb.fs.dto.DistributeDto;
import com.rb.fs.dto.ResultDto;
import com.rb.fs.entity.Files;
import com.rb.fs.entity.ProDeModel;
import com.rb.fs.entity.ReturnBean;
import com.rb.fs.service.ProDeModelService;
import com.rb.fs.util.CodeMsg;
import com.rb.fs.util.Result;
import com.rb.fs.util.ReturnCode;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 文件与设备型号生产线对应关系表
 * 对应ProDeModelService表
 * @Author yyk
 *
 */
@RestController
@RequestMapping("/fileType")
public class FileTypeController {
    @Autowired
    private ProDeModelService proDeModelService;
    @Autowired
    private ProductLineDao productLineDao;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private ModelDao modelDao;
    @Autowired
    private FileDao fileDao;


    /**
     * 根据生产线或者型号查询所有的记录
     * @param file_Id
     * @param typeName
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/selectAllByFid")
    public ResultDto findAllByFidOrModel(int page,int limit,int file_Id,String typeName){
        Page<ProDeModel> proDeModels=proDeModelService.getPageUpgradeScheduleView(page,limit,file_Id,typeName);
        List<ProDeModel>  proDeModelList=proDeModels.getContent();
        List<DistributeDto> distributeDtoList=new ArrayList<>();
        for(ProDeModel proDeModel:proDeModelList){
            //根据生产线id查询生产线
            String lineName=productLineDao.findById(proDeModel.getProName()).getName();
            //根据设备id查询设备名称
            String deviceName=deviceDao.findById(proDeModel.getDeviceName()).get().getProcess_name();
            //根据型号id查询型号名称
            String modelName=modelDao.findById(proDeModel.getModelName()).get().getType();
            DistributeDto distributeDto=new DistributeDto(proDeModel.getId(),lineName,deviceName,modelName);
            distributeDtoList.add(distributeDto);
        }
        return new ResultDto(0,"",(int)proDeModels.getTotalElements(),distributeDtoList);
    }

    /**
     * 多个删除
     * @param ids
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/mulDel")
    public Result mutiDel(String ids){
        try {
            proDeModelService.delete(ids);
            return Result.success();
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,"删除失败"));
        }
    }

    /**
     * 根据生产线查询文件
     * @param page
     * @param limit
     * @param lineId
     * @return
     */
    @RequestMapping("/getFileByLine")
    public ResultDto getFileByLine(int page,int limit,int lineId){
        ReturnBean returnBean=proDeModelService.getFileByLine(page,limit,lineId);
        List<ProDeModel> proDeModelList=returnBean.getObjectList();
        List<DistributeDto> distributeDtoList=new ArrayList<>();
        for(ProDeModel proDeModel:proDeModelList) {
            //根据生产线id查询生产线
            String lineName=productLineDao.findById(proDeModel.getProName()).getName();
            //根据设备id查询设备名称
            String deviceName = deviceDao.findById(proDeModel.getDeviceName()).get().getProcess_name();
            //根据型号id查询型号名称
            String modelName = modelDao.findById(proDeModel.getModelName()).get().getType();
            //根据文件id查询文件名称
            Files file=fileDao.findById(proDeModel.getfName()).get();
            DistributeDto distributeDto = new DistributeDto(proDeModel.getId(), lineName, deviceName, modelName,file.getName(),file.getPath());
            distributeDtoList.add(distributeDto);
        }
        returnBean.setObjectList(distributeDtoList);
        return new ResultDto(0,"",returnBean.getCount(),returnBean.getObjectList());
    }

}
