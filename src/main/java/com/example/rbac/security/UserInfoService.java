package com.example.rbac.security;

import com.example.rbac.db.DB;
import com.example.rbac.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserInfoService implements UserDetailsService {

    @Autowired
    private DB db;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = db.getUser(username);
        return userInfo.map(UserInfo::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }
}
