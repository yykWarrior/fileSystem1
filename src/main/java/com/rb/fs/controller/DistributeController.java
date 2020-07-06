package com.rb.fs.controller;


import com.rb.fs.dao.DeviceDao;
import com.rb.fs.dao.FileDao;
import com.rb.fs.dao.ModelDao;
import com.rb.fs.dao.ProductLineDao;
import com.rb.fs.dto.DistributeDto;
import com.rb.fs.dto.ResultDto;
import com.rb.fs.entity.*;
import com.rb.fs.service.*;
import com.rb.fs.util.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/dis")
public class DistributeController {

    @Autowired
    private DistributeService distributeService;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private ProductLineDao productLineDao;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private ModelDao modelDao;

    /**
     * 根据生产线分配
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/disline")
    public Result distributeLine(String proLineId, String devices, String modes, Date startdate, Date enddate){
        String message="";
        //获得所有设备
        String[] deviceArray=devices.split(",");
        //获取所有型号
        String[] modelArray=modes.split(",");
        for(int i=0;i<modelArray.length;i++){
            for(int j=0;j<deviceArray.length;j++){
                message= distributeService.add(proLineId,deviceArray[j],modelArray[i],startdate,enddate);
                if(!message.equals("true")){
                    return Result.error(new CodeMsg(ReturnCode.DATA_NOT_FOUND,message));
                }
            }
        }
        return Result.success();
    }


    /**
     * 查询所有的分配记录
     * @return
     */
  /*  @RequestMapping("/selectPage")
    public ResultDto findAll(String dateser,int page,int limit){
        List<DistributeDto> distributes=new ArrayList<>();
        int count=0;
        if(dateser==null||dateser.equals("")) {
            //查询所有数据
             distributes = distributeService.findAll(page, limit);
             count = distributeService.count();
        }else {
            try {
                //获取一天的开始时间和结束时间
                Date[] dates=UtilForDate.StringToDate(dateser);
                //开始时间
                Date startTime=dates[0];
                //结束时间
                Date endTime=dates[1];
                distributes=distributeService.findByDate(page,limit,startTime,endTime);
                count=distributeService.countByDate(startTime,endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return new ResultDto(0,"",count,distributes);
    }
*/
    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/multidel")
    public Result multiDelete(String ids){
        //String[] idss= ids.split(",");
        try {
            distributeService.delete(ids);
            /*for (int i = 0; i < idss.length; i++) {
                if (idss[i] != null && !idss[i].equals(""))
                    distributeService.delete(Integer.parseInt(idss[i]));
            }*/
            return Result.success();
        }catch (Exception e){
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }
    }

    /**
     * 根据生产线id和设备id查询数据
     * @param lineId
     * @param deviceId
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/getModelAndFile")
    public Result findByLineIdAndDeviceId(int lineId,int deviceId,String modelId){
        List<Distribute> distributes=new ArrayList<Distribute>();
        try {
            if(modelId==null||modelId.equals("")){
                distributes=distributeService.findByLineIdAndDeviceId(lineId,deviceId);
                return Result.success(distributes);
            }else{
                List<Files> filesList=new ArrayList<>();
                distributes=distributeService.findByLineIdAndDeviceIdAndModel(lineId,deviceId,Integer.parseInt(modelId));
                //根据文件id查询文件url
                for(Distribute distribute:distributes) {
                    String[] distributeArray=distribute.getFid().split(",");
                    for(int i=0;i<distributeArray.length;i++){
                        if(distributeArray[i]!=null&&!distributeArray.equals("")){
                            Files files=fileDao.findById(Integer.parseInt(distributeArray[i])).get();
                            filesList.add(files);
                        }
                    }

                }
                return Result.success(filesList);
            }

        } catch (NumberFormatException e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }
    }

    /**
     * 分配生产线
     * @param lineId
     * @param berType
     * @param proName
     * @param manDate
     * @return
     */
    @RequiresRoles("管理员")
    @ResponseBody
    @RequestMapping("/disLine")
    public Result disLine(int[] lineId,String berType,String proName,String manDate)  {
        //拆分型号
        String[] models=berType.split(",");
        //拆分设备
        String[] devices=proName.split(",");
        //拆分时间
        String[] dates=manDate.split(" - ");

        try {
            List<DistributeDto> distributeDtoList=distributeService.disLine(models,devices,dates,lineId);
            Map map=new HashMap<String,Object>();
            map.put("sum",distributeDtoList.size());
            map.put("content",distributeDtoList);
            /*for(String device:devices){
                    Date startTime=UtilForDate.StringToDate1(dates[0]);
                    Date endTime=UtilForDate.StringToDate1(dates[1]);
                    // distributeService.findByDateAndModel(startTime,endTime,lineId,Integer.parseInt(device),)
                    for(String model:models){
                        //查询文件id
                        List<ProDeModel> proDeModels=proDeModelService.findByProNameAndDeviceNameAndModelNameAndEffect(lineId[0],Integer.parseInt(device),Integer.parseInt(model),1);
                        if(proDeModels.size()!=0) {
                            for(ProDeModel proDeModel:proDeModels) {
                                fids+=proDeModel.getfName()+",";
                            }
                            Distribute distribute = new Distribute(lineId[0], Integer.parseInt(device), Integer.parseInt(model), fids, new Date(), 1, startTime, endTime);
                            distributeService.save(distribute);
                        }
                    }
            }*/
            return Result.success(map);
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));

        }
    }


    /**
     * 多条件查询
     * @param dateser
     * @param proline
     * @param beartype
     * @param dename
     * @param page
     * @param limit
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/multiquery")
    public ResultDto multiQuery(String dateser, String proline, String beartype, String dename, int page, int limit){
        try {
            Page<Distribute> distributes=distributeService.getPageUpgradeScheduleView(dateser,proline,beartype,dename,page,limit);
            List<DistributeDto> distributeDtoList=distributeDtoToDistribute(distributes.getContent());
            return new ResultDto(0,"",(int)distributes.getTotalElements(),distributeDtoList);
        } catch (ParseException e) {
            return new ResultDto(0,"",0,"数据有误");
        }
    }


    /**
     * 实体类转换成dto
     * @param distributes
     * @return
     */
    public List<DistributeDto> distributeDtoToDistribute(List<Distribute> distributes){
        List<DistributeDto> distributeDtos=new ArrayList<>();
        for(Distribute distribute:distributes){
            String fNames="";
            String pName=productLineDao.findById(distribute.getProlineName()).getName();
            String dName=deviceDao.findById(distribute.getDname()).get().getProcess_name();
            String mName=modelDao.findById(distribute.getTname()).get().getType();
            String[] fids=distribute.getFid().split(",");
            for(int i=0;i<fids.length;i++){
                if(fids[i]!=null&&!fids[i].equals("")){
                    // String fName="";
                    String fName=fileDao.findByIdGetName(Integer.parseInt(fids[i]));
                    //fName = fileDao.findById(Integer.parseInt(fids[i])).get().getName();
                    fNames += fName + ",";
                }
            }
            DistributeDto distributeDto=new DistributeDto(distribute.getId(),pName,dName,mName,fNames,UtilForDate.getDateAndTime(distribute.getCreatdate()),distribute.getEffect(),UtilForDate.getDateAndTime(distribute.getStartdate()),UtilForDate.getDateAndTime(distribute.getEnddate()));
            distributeDtos.add(distributeDto);
        }
        return distributeDtos;
    }

    /**
     * 更新分配状态
     * @param id
     * @return
     */
    @RequestMapping("/updateEffect")
    public Result updateEffect(String id){
        try {
            distributeService.updateEffect(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }
    }
}
