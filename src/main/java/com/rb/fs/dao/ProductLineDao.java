package com.rb.fs.dao;

import com.rb.fs.dto.NameAnIdDto;
import com.rb.fs.entity.Device;
import com.rb.fs.entity.ProductLine;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface ProductLineDao extends  CrudRepository<ProductLine, Integer> {
    @Modifying
    @Transactional
    @Query(value="delete from product_line where id=?1",nativeQuery=true)
    void deleteById(int id);


    /**
     * 修改
     * @param id
     * @param name
     * @param date
     */
    @Modifying
    @Transactional
    @Query(value = "update  product_line set name=?2,creatdate=?3 where id=?1",nativeQuery=true)
    void updateById(int id, String name, Date date);


    /*@Query("delete from ProductLline where id=?1")
    void delete(int id);*/

    /**
     * 根据id查询
     * @param id
     * @return
     */
    ProductLine findById(int id);

    /**
     * 按名称分页模糊查询
     * @param page
     * @param limit
     * @param name
     * @return
     */
   @Query(value = "select * from product_line where name like ?3 limit ?2 OFFSET ?1 ",nativeQuery=true)
    List<ProductLine> findByNameLikePage(int page, int limit, String name);



    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @Query(value = "select * from product_line  order by id desc limit ?2 OFFSET ?1",nativeQuery=true)
    List<ProductLine> findAllByPage(int page, int limit);

    /**
     * 查询总条数
     * @return
     */
    @Query(value = "select count(*) from product_line",nativeQuery=true)
    long count();

    /**
     * 根据条件查询总条数
     * @param name
     * @return
     */
    @Query(value = "select count(*) from product_line where name like ?1",nativeQuery=true )
    int countByName(String name);

    /**
     * 按名称不分页模糊查询
     * @param s
     * @return
     */
    List<ProductLine> findByNameLike(String s);

    @Query(value = "select p.id lineid,p.name prolinename,d.process_name dname,d.id did,m.id mid,m.md_type tname from product_line p inner join device d on p.id=d.proline_id \n" +
            "inner join model m on p.id=m.proline_id\n" +
            "where p.id=?1 and d.id=?2 and m.id=?3",nativeQuery=true)
    NameAnIdDto findNameByPidAndDidAndMid(int lid, int did, int mid);
}
