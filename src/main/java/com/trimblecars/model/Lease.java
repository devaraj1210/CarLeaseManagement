package com.trimblecars.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Lease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Users customer;

    public Lease() {}

    public Lease(LocalDate startDate, LocalDate endDate, Car car, Users customer) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.car = car;
        this.customer = customer;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }

    public Users getCustomer() { return customer; }
    public void setCustomer(Users customer) { this.customer = customer; }
}
