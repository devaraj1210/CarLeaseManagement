package com.trimblecars.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String role; // ADMIN, OWNER, CUSTOMER

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Car> ownedCars;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Lease> leases;

    public User() {}

    public User(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<Car> getOwnedCars() { return ownedCars; }
    public void setOwnedCars(List<Car> ownedCars) { this.ownedCars = ownedCars; }

    public List<Lease> getLeases() { return leases; }
    public void setLeases(List<Lease> leases) { this.leases = leases; }
}
