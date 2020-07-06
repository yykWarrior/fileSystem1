package com.rb.fs.service.impl;

import com.rb.fs.dao.ProductLineDao;
import com.rb.fs.dao.UserDao;
import com.rb.fs.dao.UserRoleDao;
import com.rb.fs.entity.ProductLine;
import com.rb.fs.entity.User;
import com.rb.fs.entity.UserRole;
import com.rb.fs.service.UserService;
import com.rb.fs.util.CodeMsg;
import com.rb.fs.util.Md5Util;
import com.rb.fs.util.Result;
import com.rb.fs.util.ReturnCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductLineDao productLineDao;
    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 查询所有用户
     * @return
     * @param page
     * @param limit
     */
    @Override
    public List<User> findAll(int page, int limit) {
        List<User> users=new ArrayList<>();
        User user1=null;
        List<User> userList=userDao.findByPage((page-1)*limit,limit);
        for(User user:userList){
            if(!StringUtils.isBlank(user.getProline())) {
                String pname = "";
                String[] pids = user.getProline().split(",");
                for (int i = 0; i < pids.length; i++) {
                    ProductLine productLine = productLineDao.findById(Integer.parseInt(pids[i]));
                    if (productLine == null) {
                        pname += null + ",";
                    } else {
                        pname += productLine.getName()+",";
                    }
                    user1 = new User(user.getId(),user.getName(), user.getPassword(), user.getCreatedate(), pname, user.getRemark());

                }
            }else{
                user1 = new User(user.getId(),user.getName(), user.getPassword(), user.getCreatedate(), null, user.getRemark());
            }
            users.add(user1);

        }
        return  users;
    }

    /**
     * 查询总页数
     * @return
     */
    @Override
    public int getPage() {
        return (int)userDao.count();
    }

    /**
     * 添加账号
     * @param proLine
     * @param name
     * @param password
     */
    @Transactional
    @Override
    public void batchAdd(int[] proLine, String name, String password) {
        String pro="";
        for(int i=0;i<proLine.length;i++){
            pro+=proLine[i]+",";
        }
        User user=new User(name, Md5Util.twiceMd5(password),new Date(),pro,"");
        userDao.insert(name, Md5Util.twiceMd5(password),new Date(),pro);
       // userDao.save(user);
        User user1=userDao.findByNameAndProline(user.getName(),user.getProline());
        //添加角色
        userRoleDao.save(new UserRole(user1.getId(),2,new Date(),""));
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public Result multiDel(String ids) {
        try {
            String[] idArray=ids.split(",");
            for(int i=0;i<idArray.length;i++){
                //删除
                userDao.delete(Integer.parseInt(idArray[i]));
                //删除用户对应的角色
                userRoleDao.deleteByUId(Integer.parseInt(idArray[i]));
            }
            return Result.success();
        } catch (NumberFormatException e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }
    }

    /**
     * 修改密码
     * @param id
     * @param password
     * @return
     */
    @Override
    public Result updatePassword(int id, String password) {
        try {
            userDao.update(id,Md5Util.twiceMd5(password));
            return Result.success();
        } catch (Exception e) {
            return Result.error(new CodeMsg(ReturnCode.DATA_IS_WRONG,e.getMessage()));
        }
    }
}