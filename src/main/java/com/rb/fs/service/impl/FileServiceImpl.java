package com.rb.fs.service.impl;

import com.rb.fs.dao.DistributeDao;
import com.rb.fs.dao.FileDao;
import com.rb.fs.dao.ProDeModelDao;
import com.rb.fs.dto.FileDto;
import com.rb.fs.entity.Device;
import com.rb.fs.entity.Files;
import com.rb.fs.entity.Model;
import com.rb.fs.entity.ProDeModel;
import com.rb.fs.service.DeviceService;
import com.rb.fs.service.FileService;

import com.rb.fs.service.ModelService;
import com.rb.fs.service.ProDeModelService;
import com.rb.fs.util.CodeMsg;
import com.rb.fs.util.Result;
import com.rb.fs.util.ReturnCode;
import com.rb.fs.util.UtilForDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;
    @Autowired
    private ProDeModelDao proDeModelDao;
    @Autowired
    private DistributeDao distributeDao;
    @Autowired
    private FileService fileService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private ProDeModelService proDeModelService;
    /**
     * 添加文件以及文件和生成线，设备，型号的关系
     * @param fileDto
     * @return
     */
/*
    @Override
    public void add(FileDto fileDto,String path) {
        //查询文件是否存在
        //Files file=fileDao.findByNameAndCodeAndVersion(fileDto.getName(),fileDto.getCode(),fileDto.getVersion());
        Files fil=fileDao.findByCodeAndNameAndVersion(fileDto.getCode(),fileDto.getName(),fileDto.getVersion());
        if(fil==null) {
            //保存文件信息
            Files files = new Files(fileDto.getCode(), fileDto.getName(), fileDto.getVersion(), new Date(), 1, path);
            fileDao.save(files);
        }

        //保存文件和生产线，设备，型号之间的关系
        //获取所有生产线
        String[] prolines=fileDto.getProline_id().split(",");
        //所有被选中的设备
        String[] devices=fileDto.getDevice_id().split(",");
        //所有被选中的型号
        String[] models=fileDto.getModel_id().split(",");

        //遍历所有型号
        for(int j=0;j<models.length;j++){
            //遍历所有生产线
            for (int i=0;i<prolines.length;i++) {
                //遍历所有的设备
                for(int m=0;m<devices.length;m++){
                    //拆分获取生产线名称
                    String[] pros=devices[m].split("-");
                    if(prolines[i].equals(pros[1])){
                        //查询文件是否存在
                        ProDeModel proDeMo=proDeModelDao.findByProNameAndDeviceNameAndModelNameAndEffect(prolines[i],pros[0],models[j],1);
                        if(proDeMo!=null){
                            proDeModelDao.update(prolines[i],pros[0],models[j],1,0);
                        }
                        //保存新的文件
                        //ProDeModel proDeModel = new ProDeModel(prolines[i], pros[0], models[j], 1, path,new Date());
                        //proDeModelDao.save(proDeModel);

                    }
                }
            }
        }
    }
*/

    @Override
    public void add(FileDto fileDto, String path) {

    }

    /**
     * 分页查询所有文件信息
     * @param page
     * @param limit
     * @return
     */
   /* @Override
    public List selectAll(int page, int limit) {
         page = (page - 1) * limit;
        return fileDao.selectAll(page, limit);
    }*/

    /**
     * 查询总条数
     * @return
     */
    /*@Override
    public int count() {
        return (int) fileDao.count();
    }*/

    /**
     * 根据name查询
     * @param page
     * @param limit
     * @param fileNumber
     * @param versionNumber
     * @param filename
     * @return
     */
   /* @Override
    public List<Files> selectByName(int page, int limit, String fileNumber, String versionNumber, String filename) {
        page=(page-1)*limit;
        return fileDao.findByName(page,limit,filename,fileNumber,versionNumber);
    }*/

   /* @Override
    public int countBcyName(String filename, String versionNumber, String fileNumber) {
        return fileDao.countByName(filename,fileNumber,versionNumber);
    }*/

    /**
     * 删除
     * @param ids
     */
    @Override
    public Result delete(String ids) {
        String[] idss=ids.split(",");
        try {
            for(int i=0;i<idss.length;i++) {
                //if (idss[i] != null && !idss[i].equals("")) {
                if (!StringUtils.isBlank(idss[i])) {
                    //去查询分配记录是否有引用这个文件
                    //List<Distribute> distributeList = distributeDao.findByFidAndEffect(idss[i], 1);
                    //if (distributeList.size() == 0) {
                        //删除文件关系表里的数据
                        proDeModelDao.deleteByFName(Integer.parseInt(idss[i]));
                        fileDao.delete(Integer.parseInt(idss[i]),new Date());
                    //}else{
                        //return Result.error(new CodeMsg(ReturnCode.BUSINESS_ERROR,"请先删除文件对应的分配记录"));
                   // }
                }
            }
            return Result.success();
        } catch (NumberFormatException e) {
            return Result.error(new CodeMsg(ReturnCode.BUSINESS_ERROR,"删除失败"));
        }
    }

    /**
     * 更改文件信息
     * @param id
     * @param fil_num
     * @param ver_num
     */
    @Override
    public void update(int id, String fil_num, String ver_num) {
        fileDao.update(id,fil_num,ver_num);
    }

    /**
     * 文件与生产线，设备，型号建立关系
     */
    @Override
    public void save(int[] fileId, int[] dids, int[] berIds) {
        List<Integer> list=new ArrayList();
        for(int i=0;i<dids.length;i++){
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
                    for(int j=0;j<fileId.length;j++) {
                        ProDeModel proDeModel = new ProDeModel(proId, dids[i], berIds[m], fileId[j], 1, new Date());
                        proDeModelDao.save(proDeModel);
                    }
                }
            }
        }
        //proDeModelDao.save(proDeModel);
    }

    /**
     * 查询文件信息
     * @param fid
     * @return
     */
    @Override
    public Files findById(int fid) {
        return fileDao.findById(fid).get();
    }


    /**
     * 多条件查询
     * @param page
     * @param limit
     * @param fileNumber
     * @param versionNumber
     * @param filename
     * @return
     */
    public Page<Files> getPageUpgradeScheduleView(int page, int limit, String filename,String fileNumber,String versionNumber) {
        Integer effect=1;
        Specification<Files> querySpeci = new Specification<Files>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList();
                if(!StringUtils.isEmpty(fileNumber)) {
                    predicates.add(criteriaBuilder
                            .like(root.get("code"), "%" + fileNumber+ "%"));
                }
                if(!StringUtils.isEmpty(versionNumber)){
                    predicates.add(criteriaBuilder
                            .like(root.get("version"), "%" + versionNumber + "%"));
                }
                if(!StringUtils.isEmpty(filename)){
                    predicates.add(criteriaBuilder
                            .like(root.get("name"), "%" + filename + "%"));
                }
                if(null != effect){
                    predicates.add(criteriaBuilder.equal(root.get("effect"), effect));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        PageRequest pageRequest = new PageRequest(page-1, limit);
        return fileDao.findAll(querySpeci,pageRequest);
    }


}
