package com.example.spring_hsqldb_rest_demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    
    @Id
    @Column
    private String userId;
    
    @Column(nullable = false)
    private String userName;
    
    @Column(nullable = true)
    private String userKana;
    
    @Column(nullable = false)
    private Boolean deleteFlg;
}
