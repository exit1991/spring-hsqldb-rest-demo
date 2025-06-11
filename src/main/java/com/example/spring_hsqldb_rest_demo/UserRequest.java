package com.example.spring_hsqldb_rest_demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String userId;
    private String userName;
    private String userKana;
    private Boolean isDelete;
}
