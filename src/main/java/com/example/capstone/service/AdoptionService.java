package com.example.capstone.service;

import com.example.capstone.model.Adoption;
import com.example.capstone.model.AdoptionStatus;
import com.example.capstone.model.Dog;
import com.example.capstone.model.User;
import com.example.capstone.repository.AdoptionRepository;
import com.example.capstone.repository.DogRepository;
import com.example.capstone.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service layer for Adoption entity
 * Manges adoption records and requests.
 * @Author:Xiwen Chen
 * Date: Dec 27, 2024
 */
@Service
public class AdoptionService {
    private final AdoptionRepository adoptionRepository;
    private final UserRepository userRepository;
    private final DogRepository dogRepository;

    @Autowired
    public AdoptionService(AdoptionRepository adoptionRepository, UserRepository userRepository, DogRepository dogRepository) {
        this.adoptionRepository = adoptionRepository;
        this.userRepository = userRepository;
        this.dogRepository = dogRepository;
    }
    //Create Adoption
    public Adoption requestAdoption(@Valid Adoption adoption){
        // 1. 检查用户和狗是否存在
        User user = userRepository.findById(adoption.getUser().getId())
                .orElseThrow(() -> new ValidationException("User not found"));
        Dog dog = dogRepository.findById(adoption.getDog().getId())
                .orElseThrow(() -> new ValidationException("Dog not found"));

        adoption.setStatus(AdoptionStatus.PENDING);
        adoption.setAdoptionDate(LocalDate.now());//Set default status
        return adoptionRepository.save(adoption);
    }
    //Find all Adoptions
    public List<Adoption> getAllAdoptions(){
        return adoptionRepository.findAll();
    }
    //Find by ID
    public Adoption getAdoptionById(Long id){
        return adoptionRepository.findById(id).orElseThrow(()->new ValidationException("Adoption not found"));
    }
    //Custom Query - Find adoption by user ID
    public List<Adoption> findByUserId(Long userId){
        return adoptionRepository.findByUserId(userId);
    }
    // Custom Query - Find adoption by status
    public List<Adoption> findByStatus(AdoptionStatus status){
        return adoptionRepository.findByStatus(status);
    }
    public void updateAdoptionStatus(Long id, String status) {
        Adoption adoption = adoptionRepository.findById(id).orElseThrow(() -> new RuntimeException("Adoption not found"));
        adoption.setStatus(AdoptionStatus.valueOf(status));
        adoptionRepository.save(adoption);
    }
    // 取消领养申请（仅限用户取消自己的申请，且仅在PENDING状态下）
    public void cancelAdoption(Long adoptionId, Long userId) {
        Adoption adoption = adoptionRepository.findById(adoptionId)
                .orElseThrow(() -> new RuntimeException("Adoption not found"));

        if (!adoption.getUser().getId().equals(userId)) {
            throw new ValidationException("You can only cancel your own adoption requests.");
        }

        if (adoption.getStatus() != AdoptionStatus.PENDING) {
            throw new ValidationException("Only pending adoptions can be cancelled.");
        }

        adoptionRepository.deleteById(adoptionId);
    }
}
