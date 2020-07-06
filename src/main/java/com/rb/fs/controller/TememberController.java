package com.rb.fs.controller;

import com.rb.fs.service.TememberService;
import com.rb.fs.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 团队成员控制器
 */
@RestController
@RequestMapping("/temember")
public class TememberController {
    @Autowired
    private TememberService tememberService;

    /**
     * 批量增加成员
     * @param members
     * @return
     */
    @RequestMapping("/batchAdd")
    public Result batchAdd(String members,int teamId){
        return tememberService.batchAdd(members,teamId);
    }


}
