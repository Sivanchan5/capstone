package com.example.capstone.service;
import com.example.capstone.model.Dog;
import com.example.capstone.model.Shelter;
import com.example.capstone.repository.DogRepository;
import com.example.capstone.repository.ShelterRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for DogService
 */
public class DogServiceTest {

    private DogService dogService;
    private DogRepository dogRepository;
    private ShelterRepository shelterRepository;
    private ShelterService shelterService;

    private Dog testDog;
    private Shelter testShelter;

    @BeforeEach
    void setUp() {
        dogRepository = Mockito.mock(DogRepository.class);
        shelterRepository = Mockito.mock(ShelterRepository.class);
        shelterService = Mockito.mock(ShelterService.class);
        dogService = new DogService(dogRepository, shelterService, shelterRepository);

        testShelter = new Shelter();
        testShelter.setId(1L);
        testShelter.setName("Happy Paws Shelter");
        testShelter.setAddress("123 Shelter Lane");

        testDog = new Dog();
        testDog.setId(1L);
        testDog.setName("Buddy");
        testDog.setBreed("Labrador");
        testDog.setAge(3);
        testDog.setShelter(testShelter);
        testDog.setImageUrl("/uploads/default.png");
    }

    @Test
    void testSaveDogWithImage() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "dog.jpg",
                "image/jpeg",
                "dummy-image-content".getBytes()
        );

        when(shelterRepository.findById(1L)).thenReturn(Optional.of(testShelter));
        when(dogRepository.save(any(Dog.class))).thenReturn(testDog);

        dogService.saveDogWithImage(testDog, file, 1L);

        ArgumentCaptor<Dog> dogCaptor = ArgumentCaptor.forClass(Dog.class);
        verify(dogRepository).save(dogCaptor.capture());
        Dog savedDog = dogCaptor.getValue();

        assertThat(savedDog.getImageUrl()).contains("dog.jpg");
        assertThat(savedDog.getShelter().getId()).isEqualTo(1L);
    }

    @Test
    void testSaveDogWithImage_ShelterNotFound() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "dog.jpg",
                "image/jpeg",
                "dummy-image-content".getBytes()
        );

        when(shelterRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dogService.saveDogWithImage(testDog, file, 1L);
        });

        assertThat(exception.getMessage()).isEqualTo("Shelter not found with ID: 1");
    }

    @Test
    void testGetAllDogs() {
        when(dogRepository.findAll()).thenReturn(List.of(testDog));

        List<Dog> dogs = dogService.getAllDogs();

        assertThat(dogs).hasSize(1);
        assertThat(dogs.get(0).getName()).isEqualTo("Buddy");
        verify(dogRepository, times(1)).findAll();
    }

    @Test
    void testGetDogById_Success() {
        when(dogRepository.findById(1L)).thenReturn(Optional.of(testDog));

        Dog foundDog = dogService.getDogById(1L);

        assertThat(foundDog).isNotNull();
        assertThat(foundDog.getName()).isEqualTo("Buddy");
    }

    @Test
    void testGetDogById_NotFound() {
        when(dogRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ValidationException.class, () -> {
            dogService.getDogById(99L);
        });

        assertThat(exception.getMessage()).isEqualTo("Dog not found");
    }

    @Test
    void testUpdateDog_Success() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "updated.jpg",
                "image/jpeg",
                "updated-image-content".getBytes()
        );

        Dog updatedDog = new Dog();
        updatedDog.setName("Max");
        updatedDog.setBreed("Golden Retriever");
        updatedDog.setAge(4);

        when(dogRepository.findById(1L)).thenReturn(Optional.of(testDog));
        when(shelterRepository.findById(1L)).thenReturn(Optional.of(testShelter));
        when(dogRepository.save(any(Dog.class))).thenReturn(testDog);

        Dog result = dogService.updateDog(1L, updatedDog, file, 1L);

        assertThat(result.getName()).isEqualTo("Max");
        assertThat(result.getImageUrl()).contains("updated.jpg");
    }

    @Test
    void testDeleteDog() {
        when(dogRepository.findById(1L)).thenReturn(Optional.of(testDog));
        doNothing().when(dogRepository).deleteById(1L);

        dogService.deleteDog(1L);

        verify(dogRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindDogByBreed() {
        when(dogRepository.findByBreed("Labrador")).thenReturn(List.of(testDog));

        List<Dog> dogs = dogService.findDogByBreed("Labrador");

        assertThat(dogs).hasSize(1);
        assertThat(dogs.get(0).getBreed()).isEqualTo("Labrador");
    }
}
