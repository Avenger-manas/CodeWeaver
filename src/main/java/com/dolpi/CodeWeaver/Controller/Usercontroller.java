package com.dolpi.CodeWeaver.Controller;

import com.dolpi.CodeWeaver.Entity.User;
import com.dolpi.CodeWeaver.JwtToken.jwttoken;
import com.dolpi.CodeWeaver.Repository.Userrepository;
import com.dolpi.CodeWeaver.UserService.EmailService;
import com.dolpi.CodeWeaver.UserService.MyUserDetails;
import com.dolpi.CodeWeaver.UserService.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/public")
public class Usercontroller {

    private String saveuser;

    @Autowired
    private jwttoken jwttoken;


    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Autowired
    private Userrepository userrepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetails myUserDetails;

    @Autowired
    EmailService emailService;

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    @Autowired
    Userservice userservice;
    @PostMapping("/sing-up")
    public ResponseEntity<?> createuser(@RequestBody User userbody){
       boolean exits=userservice.existsByUsername(userbody.getUsername());

       boolean check=pattern.matcher(userbody.getEmail()).matches();

       if(!check)  return ResponseEntity.status(HttpStatus.CONFLICT).body("User Email Incorrect Please Enter Valid Email");

       if(exits){
           return ResponseEntity.status(HttpStatus.CONFLICT).body("User Is Already Exits");
       }

       if(userrepository.existsByEmail(userbody.getEmail())){
           return ResponseEntity.status(HttpStatus.CONFLICT).body("This Email Is Already User Please Use New Email Address");
       }

        userservice.createnewuser(userbody);


       emailService.sendMail(userbody.getEmail(),"Hello "+userbody.getUsername(),"Congrats Succesfully Sing-Up In CodeWeaver");

        return new ResponseEntity<>("Congrats Succesfully Sing-Up In CodeWeaver",HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginuser(@RequestBody User user){

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

            UserDetails userDetails= myUserDetails.loadUserByUsername(user.getUsername());

            String jwt=jwttoken.generateToken(user.getUsername());

            return new ResponseEntity<>(jwt,HttpStatus.OK);


        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>("Incoorect Username Password",HttpStatus.BAD_REQUEST);
        }

    }


   @PostMapping("/forgat-password")
    public ResponseEntity<?> forgatpassword(@RequestBody  User user){
        try {
            User olduser= userrepository.findByUsername(user.getUsername());
            if(olduser==null){
                return new ResponseEntity<>("Please Enter Valid Username ",HttpStatus.NO_CONTENT);
            }

            olduser.setPassword(user.getPassword());

            userservice.createnewuser(olduser);

           return new ResponseEntity("Hi "+user.getUsername()+" Succesfully Upadate Password",HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



   }



}
