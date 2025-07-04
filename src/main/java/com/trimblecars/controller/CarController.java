package com.trimblecars.controller;


import com.trimblecars.model.Car;
import com.trimblecars.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    // Register a car by a car owner
    @PostMapping("/register")
    public ResponseEntity<Car> registerCar(@RequestBody Car car, @RequestParam Long ownerId) {
        Car savedCar = carService.registerCar(car, ownerId);
        return ResponseEntity.ok(savedCar);
    }

    // Get all cars
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    // Get cars by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Car>> getCarsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(carService.getCarsByStatus(status));
    }

    // Get car by ID
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Optional<Car> car = carService.getCarById(id);
        return car.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }
}