package com.rb.fs.service;

import com.rb.fs.entity.User;
import com.rb.fs.util.Result;

import java.util.List;

public interface UserService {
    /**
     * 查询所有用户
     * @return
     * @param page
     * @param limit
     */
    List<User> findAll(int page, int limit);

    /**
     * 查询总页数
     * @return
     */
    int getPage();

    /**
     * 添加账号
     * @param proLine
     * @param name
     * @param password
     */
    void batchAdd(int[] proLine, String name, String password);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Result multiDel(String ids);

    /**
     * 修改密码
     * @param id
     * @param password
     * @return
     */
    Result updatePassword(int id, String password);
}
