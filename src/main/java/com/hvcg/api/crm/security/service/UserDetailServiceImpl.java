package com.hvcg.api.crm.security.service;

import com.hvcg.api.crm.entity.User;
import com.hvcg.api.crm.exception.NotFoundException;
import com.hvcg.api.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> optionalUser = this.userRepository.findByUsername(s, false);
        if (optionalUser.isPresent()) {
            return UserDetailImpl.build(optionalUser.get());

        }else{
            throw new NotFoundException("User not found");
        }
    }
}
