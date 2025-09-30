package com.thehecklers.aircraftpositions;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PositionRetriever {
    private final AircraftRepositor repository;
    private final WebClient client = WebClient.create("http://localhost:7634");

    // Nuevo: sobrecarga sin argumentos para tests/controlador
    public Iterable<Aircraft> retrieveAircraftPositions() {
        return retrieveAircraftPositions("/aircraft");
    }

    Iterable<Aircraft> retrieveAircraftPositions(String endpoint) {
        repository.deleteAll();

        client.get()
                .uri((null != endpoint) ? endpoint : "")
                .retrieve()
                .bodyToFlux(Aircraft.class)
                .filter(ac -> ac.getReg() != null && !ac.getReg().isBlank())
                .toStream()
                .forEach(repository::save);

        return repository.findAll();
    }
}
