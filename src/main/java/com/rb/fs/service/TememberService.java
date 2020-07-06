package com.rb.fs.service;

import com.rb.fs.util.Result;

public interface TememberService {
    /**
     * 批量增加成员
     * @param members
     * @return
     */
     Result batchAdd(String members, int teamId);
}
