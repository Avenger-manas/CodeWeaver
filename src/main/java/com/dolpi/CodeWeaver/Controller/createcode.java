package com.dolpi.CodeWeaver.Controller;

import com.dolpi.CodeWeaver.Entity.AiClass;
import com.dolpi.CodeWeaver.Entity.User;
import com.dolpi.CodeWeaver.Repository.Userrepository;
import com.dolpi.CodeWeaver.UserService.KafkaConsumer;
import com.dolpi.CodeWeaver.UserService.KafkaProducer;
import com.dolpi.CodeWeaver.UserService.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/createcode")
public class createcode{

    @Autowired
    private Userservice userservice;

    @Autowired
   private Userrepository userrepository;

    @Autowired
    KafkaConsumer kafkaConsumer;


    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping("/codelogic")
    public void codeLogic(@RequestBody AiClass airequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
           // Flux<String>
            // Current authenticated user

//             ProjectInfoProperties.Build message string
            String s = airequest.getMessage()
                    + "\nLanguage: " + airequest.getLanguage()
                    + "\nPrompt: " + airequest.getPrompt();

           kafkaProducer.producer(s);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //getcode
    @GetMapping("/GetCode")
    public String consumes(){
        String str= kafkaConsumer.getCodes();
        if(str==null) return "Please Generate the Code";

        return str;
    }
//save project
    @PostMapping("/save-project")
    public ResponseEntity<?> savedproject(@RequestBody Map<String, String> requestBody){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null || !authentication.isAuthenticated()) {
                return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
            }
            String username = authentication.getName();

            String str=kafkaConsumer.getCodes();
            Object ResponseEntity;
            if(str==null) return new ResponseEntity<>("Please Create Project",HttpStatus.BAD_REQUEST);

            User user=userrepository.findByUsername(username);

            user.setMap(requestBody.toString(),str);

            userrepository.save(user);

            HashMap<String,String>hshmap=new HashMap<>();
            hshmap.put(requestBody.toString(),str);

            return new ResponseEntity<>(hshmap,HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>("Not Create",HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/getcode")
    public ResponseEntity<?> getcode(@RequestBody Map<String, String> requestBody){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
            }

            String username = authentication.getName();
            User user = userrepository.findByUsername(username);
            if(user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            HashMap<String,String> hshmap = user.getMap();
            if(hshmap == null) {
                return new ResponseEntity<>("Key Is Incorrect", HttpStatus.NO_CONTENT);
            }


            String data = hshmap.get(requestBody.toString());
            if(data==null){
                return new ResponseEntity<>("No data found for Key: "+requestBody,HttpStatus.BAD_REQUEST);
            }

            Map<String, String> response = new HashMap<>();
            response.put("your_data", data);

            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}