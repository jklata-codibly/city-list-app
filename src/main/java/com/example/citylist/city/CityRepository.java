package com.example.citylist.city;

import com.example.citylist.city.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CityRepository extends MongoRepository<City, String> {

    Page<City> getCitiesByName(String name, Pageable pageable);
}
