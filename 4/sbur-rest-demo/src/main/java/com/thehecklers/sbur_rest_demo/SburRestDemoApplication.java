package com.thehecklers.sbur_rest_demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.annotation.PostConstruct;

import org.springframework.data.repository.CrudRepository;

@SpringBootApplication
public class SburRestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SburRestDemoApplication.class, args);
	}
}

@Entity
class Coffee {
	@Id
	private String id;
	private String name;

	@JsonCreator
	public Coffee(@JsonProperty("id") String id,
			@JsonProperty("name") String name) {
		this.id = id == null ? UUID.randomUUID().toString() : id;
		this.name = name;
	}

	public Coffee(String name) {
		this(UUID.randomUUID().toString(), name);
	}

	protected Coffee() {
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}


@Component
class DataLoader {
	private final CoffeeRepository coffeeRepository;

	public DataLoader(CoffeeRepository coffeeRepository) {
		this.coffeeRepository = coffeeRepository;
	}

	@PostConstruct
	private void loadData() {
		this.coffeeRepository.saveAll(List.of(
				new Coffee("Café Cereza"),
				new Coffee("Café Ganador"),
				new Coffee("Café Lareño"),
				new Coffee("Café Três Pontas")));
	}
}

@RestController
@RequestMapping("/coffees")
class RestApiDemoController {
	private final CoffeeRepository coffeeRepository;

	public RestApiDemoController(CoffeeRepository coffeeRepository) {
		this.coffeeRepository = coffeeRepository;
	}

	@GetMapping
	Iterable<Coffee> getCoffees() {
		return coffeeRepository.findAll();
	}

	@GetMapping("/{id}")
	Optional<Coffee> getCoffeeById(@PathVariable String id) {
		return coffeeRepository.findById(id);
	}

	@PostMapping
	Coffee postCofffee(@RequestBody Coffee coffee) {
		return coffeeRepository.save(coffee);
	}

	@PutMapping("/{id}")
	ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee) {
		return (coffeeRepository.existsById(id))
				? new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.OK)
				: new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	void deleteCoffee(@PathVariable String id) {
		coffeeRepository.deleteById(id);
	}
}

interface CoffeeRepository extends CrudRepository<Coffee, String> {

}
