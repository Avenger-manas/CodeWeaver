package com.dolpi.CodeWeaver.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AiClass {
    private String language;

    private String message="Return only code. Do not include any explanation or text outside code";

    private String prompt;


}
