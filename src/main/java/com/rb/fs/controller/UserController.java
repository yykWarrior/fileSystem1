package com.rb.fs.controller;

import com.rb.fs.dto.ResultDto;
import com.rb.fs.entity.User;
import com.rb.fs.service.UserService;
import com.rb.fs.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;



    // 访问登录页
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    // 访问首页
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    // 登录处理

    @CrossOrigin
    @RequestMapping("/doLogin")
    public Result doLogin(String username, String password,String role) {
        // 使用Shiro编写认证处理
        // 1、获取Subject
        Subject subject = SecurityUtils.getSubject();

        // 3、执行登录
        try {
            // 2、封装用户数据
            UsernamePasswordToken token = new UsernamePasswordToken(username, Md5Util.twiceMd5(password));
            System.out.println(Md5Util.twiceMd5(password));
            // 登录成功
                subject.login(token);
            //从session中获取角色id
            Integer rId= (Integer) SessionUtil.getSession().getAttribute("role");
            String roleId=String.valueOf(rId);
            if(role.equals(roleId))
            return Result.success(roleId);
            else return Result.error(new CodeMsg(ReturnCode.USER_NOT_EXIST,"用户不存在！"));
        } catch (AuthenticationException exception) {
            // 返回错误信息
            //model.addAttribute("msg", "账号错误！");
            return Result.error(new CodeMsg(ReturnCode.USER_ACCOUNT_ERROR,"账号或密码错误！"));
        }
    }

    // 注销处理
    @RequestMapping("/doLogOut")
    public Result doLogout() {
        // 1、获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 2、执行注销
        try {
            subject.logout();
            return Result.success();
        } catch (Exception ex) {
            return Result.error(new CodeMsg(ReturnCode.BUSINESS_ERROR,ex.getMessage()));
        }
    }


    /**
     * 查询所有的用户
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/findByAllUser")
    public ResultDto findByAllUser(int page,int limit){
        List<User> userList=userService.findAll(page,limit);
        //查询总页数
        int count=userService.getPage();
        return new ResultDto(0,"",count,userList);
    }

    /**
     * 添加账号
     * @param proLine
     * @param name
     * @param password
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/add")
    public Result batchAdd(int[] proLine,String name,String password){
        try {
            userService.batchAdd(proLine,name,password);
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
    @RequiresRoles("管理员")
    @RequestMapping("/mulDel")
    public Result multiDel(String ids){
       return userService.multiDel(ids);
    }

    /**
     * 修改密码
     * @param id
     * @param password
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping("/update")
    public Result updatePassword(int id,String password){
       return userService.updatePassword(id,password);
    }

}
