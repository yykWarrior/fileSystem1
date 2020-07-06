package com.rb.fs.service.impl;

import com.rb.fs.dao.TememberDao;
import com.rb.fs.entity.Temember;
import com.rb.fs.service.TememberService;
import com.rb.fs.util.CodeMsg;
import com.rb.fs.util.Result;
import com.rb.fs.util.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TememberServiceImpl implements TememberService {
    @Autowired
    private TememberDao tememberDao;

    /**
     * 批量增加成员
     * @param members
     * @return
     */
    @Override
    public Result batchAdd(String members,int teamId) {
        int leader=0;
        try {
            String[] memberArray=members.split(",");
            for(int i=0;i<memberArray.length;i++){
                String[] member=memberArray[i].split("-");
                if(member[0].equals("1")){
                  leader=1;
                }
                Temember temember=new Temember(member[1],member[0],teamId,leader);
                tememberDao.save(temember);
            }
            return Result.success();
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }

    }
}
