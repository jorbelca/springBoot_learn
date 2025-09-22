
package com.thehecklers.aircraftpositions;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thehecklers.aircraftpositions.Aircraft;
import com.thehecklers.aircraftpositions.AircraftRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class PositionRetriever {
    private final AircraftRepository repo;

    @Bean
    Consumer<List<Aircraft>> retrieveAircraftPositions() {
        return acList -> {
            repo.deleteAll();
            repo.saveAll(acList);
            repo.findAll().forEach(System.out::println);
        };
    }
}