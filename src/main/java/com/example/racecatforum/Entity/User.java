package com.example.racecatforum.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private int userId;
    private String userName;
    private String userPass;
    private String userEmail;



}
