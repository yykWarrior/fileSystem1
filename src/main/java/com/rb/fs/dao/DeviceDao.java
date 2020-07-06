package com.rb.fs.dao;


import com.rb.fs.entity.Device;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface DeviceDao extends CrudRepository<Device, Integer>, JpaSpecificationExecutor<Device> {


    /**
     * 修改设备
     * @param deviceId
     * @param productLineId
     * @param process_name
     * @param d_type
     */
    @Query(value="update device set proline_id=?2,process_name=?3,d_type=?4 where id=?1",nativeQuery=true)
    @Transactional
    @Modifying
    void update(int deviceId,int productLineId,String process_name,String d_type);

    /**
     *  根据生产线id删除设备
     * @param id
     */
    @Query(value="delete from device where proline_id=?1",nativeQuery=true)
    @Transactional
    @Modifying
    void deleteByPid(int id);

    @Query(value="delete from device where id=?1",nativeQuery=true)
    @Transactional
    @Modifying
    void delete(int id);

    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @Query(value = "select * from device  limit ?2 OFFSET ?1",nativeQuery=true)
    List<Device> findAllByPage(int page,int limit);

    /**
     * 根据条件进行分页查询
     * @param processname
     * @param devicename
     * @param limit
     * @param page
     * @return
     */
    @Query(value = "select * from device where process_name like ?1 or d_type like ?2 limit ?3 OFFSET ?4",nativeQuery=true)
    List<Device> findAllByProcessNameAndDeviceName(String processname, String devicename, int limit, int page);

    @Query(value = "select count(*) from device where process_name like ?1 or d_type like ?2",nativeQuery=true)
    int countByName(String processname, String devicename);

    @Query(value = "select * from device where proline_id=?1",nativeQuery=true)
    List<Device> findByProlineId(int line);

    /**
     * 根据生产线查询出所有的设备
     * @param prolies
     * @return
     */
    @Query(value = "select * from device where proline_id=?1",nativeQuery=true)
    List<Device> getDeviceByProLine(Integer prolies);

    /**
     * 根据设备名称模糊查询
     * @param s
     * @return
     */
    @Query(value = "select * from device where d_type like ?1",nativeQuery=true)
    List<Device> findByDtypeLike(String s);

    /**
     *  根据名称模糊查询
     * @param s
     * @return
     */
    @Query(value = "select * from device where process_name like ?1",nativeQuery=true)
    List<Device> findByNameLike(String s);

    /**
     * 分页根据生产线id查询设备
     * @param page
     * @param limit
     * @param lineId
     * @return
     */
    @Query(value = "select * from device where proline_id=?3 limit ?2 OFFSET ?1",nativeQuery=true)
    List<Device> findDeviceByProlineId(int page, int limit, int lineId);

    /**
     * 根据生产线id查询总条数
     * @param lineId
     * @return
     */
    @Query(value = "select count(*) from device where proline_id=?1",nativeQuery=true)
    int getCountByLineId(int lineId);
}
