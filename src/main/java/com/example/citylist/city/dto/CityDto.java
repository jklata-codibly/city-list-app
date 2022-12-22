package com.example.citylist.city.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityDto {
    private String id;
    private String name;
    private String photo;
}
