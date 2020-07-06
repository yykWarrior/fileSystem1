package com.rb.fs.service;

import com.rb.fs.entity.ProDeModel;
import com.rb.fs.entity.ReturnBean;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ProDeModelService {
    /**
     * 根据条件查询数据
     * @param proId
     * @param i
     * @param i1
     * @param parseInt
     * @param i2
     * @return
     */
    ProDeModel findByProNameAndDeviceNameAndModelNameAndFNameAndEffect(int proId, int i, int i1, int parseInt, int i2);

    /**
     * 更新数据
     * @param proId
     * @param i
     * @param i1
     * @param parseInt
     */
    void updateEffect(int proId, int i, int i1, int parseInt);

    /**
     * 根据条件查询
     * @param lineId
     * @param parseInt
     * @param parseInt1
     * @param i
     * @return
     */
    List<ProDeModel> findByProNameAndDeviceNameAndModelNameAndEffect(int lineId, int parseInt, int parseInt1, int i);

    /**
     * 根据文件id删除
     * @param fid
     */
    void deleteByFid(int fid);

    /**
     * 模糊分页查询文件关联关系
     * @param page
     * @param limit
     * @param file_id
     * @param typeName
     * @return
     */
    Page<ProDeModel> getPageUpgradeScheduleView(int page, int limit, int file_id,String typeName);

    /**
     * 批量删除
     * @param ids
     */
    void delete(String ids);

    /**
     * 根据生产线id查询文件
     * @param page
     * @param limit
     * @param lineId
     * @return
     */
    ReturnBean getFileByLine(int page, int limit, int lineId);
}
