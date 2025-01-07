package com.example.capstone.service;
import com.example.capstone.model.Dog;
import com.example.capstone.model.Shelter;
import com.example.capstone.repository.DogRepository;
import com.example.capstone.repository.ShelterRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Service layer for Dog entity.
 * Manages dog information and adoption status.
 * @Author:Xiwen Chen
 * Date: Dec 27, 2024
 */
@Service
public class DogService {
    private final DogRepository dogRepository;
    private final ShelterService shelterService;
    private final ShelterRepository shelterRepository;

    @Autowired
    public DogService(DogRepository dogRepository, ShelterService shelterService, ShelterRepository shelterRepository) {
        this.dogRepository = dogRepository;
        this.shelterService = shelterService;
        this.shelterRepository = shelterRepository;
    }
    private String storeFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return "/uploads/default.png";  // 文件为空时，返回默认路径
        }
        String fileName = file.getOriginalFilename();
        String safeFileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");  // 处理特殊字符
        Path uploadDir = Paths.get("src/main/resources/static/uploads/");
        Files.createDirectories(uploadDir);  // 确保上传路径存在
        Path filePath = uploadDir.resolve(safeFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + safeFileName;
    }

    public void saveDogWithImage(Dog dog, MultipartFile file,Long shelterId) throws IOException {
        if (!file.isEmpty()) {
               String filePath = storeFile(file);  // Use storeFile and get String path
                dog.setImageUrl(filePath);  // Directly set String path
            }else {
            // 如果未上传文件，设置默认路径
            dog.setImageUrl("/uploads/default.png");
        }
        Shelter shelter = shelterRepository.findById(shelterId)
                .orElseThrow(() -> new IllegalArgumentException("Shelter not found with ID: " + shelterId));
        dog.setShelter(shelter);  // 将 shelter 关联到 dog
            dogRepository.save(dog);
        }
    //Add Dog
    public Dog addDog(@Valid Dog dog){
        return dogRepository.save(dog);
    }
    //Get All Dogs
    public List<Dog> getAllDogs(){
        return dogRepository.findAll();
    }
    //Get Dog by ID
    public Dog getDogById(Long id){
        return dogRepository.findById(id).orElseThrow(()-> new ValidationException("Dog not found"));

    }
    //Update Dog information
    public Dog updateDog(Long id, @Valid Dog updateDog,MultipartFile file,Long shelterId)throws IOException{
        Dog dog = getDogById(id);
        Shelter shelter = shelterRepository.findById(shelterId)
                .orElseThrow(() -> new ValidationException("Shelter not found"));
        dog.setName(updateDog.getName());
        dog.setBreed(updateDog.getBreed());
        dog.setAge(updateDog.getAge());
        dog.setShelter(shelter);
        if (file != null && !file.isEmpty()) {
            String filePath = storeFile(file);
            dog.setImageUrl(filePath);
        }else{
            dog.setImageUrl("/uploads/default.png");
        }
        return dogRepository.save(dog);
    }
    //Delete Dog
    public void deleteDog(Long id){
        Dog dog = getDogById(id);
        dogRepository.deleteById(id);
    }
    //Custom Query - Find dogs by breed
    public List<Dog> findDogByBreed(String breed){
        return dogRepository.findByBreed(breed);
    }
}
