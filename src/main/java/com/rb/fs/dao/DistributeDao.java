package com.rb.fs.dao;

import com.rb.fs.entity.Distribute;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


public interface DistributeDao extends CrudRepository<Distribute, Integer>, JpaSpecificationExecutor<Distribute> {
    /**
     * 根据条件查询分配
     * @param proLineId
     * @param deviceName
     * @param modelName
     * @param i
     * @return
     */
    @Query(value = "select * from disrecord where  proline_name=?1 and d_name=?2 and t_name=?3 and effect=?4 and startdate<=?5 and enddate>=?6",nativeQuery = true)
    Distribute findByProlineNameAndDnameAndTnameAndFidAndEffect(int proLineId, int deviceName, int modelName, int i, Date startdate, Date enddate);

    /**
     * 更改分配表中数据为无效
     * @param proLineId
     * @param deviceName
     * @param modelName
     * @param oldeffect
     * @param neweffect
     */
    @Modifying
    @Transactional
    @Query(value = "update disrecord set effect=?5 where proline_name=?1 and d_name=?2 and t_name=?3 and effect=?4",nativeQuery = true)

    //@Query(value = "update filetype set effect=?5 where f_id=?1 and d_id=?2 and t_id=?3 and effect=?4",nativeQuery = true)

    void update(String proLineId, String deviceName, String modelName, int oldeffect, int neweffect);

    /**
     * 分页查询所有
     * @param page
     * @param limit
     * @return
     */
    @Query(value = "select * from disrecord  where effect=?3 order by id desc limit ?2 OFFSET ?1",nativeQuery = true)
    List<Distribute> findAll(int page, int limit,int effect);

    /**
     * 根据时间段查询
     * @param page
     * @param limit
     * @param starttime
     * @param endtime
     * @param i
     * @return
     */
    @Query(value = "select * from disrecord where creatdate>=?3 and creatdate<=?4 and effect=?5  order by id asc limit ?2 OFFSET ?1",nativeQuery = true)
    List<Distribute> findByDate(int page, int limit, Date starttime, Date endtime, int i);

    /**
     * 根据时间段查询总数
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "select count(*) from disrecord where creatdate>=?1 and creatdate<=?2 and effect=?3",nativeQuery = true)
    int countByDate(Date startTime, Date endTime,int effect);

    /**
     * 根据设备和生产线查询数据
     * @param lineId
     * @param deviceId
     * @return
     */
    List<Distribute> findByProlineNameAndDnameAndEffect(int lineId, int deviceId,int effect);

    /**
     * 根据生产线，设备，型号查询数据
     * @param lineId
     * @param deviceId
     * @param parseInt
     * @param i
     * @return
     */
    List<Distribute> findByProlineNameAndDnameAndTnameAndEffect(int lineId, int deviceId, int parseInt, int i);

    /**
     * 操作员页面查询工艺卡
     * @param lineId
     * @param deviceId
     * @param modelId
     * @param effect
     * @param date
     * @return
     */
    @Query(value = "select * from disrecord where proline_id=?1 and dev_id=?2 and t_id=?3 and effect=?4 and startdate<?5 and enddate>?5",nativeQuery = true)
    List<Distribute> findByProlineNameAndDnameAndTnameAndFidAndEffectAndDate(Integer lineId, int deviceId, int modelId, int effect, Date date);

    /**
     * 根据分配获取型号
     * @param lineId
     * @param deviceId
     * @param i
     * @param date
     * @return
     */
    @Query(value = "select * from disrecord where proline_id=?1 and dev_id=?2 and effect=?3 and startdate<?4 and enddate>?4",nativeQuery = true)
    List<Distribute> findByLineIdAndDeviceIdAndEffectAndDate(int lineId, int deviceId, int i, Date date);

    /**
     * 根据文件id和有效性查询
     * @param idss
     * @param i
     * @return
     */
    List<Distribute> findByFidAndEffect(int idss, int i);

    /**
     * 通过线id设置分配记录失效
     * @param oldEffect
     * @param newEffect
     * @param id
     */
    @Transactional
    @Modifying
    @Query(value = "update disrecord set effect=?2 where effect=?1 and proline_id=?3",nativeQuery = true)
    void updateEffectByPid(int oldEffect, int newEffect, int id);

    /**
     * 查出所有有效的数据
     * @param effect
     * @return
     */
    @Query(value = "select count (*) from disrecord where effect=?1",nativeQuery = true)
    int countByEffect(int effect);

    /**
     * 设置分配表里与此设备相关的数据失效
     * @param oldEffect
     * @param newEffect
     * @param id
     */
    @Transactional
    @Modifying
    @Query(value = "update disrecord set effect=?2 where effect=?1 and dev_id=?3",nativeQuery = true)
    void updateEffectByDid(int oldEffect, int newEffect, int id);

    /**
     * 设置分配表里与此型号相关的数据失效
     * @param oldEffect
     * @param newEffect
     * @param id
     */
    @Transactional
    @Modifying
    @Query(value = "update disrecord set effect=?2 where effect=?1 and t_id=?3",nativeQuery = true)
    void updateEffectByTid(int oldEffect, int newEffect, int id);

    /**
     * 设置文件失效
     * @param i
     * @param parseInt
     * @param parseInt1
     */
    @Transactional
    @Modifying
    @Query(value = "update disrecord set effect=0 where proline_id=?1 and dev_id=?2 and t_id=?3 and effect=1",nativeQuery = true)
    void setNoEffecct(int i, int parseInt, int parseInt1);

    /**
     * 更新分配状态
     * @param id
     * @param effect
     */
    @Transactional
    @Modifying
    @Query(value = "update disrecord set effect=?2 where id=?1",nativeQuery = true)
    void updateEffect(@Param("id") int id, @Param("effect") int effect);
}
