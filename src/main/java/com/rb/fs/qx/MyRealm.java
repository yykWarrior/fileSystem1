package com.rb.fs.qx;

import com.rb.fs.dao.PermissionDao;
import com.rb.fs.dao.RoleDao;
import com.rb.fs.dao.UserDao;
import com.rb.fs.dao.UserRoleDao;
import com.rb.fs.entity.Permission;
import com.rb.fs.entity.Role;
import com.rb.fs.entity.User;
import com.rb.fs.entity.UserRole;
import com.rb.fs.util.SessionUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName=(String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        Set<String> roles=new HashSet<String>();
        List<Role> rolesByUserName = roleDao.findByRname(userName);
        for(Role role:rolesByUserName) {
            roles.add(role.getRname());
        }
       /* List<Permission> permissionsByUserName = permissionDao.findByPname(userName);
        for(Permission permission:permissionsByUserName) {
            info.addStringPermission(permission.getPname());
        }*/
        info.setRoles(roles);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = token.getPrincipal().toString();
        User user = userDao.findByUsername(userName);
        Session session = SecurityUtils.getSubject().getSession();
        //把生产线id放入session
        session.setAttribute("line",user.getProline());
        //查询出用户对应的角色
        UserRole userRole=userRoleDao.findByUid(user.getId());
        //把角色id放入session
       session.setAttribute("role",userRole.getRid());

        if (user != null) {
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getName(), user.getPassword(), getName());
            return authcInfo;
        } else {
            return null;
        }
    }
}
