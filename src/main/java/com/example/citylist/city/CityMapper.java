package com.example.citylist.city;

import com.example.citylist.city.dto.CityDto;
import com.example.citylist.city.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Mapper
interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    City dtoToEntity (CityDto dto);
    CityDto entityToDto (City entity);

    default Page<CityDto> entitiesPageToDtosPage(Page<City> dtosPage, Pageable pageable) {
        return Optional.ofNullable(dtosPage)
                .map(n -> n.stream()
                        .filter(Predicate.not(Objects::isNull))
                        .map(this::entityToDto)
                        .toList()
                ).map(n -> new PageImpl<>(n, pageable, dtosPage.getTotalElements()))
                .orElse(new PageImpl<>(Collections.emptyList(), pageable, 0));
    }

}
