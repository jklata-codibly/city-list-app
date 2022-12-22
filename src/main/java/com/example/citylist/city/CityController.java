package com.example.citylist.city;

import com.example.citylist.city.dto.CityDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cities")
@Slf4j
class CityController {

    private final CityService cityService;

    CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PatchMapping("/{id}")
    ResponseEntity<CityDto> updateExisting(@PathVariable String id, @RequestParam(required = false) String name, @RequestParam(required = false) String photo) {
        Optional<CityDto> cityDto = cityService.updateExistingCity(id, name, photo);
        return cityDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    ResponseEntity<Page<CityDto>> getPageOfCities(@RequestParam(required = false) String name, Pageable pageable) {
        return new ResponseEntity<>(cityService.getCities(name, pageable), HttpStatus.OK);
    }


}
