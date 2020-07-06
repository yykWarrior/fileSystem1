package com.rb.fs.dao;

import com.rb.fs.entity.Idea;
import org.springframework.data.repository.CrudRepository;

public interface IdeaDao extends CrudRepository<Idea,Integer> {
}
