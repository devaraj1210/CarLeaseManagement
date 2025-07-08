package com.trimblecars.services;

import com.trimblecars.exception.ResourceNotFoundException;
import com.trimblecars.model.Car;
import com.trimblecars.model.Users;
import com.trimblecars.repository.CarRepository;
import com.trimblecars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @Autowired
    public CarService(CarRepository carRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    // Register a new car
    public Car registerCar(Car car, Long ownerId) {
        Optional<Users> ownerOpt = userRepository.findById(ownerId);
        if (ownerOpt.isEmpty()) {
            throw new ResourceNotFoundException("Car owner not found with ID: " + ownerId);
        }

        car.setOwner(ownerOpt.get());
        car.setStatus("IDLE");
        return carRepository.save(car);
    }

    // Get all cars
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // Get cars by status
    public List<Car> getCarsByStatus(String status) {
        return carRepository.findByStatus(status);
    }

    // Get car by ID
    public Optional<Car> getCarById(Long carId) {
        return carRepository.findById(carId);
    }

    // Update car status
    public Car updateCarStatus(Long carId, String status) {
    	Car car = carRepository.findById(carId)
    	        .orElseThrow(() -> new ResourceNotFoundException("Car not found with ID: " + carId));

        car.setStatus(status);
        return carRepository.save(car);
    }
}