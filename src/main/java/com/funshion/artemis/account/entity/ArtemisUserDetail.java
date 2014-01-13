package com.funshion.artemis.account.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 13-5-21
 * Time: 下午9:56
 * To change this template use File | Settings | File Templates.
 */
public class ArtemisUserDetail extends User {
    String userName;
    String password;
    String enabled;
    String accountNonExpired;


    public ArtemisUserDetail (String username, Collection<? extends GrantedAuthority> authorities){
        this(username,"password",true,true,true,true,authorities);
    }

    public ArtemisUserDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userName=username;
        this.password=password;
    }
}
