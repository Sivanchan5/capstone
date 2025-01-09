package com.example.capstone.repository;
import com.example.capstone.model.Shelter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ShelterRepositoryTest {

    @Autowired
    private ShelterRepository shelterRepository;

    private Shelter testShelter;

    @BeforeEach
    void setUp() {
        testShelter = new Shelter();
        testShelter.setName("Happy Paws Shelter");
        testShelter.setAddress("123 Shelter Lane");
        testShelter.setContactNumber("123-456-7890");

        shelterRepository.save(testShelter);
    }

    @Test
    void testFindByName_Success() {
        Shelter foundShelter = shelterRepository.findByName("Happy Paws Shelter");
        assertThat(foundShelter).isNotNull();
        assertThat(foundShelter.getName()).isEqualTo("Happy Paws Shelter");
    }

    @Test
    void testFindByName_NotFound() {
        Shelter foundShelter = shelterRepository.findByName("Nonexistent Shelter");
        assertThat(foundShelter).isNull();
    }
}
