package com.rb.fs.service;




import com.rb.fs.dto.DistributeDto;
import com.rb.fs.entity.Distribute;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface DistributeService {
    /**
     * 添加数据
     */
    String add(String proLineId, String deviceName, String modelName, Date startdate, Date enddate);

    /**
     * 查询所有
     * @return
     */
   // List<DistributeDto> findAll(int page, int limit);

   // int count();

    /**
     * 删除
     * @param parseInt
     */
    void delete(String parseInt);

    /**
     * 根据时间查询
     * @param page
     * @param limit
     * @param starttime
     * @param endtime
     * @return
     */
    //List<DistributeDto> findByDate(int page, int limit, Date starttime, Date endtime);

    /**
     * 根据时间段查询总数
     * @param startTime
     * @param endTime
     * @return
     */
   // int countByDate(Date startTime, Date endTime);

    /**
     * 根据生产线和设备id查询数据
     * @param lineId
     * @param deviceId
     * @return
     */
    List<Distribute> findByLineIdAndDeviceId(int lineId, int deviceId);

    /**
     * 根据设备，生产线和型号查询数据
     * @param lineId
     * @param deviceId
     * @param parseInt
     * @return
     */
    List<Distribute> findByLineIdAndDeviceIdAndModel(int lineId, int deviceId, int parseInt);

    void save(Distribute distribute);

    /**
     * 操作员页面查询工艺卡
     * @param lineId
     * @param deviceId
     * @param modelId
     * @param i
     * @param date
     * @return
     */
    List<Distribute> findByLineIdAndDeviceIdAndModelAndEffectAndDate(Integer lineId, int deviceId, int modelId, int i, Date date);

    /**
     * 根据分配获取型号
     * @param lineId
     * @param deviceId
     * @param i
     * @param date
     * @return
     */
    List<Distribute> findByLineIdAndDeviceIdAndEffectAndDate(int lineId, int deviceId, int i, Date date);

    /**
     * 多条件查询
     * @param distribute
     * @param page
     * @param limit
     * @param dateser
     * @return
     */
    List<DistributeDto> multiQuery(Distribute distribute, int page, int limit, String dateser);

    /**
     * 根据文件id和有效性查询
     * @param idss
     * @param i
     * @return
     */
    List<Distribute> findByFidAndEffect(String idss, int i);


    /**
     * 分配生产线
     * @param models
     * @param devices
     * @param dates
     * @param lineId
     */
    List<DistributeDto> disLine(String[] models, String[] devices, String[] dates, int[] lineId) throws ParseException;

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
    Page<Distribute> getPageUpgradeScheduleView(String dateser, String proline, String beartype, String dename, int page, int limit) throws ParseException;

    /**
     * 更新分配状态
     * @param id
     */
    void updateEffect(String id);
}
