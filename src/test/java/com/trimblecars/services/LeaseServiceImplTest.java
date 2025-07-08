package com.trimblecars.services;

import com.trimblecars.model.Car;
import com.trimblecars.model.Lease;
import com.trimblecars.model.Users;
import com.trimblecars.repository.CarRepository;
import com.trimblecars.repository.LeaseRepository;
import com.trimblecars.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeaseServiceImplTest {

    @Mock private LeaseRepository leaseRepository;
    @Mock private CarRepository carRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks private LeaseServiceImpl leaseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStartLeaseSuccess() {
        Users customer = new Users("Test", "test@example.com", "CUSTOMER");
        customer.setId(1L);
        Car car = new Car("TestModel", "Base", "IDLE", customer);
        car.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(carRepository.findById(2L)).thenReturn(Optional.of(car));
        when(leaseRepository.findByCustomerId(1L)).thenReturn(Collections.emptyList());
        when(carRepository.save(any())).thenReturn(car);
        when(leaseRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Lease lease = leaseService.startLease(1L, 2L);
        assertNotNull(lease);
        assertEquals("ON_LEASE", car.getStatus());
    }
}
