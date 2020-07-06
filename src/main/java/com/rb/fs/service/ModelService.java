package com.rb.fs.service;

import com.rb.fs.dto.PreModelDto;
import com.rb.fs.entity.Model;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ModelService {

    /**
     *
     * @param id
     * @return
     */
    void delete(String id);



    /**
     * 批量添加
     */
    void batchAdd(String[] modelNameArray, String[] modelTypeArray, int lineId);

    /**
     * 分页查询所有
     *
     * @param i
     * @param page
     * @param limit
     * @return
     */
    List<Model> selectAll(int i, int page, int limit);

    /**
     * 总数
     * @return
     * @param lineId
     */
    int count(int lineId);

    /**
     * 根据名称查询总数
     *
     * @param s
     * @param modelname
     * @param lineId
     * @return
     */
    int countByName(String s, String modelname, int lineId);

    /**
     * 根据名称查询数据
     *
     * @param s
     * @param modelname
     * @param i
     * @param page
     * @param limit
     * @return
     */
    //List<Model> selectByName(String s, String modelname, int i, int page, int limit);

    /**
     * 根据线id查询所有
     * @return
     * @param i
     */
    List<PreModelDto> selectByLinid(int i);

    /**
     * 根据生产线id和id查询数据
     * @param proId
     * @param i
     * @return
     */
    Model findByLineIdAndId(int proId, int i);

    /**
     * 根据id查询型号
     * @param tname
     * @return
     */
    Model findById(int tname);

    /**
     * 多条件查询
     * @param page
     * @param limit
     * @param processname
     * @param devicename
     * @return
     */
    Page<Model> getPageUpgradeScheduleView(int page, int limit, String processname, String devicename,Integer lineId) ;

    /**
     * 根据类型模糊查询
     * @param beartype
     * @return
     */
    List<Model> findByTypeLike(String beartype);
}
