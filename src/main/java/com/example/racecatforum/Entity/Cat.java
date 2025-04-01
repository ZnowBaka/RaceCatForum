package com.example.racecatforum.Entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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


