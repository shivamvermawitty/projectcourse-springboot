package com.projectcourse.projectcourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projectcourse.projectcourse.entity.User;
import com.projectcourse.projectcourse.entity.UserPrincipals;
import com.projectcourse.projectcourse.repository.UserRepository;
@Service
public class MyUserDetailsService implements UserDetailsService  {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user=userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));;
        if(user==null){
            throw new UsernameNotFoundException("No Such User Found");
        }
        return new UserPrincipals(user);
    }
    
}
