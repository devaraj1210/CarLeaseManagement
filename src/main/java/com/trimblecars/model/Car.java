package com.trimblecars.model;

import jakarta.persistence.*;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private String variant;
    private String status; // IDLE, ON_LEASE, ON_SERVICE

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Users owner;

    public Car() {}

    public Car(String model, String variant, String status, Users owner) {
        this.model = model;
        this.variant = variant;
        this.status = status;
        this.owner = owner;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getVariant() { return variant; }
    public void setVariant(String variant) { this.variant = variant; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Users getOwner() { return owner; }
    public void setOwner(Users owner) { this.owner = owner; }
}
