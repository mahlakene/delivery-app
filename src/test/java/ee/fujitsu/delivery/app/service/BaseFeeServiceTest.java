package ee.fujitsu.delivery.app.service;

import ee.fujitsu.delivery.app.dto.BaseFeeDto;
import ee.fujitsu.delivery.app.entity.BaseFeeEntity;
import ee.fujitsu.delivery.app.exception.FeeAlreadyExists;
import ee.fujitsu.delivery.app.repository.BaseFeeRepository;
import ee.fujitsu.delivery.app.repository.CityRepository;
import ee.fujitsu.delivery.app.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseFeeServiceTest {

    @Mock
    private BaseFeeRepository baseFeeRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @InjectMocks
    private BaseFeeService baseFeeService;

    @Test
    void registerBaseFee_Success() {
        // Given
        BaseFeeEntity mockEntity = new BaseFeeEntity();
        mockEntity.setVehicleId(1L);
        mockEntity.setCityId(1L);
        BaseFeeDto baseFeeDto = new BaseFeeDto();
        baseFeeDto.setCityId(1L);
        baseFeeDto.setVehicleId(1L);
        when(baseFeeRepository.findByCityIdAndVehicleId(1L, 1L)).thenReturn(Optional.empty());
        when(baseFeeRepository.save(any())).thenReturn(mockEntity);
        when(cityRepository.existsById(1L)).thenReturn(true);
        when(vehicleRepository.existsById(1L)).thenReturn(true);

        // When
        baseFeeService.registerBaseFee(baseFeeDto);

        // Then
        verify(baseFeeRepository, times(1)).save(any(BaseFeeEntity.class));
    }

    @Test
    void registerBaseFee_FeeAlreadyExists_ThrowsException() {
        // Given
        BaseFeeDto baseFeeDto = new BaseFeeDto();
        baseFeeDto.setCityId(1L);
        baseFeeDto.setVehicleId(1L);
        when(baseFeeRepository.findByCityIdAndVehicleId(1L, 1L)).thenReturn(Optional.of(new BaseFeeEntity()));
        when(cityRepository.existsById(1L)).thenReturn(true);
        when(vehicleRepository.existsById(1L)).thenReturn(true);

        // When/Then
        FeeAlreadyExists exception = assertThrows(FeeAlreadyExists.class, () -> baseFeeService.registerBaseFee(baseFeeDto));
        assertEquals("Base fee of this city and vehicle already exists.", exception.getMessage());
        verify(baseFeeRepository, never()).save(any());
    }

    @Test
    void testGetAllBaseFees() {
        BaseFeeEntity mockEntity = new BaseFeeEntity();
        mockEntity.setCityId(2L);
        BaseFeeEntity mockEntity2 = new BaseFeeEntity();
        mockEntity.setCityId(2L);
        List<BaseFeeEntity> entities = new ArrayList<>();
        entities.add(mockEntity);
        entities.add(mockEntity2);
        when(baseFeeRepository.findAll()).thenReturn(entities);

        List<BaseFeeDto> baseFeeDtos = baseFeeService.getAllBaseFees();

        assertNotNull(baseFeeDtos);
        assertEquals(2L, baseFeeDtos.get(0).getCityId());
        assertEquals(2, baseFeeDtos.size());
    }

    @Test
    void testUpdateBaseFee() {
        BaseFeeEntity mockEntity = new BaseFeeEntity();
        mockEntity.setFee(BigDecimal.ONE);
        when(cityRepository.existsById(any())).thenReturn(true);
        when(vehicleRepository.existsById(any())).thenReturn(true);
        when(baseFeeRepository.findById(anyLong())).thenReturn(Optional.of(mockEntity));

        BaseFeeDto baseFeeDto = new BaseFeeDto();
        baseFeeDto.setFee(BigDecimal.valueOf(5));
        baseFeeService.updateBaseFee(1L, baseFeeDto);

        assertEquals(BigDecimal.valueOf(5), mockEntity.getFee());
    }

    @Test
    void testDeleteBaseFee() {
        when(baseFeeRepository.existsById(1L)).thenReturn(true);
        baseFeeService.deleteBaseFee(1L);
        verify(baseFeeRepository, times(1)).deleteById(1L);
    }
}
