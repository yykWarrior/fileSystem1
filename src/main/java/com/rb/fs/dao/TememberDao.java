package com.rb.fs.dao;

import com.rb.fs.entity.Temember;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TememberDao extends CrudRepository<Temember,Integer> {
    /**
     * 根据名称模糊查询
     * @param responer
     * @return
     */
    List<Temember> findByNameLike(String responer);
}
