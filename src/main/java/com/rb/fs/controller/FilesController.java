package com.rb.fs.controller;

import com.rb.fs.dao.FileDao;
import com.rb.fs.dto.FileDto;
import com.rb.fs.dto.ResultDto;
import com.rb.fs.entity.*;
import com.rb.fs.service.*;
import com.rb.fs.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * 文件控制器
 */
@RestController
@RequestMapping("/file")
public class FilesController {

    @Autowired
    private FileService fileService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private ProDeModelService proDeModelService;
    @Autowired
    private DistributeService distributeService;
    @Autowired
    private FileDao fileDao;
    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
    public final static String UPLOAD_PATH_PREFIX = "static/uploadFile/";


    @RequiresRoles("管理员")
    @RequestMapping("/uploadFile")
    public Result importExcel(@RequestParam("uploadFile") MultipartFile[] uploadFiles, HttpServletRequest request, FileDto fileDto) throws IOException {
        String filePath="";
        for(int i=0;i<uploadFiles.length;i++) {
            MultipartFile uploadFile=uploadFiles[i];
            if (uploadFile.isEmpty()) {
                return Result.error(new CodeMsg(ReturnCode.DATA_NOT_FOUND, "文件未找到"));
            }

            String realPath = new String("src/main/resources/" + UPLOAD_PATH_PREFIX);
            //存放上传文件的文件夹
            File file = new File(realPath);
            if (!file.isDirectory()) {
                //递归生成文件夹
                file.mkdirs();
            }

            //获取原始的名字  original:最初的，起始的  方法是得到原来的文件名在客户机的文件系统名称
            String oldName = "";
           // if (fileDto.getName().equals("") || fileDto.getName() == null) {
            if (!StringUtils.isBlank(fileDto.getName())) {
                oldName = uploadFile.getOriginalFilename();
                fileDto.setName(oldName.split(",")[0]);
            } else {
                oldName = fileDto.getName() + ".pdf";
            }

            try {
                //构建真实的文件路径,getAbsolutePath方法获取文件绝对路径，separator表示分隔符
                File newFile = new File(file.getAbsolutePath() + File.separator + oldName);
                System.out.println(file.getAbsolutePath() + File.separator + oldName);
                //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
                uploadFile.transferTo(newFile);
                filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/uploadFile/" + oldName;
                fileService.add(fileDto, filePath);
                //return Result.success("文件上传成功");
            } catch (Exception e) {
                return Result.error(new CodeMsg(ReturnCode.BUSINESS_ERROR, e.getMessage()));
            }
        }
        return  Result.success("文件上传成功");
    }

    /**
     * 查询所有文件
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/selectall")
    public ResultDto selectAllFile(int page,int limit,String filename,String fileNumber,String versionNumber){
       /* if(filename!=null&&!filename.equals("")){
            filename="%"+filename+"%";
        }
        if(fileNumber!=null&&!fileNumber.equals("")){
            fileNumber="%"+fileNumber+"%";
        }
        if(versionNumber!=null&&!versionNumber.equals("")){
            versionNumber="%"+versionNumber+"%";
        }
        List<Files> fileList=new ArrayList<>();

        int count=0;
        if((filename==null||filename.equals(""))&&(versionNumber==null||versionNumber.equals(""))&&(fileNumber==null||fileNumber.equals(""))){
            fileList=fileService.selectAll(page,limit);
            count=fileService.count();
        }else{
            fileList=fileService.selectByName(page,limit,fileNumber,versionNumber,filename);
            count=fileService.countBcyName(filename,versionNumber,fileNumber);
        }*/
        Page<Files> fileList= fileService.getPageUpgradeScheduleView(page,limit,filename,fileNumber,versionNumber);
        return new ResultDto(0,"",(int)fileList.getTotalElements(),fileList.getContent());
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/muldel")
    public Result muldelete(String ids){
        return fileService.delete(ids);
        /*String[] idss=ids.split(",");
        try {
            for(int i=0;i<idss.length;i++){
                if (idss[i] != null && !idss[i].equals("")) {
                    //去查询分配记录是否有引用这个文件
                    List<Distribute> distributeList=distributeService.findByFidAndEffect(idss[i],1);
                    if(distributeList.size()==0) {
                        //删除文件关系表里的数据
                        proDeModelService.deleteByFid(Integer.parseInt(idss[i]));
                        fileService.delete(Integer.parseInt(idss[i]));
                    }else{
                        return Result.error(new CodeMsg(ReturnCode.BUSINESS_ERROR,"请先删除文件对应的分配记录"));
                    }
                }
            }
            return Result.success();
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.BUSINESS_ERROR,"删除失败"));
        }*/
    }

    /**
     * 多文件上传
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/multiupload")
    public Result multiUpload(@RequestParam("file") MultipartFile[]  files, HttpServletRequest request){
        for(int i=0;i<files.length;i++) {
            long time= UtilForDate.getMillis(new Date());
            MultipartFile uploadFile=files[i];
            if (uploadFile.isEmpty()) {
                return Result.error(new CodeMsg(ReturnCode.DATA_NOT_FOUND, "文件未找到"));
            }

            String realPath = new String("src/main/webapp/pdfFile");
            //存放上传文件的文件夹
            File file = new File(realPath);
            if (!file.isDirectory()) {
                //递归生成文件夹
                file.mkdirs();
            }
           String oldName = uploadFile.getOriginalFilename();
            String[] names=oldName.split(".pdf");
            oldName=names[0]+"-"+time+".pdf";
            oldName=RandomDate.nullToStr(oldName);
            try {
                //构建真实的文件路径,getAbsolutePath方法获取文件绝对路径，separator表示分隔符
                File newFile = new File(file.getAbsolutePath() + File.separator + oldName);
                System.out.println(file.getAbsolutePath() + File.separator + oldName+"111");
                //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
                uploadFile.transferTo(newFile);
                String filePath =  "/pdfFile/" + oldName;
                Files files1=new Files(oldName,"","",new Date(),1,filePath);
                fileDao.save(files1);
                //return Result.success("文件上传成功");
            } catch (Exception e) {
                return Result.error(new CodeMsg(ReturnCode.BUSINESS_ERROR, e.getMessage()));
            }
        }
        return  Result.success("文件上传成功");
        }

    /**
     * 文件属性更改
     * @param id
     * @param fil_num
     * @param ver_num
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("update")
    public Result updata(String id, String fil_num, String ver_num) {
        id = id.split(",")[0];
        try {
            fileService.update(Integer.parseInt(id), fil_num, ver_num);
            return Result.success();
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.BUSINESS_ERROR, e.getMessage()));
        }
    }

    /**
     * 文件与生产线，设备，型号建立关系
     * @param fileId
     * @param dids
     * @param berIds
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/makerelate")
    public Result makeRelate(int[] fileId, int[] dids, int[] berIds) {
        try {
            fileService.save(fileId,dids,berIds);
           /* for(int i=0;i<dids.length;i++){
                //根据设备查询id查询线id
                Device device=deviceService.findById(dids[i]);
                int proId=device.getProline_id();
                        list.add(proId);
                        for(int m=0;m<berIds.length;m++){
                           Model model= modelService.findByLineIdAndId(proId,berIds[m]);
                           if(model!=null){
                               ProDeModel proDeModel1=proDeModelService.findByProNameAndDeviceNameAndModelNameAndFNameAndEffect(proId,dids[i],berIds[m],fileId[0],1);
                               if(proDeModel1!=null){
                                   proDeModelService.updateEffect(proId,dids[i],berIds[m],fileId[0]);
                               }
                               ProDeModel proDeModel=new ProDeModel(proId,dids[i],berIds[m],fileId[0],1,new Date());
                               fileService.save(proDeModel);
                           }
                }
            }*/
            return Result.success();
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.BUSINESS_ERROR, e.getMessage()));
        }
    }


    /**
     * 操作员页面查询工艺卡
     * @return
     */
    @RequiresRoles("普通员工")
    @RequestMapping("/getFileMessage")
    public Result getFileMessage(int lineId,int dId,int berId){
       List<String> stringList=new ArrayList<>();
        //查询分配记录去文件-型号表里查询对应的文件信息
        List<Distribute> distributes=distributeService.findByLineIdAndDeviceIdAndModelAndEffectAndDate(lineId,dId,berId,1,new Date());
        for(Distribute distribute:distributes) {
            if (distribute == null) {
                return Result.error(new CodeMsg(ReturnCode.DATA_NOT_FOUND, "未找到分配记录"));
            } else {
                    //获取文件id
                    String fids = distribute.getFid();
                    String[] fidArray=fids.split(",");
                    for(int i=0;i<fidArray.length;i++) {
                        if(fidArray[i]!=null&&!fidArray.equals("")) {
                            //查询文件信息
                            Files files = fileService.findById(Integer.parseInt(fidArray[i]));
                            stringList.add(files.getPath());
                        }
                    }
            }
        }
        return Result.success(stringList);
    }

    }
