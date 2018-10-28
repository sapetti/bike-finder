package com.bf.bikefinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bf.bikefinder.model.MyUserPrincipal;
import com.bf.bikefinder.model.User;
import com.bf.bikefinder.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository;
 
    @Override
    public UserDetails loadUserByUsername(String username) {
        
    	UserDetails userPrincipal = null;
    	User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
        	userPrincipal = new MyUserPrincipal(user);
        }
        
		return userPrincipal;
    }
}