package com.example.capstone.model;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;

/**
 * Entity class representing an Adoption in the system.
 @Author: Xiwen Chen
 Date: Dec 25, 2024
 */
@Entity
@Table(name = "adoptions")
public class Adoption {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name="dog_id", nullable = false)
    private Dog dog;

    @Column(name= "adoption_date")
    private LocalDate adoptionDate;

    @Enumerated(EnumType.STRING)//This stores the enum value as String in the database
    @Column(nullable = false)
    private AdoptionStatus status;

    //Default constructor
    public Adoption(){
        this.status = AdoptionStatus.PENDING; //Default status

    }
    //Parameterized constructor
    public Adoption(Long id, User user, Dog dog, LocalDate adoptionDate, AdoptionStatus status) {
        this.id = id;
        this.user = user;
        this.dog = dog;
        this.adoptionDate = adoptionDate;
        this.status = status !=null ? status : AdoptionStatus.PENDING;
    }

    //Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull User getUser() {
        return user;
    }

    public void setUser(@NotNull User user) {
        this.user = user;
    }

    public @NotNull Dog getDog() {
        return dog;
    }

    public void setDog(@NotNull Dog dog) {
        this.dog = dog;
    }

    public LocalDate getAdoptionDate() {
        return adoptionDate;
    }

    public void setAdoptionDate(LocalDate adoptionDate) {
        this.adoptionDate = adoptionDate;
    }

    public AdoptionStatus getStatus() {
        return status;
    }

    public void setStatus(AdoptionStatus status) {
        this.status = status;
    }
}
