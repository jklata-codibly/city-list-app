package com.example.citylist.city;

import com.example.citylist.city.dto.CityDto;
import com.example.citylist.city.model.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    void shouldPerformQueryWithoutFilteringByNullName(){
        //given
        Pageable pageable = PageRequest.of(0,1);
        //when
        cityService.getCities(null, pageable);
        //then
        verify(cityRepository, times(1)).findAll(pageable);
        verify(cityRepository, times(0)).getCitiesByName(anyString(), any(Pageable.class));
    }
    @Test
    void shouldPerformQueryWithoutFilteringByBlankName(){
        //given
        Pageable pageable = PageRequest.of(0,1);
        //when
        cityService.getCities("\n\n\n", pageable);
        //then
        verify(cityRepository, times(1)).findAll(pageable);
        verify(cityRepository, times(0)).getCitiesByName(anyString(), any(Pageable.class));
    }

    @Test
    void shouldPerformQueryWithFilteringByName(){
        //given
        Pageable pageable = PageRequest.of(0,1);
        //when
        cityService.getCities("Warsaw", pageable);
        //then
        verify(cityRepository, times(0)).findAll(pageable);
        verify(cityRepository, times(1)).getCitiesByName("Warsaw", pageable);
    }

    @Test
    void shouldReturnEmptyOptionalWhenNoRecordIdDBWithGivenId() {
        //given
        String id = "not_existing";
        given(cityRepository.findById(id)).willReturn(Optional.empty());
        //when
        Optional<CityDto> actual = cityService.updateExistingCity(id, "name", "photo");
        //then
        assertThat(actual).isNotPresent();
    }


    @ParameterizedTest
    @MethodSource("testParams")
    void shouldUpdateGivenValues(String givenName, String expectedName, String givenPhoto, String expectedPhoto) {
        //given
        String id = "id";
        String nameFromDB = "nameFromDB";
        String photoFromDB = "photoFromDB";

        given(cityRepository.findById(id)).willReturn(Optional.of(City.builder().id(id).name(nameFromDB).photo(photoFromDB).build()));
        City expected = City.builder()
                .id(id)
                .name(expectedName)
                .photo(expectedPhoto)
                .build();

        //when
        cityService.updateExistingCity(id, givenName, givenPhoto);
        //then
        verify(cityRepository, times(1)).save(expected);
    }

    private static Stream<Arguments> testParams() {
        return Stream.of(
                Arguments.of("newName", "newName", "newPhoto", "newPhoto"),
                Arguments.of("\n\n\n", "nameFromDB", "newPhoto", "newPhoto"),
                Arguments.of("", "nameFromDB", "newPhoto", "newPhoto"),
                Arguments.of(null, "nameFromDB", "newPhoto", "newPhoto"),
                Arguments.of("newName", "newName", "\n\n\n", "photoFromDB"),
                Arguments.of("newName", "newName", "", "photoFromDB"),
                Arguments.of("newName", "newName", null, "photoFromDB")
        );
    }
}
