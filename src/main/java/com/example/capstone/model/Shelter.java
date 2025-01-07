package com.example.capstone.model;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

import jakarta.validation.constraints.NotBlank;


/**
 * Entity class representing an Adoption in the system.
 @Author: Xiwen Chen
 Date: Dec 25, 2024
 */
@Entity
@Table(name = "shelters")
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Shelter name cannot be blank")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Address cannot be blank")
    @Column(nullable = false)
    private String address;

    @NotBlank(message = "Contact number cannot be blank")
    @Column(nullable = false)
    private String contactNumber;

    //Default constructor
    public Shelter(){

    }

    //Parameterized constructor

    public Shelter(Long id, String name, String address, String contactNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;

    }
    //Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Shelter name cannot be blank") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Shelter name cannot be blank") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Address cannot be blank") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "Address cannot be blank") String address) {
        this.address = address;
    }

    public @NotBlank(message = "Contact number cannot be blank") String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(@NotBlank(message = "Contact number cannot be blank") String contactNumber) {
        this.contactNumber = contactNumber;
    }


}
