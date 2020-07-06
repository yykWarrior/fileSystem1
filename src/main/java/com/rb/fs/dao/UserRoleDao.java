package com.rb.fs.dao;

import com.rb.fs.entity.UserRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * 用户角色表
 */
public interface UserRoleDao extends CrudRepository<UserRole,Integer> {

    /**
     * 根据用户id查询角色
     * @param id
     * @return
     */
    UserRole findByUid(int id);

    /**
     * 删除
     * @param parseInt
     */
    @Transactional
    @Modifying
    @Query(value = "delete from user_role where u_id=?1",nativeQuery = true)
    void deleteByUId(int parseInt);
}
