package com.example.citylist.city;

import com.example.citylist.city.model.City;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@Component
@Profile("csvImport")
@RequiredArgsConstructor
@Slf4j
public class CityDatabaseInitializer {

    private final CityRepository cityRepository;

    @Value("${city-list.csv.path}")
    private String csvPath;

    @Value("${city-list.database.drop-before-csv-import}")
    private boolean dropDB;

    private static final String[] HEADERS = {"id", "name", "photo"};

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void populateDBWithStubDataFromCSV() throws IOException {

        if (dropDB) {
            cityRepository.deleteAll();
        }

        Reader in = new FileReader(csvPath);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                .setHeader(HEADERS)
                .setSkipHeaderRecord(true)
                .build()
                .parse(in);

        for (CSVRecord row : records) {
            City city = City.builder()
                    .id(row.get("id"))
                    .name(row.get("name"))
                    .photo(row.get("photo"))
                    .build();
            cityRepository.save(city);

        }
    }
}
