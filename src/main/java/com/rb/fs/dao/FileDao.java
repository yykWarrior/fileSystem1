package com.rb.fs.dao;

import com.rb.fs.entity.Files;
import com.rb.fs.entity.Idea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface FileDao extends CrudRepository<Files, Integer> , JpaRepository<Files,Integer> , JpaSpecificationExecutor<Files> {

    /**
     * 根据名称，编码，版本查看文件是否存在
     * @param name
     * @param code
     * @param version
     * @return
     */
    Files findByNameAndCodeAndVersion(String name, String code, String version);

    @Query(value = "select * from files where name=?1 and code=?2 and version=?3",nativeQuery = true)
    Files findByCodeAndNameAndVersion(String code, String name, String version);

    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */

    @Query(value =  "select * from files  limit ?2 OFFSET ?1",nativeQuery=true)
    List<Files> selectAll(int page, int limit);

    /**
     * 根据条件分页查询
     * @param page
     * @param limit
     * @param filename
     * @param fileNumber
     * @param versioname
     * @return
     */
    @Query(value = "select * from files where name like ?3 or code like ?4 or version like ?5 limit ?2 OFFSET ?1 ",nativeQuery=true)
    List<Files> findByName(int page, int limit, String filename, String fileNumber, String versioname);

    @Query(value="select count(*) from files where name like ?1 or code like ?2 or version like ?3",nativeQuery=true)
    int countByName(String filename, String fileNumber, String s);

    /**
     *
     * 删除
     * @param idss
     * @param date
     */
    @Query(value = "update files set effect=0 ,updatetime=?2 where id=?1",nativeQuery=true)
    @Transactional
    @Modifying
    void delete(int idss, Date date);

    /**
     * 更改文件属性
     * @param id
     * @param fil_num
     * @param ver_num
     */
    @Query(value = "update files set code=?2,version=?3 where id=?1",nativeQuery=true)
    @Transactional
    @Modifying
    void update(int id, String fil_num, String ver_num);


    /**
     * 根据名称模糊查询
     * @param s
     * @return
     */
    List<Files> findByNameLike(String s);

    @Query(value = "select name from files where id=?1",nativeQuery=true)
    String findByIdGetName(int id);


}
