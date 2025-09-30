package com.thehecklers.aircraftpositions;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AircraftRepositor extends ReactiveCrudRepository<Aircraft, Long> {
}
