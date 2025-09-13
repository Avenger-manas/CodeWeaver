package com.dolpi.CodeWeaver.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//import javax.validation.constraints.NotNull;
//
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String id;
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    private HashMap<String,String>map=new HashMap<>();

    private List<String> roles;

    public void setMap(String projectname, String str) {
        map.put(projectname,str);
    }
}
