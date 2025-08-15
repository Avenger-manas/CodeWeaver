package com.dolpi.CodeWeaver.Controller;

import com.dolpi.CodeWeaver.Entity.User;
import com.dolpi.CodeWeaver.UserService.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class Usercontroller {
    @Autowired
    Userservice userservice;
    @PostMapping("/craeteuser")
    public ResponseEntity<?> createuser(@RequestBody User userbody){
        userservice.createnewuser(userbody);
        return new ResponseEntity<>(userbody, HttpStatus.CREATED);
    }

}
