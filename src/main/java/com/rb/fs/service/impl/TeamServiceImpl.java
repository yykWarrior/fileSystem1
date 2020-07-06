package com.rb.fs.service.impl;

import com.rb.fs.dao.TeamDao;
import com.rb.fs.entity.Team;
import com.rb.fs.service.TeamService;
import com.rb.fs.util.CodeMsg;
import com.rb.fs.util.Result;
import com.rb.fs.util.ReturnCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamDao teamDao;
    /**
     * 批量添加团队
     * @param names
     * @return
     */
    @Override
    public Result batchAdd(String names) {
        try {
            //拆分团队名称
            String[] nameArray=names.split(",");
            for(int i=0;i<nameArray.length;i++){
                if(StringUtils.isBlank(nameArray[i])) {
                    Team team=new Team(nameArray[i]);
                    teamDao.save(team);
                }
            }
            return Result.success();
        } catch (Exception e) {
           return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Override
    public Result multiDel(String ids) {
        String[] idArray=ids.split(",");
        for(int i=0;i<idArray.length;i++){

        }
        return null;
    }
}
