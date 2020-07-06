package com.rb.fs.service;

import com.rb.fs.entity.Device;
import com.rb.fs.entity.ProductLine;

import java.util.List;

public interface ProductLineService {
    /**
     * 添加生产线
     * @param name
     */
    void addLine(String name);

    /**
     * 查询所有生产线
     * @return
     * @param page
     * @param limit
     */
    List<ProductLine> selectLine(int page, int limit);

    /**
     * 删除生产线
     * @param id
     */
    void delete(String id);

    /**
     * 修改
     * @param id
     * @param name
     */
    void update(int id, String name);


    /**
     * 根据生产线获取
     * @param prolies
     * @return
     */
    List<Device> getDeviceByProLine(Integer prolies);

    /**
     * 查询生产线
     * @param id
     * @return
     */
    ProductLine findById(int id);

    /**
     * 模糊查询
     *
     * @param page
     * @param limit
     * @param name
     * @return
     */
    List<ProductLine> selectByName(int page, int limit, String name);

    /**
     * 查询总条数
     * @return
     */
    int count();

    /**
     * 根据名称查询总条数
     * @param name
     * @return
     */
    int countByName(String name);

    /**
     * 不分页查询所有
     * @return
     */
    List<ProductLine> selectAll();

    /**
     * 根据名称模糊查询
     * @param proline
     * @return
     */
    List<ProductLine> findByLikeName(String proline);
}
