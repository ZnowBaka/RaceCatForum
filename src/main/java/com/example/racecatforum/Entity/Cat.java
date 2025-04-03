package com.example.racecatforum.Entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Cat {
    private int catId;
    private int ownerId;
    private String catName;
    private String catDescription;
    private String catGender;
    private String catImage;
    private int catAge;

}


