package com.rb.fs.dao;


import com.rb.fs.entity.ProDeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ProDeModelDao extends CrudRepository<ProDeModel,Integer> , JpaSpecificationExecutor {

    /**
     * 根据生产线，设备，型号名称查询
     * @param proline
     * @param pro
     * @param model
     * @return
     */
    List<ProDeModel> findByProNameAndDeviceNameAndModelNameAndEffect(int proline, int pro, int model, int effect);

    /**
     * 修改存储文件为无效
     * @param proline
     * @param pro
     * @param model
     * @param oldeffect
     * @param neweffect
     */
    @Modifying
    @Transactional
    @Query(value = "update filetype set effect=?5 where f_id=?1 and d_id=?2 and t_id=?3 and effect=?4",nativeQuery = true)
    void update(String proline, String pro, String model, int oldeffect, int neweffect);

    /**
     * 根据生产线修改文件失效
     * @param id
     */
    @Modifying
    @Transactional
    @Query(value = "update filetype set effect=?3 where p_id=?1 and effect=?2",nativeQuery = true)
    void updateByPid(int id,int oldeffect,int neweffect);

    /**
     * 根据设备名称使生产线失效
     * @param id
     */
    @Modifying
    @Transactional
    @Query(value = "update filetype set effect=?3 where d_id=?1 and effect=?2",nativeQuery = true)
    void updateByDName(int id,int oldeffect,int neweffect);

    /**
     * 根据型号名称使生产线失效
     * @param id
     */
    @Modifying
    @Transactional
    @Query(value = "update filetype set effect=?3 where t_id=?1 and effect=?2",nativeQuery = true)
    void updateByMName(int id,int oldeffect,int neweffect);



    Page findAll(Specification var1, Pageable var2);

    /**
     * 根据条件查询数据
     * @param proId
     * @param did
     * @param mid
     * @param fid
     * @param effect
     * @return
     */
    ProDeModel findByProNameAndDeviceNameAndModelNameAndFNameAndEffect(int proId, int did, int mid, int fid, int effect);

    /**
     * 更新数据
     * @param proId
     * @param did
     * @param mid
     * @param fid
     * @param i
     */
    @Modifying
    @Transactional
    @Query(value = "update filetype set p_id=?1 ,d_id=?2,t_id=?3,f_id=?4,effect=?5 where effect=1",nativeQuery = true)
    void updateEffect(int proId, int did, int mid, int fid, int i);

    @Modifying
    @Transactional
    @Query(value = "update filetype set effect=0 where f_id=?1 and effect=1",nativeQuery = true)
    void deleteByFName(int fid);

    /**
     * 删除数据，更新数据失效
     * @param id
     * @param oldEffect
     * @param newEffect
     */
    @Modifying
    @Transactional
    @Query(value = "update filetype set effect=?3 where effect=?2 and id=?1",nativeQuery = true)
    void delete(int id, int oldEffect, int newEffect);

    /**
     * 根据生产线id查询文件
     * @param page
     * @param limit
     * @param lineId
     * @return
     */
    @Query(value = "select * from filetype where p_id=?3 and effect=1 limit ?2 OFFSET ?1",nativeQuery=true)
    List<ProDeModel> getFileByLine(int page, int limit, int lineId);

    /**
     * 根据生产线id查询总的条数
     * @param lineId
     * @return
     */
    @Query(value = "select count(*) from filetype where p_id=?1 and effect=1",nativeQuery=true)
    int countByLine(int lineId);
}
