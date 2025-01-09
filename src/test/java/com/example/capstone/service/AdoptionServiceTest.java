package com.example.capstone.service;
import com.example.capstone.model.Adoption;
import com.example.capstone.model.AdoptionStatus;
import com.example.capstone.model.Dog;
import com.example.capstone.model.User;
import com.example.capstone.repository.AdoptionRepository;
import com.example.capstone.repository.DogRepository;
import com.example.capstone.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AdoptionService
 */
public class AdoptionServiceTest {

    private AdoptionService adoptionService;
    private AdoptionRepository adoptionRepository;
    private UserRepository userRepository;
    private DogRepository dogRepository;

    private User testUser;
    private Dog testDog;
    private Adoption testAdoption;

    @BeforeEach
    void setUp() {
        adoptionRepository = Mockito.mock(AdoptionRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        dogRepository = Mockito.mock(DogRepository.class);

        adoptionService = new AdoptionService(adoptionRepository, userRepository, dogRepository);

        testUser = new User("john_doe", "john@example.com", "password123", com.example.capstone.model.Role.USER);
        testUser.setId(1L);

        testDog = new Dog();
        testDog.setId(1L);
        testDog.setName("Buddy");
        testDog.setBreed("Labrador");

        testAdoption = new Adoption();
        testAdoption.setId(1L);
        testAdoption.setUser(testUser);
        testAdoption.setDog(testDog);
        testAdoption.setStatus(AdoptionStatus.PENDING);
    }

    @Test
    void testRequestAdoption_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(dogRepository.findById(1L)).thenReturn(Optional.of(testDog));
        when(adoptionRepository.save(any(Adoption.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Adoption adoption = adoptionService.requestAdoption(testAdoption);

        assertThat(adoption).isNotNull();
        assertThat(adoption.getStatus()).isEqualTo(AdoptionStatus.PENDING);
        assertThat(adoption.getAdoptionDate()).isEqualTo(LocalDate.now());
        verify(adoptionRepository, times(1)).save(adoption);
    }

    @Test
    void testRequestAdoption_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ValidationException.class, () -> {
            adoptionService.requestAdoption(testAdoption);
        });

        assertThat(exception.getMessage()).isEqualTo("User not found");
    }

    @Test
    void testGetAllAdoptions() {
        when(adoptionRepository.findAll()).thenReturn(List.of(testAdoption));

        List<Adoption> adoptions = adoptionService.getAllAdoptions();

        assertThat(adoptions).isNotEmpty();
        assertThat(adoptions.size()).isEqualTo(1);
        verify(adoptionRepository, times(1)).findAll();
    }

    @Test
    void testGetAdoptionById_Success() {
        when(adoptionRepository.findById(1L)).thenReturn(Optional.of(testAdoption));

        Adoption adoption = adoptionService.getAdoptionById(1L);

        assertThat(adoption).isNotNull();
        assertThat(adoption.getId()).isEqualTo(1L);
    }

    @Test
    void testGetAdoptionById_NotFound() {
        when(adoptionRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ValidationException.class, () -> {
            adoptionService.getAdoptionById(99L);
        });

        assertThat(exception.getMessage()).isEqualTo("Adoption not found");
    }

    @Test
    void testFindByStatus() {
        when(adoptionRepository.findByStatus(AdoptionStatus.PENDING)).thenReturn(List.of(testAdoption));

        List<Adoption> adoptions = adoptionService.findByStatus(AdoptionStatus.PENDING);

        assertThat(adoptions).isNotEmpty();
        assertThat(adoptions.get(0).getStatus()).isEqualTo(AdoptionStatus.PENDING);
    }

    @Test
    void testUpdateAdoptionStatus_Success() {
        when(adoptionRepository.findById(1L)).thenReturn(Optional.of(testAdoption));

        adoptionService.updateAdoptionStatus(1L, "APPROVED");

        assertThat(testAdoption.getStatus()).isEqualTo(AdoptionStatus.APPROVED);
        verify(adoptionRepository, times(1)).save(testAdoption);
    }

    @Test
    void testCancelAdoption_Success() {
        when(adoptionRepository.findById(1L)).thenReturn(Optional.of(testAdoption));

        adoptionService.cancelAdoption(1L, 1L);

        verify(adoptionRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCancelAdoption_NotPending() {
        testAdoption.setStatus(AdoptionStatus.APPROVED);
        when(adoptionRepository.findById(1L)).thenReturn(Optional.of(testAdoption));

        Exception exception = assertThrows(ValidationException.class, () -> {
            adoptionService.cancelAdoption(1L, 1L);
        });

        assertThat(exception.getMessage()).isEqualTo("Only pending adoptions can be cancelled.");
    }
}
