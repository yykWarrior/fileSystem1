package com.rb.fs.dao;

import com.rb.fs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface UserDao extends CrudRepository<User, Integer> {
 // 根据账号查询用户
    @Query(value = "SELECT * FROM usr WHERE name=?1", nativeQuery = true)
    User findByUsername( String username);


    /**
     * 分页查询
     * @return
     * @param page
     * @param limit
     */
    @Query(value = "select * from usr order by id limit ?2 OFFSET ?1", nativeQuery = true)
    List<User> findByPage(int page, int limit);

   User findByNameAndProline(String name, String proline);

   /**
    * 添加
    * @param name
    * @param password
    * @param date
    * @param pro
    */
   @Transactional
   @Modifying
   @Query(value = "insert into usr(name,password,createdate,proline) values(?1,?2,?3,?4)", nativeQuery = true)
   void insert(String name, String password, Date date, String pro);

   /**
    * 删除
    * @param s
    */
   @Transactional
   @Modifying
   @Query(value = "delete from usr where id=?1",nativeQuery = true)
   void delete(int s);

    /**
     * 修改密码
     * @param id
     * @param password
     */
    @Transactional
    @Modifying
    @Query(value = "update usr set password=?2 where id=?1",nativeQuery = true)
    void update(int id, String password);
}
