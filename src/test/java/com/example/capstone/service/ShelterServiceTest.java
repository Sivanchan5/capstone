package com.example.capstone.service;
import com.example.capstone.model.Shelter;
import com.example.capstone.repository.ShelterRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ShelterService
 */
@SpringBootTest
public class ShelterServiceTest {

    private ShelterService shelterService;

    private ShelterRepository shelterRepository;

    private Shelter testShelter;

    @BeforeEach
    void setUp() {
        // 使用 Mockito 模拟 ShelterRepository
        shelterRepository = Mockito.mock(ShelterRepository.class);
        shelterService = new ShelterService(shelterRepository);

        // 创建一个测试 Shelter
        testShelter = new Shelter();
        testShelter.setId(1L);
        testShelter.setName("Happy Tails Shelter");
        testShelter.setAddress("123 Shelter Lane");
        testShelter.setContactNumber("123-456-7890");
    }

    // 测试创建 Shelter
    @Test
    void testCreateShelter() {
        when(shelterRepository.save(testShelter)).thenReturn(testShelter);

        Shelter savedShelter = shelterService.createShelters(testShelter);

        assertThat(savedShelter).isNotNull();
        assertThat(savedShelter.getName()).isEqualTo("Happy Tails Shelter");
        verify(shelterRepository, times(1)).save(testShelter);
    }

    // 测试查找所有收容所
    @Test
    void testGetAllShelters() {
        List<Shelter> shelters = Arrays.asList(testShelter);
        when(shelterRepository.findAll()).thenReturn(shelters);

        List<Shelter> result = shelterService.getAllShelters();

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
        verify(shelterRepository, times(1)).findAll();
    }

    // 测试通过ID查找收容所 - 未找到情况
    @Test
    void testGetShelterById_NotFound() {
        when(shelterRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ValidationException.class, () -> {
            shelterService.getShelterById(99L);
        });

        assertThat(exception.getMessage()).isEqualTo("Shelter not found");
    }

    // 测试更新 Shelter
    @Test
    void testUpdateShelter() {
        Shelter updatedShelter = new Shelter();
        updatedShelter.setName("Updated Shelter");
        updatedShelter.setAddress("456 New Lane");
        updatedShelter.setContactNumber("987-654-3210");

        when(shelterRepository.findById(1L)).thenReturn(Optional.of(testShelter));
        when(shelterRepository.save(testShelter)).thenReturn(testShelter);

        Shelter result = shelterService.updateShelter(1L, updatedShelter);

        assertThat(result.getName()).isEqualTo("Updated Shelter");
        assertThat(result.getAddress()).isEqualTo("456 New Lane");
        verify(shelterRepository, times(1)).save(testShelter);
    }

    // 测试删除 Shelter
    @Test
    void testDeleteShelter() {
        doNothing().when(shelterRepository).deleteById(1L);

        shelterService.deleteShelter(1L);

        verify(shelterRepository, times(1)).deleteById(1L);
    }

    // 测试通过名称查找 Shelter
    @Test
    void testFindByName_Success() {
        when(shelterRepository.findByName("Happy Tails Shelter")).thenReturn(testShelter);

        Shelter foundShelter = shelterService.findByName("Happy Tails Shelter");

        assertThat(foundShelter).isNotNull();
        assertThat(foundShelter.getName()).isEqualTo("Happy Tails Shelter");
    }

    @Test
    void testFindByName_NotFound() {
        when(shelterRepository.findByName("Unknown Shelter")).thenReturn(null);

        Shelter shelter = shelterService.findByName("Unknown Shelter");

        assertThat(shelter).isNull();
    }
}