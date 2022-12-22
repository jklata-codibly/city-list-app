package com.example.citylist.city;

import com.example.citylist.city.dto.CityDto;
import com.example.citylist.city.model.City;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class CityService {

    private final CityRepository cityRepository;

    Page<CityDto> getCities(String name, Pageable pageable) {
        if (!isNullOrBlank(name)){
            return getCitiesByName(name, pageable);
        }
        Page<City> all = cityRepository.findAll(pageable);
        return CityMapper.INSTANCE.entitiesPageToDtosPage(all, pageable);
    }

    private Page<CityDto> getCitiesByName(String name, Pageable pageable) {
        Page<City> citiesByName = cityRepository.getCitiesByName(name, pageable);
        return CityMapper.INSTANCE.entitiesPageToDtosPage(citiesByName, pageable);
    }


    Optional<CityDto> updateExistingCity(String id, String name, String photo) {
        Optional<City> cityOptional = cityRepository.findById(id);

        return cityOptional.map(cityFromDB -> cityRepository.save(City.builder()
                        .id(cityFromDB.getId())
                        .name(isNullOrBlank(name) ? cityFromDB.getName() : name)
                        .photo(isNullOrBlank(photo) ? cityFromDB.getPhoto() : photo)
                        .build()))
                .map(CityMapper.INSTANCE::entityToDto);
    }

    private boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }
}
