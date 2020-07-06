package com.rb.fs.controller;

import com.rb.fs.service.TeamService;
import com.rb.fs.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 团队控制器
 */
@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    /**
     * 批量添加团队
     * @param names
     * @return
     */
    @RequestMapping("/batchAdd")
    public Result batchAdd(String names){
        return teamService.batchAdd(names);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     * @Author yyk
     */
    @RequestMapping("/multiDel")
    public Result multiDel(String ids){
        return teamService.multiDel(ids);
    }
}
