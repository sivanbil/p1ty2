package org.example.dinner.oauth2.server.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dinner.commons.model.domain.SignInIdentity;
import org.example.dinner.commons.model.pojo.DinnerUser;
import org.example.dinner.commons.utils.AssertUtil;
import org.example.dinner.oauth2.server.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(">>>>loadUserByUsername, {}", username);
        if (AssertUtil.isEmpty(username)) {
            throw new UsernameNotFoundException("请输入正确的用户名");
        }
        DinnerUser user = userMapper.selectByAccountInfo(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误，请重新输入");
        }
        SignInIdentity signInIdentity = new SignInIdentity();
        BeanUtils.copyProperties(user, signInIdentity);
        log.info(">>>>user, {}", user);

        return signInIdentity;
    }
}
