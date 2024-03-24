package ee.fujitsu.delivery.app.service;

import ee.fujitsu.delivery.app.dto.DeliveryFeeDto;
import ee.fujitsu.delivery.app.dto.WeatherDto;
import ee.fujitsu.delivery.app.entity.BaseFeeEntity;
import ee.fujitsu.delivery.app.entity.CityEntity;
import ee.fujitsu.delivery.app.entity.extrafee.AirTemperatureFeeEntity;
import ee.fujitsu.delivery.app.entity.extrafee.PhenomenonFeeEntity;
import ee.fujitsu.delivery.app.entity.extrafee.WindSpeedFeeEntity;
import ee.fujitsu.delivery.app.exception.ForbiddenVehicleException;
import ee.fujitsu.delivery.app.repository.BaseFeeRepository;
import ee.fujitsu.delivery.app.repository.CityRepository;
import ee.fujitsu.delivery.app.repository.VehicleRepository;
import ee.fujitsu.delivery.app.repository.extrafee.AirTemperatureFeeRepository;
import ee.fujitsu.delivery.app.repository.extrafee.WeatherPhenomenonRepository;
import ee.fujitsu.delivery.app.repository.extrafee.WindSpeedFeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeliveryFeeServiceTest {

    @Mock
    private BaseFeeRepository baseFeeRepository;
    @Mock
    private WindSpeedFeeRepository windSpeedFeeRepository;
    @Mock
    private AirTemperatureFeeRepository airTemperatureFeeRepository;
    @Mock
    private WeatherPhenomenonRepository weatherPhenomenonRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @InjectMocks
    private DeliveryFeeService deliveryFeeService;
    @Mock
    private WeatherService weatherService;

    @BeforeEach
    public void setUp() {
        BaseFeeEntity carBaseFeeEntity = new BaseFeeEntity();
        carBaseFeeEntity.setCityId(1L);
        carBaseFeeEntity.setVehicleId(1L);
        carBaseFeeEntity.setFee(BigDecimal.valueOf(4));
        when(baseFeeRepository.findByCityIdAndVehicleId(1L,1L)).thenReturn(Optional.of(carBaseFeeEntity));

        when(baseFeeRepository.findByCityIdAndVehicleId(1L,1L)).thenReturn(Optional.of(carBaseFeeEntity));
        when(cityRepository.existsById(any())).thenReturn(true);
        when(vehicleRepository.existsById(any())).thenReturn(true);
        when(cityRepository.findById(any())).thenReturn(Optional.of(new CityEntity()));
    }

    private AirTemperatureFeeEntity createATFEntity(Float lower, Float upper,
                                                    Long vehicleId, BigDecimal fee) {
        AirTemperatureFeeEntity entity = new AirTemperatureFeeEntity();
        entity.setLowerBound(lower);
        entity.setUpperBound(upper);
        entity.setVehicleId(vehicleId);
        entity.setFee(fee);
        return entity;
    }

    private WindSpeedFeeEntity createWSFEntity(Float lower, Float upper,
                                               Long vehicleId, BigDecimal fee) {
        WindSpeedFeeEntity entity = new WindSpeedFeeEntity();
        entity.setLowerBound(lower);
        entity.setUpperBound(upper);
        entity.setVehicleId(vehicleId);
        entity.setFee(fee);
        return entity;
    }

    private PhenomenonFeeEntity createWPFEntity(String name, Long vehicleId,
                                                BigDecimal fee, boolean forbidden) {
        PhenomenonFeeEntity entity = new PhenomenonFeeEntity();
        entity.setName(name);
        entity.setVehicleId(vehicleId);
        entity.setFee(fee);
        entity.setForbidden(forbidden);
        return entity;
    }

    @Test
    void calculateDeliveryFee_OnlyBaseFee() {
        WeatherDto weatherDto = new WeatherDto();
        when(weatherService.getWeatherInfo(any(), any())).thenReturn(weatherDto);

        DeliveryFeeDto result = deliveryFeeService.calculateDeliveryFee(1L, 1L, null);

        assertEquals(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP), result.getExtraFee());
        assertEquals(BigDecimal.valueOf(4).setScale(2, RoundingMode.HALF_UP), result.getBaseFee());
        assertEquals(BigDecimal.valueOf(4).setScale(2, RoundingMode.HALF_UP), result.getTotalFee());
    }

    @Test
    void calculateDeliveryFee_WindSpeedFee() {
        WindSpeedFeeEntity WSFEntity1 = createWSFEntity(10F, 20F, 3L, BigDecimal.valueOf(0.5));
        when(windSpeedFeeRepository.findByWindSpeedAndVehicle(any(), any())).thenReturn(Optional.of(WSFEntity1));
        WeatherDto weatherDto = new WeatherDto();
        when(weatherService.getWeatherInfo(any(), any())).thenReturn(weatherDto);

        DeliveryFeeDto result = deliveryFeeService.calculateDeliveryFee(1L, 1L, null);

        assertEquals(BigDecimal.valueOf(0.5).setScale(2, RoundingMode.HALF_UP), result.getExtraFee());
        assertEquals(BigDecimal.valueOf(4).setScale(2, RoundingMode.HALF_UP), result.getBaseFee());
        assertEquals(BigDecimal.valueOf(4.5).setScale(2, RoundingMode.HALF_UP), result.getTotalFee());
    }

    @Test
    void calculateDeliveryFee_AirTemperatureFee() {
        AirTemperatureFeeEntity ATFEntity1 = createATFEntity(-200F, -10F, 3L, BigDecimal.valueOf(1));
        when(airTemperatureFeeRepository.findByTemperatureAndVehicle(any(), any())).thenReturn(Optional.of(ATFEntity1));
        WeatherDto weatherDto = new WeatherDto();
        when(weatherService.getWeatherInfo(any(), any())).thenReturn(weatherDto);

        DeliveryFeeDto result = deliveryFeeService.calculateDeliveryFee(1L, 1L, null);

        assertEquals(BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP), result.getExtraFee());
        assertEquals(BigDecimal.valueOf(4).setScale(2, RoundingMode.HALF_UP),result.getBaseFee());
        assertEquals(BigDecimal.valueOf(5).setScale(2, RoundingMode.HALF_UP), result.getTotalFee());
    }

    @Test
    void calculateDeliveryFee_WeatherPhenomenonFee() {
        PhenomenonFeeEntity WPFEntity1 = createWPFEntity("Snow", 3L, BigDecimal.valueOf(2.5), false);
        when(weatherPhenomenonRepository.findByPhenomenonAndVehicle(any(), any())).thenReturn(Optional.of(WPFEntity1));
        WeatherDto weatherDto = new WeatherDto();
        when(weatherService.getWeatherInfo(any(), any())).thenReturn(weatherDto);

        DeliveryFeeDto result = deliveryFeeService.calculateDeliveryFee(1L, 1L, null);

        assertEquals(BigDecimal.valueOf(2.5).setScale(2, RoundingMode.HALF_UP), result.getExtraFee());
        assertEquals(BigDecimal.valueOf(4).setScale(2, RoundingMode.HALF_UP), result.getBaseFee());
        assertEquals(BigDecimal.valueOf(6.5).setScale(2, RoundingMode.HALF_UP), result.getTotalFee());
    }

    @Test
    void calculateDeliveryFee_AllExtraFees() {
        WindSpeedFeeEntity WSFEntity1 = createWSFEntity(10F, 20F, 3L, BigDecimal.valueOf(1.25));
        when(windSpeedFeeRepository.findByWindSpeedAndVehicle(any(), any())).thenReturn(Optional.of(WSFEntity1));
        AirTemperatureFeeEntity ATFEntity1 = createATFEntity(-200F, -10F, 3L, BigDecimal.valueOf(1.75));
        when(airTemperatureFeeRepository.findByTemperatureAndVehicle(any(), any())).thenReturn(Optional.of(ATFEntity1));
        PhenomenonFeeEntity WPFEntity1 = createWPFEntity("Snow", 3L, BigDecimal.valueOf(3), false);
        when(weatherPhenomenonRepository.findByPhenomenonAndVehicle(any(), any())).thenReturn(Optional.of(WPFEntity1));
        WeatherDto weatherDto = new WeatherDto();
        when(weatherService.getWeatherInfo(any(), any())).thenReturn(weatherDto);

        DeliveryFeeDto result = deliveryFeeService.calculateDeliveryFee(1L, 1L, null);

        assertEquals(BigDecimal.valueOf(6).setScale(2, RoundingMode.HALF_UP), result.getExtraFee());
        assertEquals(BigDecimal.valueOf(4).setScale(2, RoundingMode.HALF_UP), result.getBaseFee());
        assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP), result.getTotalFee());
    }

    @Test
    void testForbiddenVehicleType() {
        PhenomenonFeeEntity WPFEntity1 = createWPFEntity("Glaze", 2L, BigDecimal.valueOf(0), true);
        when(weatherPhenomenonRepository.findByPhenomenonAndVehicle(any(), any())).thenReturn(Optional.of(WPFEntity1));
        WeatherDto weatherDto = new WeatherDto();
        when(weatherService.getWeatherInfo(any(), any())).thenReturn(weatherDto);

        ForbiddenVehicleException exception = assertThrows(ForbiddenVehicleException.class, () ->
                deliveryFeeService.calculateDeliveryFee(1L, 1L, null));
        assertEquals("Usage of selected vehicle type is forbidden", exception.getMessage());
    }
}