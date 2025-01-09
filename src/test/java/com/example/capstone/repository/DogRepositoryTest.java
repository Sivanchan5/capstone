package com.example.capstone.repository;

import com.example.capstone.model.Dog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DogRepositoryTest {

    @Autowired
    private DogRepository dogRepository;

    private Dog dog1;
    private Dog dog2;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
        dog1 = new Dog();
        dog1.setName("Buddy");
        dog1.setBreed("Labrador");
        dog1.setAge(3);

        dog2 = new Dog();
        dog2.setName("Max");
        dog2.setBreed("Labrador");
        dog2.setAge(2);

        dogRepository.save(dog1);
        dogRepository.save(dog2);
    }

    @Test
    void testFindByBreed_Success() {
        List<Dog> foundDogs = dogRepository.findByBreed("Labrador");
        assertThat(foundDogs).isNotEmpty();
        assertThat(foundDogs).hasSize(2);
        assertThat(foundDogs.get(0).getBreed()).isEqualTo("Labrador");
    }

    @Test
    void testFindByBreed_NotFound() {
        List<Dog> foundDogs = dogRepository.findByBreed("Poodle");
        assertThat(foundDogs).isEmpty();
    }
}
