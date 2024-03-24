package ee.fujitsu.delivery.app.service;

import ee.fujitsu.delivery.app.dto.NumericalExtraFeeDto;
import ee.fujitsu.delivery.app.dto.PhenomenonExtraFeeDto;
import ee.fujitsu.delivery.app.entity.extrafee.AirTemperatureFeeEntity;
import ee.fujitsu.delivery.app.entity.extrafee.PhenomenonFeeEntity;
import ee.fujitsu.delivery.app.entity.extrafee.WindSpeedFeeEntity;
import ee.fujitsu.delivery.app.exception.FeeAlreadyExists;
import ee.fujitsu.delivery.app.exception.NotFoundException;
import ee.fujitsu.delivery.app.repository.VehicleRepository;
import ee.fujitsu.delivery.app.repository.extrafee.AirTemperatureFeeRepository;
import ee.fujitsu.delivery.app.repository.extrafee.WeatherPhenomenonRepository;
import ee.fujitsu.delivery.app.repository.extrafee.WindSpeedFeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExtraFeeServiceTest {

    @Mock
    private AirTemperatureFeeRepository airTemperatureFeeRepository;

    @Mock
    private WindSpeedFeeRepository windSpeedFeeRepository;

    @Mock
    private WeatherPhenomenonRepository weatherPhenomenonRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private ExtraFeeService extraFeeService;


    @Test
    void testGetAllAirTemperatureExtraFees() {
        AirTemperatureFeeEntity entity = new AirTemperatureFeeEntity();
        entity.setFee(BigDecimal.valueOf(2));
        when(airTemperatureFeeRepository.findAll()).thenReturn(List.of(entity));
        List<NumericalExtraFeeDto> dtos = extraFeeService.getAllAirTemperatureExtraFees();
        assertFalse(dtos.isEmpty());
        assertEquals(1, dtos.size());
        assertEquals(BigDecimal.valueOf(2), dtos.get(0).getFee());
    }

    @Test
    void testRegisterAirTemperatureExtraFee_Success() {
        NumericalExtraFeeDto dto = new NumericalExtraFeeDto();
        dto.setVehicleId(1L);
        dto.setLowerBound(10F);
        dto.setUpperBound(20F);

        when(airTemperatureFeeRepository.existsByLowerBoundAndUpperBound(any(Float.class), any(Float.class))).thenReturn(false);
        when(vehicleRepository.existsById(any(Long.class))).thenReturn(true);

        assertDoesNotThrow(() -> extraFeeService.registerAirTemperatureExtraFee(dto));
    }

    @Test
    void testRegisterAirTemperatureExtraFee_FeeAlreadyExists() {
        NumericalExtraFeeDto dto = new NumericalExtraFeeDto();
        dto.setVehicleId(1L);
        dto.setLowerBound(10F);
        dto.setUpperBound(20F);

        when(airTemperatureFeeRepository.existsByLowerBoundAndUpperBound(any(Float.class), any(Float.class))).thenReturn(true);
        when(vehicleRepository.existsById(any(Long.class))).thenReturn(true);

        FeeAlreadyExists exception = assertThrows(FeeAlreadyExists.class, () -> extraFeeService.registerAirTemperatureExtraFee(dto));
        assertEquals("Given lower and upper bounds conflict with some existing fees.", exception.getMessage());
    }

    @Test
    void testUpdateAirTemperatureExtraFee() {
        AirTemperatureFeeEntity entity = new AirTemperatureFeeEntity();
        entity.setId(1L);

        NumericalExtraFeeDto dto = new NumericalExtraFeeDto();
        dto.setFee(BigDecimal.valueOf(100));

        when(airTemperatureFeeRepository.findById(1L)).thenReturn(Optional.of(entity));

        assertDoesNotThrow(() -> extraFeeService.updateAirTemperatureExtraFee(1L, dto));
        assertEquals(BigDecimal.valueOf(100), entity.getFee());
    }

    @Test
    void testUpdateAirTemperatureExtraFee_NotFound() {
        NumericalExtraFeeDto dto = new NumericalExtraFeeDto();
        when(airTemperatureFeeRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                extraFeeService.updateAirTemperatureExtraFee(1L, dto));
        assertEquals("Extra fee with given ID was not found.", exception.getMessage());
    }

    @Test
    void testDeleteAirTemperatureExtraFee() {
        when(airTemperatureFeeRepository.existsById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> extraFeeService.deleteAirTemperatureExtraFee(1L));
        verify(airTemperatureFeeRepository, times(1)).deleteById(any());
    }

    @Test
    void testDeleteAirTemperatureExtraFee_NotFound() {
        when(airTemperatureFeeRepository.existsById(any(Long.class))).thenReturn(false);
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                extraFeeService.deleteAirTemperatureExtraFee(1L));
        assertEquals("Extra fee with given ID was not found.", exception.getMessage());
    }

    @Test
    void testGetAllWindSpeedExtraFees() {
        WindSpeedFeeEntity entity = new WindSpeedFeeEntity();
        entity.setFee(BigDecimal.valueOf(2));
        when(windSpeedFeeRepository.findAll()).thenReturn(List.of(entity));
        List<NumericalExtraFeeDto> dtos = extraFeeService.getAllWindSpeedExtraFees();
        assertFalse(dtos.isEmpty());
        assertEquals(1, dtos.size());
        assertEquals(BigDecimal.valueOf(2), dtos.get(0).getFee());
    }

    @Test
    void testRegisterWindSpeedExtraFee_Success() {
        NumericalExtraFeeDto dto = new NumericalExtraFeeDto();
        dto.setVehicleId(1L);
        dto.setLowerBound(10F);
        dto.setUpperBound(20F);

        when(windSpeedFeeRepository.existsByLowerBoundAndUpperBound(any(Float.class), any(Float.class))).thenReturn(false);
        when(vehicleRepository.existsById(any(Long.class))).thenReturn(true);

        assertDoesNotThrow(() -> extraFeeService.registerWindSpeedExtraFee(dto));
    }

    @Test
    void testRegisterWindSpeedExtraFee_FeeAlreadyExists() {
        NumericalExtraFeeDto dto = new NumericalExtraFeeDto();
        dto.setVehicleId(1L);
        dto.setLowerBound(10F);
        dto.setUpperBound(20F);

        when(windSpeedFeeRepository.existsByLowerBoundAndUpperBound(any(Float.class), any(Float.class))).thenReturn(true);
        when(vehicleRepository.existsById(any(Long.class))).thenReturn(true);

        FeeAlreadyExists exception = assertThrows(FeeAlreadyExists.class, () -> extraFeeService.registerWindSpeedExtraFee(dto));
        assertEquals("Given lower and upper bounds conflict with some existing fees.", exception.getMessage());
    }

    @Test
    void testUpdateWindSpeedExtraFee() {
        WindSpeedFeeEntity entity = new WindSpeedFeeEntity();
        entity.setId(1L);

        NumericalExtraFeeDto dto = new NumericalExtraFeeDto();
        dto.setFee(BigDecimal.valueOf(100));

        when(windSpeedFeeRepository.findById(1L)).thenReturn(Optional.of(entity));

        assertDoesNotThrow(() -> extraFeeService.updateWindSpeedExtraFee(1L, dto));
        assertEquals(BigDecimal.valueOf(100), entity.getFee());
    }

    @Test
    void testUpdateWindSpeedExtraFee_NotFound() {
        NumericalExtraFeeDto dto = new NumericalExtraFeeDto();
        when(windSpeedFeeRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                extraFeeService.updateWindSpeedExtraFee(1L, dto));
        assertEquals("Extra fee with given ID was not found.", exception.getMessage());
    }

    @Test
    void testDeleteWindSpeedExtraFee() {
        when(windSpeedFeeRepository.existsById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> extraFeeService.deleteWindSpeedExtraFee(1L));
        verify(windSpeedFeeRepository, times(1)).deleteById(any());
    }

    @Test
    void testDeleteWindSpeedExtraFee_NotFound() {
        when(windSpeedFeeRepository.existsById(any(Long.class))).thenReturn(false);
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                extraFeeService.deleteWindSpeedExtraFee(1L));
        assertEquals("Extra fee with given ID was not found.", exception.getMessage());
    }

    @Test
    void testGetAllPhenomenonExtraFees() {
        PhenomenonFeeEntity entity = new PhenomenonFeeEntity();
        entity.setName("Snow");
        when(weatherPhenomenonRepository.findAll()).thenReturn(List.of(entity));
        List<PhenomenonExtraFeeDto> dtos = extraFeeService.getAllPhenomenonExtraFees();
        assertFalse(dtos.isEmpty());
        assertEquals(1, dtos.size());
        assertEquals("Snow", dtos.get(0).getName());
    }

    @Test
    void testRegisterPhenomenonExtraFee_Success() {
        PhenomenonExtraFeeDto dto = new PhenomenonExtraFeeDto();
        dto.setVehicleId(1L);
        dto.setName("Test Phenomenon");

        when(weatherPhenomenonRepository.existsByNameAndVehicleId(any(String.class), any(Long.class))).thenReturn(false);
        when(vehicleRepository.existsById(any(Long.class))).thenReturn(true);

        assertDoesNotThrow(() -> extraFeeService.registerPhenomenonExtraFee(dto));
    }

    @Test
    void testRegisterPhenomenonExtraFee_FeeAlreadyExists() {
        PhenomenonExtraFeeDto dto = new PhenomenonExtraFeeDto();
        dto.setVehicleId(1L);
        dto.setName("Test Phenomenon");

        when(weatherPhenomenonRepository.existsByNameAndVehicleId(any(String.class), any(Long.class))).thenReturn(true);
        when(vehicleRepository.existsById(any(Long.class))).thenReturn(true);

        FeeAlreadyExists exception = assertThrows(FeeAlreadyExists.class, () -> extraFeeService.registerPhenomenonExtraFee(dto));
        assertEquals("Entry with given vehicle and phenomenon already exists.", exception.getMessage());
    }

    @Test
    void testUpdatePhenomenonExtraFee() {
        PhenomenonFeeEntity entity = new PhenomenonFeeEntity();
        entity.setId(1L);

        PhenomenonExtraFeeDto dto = new PhenomenonExtraFeeDto();
        dto.setFee(BigDecimal.valueOf(100));

        when(weatherPhenomenonRepository.findById(1L)).thenReturn(Optional.of(entity));

        assertDoesNotThrow(() -> extraFeeService.updatePhenomenonExtraFee(1L, dto));
        assertEquals(BigDecimal.valueOf(100), entity.getFee());
    }

    @Test
    void testUpdatePhenomenonExtraFee_NotFound() {
        PhenomenonExtraFeeDto dto = new PhenomenonExtraFeeDto();
        when(weatherPhenomenonRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                extraFeeService.updatePhenomenonExtraFee(1L, dto));
        assertEquals("Extra fee with given ID was not found.", exception.getMessage());
    }

    @Test
    void testDeletePhenomenonExtraFee() {
        when(weatherPhenomenonRepository.existsById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> extraFeeService.deletePhenomenonFee(1L));
        verify(weatherPhenomenonRepository, times(1)).deleteById(any());
    }

    @Test
    void testDeletePhenomenonExtraFee_NotFound() {
        when(weatherPhenomenonRepository.existsById(any(Long.class))).thenReturn(false);
        assertThrows(NotFoundException.class, () -> extraFeeService.deletePhenomenonFee(1L));
        verify(windSpeedFeeRepository, times(0)).deleteById(any());
    }
}

