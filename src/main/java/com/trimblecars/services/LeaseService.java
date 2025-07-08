package com.trimblecars.services;

import com.trimblecars.exception.ResourceNotFoundException;
import com.trimblecars.model.Car;
import com.trimblecars.model.Lease;
import com.trimblecars.model.Users;
import com.trimblecars.repository.CarRepository;
import com.trimblecars.repository.LeaseRepository;
import com.trimblecars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaseService {

    private final LeaseRepository leaseRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @Autowired
    public LeaseService(LeaseRepository leaseRepository, CarRepository carRepository, UserRepository userRepository) {
        this.leaseRepository = leaseRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    // Start a lease
    public Lease startLease(Long customerId, Long carId) {
    	Users customer = userRepository.findById(customerId)
    	        .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

    	Car car = carRepository.findById(carId)
    	        .orElseThrow(() -> new ResourceNotFoundException("Car not found with ID: " + carId));

        if (!car.getStatus().equals("IDLE")) {
            throw new RuntimeException("Car is not available for lease");
        }

        long activeLeases = leaseRepository.findByCustomer(customer)
            .stream()
            .filter(lease -> lease.getEndDate() == null)
            .count();

        if (activeLeases >= 2) {
            throw new RuntimeException("Customer already has 2 active leases");
        }

        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setStartDate(LocalDate.now());
        lease.setEndDate(null);

        car.setStatus("ON_LEASE");
        carRepository.save(car);

        return leaseRepository.save(lease);
    }

    // End a lease
    public Lease endLease(Long leaseId) {
    	Lease lease = leaseRepository.findById(leaseId)
    	        .orElseThrow(() -> new ResourceNotFoundException("Lease not found with ID: " + leaseId));

        lease.setEndDate(LocalDate.now());

        Car car = lease.getCar();
        car.setStatus("IDLE");
        carRepository.save(car);

        return leaseRepository.save(lease);
    }

    // View leases for a user
    public List<Lease> getLeasesByCustomerId(Long customerId) {
        Users customer = userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        return leaseRepository.findByCustomer(customer);
    }

    // View all leases
    public List<Lease> getAllLeases() {
        return leaseRepository.findAll();
    }
}