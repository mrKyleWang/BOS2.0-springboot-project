package top.kylewang.bos.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import top.kylewang.bos.domain.system.Permission;
import top.kylewang.bos.domain.system.Role;
import top.kylewang.bos.domain.system.User;
import top.kylewang.bos.service.system.PermissionService;
import top.kylewang.bos.service.system.RoleService;
import top.kylewang.bos.service.system.UserService;

import java.util.List;

/**
 * @author Kyle.Wang
 * 2018/1/10 0010 15:48
 */
//@Service("BosRealm") 转由xml配置realm,来设置缓存
public class BosRealm extends AuthorizingRealm{

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;



    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        User user = userService.findByUsername(usernamePasswordToken.getUsername());
        if(user == null){
            // 用户不存在
            return null;
        }else{
            /*
             * 用户存在
             *  返回SimpleAuthenticationInfo :
             *      参数一 : 期望登录后, 保存在Subject中的信息
             *      参数二 : 密码, 如果返回为null, 说明用户不存在, 报用户名不存在异常
             *      参数三 : realm名称
             *  当返回用户密码时, securityManager安全管理器会自动比较返回密码和用户输入密码是否一致
             *  如果密码一致, 登录成功, 如果密码不一致, 报密码错误异常
             */
            return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
        }
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 根据当前登录用户查询对应角色和权限
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        // 调用service查询角色并授予当前用户
        List<Role> roleList = roleService.findByUser(user);
        for (Role role : roleList) {
            System.out.println(role.getKeyword());
            simpleAuthorizationInfo.addRole(role.getKeyword());
        }
        // 调用service查询权限并授予当前用户
        List<Permission> permissionList = permissionService.findByUser(user);
        for (Permission permission : permissionList) {
            System.out.println(permission.getKeyword());
            simpleAuthorizationInfo.addStringPermission(permission.getKeyword());
        }
        return simpleAuthorizationInfo;
    }

}
