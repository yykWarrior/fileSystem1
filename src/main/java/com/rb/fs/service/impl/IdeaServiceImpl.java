package com.rb.fs.service.impl;

import com.rb.fs.dao.IdeaDao;
import com.rb.fs.entity.Idea;
import com.rb.fs.service.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
@Transactional
@Service
public class IdeaServiceImpl implements IdeaService {
    @Autowired
    private IdeaDao ideaDao;
    /**
     * 插入建议信息
     * @param content
     * @param admin
     * @param date
     * @param filePath
     * @param i
     */
    @Override
    public void addIdea(String content, String admin, Date date, String filePath, int i) {
        Idea idea=new Idea(content,admin,filePath,date,i,"");
        ideaDao.save(idea);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Idea> select() {
       return (List<Idea>) ideaDao.findAll();
    }
}
