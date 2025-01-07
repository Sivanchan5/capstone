package com.example.capstone.model;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
/**
 * Entity class representing a Dog in the system.
 @Author: Xiwen Chen
 Date: Dec 25, 2024
 */
@Entity
@Table(name = "dogs")

public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Dog name cannot be blank")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Breed cannot be blank")
    @Column(name = "breed",nullable = false)
    private String breed;

    @Min(value = 0, message = "Age cannot be negative")
    @Column
    private Integer age;

    @NotBlank(message = "Image URL cannot be blank")
    @Column(name = "image_url")

    private String imageUrl="/static/uploads/default.png";
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shelter_id", nullable = true)
    private Shelter shelter;

    //Default Constructor
    public Dog(){

    }
    public Dog(Long id) {
        this.id = id;
    }

    public Dog(String name, String breed, Integer age,String imageUrl,Shelter shelter) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.imageUrl = imageUrl;
        this.shelter = shelter;
    }

    //Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public @NotBlank(message = "Dog name cannot be blank") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Dog name cannot be blank") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Breed cannot be blank") String getBreed() {
        return breed;
    }

    public void setBreed(@NotBlank(message = "Breed cannot be blank") String breed) {
        this.breed = breed;
    }

    public @Min(value = 0, message = "Age cannot be negative") Integer getAge() {
        return age;
    }

    public void setAge(@Min(value = 0, message = "Age cannot be negative") Integer age) {
        this.age = age;
    }

    public @NotBlank(message = "Image URL cannot be blank") String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NotBlank(message = "Image URL cannot be blank") String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
