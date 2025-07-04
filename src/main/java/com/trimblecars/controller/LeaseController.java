package com.trimblecars.controller;


import com.trimblecars.model.Lease;
import com.trimblecars.services.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leases")
public class LeaseController {

    private final LeaseService leaseService;

    @Autowired
    public LeaseController(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    // Start a lease
    @PostMapping("/start")
    public ResponseEntity<Lease> startLease(@RequestParam Long customerId, @RequestParam Long carId) {
        Lease lease = leaseService.startLease(customerId, carId);
        return ResponseEntity.ok(lease);
    }

    // End a lease
    @PostMapping("/end/{leaseId}")
    public ResponseEntity<Lease> endLease(@PathVariable Long leaseId) {
        Lease lease = leaseService.endLease(leaseId);
        return ResponseEntity.ok(lease);
    }

    // Get lease history for a customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Lease>> getLeaseHistory(@PathVariable Long customerId) {
        return ResponseEntity.ok(leaseService.getLeasesByCustomerId(customerId));
    }

    // Get all leases (admin)
    @GetMapping
    public ResponseEntity<List<Lease>> getAllLeases() {
        return ResponseEntity.ok(leaseService.getAllLeases());
    }
}