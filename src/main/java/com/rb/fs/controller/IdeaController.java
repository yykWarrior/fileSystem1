package com.rb.fs.controller;

import com.rb.fs.entity.Idea;
import com.rb.fs.service.IdeaService;
import com.rb.fs.util.CodeMsg;
import com.rb.fs.util.Result;
import com.rb.fs.util.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rb.fs.controller.FilesController.UPLOAD_PATH_PREFIX;


@RestController
@RequestMapping("/idea")
public class IdeaController {
    @Autowired
    private IdeaService ideaService;


    @RequestMapping(value="/import",method= RequestMethod.POST)
    public Result addIdea(@RequestParam("excelFile") MultipartFile excelFile, HttpServletRequest request,String content){
        String filePath="";
        if (!excelFile.isEmpty()) {
            //return Result.error(new CodeMsg(ReturnCode.DATA_NOT_FOUND, "文件未找到"));
            String realPath = new String("src/main/resources/" + UPLOAD_PATH_PREFIX);
            //存放上传文件的文件夹
            File file = new File(realPath);
            if (!file.isDirectory()) {
                //递归生成文件夹
                file.mkdirs();
            }

            //获取原始的名字  original:最初的，起始的  方法是得到原来的文件名在客户机的文件系统名称
            String oldName = excelFile.getOriginalFilename();


            try {
                //构建真实的文件路径,getAbsolutePath方法获取文件绝对路径，separator表示分隔符
                File newFile = new File(file.getAbsolutePath() + File.separator + oldName);
                //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
                excelFile.transferTo(newFile);
                filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/uploadFile/" + oldName;
                //return Result.success("文件上传成功");
            } catch (Exception e) {
                return Result.error(new CodeMsg(ReturnCode.BUSINESS_ERROR, e.getMessage()));
            }
        }
        //插入到数据表中
        ideaService.addIdea(content,"admin",new Date(),filePath,0);
        return Result.success("文件上传成功");
    }


    @RequestMapping("/select")
    public Map select(){
        Map map=new HashMap();
        List<Idea> ideas=ideaService.select();
        map.put("rows",ideas);
        return map;
    }
}
