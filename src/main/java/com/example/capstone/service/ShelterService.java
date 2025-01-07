package com.example.capstone.service;

import com.example.capstone.model.Shelter;
import com.example.capstone.repository.ShelterRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for Shelter entity
 * Handles shelter information and management
 * @Author:Xiwen Chen
 * Date:Dec 27,2024
 */
@Service
public class ShelterService {
    private final ShelterRepository shelterRepository;
    @Autowired
    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }
    //Create Shelter
    public Shelter createShelters(@Valid Shelter shelter) {
        return shelterRepository.save(shelter);
    }
    //Find All Shelters
    public List<Shelter> getAllShelters() {
        List<Shelter> shelters = shelterRepository.findAll();
        System.out.println("Shelters from DB: " + shelters.size());
        return shelters;
    }
    //Find by ID
    public Shelter getShelterById(Long id) {
        return shelterRepository.findById(id).orElseThrow(()-> new ValidationException("Shelter not found"));
    }
    // update Shelter
    public Shelter updateShelter(Long id, @Valid Shelter updatedShelter) {
        Shelter shelter = getShelterById(id);
        shelter.setName(updatedShelter.getName());
        shelter.setAddress(updatedShelter.getAddress());
        shelter.setContactNumber(updatedShelter.getContactNumber());
        return shelterRepository.save(shelter);
    }
    //Delete Shelter
    public void deleteShelter(Long id) {
        shelterRepository.deleteById(id);
    }
    //Custom Query
    public Shelter findByName(String name) {
        return shelterRepository.findByName(name);
    }
}
