package com.trimblecars.services;

import com.trimblecars.model.Car;
import com.trimblecars.model.Lease;
import com.trimblecars.model.Users;
import com.trimblecars.repository.CarRepository;
import com.trimblecars.repository.LeaseRepository;
import com.trimblecars.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Primary
@Service    

public class LeaseServiceImpl extends LeaseService {

	public LeaseServiceImpl(LeaseRepository leaseRepository, CarRepository carRepository,
			UserRepository userRepository) {
		super(leaseRepository, carRepository, userRepository);
		// TODO Auto-generated constructor stub
	}

	private static final Logger logger = LoggerFactory.getLogger(LeaseServiceImpl.class);
    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Lease startLease(Long customerId, Long carId) {
        logger.info("Starting lease: customerId={}, carId={}", customerId, carId);
        Users customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Lease> activeLeases = leaseRepository.findByCustomerId(customerId).stream()
                .filter(l -> l.getEndDate() == null)
                .toList();

        if (activeLeases.size() >= 2) {
            throw new IllegalStateException("Customer already has 2 active leases.");
        }

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        if (!"IDLE".equalsIgnoreCase(car.getStatus())) {
            throw new IllegalStateException("Car is not available for lease.");
        }

        Lease lease = new Lease();
        lease.setCustomer(customer);
        lease.setCar(car);
        lease.setStartDate(LocalDate.now());

        car.setStatus("ON_LEASE");
        carRepository.save(car);

        return leaseRepository.save(lease);
    }

    @Override
    @Transactional
    public Lease endLease(Long leaseId) {
        logger.info("Ending lease: leaseId={}", leaseId);
        Lease lease = leaseRepository.findById(leaseId)
                .orElseThrow(() -> new RuntimeException("Lease not found"));

        lease.setEndDate(LocalDate.now());

        Car car = lease.getCar();
        car.setStatus("IDLE");
        carRepository.save(car);

        return leaseRepository.save(lease);
    }

    @Override
    public List<Lease> getLeasesByCustomerId(Long customerId) {
        return leaseRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Lease> getAllLeases() {
        return leaseRepository.findAll();
    }
}
