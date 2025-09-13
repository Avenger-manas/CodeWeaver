package com.dolpi.CodeWeaver.UserService;

import com.dolpi.CodeWeaver.Entity.User;
import com.dolpi.CodeWeaver.Repository.Userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;

@Service
public class Userservice {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
   private Userrepository userrepository;

    //crate new user
    public User createnewuser(User body){
        String encodepassword=passwordEncoder.encode(body.getPassword());
        body.setPassword(encodepassword);
         body.setRoles(Arrays.asList("USER"));
          User savedUser= userrepository.save(body);
//           return ResponseEntity.ok(savedUser);

        return savedUser;
    }

    //find user
    public User finduser(String username) {
        User user = userrepository.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found with username: " + username
            );
        }
        return user;

    }

    public boolean existsByUsername(String username) {
        return userrepository.findByUsername(username) !=null;
    }



}
