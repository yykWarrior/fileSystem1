package com.rb.fs.service.impl;

import com.rb.fs.dao.DeviceDao;
import com.rb.fs.dao.DistributeDao;
import com.rb.fs.dao.ProDeModelDao;
import com.rb.fs.dao.ProductLineDao;
import com.rb.fs.dto.DeLineDto;
import com.rb.fs.entity.Device;
import com.rb.fs.entity.PageBean;
import com.rb.fs.entity.ProductLine;
import com.rb.fs.entity.ReturnBean;
import com.rb.fs.service.DeviceService;

import com.rb.fs.util.CodeMsg;
import com.rb.fs.util.JudgeIsNull;
import com.rb.fs.util.Result;
import com.rb.fs.util.ReturnCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Transactional
@Service
public class DeviceServiceImpl  implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private ProductLineDao productLineDao;
    @Autowired
    private ProDeModelDao proDeModelDao;
    @Autowired
    private DistributeDao distributeDao;
    /**
     * 添加设备
     * @param productLineId
     * @param process_name
     * @param d_type
     * @return
     */
    @Override
    public Result add(int productLineId, String process_name, String d_type) {
        try {
            Device device=new Device(process_name,d_type,productLineId);
            deviceDao.save(device);
            return Result.success();
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }
    }

    /**
     * 修改设备
     * @return
     */
    @Override
    public Result update(int deviceId,int productLineId,String process_name,String d_type) {
        try {
            deviceDao.update(deviceId,productLineId,process_name,d_type);
            return Result.success();
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }
    }

    /**
     * 查询所有设备
     * @return
     */
   /* @Override
    public List<DeLineDto> selectAll(int page,int limit) {
        page=(page-1)*limit;
        List<Device> deviceList= new ArrayList<>();
        //查询出所有设备
        deviceList =  deviceDao.findAllByPage(page,limit);
        List<DeLineDto> deLineDtosList=setDeviceToDelineDto(deviceList);
        return deLineDtosList;
    }*/

    /**
     * 批量添加设备
     * @return
     */
    @Override
    public void batchAdd(String[] processNameArray, String[] deviceNameArray, int lineId) {
        for(int i=0;i<processNameArray.length;i++){
           Device device=new Device(JudgeIsNull.setStringToNull(processNameArray[i]),JudgeIsNull.setStringToNull(deviceNameArray[i]),lineId);
            deviceDao.save(device);
        }
    }

    /**
     * 删除设备
     * @param ids
     * @return
     */
    @Override
    public void delete(String ids) {
        String[] idss= ids.split(",");
            for (int i = 0; i < idss.length; i++) {
                if (idss[i] != null && !idss[i].equals("")) {
                    int id=Integer.parseInt(idss[i]);
                    //设置文件关系表中这个设备信息失效
                    proDeModelDao.updateByDName(id,1,0);
                    //设置分配表里与此设备相关的数据失效
                    distributeDao.updateEffectByDid(1,0,id);
                    deviceDao.delete(id);
                }
            }
    }
//               //设置文件关系表中这个设备信息失效
//        try {
//            proDeModelDao.updateByDName(id,1,0);
//            deviceDao.delete(id);
//            return Result.success();
//        } catch (Exception e) {
//            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
//        }



    /**
     * 查询总条数
     * @return
     */
   /* @Override
    public int count() {
        return (int) deviceDao.count();
    }*/

    /**
     * 根据设备名称和工序名称查询
     * @param processname
     * @param devicename
     * @param limit
     * @param page
     * @return
     */
  /*  @Override
    public List<DeLineDto> findAllByProcessNameAndDeviceName(String processname, String devicename, int limit, int page) {
        if(processname!=null&&!processname.equals("")){
            processname="%"+processname+"%";
        }
        if(devicename!=null&&!devicename.equals("")){
            devicename="%"+devicename+"%";
        }
        page=(page-1)*limit;
        List<Device> deviceList=deviceDao.findAllByProcessNameAndDeviceName(processname,devicename,limit,page);
        List<DeLineDto> deLineDtosList=setDeviceToDelineDto(deviceList);
        return deLineDtosList;
    }*/

    /**
     * 查询符合条件的总条数
     * @param processname
     * @param devicename
     * @return
     */
   /* @Override
    public int countByName(String processname, String devicename) {
        if(processname!=null&&!processname.equals("")){
            processname="%"+processname+"%";
        }
        if(devicename!=null&&!devicename.equals("")){
            devicename="%"+devicename+"%";
        }
       return deviceDao.countByName(processname,devicename);

    }*/

    /**
     * 根据线id查询所有
     * @return
     * @param line
     */
    @Override
    public List<DeLineDto> selectByLineId(int line) {
        List<Device> deviceList=new ArrayList<>();
        List<DeLineDto> deLineDtoList=new ArrayList<>();
        deviceList=deviceDao.findByProlineId(line);
        for(Device deLineDto:deviceList){
            ProductLine productLine= productLineDao.findById(deLineDto.getProline_id());
            DeLineDto deLineDto1=new DeLineDto(deLineDto.getId(),productLine.getName()+"-"+deLineDto.getProcess_name(),deLineDto.getD_type(),deLineDto.getProline_id(),productLine.getName());
            deLineDtoList.add(deLineDto1);
            //deLineDto.setProcess_name(productLine.getName()+"-"+deLineDto.getProcess_name());
        }
        return  deLineDtoList;
    }


    /**
     * 根据id查找
     * @param i
     * @return
     */
    @Override
    public Device findById(int i) {
        return deviceDao.findById(i).get();
    }

    /**
     * device转换成封装类型
     * @param deviceList
     * @return
     */
    public List<DeLineDto> setDeviceToDelineDto(List<Device> deviceList){
        List<DeLineDto> deLineDtosList= new ArrayList<>();
        for(Device device:deviceList){
            //查询出所有生产线
            ProductLine productLine=productLineDao.findById(device.getProline_id());
            //生成封装对象
            DeLineDto deLineDto=new DeLineDto(device.getId(),device.getProcess_name(),device.getD_type(),device.getProline_id(),productLine.getName());
            deLineDtosList.add(deLineDto);
        }
        return deLineDtosList;
    }

    /**
     * 多条件搜索
     * @param page
     * @param limit
     * @param processname
     * @param devicename
     * @param lineName
     * @return
     */
    public Page<Device> getPageUpgradeScheduleView(int page, int limit, String processname, String devicename, String lineName) {
        Specification<Device> querySpeci = new Specification<Device>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList();
                List<ProductLine> productLineList=new ArrayList<>();
                if(JudgeIsNull.IsOrNull(lineName)) {
                    productLineList=productLineDao.findByNameLike("%"+lineName+"%");
                }
                if(productLineList.size()!=0){
                    if(productLineList.size()>0&&productLineList!=null){
                        CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("proline_id"));
                        for (int i = 0; i < productLineList.size(); i++) {
                            in.value(productLineList.get(i).getId());//存入值
                        }
                        predicates.add(in);
                    }
                    if (!StringUtils.isEmpty(processname)) {
                        predicates.add(criteriaBuilder
                                .like(root.get("process_name"), "%" + processname + "%"));
                    }
                    if (!StringUtils.isEmpty(devicename)) {
                        predicates.add(criteriaBuilder
                                .like(root.get("d_type"), "%" + devicename + "%"));
                    }
                }else {

                    if (!StringUtils.isEmpty(processname)) {
                        predicates.add(criteriaBuilder
                                .like(root.get("process_name"), "%" + processname + "%"));
                    }
                    if (!StringUtils.isEmpty(devicename)) {
                        predicates.add(criteriaBuilder
                                .like(root.get("d_type"), "%" + devicename + "%"));
                    }
                }
              /*  if(null != upgradeViewSelector.getOver()){
                    predicates.add(criteriaBuilder.equal(root.get("over"), upgradeViewSelector.getOver()));
                }
                if(null != upgradeViewSelector.getFlag()){
                    predicates.add(criteriaBuilder.equal(root.get("flag"), upgradeViewSelector.getFlag()));
                }*/
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                /*criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id").as(Integer.class)));
                return criteriaQuery.getRestriction();*/
            }
        };
        PageRequest pageRequest = new PageRequest(page-1, limit);
        // return deviceDao.findAll(querySpeci,pageRequest);
        return deviceDao.findAll(querySpeci,pageRequest);
    }

    /**
     * 根据设备名称模糊查询
     * @param dename
     * @return
     */
    @Override
    public List<Device> findByDtype(String dename) {
        return deviceDao.findByDtypeLike("%"+dename+"%");
    }

    /**
     * 根据生产线id查询设备
     *
     * @param page
     * @param limit
     * @param lineId
     * @return
     */
    @Override
    public ReturnBean getDeviceByLine(int page, int limit, int lineId) {
        page=(page-1)*limit;
        List<Device> deviceList=deviceDao.findDeviceByProlineId(page,limit,lineId);
        int count=deviceDao.getCountByLineId(lineId);
        ReturnBean returnBean=new ReturnBean(deviceList,count);
        return returnBean;
    }


}
