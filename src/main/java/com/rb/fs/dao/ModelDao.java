package com.rb.fs.dao;


import com.rb.fs.entity.Model;
import com.rb.fs.entity.ProductLine;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ModelDao extends CrudRepository<Model, Integer>, JpaSpecificationExecutor<Model> {

    /**
     * 删除型号
     * @param id
     */
    @Modifying
    @Transactional
    @Query(value = "delete from model where id=?1",nativeQuery = true)
    void delete(int id);

    /**
     * 删除生产线下所有型号
     * @param id
     */
    @Modifying
    @Transactional
    @Query(value = "delete from model where proline_id=?1",nativeQuery = true)
    void deleteByPid(int id);

    /**
     * 分页查询所有
     *
     * @param lineId
     * @param page
     * @param limit
     * @return
     */
    @Query(value = "select * from model where proline_id=?3 limit ?2 OFFSET ?1",nativeQuery = true)
    List<Model> selectPage( int page, int limit,int lineId);

    /**
     * 根据名称查询数据总数
     * @param modelname
     * @param lineId
     * @return
     */
    @Query(value = "select count(*) from (select * from model where proline_id=?3) t where t.md_name like ?2 or t.md_type like ?1",nativeQuery = true)
    int countByName(String typename,String modelname, int lineId);

    /**
     * 根据条件查询数据
     *
     * @param typename
     * @param modelname
     * @param lineId
     * @param page
     * @param limit
     * @return
     */
    @Query(value = "select * from (select * from model where proline_id=?5) t where t.md_name like ?2 or t.md_type like ?1 limit ?4 OFFSET ?3",nativeQuery = true)
    List<Model> selectByName(String typename, String modelname,  int page, int limit,int lineId);

    /**
     * 通过线id查询
     * @param lineId
     * @return
     */
    @Query(value = "select count(*) from model where proline_id=?1",nativeQuery = true)
    int count(int lineId);

    /**
     * 根据线id查询所有
     * @param lineid
     * @return
     */
    @Query(value = "select * from model where proline_id=?1",nativeQuery = true)
    List<Model> selectByLinid(int lineid);

    /**
     * 根据生产线id和id查询数据
     * @param proId
     * @param i
     * @return
     */
    @Query(value = "select * from model where proline_id=?1 and id=?2",nativeQuery = true)
    Model findByLineIdAndId(int proId, int i);

    /**
     * 不分页根据名称模糊查询
     * @param s
     * @return
     */
    @Query(value = "select * from model where md_type like ?1",nativeQuery = true)
    List<Model> findByTypeLike(String s);


}
