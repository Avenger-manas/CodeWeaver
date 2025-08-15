package com.dolpi.CodeWeaver.UserService;

import com.dolpi.CodeWeaver.Entity.User;
import com.dolpi.CodeWeaver.Repository.Userrepository;
//import com.dolpi.CodeWeaver.model.User;
//import com.dolpi.CodeWeaver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private Userrepository userRep;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRep.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found: " + username);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // already encoded in DB
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }
}