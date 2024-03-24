package ee.fujitsu.delivery.app.service;

import ee.fujitsu.delivery.app.dto.WeatherDto;
import ee.fujitsu.delivery.app.entity.BaseFeeEntity;
import ee.fujitsu.delivery.app.entity.CityEntity;
import ee.fujitsu.delivery.app.entity.extrafee.AirTemperatureFeeEntity;
import ee.fujitsu.delivery.app.entity.extrafee.PhenomenonFeeEntity;
import ee.fujitsu.delivery.app.entity.extrafee.WindSpeedFeeEntity;
import ee.fujitsu.delivery.app.exception.ForbiddenVehicleException;
import ee.fujitsu.delivery.app.repository.BaseFeeRepository;
import ee.fujitsu.delivery.app.repository.CityRepository;
import ee.fujitsu.delivery.app.repository.extrafee.AirTemperatureFeeRepository;
import ee.fujitsu.delivery.app.repository.extrafee.WeatherPhenomenonRepository;
import ee.fujitsu.delivery.app.repository.extrafee.WindSpeedFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryFeeService {

    private final BaseFeeRepository baseFeeRepository;
    private final CityRepository cityRepository;
    private final WeatherService weatherService;
    private final AirTemperatureFeeRepository airTemperatureFeeRepository;
    private final WeatherPhenomenonRepository weatherPhenomenonRepository;
    private final WindSpeedFeeRepository windSpeedFeeRepository;
    private static final String FORBIDDEN_VEHICLE_MESSAGE = "Usage of selected vehicle type is forbidden";

    public BigDecimal calculateDeliveryFee(Long cityId, Long vehicleId, LocalDateTime dateTime) {
        BaseFeeEntity baseFeeEntity = baseFeeRepository.findByCityIdAndVehicleId(cityId, vehicleId);
        BigDecimal baseFee = baseFeeEntity.getFee();
        BigDecimal extraFee = calculateExtraFees(cityId, vehicleId, dateTime);
        return baseFee.add(extraFee);
    }

    private BigDecimal calculateExtraFees(Long cityId, Long vehicleId, LocalDateTime dateTime) {
        Integer wmoCode;
        Optional<CityEntity> cityEntity = cityRepository.findById(cityId);
        if (cityEntity.isPresent()) {
            wmoCode = cityEntity.get().getWmoCode();
        } else {
            throw new IllegalArgumentException();
        }
        WeatherDto weatherDto = weatherService.getWeatherInfo(wmoCode, dateTime);
        BigDecimal airTemperatureFee = getAirTemperatureFee(vehicleId, weatherDto.getAirTemperature());
        BigDecimal windSpeedFee = getWindSpeedFee(vehicleId, weatherDto.getWindSpeed());
        BigDecimal phenomenonFee = getPhenomenonFee(vehicleId, weatherDto.getPhenomenon());
        return airTemperatureFee.add(windSpeedFee).add(phenomenonFee);
    }

    private BigDecimal getAirTemperatureFee(Long vehicleId, Float temperature) {
        Optional<AirTemperatureFeeEntity> entity = airTemperatureFeeRepository.findByTemperatureAndVehicle(temperature, vehicleId);
        if (entity.isPresent()) {
            AirTemperatureFeeEntity item = entity.get();
            if (item.isForbidden()) {
                throw new ForbiddenVehicleException(FORBIDDEN_VEHICLE_MESSAGE);
            }
            return item.getFee();
        }
        return BigDecimal.valueOf(0);
    }

    private BigDecimal getWindSpeedFee(Long vehicleId, Float windSpeed) {
        Optional<WindSpeedFeeEntity> entity = windSpeedFeeRepository.findByWindSpeedAndVehicle(windSpeed, vehicleId);
        if (entity.isPresent()) {
            WindSpeedFeeEntity item = entity.get();
            if (item.isForbidden()) {
                throw new ForbiddenVehicleException(FORBIDDEN_VEHICLE_MESSAGE);
            }
            return item.getFee();
        }
        return BigDecimal.valueOf(0);
    }

    private BigDecimal getPhenomenonFee(Long vehicleId, String phenomenon) {
        Optional<PhenomenonFeeEntity> entity = weatherPhenomenonRepository.findByPhenomenonAndVehicle(phenomenon, vehicleId);
        if (entity.isPresent()) {
            PhenomenonFeeEntity item = entity.get();
            if (item.isForbidden()) {
                throw new ForbiddenVehicleException(FORBIDDEN_VEHICLE_MESSAGE);
            }
            return item.getFee();
        }
        return BigDecimal.valueOf(0);
    }
}
