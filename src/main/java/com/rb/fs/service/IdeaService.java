package com.rb.fs.service;

import com.rb.fs.entity.Idea;

import java.util.Date;
import java.util.List;

public interface IdeaService {
    /**
     * 插入建议信息
     * @param content
     * @param admin
     * @param date
     * @param filePath
     * @param i
     */
    void addIdea(String content, String admin, Date date, String filePath, int i);

    /**
     * 查询所有
     * @return
     */
    List<Idea> select();
}
