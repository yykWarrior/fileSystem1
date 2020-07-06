package com.rb.fs.service;

import com.rb.fs.util.Result;

public interface TeamService {
    /**
     * 批量添加团队
     * @param names
     * @return
     */
    Result batchAdd(String names);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Result multiDel(String ids);
}
