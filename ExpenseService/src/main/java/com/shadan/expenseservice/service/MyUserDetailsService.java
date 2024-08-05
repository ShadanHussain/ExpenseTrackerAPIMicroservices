package com.shadan.expenseservice.service;

import com.shadan.expenseservice.config.JwtFilter;
import com.shadan.expenseservice.feign.UserInterface;
import com.shadan.expenseservice.model.User;
import com.shadan.expenseservice.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserInterface userInterface;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String fullHeader = jwtFilter.getFullHeader();
        boolean checkUserInDB = userInterface.getUser(fullHeader);
        if(!checkUserInDB) {
            throw new UsernameNotFoundException("User not found");
        }
        String usernameInDB = jwtFilter.getUsernameLocal();
        return new UserPrincipal(new User(usernameInDB));
    }
}
