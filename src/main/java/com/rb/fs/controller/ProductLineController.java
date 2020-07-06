package com.rb.fs.controller;

import com.rb.fs.dto.LineDeviceDto;
import com.rb.fs.dto.PreModelDto;
import com.rb.fs.dto.ProLineDto;
import com.rb.fs.dto.ResultDto;
import com.rb.fs.entity.Device;
import com.rb.fs.entity.Distribute;
import com.rb.fs.entity.Model;
import com.rb.fs.entity.ProductLine;
import com.rb.fs.service.DistributeService;
import com.rb.fs.service.ModelService;
import com.rb.fs.service.ProductLineService;
import com.rb.fs.util.CodeMsg;
import com.rb.fs.util.Result;
import com.rb.fs.util.ReturnCode;
import com.rb.fs.util.UtilForDate;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

/**
 * 生产线控制器
 */
@RestController
@RequestMapping("/line")
public class ProductLineController {
    @Autowired
    private ProductLineService productLineService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private DistributeService distributeService;

    /**
     * 添加生产线
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/add")
    public Result<ProductLine> addLine(String name){
        try {
            productLineService.addLine(name);
            return Result.success();
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }

    }

    /**
     * 分页查询所有生产线
     * @return
     */
    @RequiresRoles("管理员")
    @ResponseBody
    @RequestMapping("/selectLine")
    public ResultDto selectLine(int page,int limit,String name){
        int count=0;
        List<ProductLine> productLines=new ArrayList<>();
        if(!StringUtils.isBlank(name)) {
            productLines= productLineService.selectByName(page,limit,name);
            count=productLineService.countByName(name);
      }else {
          productLines=productLineService.selectLine(page,limit);
          //查询出总条数
           count = productLineService.count();
      }
        List<ProLineDto> proLineDtos = proLineDtoToProLine(productLines);
        return new ResultDto(0,"",count,proLineDtos);
    }

    /**
     * 不分页查询所有
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/selectall")
    @ResponseBody
    public Result selectAll(){
        try {
            List<ProductLine> productLines=productLineService.selectAll();
            return Result.success(productLines);
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }
    }


    /**
     * 根据id删除生产线
     * @param id
     * @return
     */
   /* @RequestMapping("/delete")
    public Result<ProductLine> delete(int id){
        try {

            //删除所有生产线
            productLineService.delete(id);

            return Result.success();
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }
    }*/

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/muldel")
    public Result muiltiDelete(String ids){
        try {
            productLineService.delete(ids);
            return Result.success();
        }catch (Exception e){
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()) );
        }
    }

    /**
     *更新生成线
     * @param id
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/update")
    public Result<ProductLine> update(int id,String name){
        try {
            productLineService.update(id,name);
            return Result.success();
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }
    }

    /**
     * 操作员页面根据生成线获取所有设备
     * @return
     */
    @RequiresRoles("普通员工")
    @RequestMapping("/getDeviceByLine")
    public Result getDeviceByProLine(){
        List<LineDeviceDto> lineDeviceDtoList=new ArrayList<>();
        Session session = SecurityUtils.getSubject().getSession();
        String lineId= (String) session.getAttribute("line");
        String[] lineIds=lineId.split(",");
        for(int i=0;i<lineIds.length;i++) {
            //根据id查询生产线
            ProductLine productLine = productLineService.findById(Integer.parseInt(lineIds[i]));
            //线位名称
            String lineName = productLine.getName();
            List<Device> devices = productLineService.getDeviceByProLine(Integer.parseInt(lineIds[i]));
            LineDeviceDto lineDeviceDto=new LineDeviceDto(Integer.parseInt(lineIds[i]),lineName,devices);
            lineDeviceDtoList.add(lineDeviceDto);
        }
        return Result.success(lineDeviceDtoList);
    }

    /**
     * 操作员页面根据线id查询型号
     * @return
     */
    @RequiresRoles("普通员工")
    @RequestMapping("/getModelByProLine")
    public Result getModelByProLine(int lineId,int deviceId){
        List<PreModelDto> preModelDtoList=new ArrayList<>();
        try {
            //查询分配记录
            List<Distribute> distributes=distributeService.findByLineIdAndDeviceIdAndEffectAndDate(lineId,deviceId,1,new Date());
            for(Distribute distribute:distributes){
                //根据型号id查询型号名称
                Model model=modelService.findById(distribute.getTname());
                PreModelDto preModelDto=new PreModelDto(distribute.getProlineName(),distribute.getDname(),distribute.getTname(),model.getName(),model.getType());
                preModelDtoList.add(preModelDto);
            }
            return Result.success(preModelDtoList);
        } catch (InvalidSessionException e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }
    }

    /**
     * 批量添加生产线
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/batchadd")
    public Result batchAdd( String names){
        try {
           productLineService.addLine(names);
            return Result.success();
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }
    }







    /**
     * 把时间转换成年月日格式
     * @param productLines
     * @return
     */
    public List<ProLineDto> proLineDtoToProLine(List<ProductLine> productLines){
        List<ProLineDto> proLineDtos=new ArrayList<>();
        for(ProductLine productLine:productLines){
            String date =UtilForDate.getDateAndTime(productLine.getCreatdate());
            ProLineDto proLineDto=new ProLineDto(productLine.getId(),productLine.getName(),productLine.getCompany(),date);
            proLineDtos.add(proLineDto);
        }
        return proLineDtos;
    }

}
