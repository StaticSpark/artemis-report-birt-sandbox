package com.funshion.artemis.account.service;

import com.funshion.artemis.account.entity.ArtemisUserDetail;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 13-5-21
 * Time: 下午9:39
 * To change this template use File | Settings | File Templates.
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException, DataAccessException {
//        return a user detail .
        Set<GrantedAuthority> authoritySet  = new HashSet<GrantedAuthority>();
//        authSet.add(new GrantedAuthorityImpl(authority.getPrefixedName()));
        UserDetails userDetails = new ArtemisUserDetail(s,authoritySet);
        return userDetails;
    }

}
