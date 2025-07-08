package com.trimblecars.repository;

import com.trimblecars.model.Lease;
import com.trimblecars.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaseRepository extends JpaRepository<Lease, Long> {
    List<Lease> findByCustomer(Users customer);
    List<Lease> findByCustomerId(Long customerId);

}
