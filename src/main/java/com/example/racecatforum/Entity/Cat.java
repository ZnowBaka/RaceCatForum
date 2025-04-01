package com.example.racecatforum.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cat {
    private int catId;
    private String ownerName;
    private String catName;
    private int catAge;
    private String catGender;
    private String catDescription;
    private String catImage;
}
