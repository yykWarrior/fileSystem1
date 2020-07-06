package com.rb.fs.controller;

import com.alibaba.fastjson.JSON;
import com.rb.fs.dto.PreModelDto;
import com.rb.fs.dto.ResultDto;
import com.rb.fs.entity.Model;
import com.rb.fs.service.ModelService;
import com.rb.fs.util.CodeMsg;
import com.rb.fs.util.JudgeIsNull;
import com.rb.fs.util.Result;
import com.rb.fs.util.ReturnCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

/**
 * 型号控制器
 */
@RestController
@RequestMapping("/model")
public class ModelController {
    @Autowired
    private ModelService modelService;

    /**
     * 删除型号
     * @param ids
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("muldel")
    public Result delete(String  ids){
        try {
            modelService.delete(ids);
            return Result.success();
        }catch (Exception e){
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()) );
        }
    }

    /**
     * 批量插入型号
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/batchadd")
    public Result batchAdd( String modelNames,String modelType,int lineid){
        modelNames=JudgeIsNull.setStringToNull(modelNames);
        modelType=JudgeIsNull.setStringToNull(modelType);
       String[] modelNameArray=modelNames.split(",");
       String[] modelTypeArray=modelType.split(",");
       if(modelNameArray.length!=modelTypeArray.length){
           return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,"输入格式有问题"));
        }
        try {
            modelService.batchAdd(modelNameArray,modelTypeArray,lineid);
            return Result.success();
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }

    }


    /**
     * 分页查询所有
     * @param page
     * @param limit
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/selectpage")
    public ResultDto selectAll(int page,int limit,String modelname,String typename,Integer lineId){
            Page<Model> modelPage=modelService.getPageUpgradeScheduleView(page,limit,modelname,typename,lineId);
            return new ResultDto(0,"",(int)modelPage.getTotalElements(),modelPage.getContent());
    }

    /**
     * 根据生产线查询
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/selectByLinId")
    @ResponseBody
    public Result selectByLinid(int[] lineId) {
        List<PreModelDto> models = new ArrayList<>();
        List<PreModelDto> modelList = new ArrayList<>();
        try {
            for (int i = 0; i < lineId.length; i++) {
                models = modelService.selectByLinid(lineId[i]);
                modelList.addAll(models);
            }
            return Result.success(modelList);
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG, e.getMessage()));
        }
    }


}
