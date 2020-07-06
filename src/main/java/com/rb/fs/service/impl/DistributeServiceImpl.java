package com.rb.fs.service.impl;

import com.rb.fs.dao.*;
import com.rb.fs.dto.DistributeDto;
import com.rb.fs.dto.NameAnIdDto;
import com.rb.fs.entity.*;
import com.rb.fs.service.DistributeService;
import com.rb.fs.util.JudgeIsNull;
import com.rb.fs.util.UtilForDate;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class DistributeServiceImpl implements DistributeService {

    @Autowired
    private DistributeDao distributeDao;
    @Autowired
    private ProductLineDao productLineDao;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private ModelDao modelDao;
    @Autowired
    private ProDeModelDao proDeModelDao;

    @Override
    public String add(String proLineId, String deviceName, String modelName, Date startdate, Date enddate) {
        return null;
    }

    /**
     * 查询所有
     * @return
     */
   /* @Override
    public List<DistributeDto> findAll(int page, int limit) {
        page=(page-1)*limit;
        List<Distribute> distributes=  distributeDao.findAll(page,limit,1);
        List<DistributeDto> distributeDtos=distributeDtoToDistribute(distributes);
        return distributeDtos;
    }*/

   /* @Override
    public int count() {
        return  distributeDao.countByEffect(1);
    }
*/
    /**
     * 删除
     * @param ids
     */
    @Override
    public void delete(String ids) {
        String[] idss= ids.split(",");
        for (int i = 0; i < idss.length; i++) {
            //if (idss[i] != null && !idss[i].equals(""))
            if (!StringUtils.isBlank(idss[i]))
                distributeDao.deleteById(Integer.parseInt(idss[i]));
        }

    }

    /**
     * 根据时间查询
     * @param page
     * @param limit
     * @param startTime
     * @param endTime
     * @return
     */
   /* @Override
    public List<DistributeDto> findByDate(int page, int limit, Date startTime, Date endTime) {
        page=(page-1)*limit;
        List<Distribute> distributes=distributeDao.findByDate(page,limit,startTime,endTime,1);
        List<DistributeDto> distributeDtos= distributeDtoToDistribute(distributes);
        return distributeDtos;
    }*/

    /**
     * 根据时间段查询总数
     * @param startTime
     * @param endTime
     * @return
     */
   /* @Override
    public int countByDate(Date startTime, Date endTime) {
        return distributeDao.countByDate(startTime,endTime,1);
    }*/

    /**
     * 根据生产线和设备id查询数据
     * @param lineId
     * @param deviceId
     * @return
     */
    @Override
    public List<Distribute> findByLineIdAndDeviceId(int lineId, int deviceId) {
        return distributeDao.findByProlineNameAndDnameAndEffect(lineId,deviceId,1);
    }

    /**
     * 根据设备型号，生产线查询数据
     * @param lineId
     * @param deviceId
     * @param parseInt
     * @return
     */
    @Override
    public List<Distribute> findByLineIdAndDeviceIdAndModel(int lineId, int deviceId, int parseInt) {
        return distributeDao.findByProlineNameAndDnameAndTnameAndEffect(lineId,deviceId,parseInt,1);
    }

    @Override
    public void save(Distribute distribute) {
        distributeDao.save(distribute);

    }

    /**
     * 操作员页面查询工艺卡
     * @param lineId
     * @param deviceId
     * @param modelId
     * @param effect
     * @param date
     * @return
     */
    @Override
    public List<Distribute> findByLineIdAndDeviceIdAndModelAndEffectAndDate(Integer lineId, int deviceId, int modelId, int effect, Date date) {
        return distributeDao.findByProlineNameAndDnameAndTnameAndFidAndEffectAndDate(lineId,deviceId,modelId,effect,date);
    }

    /**
     * 根据分配获取型号
     * @param lineId
     * @param deviceId
     * @param i
     * @param date
     * @return
     */
    @Override
    public List<Distribute> findByLineIdAndDeviceIdAndEffectAndDate(int lineId, int deviceId, int i, Date date) {
        return distributeDao.findByLineIdAndDeviceIdAndEffectAndDate(lineId,deviceId,i,date);
    }

    @Override
    public List<DistributeDto> multiQuery(Distribute distribute, int page, int limit, String dateser) {
        return null;
    }

    /**
     * 根据文件id和有效性查询
     * @param idss
     * @param i
     * @return
     */
    @Override
    public List<Distribute> findByFidAndEffect(String idss, int i) {
        return distributeDao.findByFidAndEffect(Integer.parseInt(idss),1);
    }

    /**
     * 分配生产线
     * @param models
     * @param devices
     * @param dates
     * @param lineId
     */
    @Override
    public List<DistributeDto> disLine(String[] models, String[] devices, String[] dates, int[] lineId) throws ParseException {
        List<DistributeDto> distributeDtoList=new ArrayList<>();
        for(String device:devices){
            Date startTime= UtilForDate.StringToDate1(dates[0]);
            Date endTime=UtilForDate.StringToDate1(dates[1]);
            // distributeService.findByDateAndModel(startTime,endTime,lineId,Integer.parseInt(device),)
            for(String model:models){
                //查询文件id
                List<ProDeModel> proDeModels=proDeModelDao.findByProNameAndDeviceNameAndModelNameAndEffect(lineId[0],Integer.parseInt(device),Integer.parseInt(model),1);
                if(proDeModels.size()!=0) {
                    String fids="";
                    for(ProDeModel proDeModel:proDeModels) {
                        fids+=proDeModel.getfName()+",";
                    }
                    Distribute distribute = new Distribute(lineId[0], Integer.parseInt(device), Integer.parseInt(model), fids, new Date(), 1, startTime, endTime);
                    //设置以前的文件失效
                    distributeDao.setNoEffecct(lineId[0], Integer.parseInt(device), Integer.parseInt(model));
                    save(distribute);
                }else{
                   // NameAnIdDto nameAnIdDto=productLineDao.findNameByPidAndDidAndMid(lineId[0],Integer.parseInt(device), Integer.parseInt(model));
                    //根据生产线id查询生产线
                    String lineName=productLineDao.findById(lineId[0]).getName();
                    //根据设备id查询设备名称
                    String deviceName=deviceDao.findById(Integer.parseInt(device)).get().getProcess_name();
                    //根据型号id查询型号名称
                    String modelName=modelDao.findById(Integer.parseInt(model)).get().getType();
                    DistributeDto distributeDto=new DistributeDto(lineName,deviceName,modelName);
                   if(distributeDto!=null)
                    distributeDtoList.add(distributeDto);
                }
            }
        }
        return distributeDtoList;
    }

    /**
     * 模糊查询
     * @param dateser
     * @param proline
     * @param beartype
     * @param dename
     * @param page
     * @param limit
     * @return
     */
    @Override
    public  Page<Distribute> getPageUpgradeScheduleView(String dateser, String proline, String beartype, String dename, int page, int limit) throws ParseException {


        Specification<Distribute> querySpeci = new Specification<Distribute>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Date startTime=null;
                Date endTime=null;
                Integer effect=1;
                //Integer effect=1;
                if(JudgeIsNull.IsOrNull(dateser)) {
                    //获取一天的开始时间和结束时间
                    Date[] dates = new Date[0];
                    try {
                        dates = UtilForDate.StringToDate(dateser);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //开始时间
                    startTime = dates[0];
                    //结束时间
                    endTime = dates[1];
                }
                List<Predicate> predicates = new ArrayList();
                List<ProductLine> productLineList = new ArrayList<>();
                List<Model> modelList = new ArrayList<>();
                List<Device> deviceList = new ArrayList<>();
                if (JudgeIsNull.IsOrNull(proline)) {
                    productLineList = productLineDao.findByNameLike("%" + proline + "%");
                }

                if (productLineList.size() != 0) {
                    if (productLineList.size() > 0 && productLineList != null) {
                        CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("prolineName"));
                        for (int i = 0; i < productLineList.size(); i++) {
                            in.value(productLineList.get(i).getId());//存入值
                        }
                        predicates.add(in);
                    }
                }

                if (JudgeIsNull.IsOrNull(beartype)) {
                    modelList = modelDao.findByTypeLike("%" + beartype + "%");
                }

                if (modelList.size() != 0) {
                    if (modelList.size() > 0 && modelList != null) {
                        CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("tname"));
                        for (int i = 0; i < modelList.size(); i++) {
                            in.value(modelList.get(i).getId());//存入值
                        }
                        predicates.add(in);
                    }
                }

                if (JudgeIsNull.IsOrNull(dename)) {
                    deviceList = deviceDao.findByNameLike("%" + dename + "%");
                }

                if (deviceList.size() != 0) {
                    if (deviceList.size() > 0 && deviceList != null) {
                        CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("dname"));
                        for (int i = 0; i < deviceList.size(); i++) {
                            in.value(deviceList.get(i).getId());//存入值
                        }
                        predicates.add(in);
                    }
                }

                if (startTime != null && endTime != null) {
                    predicates.add(criteriaBuilder
                            .between(root.get("creatdate"), startTime, endTime));
                }

                if(effect!=null){
                    predicates.add(criteriaBuilder.equal(root.get("effect"), effect));

                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

            }
        };
        PageRequest pageRequest = new PageRequest(page-1, limit);
        // return deviceDao.findAll(querySpeci,pageRequest);

        return distributeDao.findAll(querySpeci,pageRequest);
       /* List<DistributeDto> distributeDtoList=distributeDtoToDistribute(distributePage.getContent());
        return distributeDtoList;*/

    }

    /**
     * 更新分配状态
     * @param id
     */
    @Override
    public void updateEffect(String id) {
        distributeDao.updateEffect(Integer.parseInt(id),0);
    }


}
