package com.example.citylist.city.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cities")
@Builder
@Data
public class City {

    @Id
    private String id;
    private String name;
    private String photo;
}
