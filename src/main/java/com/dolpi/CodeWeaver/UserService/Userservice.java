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
    public ResponseEntity<?> createnewuser(User body){
        String encodepassword=passwordEncoder.encode(body.getPassword());
        body.setPassword(encodepassword);
         body.setRoles(Arrays.asList("USER"));
          User savedUser= userrepository.save(body);
           return ResponseEntity.ok(savedUser);
    }

    public User finduser(String username) {
        User user = userrepository.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found with username: " + username
            );
        }
        return user;

    }

//    public User finduser(String username){
//        User user = userrepository.findByUsername(username);
//        if (user == null) {
//            throw new RuntimeException("User not found with username: " + username);
//        }
//        return user;
//
//    }
}
