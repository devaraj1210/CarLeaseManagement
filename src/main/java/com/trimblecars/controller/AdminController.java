package com.trimblecars.controller;


import com.trimblecars.model.Car;
import com.trimblecars.model.Lease;
import com.trimblecars.model.User;
import com.trimblecars.services.CarService;
import com.trimblecars.services.LeaseService;
import com.trimblecars.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final CarService carService;
    private final LeaseService leaseService;

    @Autowired
    public AdminController(UserService userService, CarService carService, LeaseService leaseService) {
        this.userService = userService;
        this.carService = carService;
        this.leaseService = leaseService;
    }

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get all cars
    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    // Get all leases
    @GetMapping("/leases")
    public ResponseEntity<List<Lease>> getAllLeases() {
        return ResponseEntity.ok(leaseService.getAllLeases());
    }

    // Admin can update car status
    @PostMapping("/cars/{carId}/status")
    public ResponseEntity<Car> updateCarStatus(@PathVariable Long carId, @RequestParam String status) {
        Car updatedCar = carService.updateCarStatus(carId, status);
        return ResponseEntity.ok(updatedCar);
    }
}