package com.example.spring_hsqldb_rest_demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessResultResponse {
    private Boolean result;
    private String message;
}
