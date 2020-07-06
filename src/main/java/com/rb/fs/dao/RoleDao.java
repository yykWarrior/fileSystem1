package com.rb.fs.dao;

import com.rb.fs.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleDao extends CrudRepository<Role,Integer> {
    @Query(value = "select * from role where id=(select r_id from user_role where u_id=(select id from usr where name=?1))",nativeQuery=true)
    List<Role> findByRname(String userName);
}
