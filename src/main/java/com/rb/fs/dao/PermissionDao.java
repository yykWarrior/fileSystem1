package com.rb.fs.dao;



import com.rb.fs.entity.Permission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PermissionDao extends CrudRepository<Permission,Integer> {
     @Query(value = "select  p.* from  permission p left join(select * from role_permission where r_id=(select r_id from user_role where u_id=(select id from usr where name='a'))) m\n" +
             "on p.id=m.p_id",nativeQuery = true)
     List<Permission> findByPname(String userName) ;
}
