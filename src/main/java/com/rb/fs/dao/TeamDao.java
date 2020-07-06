package com.rb.fs.dao;

import com.rb.fs.entity.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TeamDao extends CrudRepository<Team,Integer> {

    /**
     * 根据名称模糊查询
     * @param teamName
     * @return
     */
    List<Team> findByNameLike(String teamName);
}
