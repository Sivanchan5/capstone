package com.example.capstone;
import com.example.capstone.model.Adoption;
import com.example.capstone.model.AdoptionStatus;
import com.example.capstone.model.Dog;
import com.example.capstone.model.User;
import com.example.capstone.repository.AdoptionRepository;
import com.example.capstone.repository.DogRepository;
import com.example.capstone.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AdoptionRepositoryTest {

    @Autowired
    private AdoptionRepository adoptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DogRepository dogRepository;

    private User testUser;
    private Dog testDog;
    private Adoption adoption1;
    private Adoption adoption2;

    @BeforeEach
    void setUp() {
        testUser = new User("john_doe", "john@example.com", "password123", com.example.capstone.model.Role.USER);
        userRepository.save(testUser);
        testDog = new Dog();
        testDog.setName("Buddy");
        testDog.setBreed("Golden Retriever");
        testDog.setAge(2);
        dogRepository.save(testDog);
        adoption1 = new Adoption();
        adoption1.setUser(testUser);
        adoption1.setDog(testDog);
        adoption1.setStatus(AdoptionStatus.PENDING);
        adoption1.setAdoptionDate(LocalDate.now());
        adoptionRepository.save(adoption1);
        adoption2 = new Adoption();
        adoption2.setUser(testUser);
        adoption2.setDog(testDog);
        adoption2.setStatus(AdoptionStatus.APPROVED);
        adoption2.setAdoptionDate(LocalDate.now());
        adoptionRepository.save(adoption2);
    }

    @Test
    void testFindByUserId_Success() {
        List<Adoption> adoptions = adoptionRepository.findByUserId(testUser.getId());
        assertThat(adoptions).isNotEmpty();
        assertThat(adoptions).hasSize(2);
    }

    @Test
    void testFindByUserId_NotFound() {
        List<Adoption> adoptions = adoptionRepository.findByUserId(999L);
        assertThat(adoptions).isEmpty();
    }

    @Test
    void testFindByStatus_Success() {
        List<Adoption> pendingAdoptions = adoptionRepository.findByStatus(AdoptionStatus.PENDING);
        assertThat(pendingAdoptions).hasSize(1);
        assertThat(pendingAdoptions.get(0).getStatus()).isEqualTo(AdoptionStatus.PENDING);
    }

    @Test
    void testFindByStatus_EmptyResult() {
        List<Adoption> rejectedAdoptions = adoptionRepository.findByStatus(AdoptionStatus.REJECTED);
        assertThat(rejectedAdoptions).isEmpty();
    }
}
