package com.dolpi.CodeWeaver.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//import javax.validation.constraints.NotNull;
//
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String id;
    @NotNull
    private String username;

    @NotNull
    private String password;

    private List<String> roles;


}
