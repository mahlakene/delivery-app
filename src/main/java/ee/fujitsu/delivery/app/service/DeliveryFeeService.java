package ee.fujitsu.delivery.app.service;

import ee.fujitsu.delivery.app.dto.DeliveryFeeDto;
import ee.fujitsu.delivery.app.dto.WeatherDto;
import ee.fujitsu.delivery.app.entity.BaseFeeEntity;
import ee.fujitsu.delivery.app.entity.CityEntity;
import ee.fujitsu.delivery.app.entity.extrafee.AirTemperatureFeeEntity;
import ee.fujitsu.delivery.app.entity.extrafee.PhenomenonFeeEntity;
import ee.fujitsu.delivery.app.entity.extrafee.WindSpeedFeeEntity;
import ee.fujitsu.delivery.app.exception.ForbiddenVehicleException;
import ee.fujitsu.delivery.app.exception.NotFoundException;
import ee.fujitsu.delivery.app.repository.BaseFeeRepository;
import ee.fujitsu.delivery.app.repository.CityRepository;
import ee.fujitsu.delivery.app.repository.VehicleRepository;
import ee.fujitsu.delivery.app.repository.extrafee.AirTemperatureFeeRepository;
import ee.fujitsu.delivery.app.repository.extrafee.WeatherPhenomenonRepository;
import ee.fujitsu.delivery.app.repository.extrafee.WindSpeedFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service class for calculating delivery fees.
 */
@Service
@RequiredArgsConstructor
public class DeliveryFeeService {

    private final BaseFeeRepository baseFeeRepository;
    private final CityRepository cityRepository;
    private final VehicleRepository vehicleRepository;
    private final WeatherService weatherService;
    private final AirTemperatureFeeRepository airTemperatureFeeRepository;
    private final WeatherPhenomenonRepository weatherPhenomenonRepository;
    private final WindSpeedFeeRepository windSpeedFeeRepository;
    private static final String FORBIDDEN_VEHICLE_MESSAGE = "Usage of selected vehicle type is forbidden";

    /**
     * Calculates the delivery fee based on the provided parameters.
     *
     * @param cityId     the ID of the city
     * @param vehicleId  the ID of the vehicle
     * @param dateTime   the date and time of the delivery
     * @return Delivery fee DTO
     * @throws NotFoundException        if the base fee or city or vehicle does not exist in the database
     * @throws ForbiddenVehicleException if the vehicle is forbidden for some reason
     */
    public DeliveryFeeDto calculateDeliveryFee(Long cityId, Long vehicleId, LocalDateTime dateTime) {
        checkIdsExistence(cityId, vehicleId);
        DeliveryFeeDto result = new DeliveryFeeDto();
        Optional<BaseFeeEntity> baseFeeEntity = baseFeeRepository.findByCityIdAndVehicleId(cityId, vehicleId);
        if (baseFeeEntity.isEmpty()) {
            throw new NotFoundException("Base fee doesn't exist in the database.");
        }
        BigDecimal baseFee = baseFeeEntity.get().getFee();
        BigDecimal extraFee = calculateExtraFees(cityId, vehicleId, dateTime);
        result.setCityId(cityId);
        result.setVehicleId(vehicleId);
        result.setBaseFee(baseFee.setScale(2, RoundingMode.HALF_UP));
        result.setExtraFee(extraFee.setScale(2, RoundingMode.HALF_UP));
        result.setTotalFee(baseFee.add(extraFee).setScale(2, RoundingMode.HALF_UP));
        return result;
    }

    /**
     * Calculates the extra fees based on the city, vehicle, and date/time of delivery.
     *
     * @param cityId    the ID of the city
     * @param vehicleId the ID of the vehicle
     * @param dateTime  the date and time of the delivery
     * @return the total extra fees
     * @throws NotFoundException if the city does not exist in the database
     */
    private BigDecimal calculateExtraFees(Long cityId, Long vehicleId, LocalDateTime dateTime) {
        Integer wmoCode;
        Optional<CityEntity> cityEntity = cityRepository.findById(cityId);
        if (cityEntity.isPresent()) {
            wmoCode = cityEntity.get().getWmoCode();
        } else {
            throw new NotFoundException("Unable to find city.");
        }
        WeatherDto weatherDto = weatherService.getWeatherInfo(wmoCode, dateTime);
        BigDecimal airTemperatureFee = getAirTemperatureFee(vehicleId, weatherDto.getAirTemperature());
        BigDecimal windSpeedFee = getWindSpeedFee(vehicleId, weatherDto.getWindSpeed());
        BigDecimal phenomenonFee = getPhenomenonFee(vehicleId, weatherDto.getPhenomenon());
        return airTemperatureFee.add(windSpeedFee).add(phenomenonFee);
    }

    /**
     * Retrieves the air temperature fee based on the vehicle and temperature.
     *
     * @param vehicleId   the ID of the vehicle
     * @param temperature the air temperature
     * @return the air temperature fee
     * @throws ForbiddenVehicleException if the vehicle is forbidden
     */
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

    /**
     * Retrieves the wind speed fee based on the vehicle and wind speed.
     *
     * @param vehicleId the ID of the vehicle
     * @param windSpeed the wind speed
     * @return the wind speed fee
     * @throws ForbiddenVehicleException if the vehicle is forbidden
     */
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

    /**
     * Retrieves the phenomenon fee based on the vehicle and phenomenon.
     *
     * @param vehicleId  the ID of the vehicle
     * @param phenomenon the weather phenomenon
     * @return the phenomenon fee
     * @throws ForbiddenVehicleException if the vehicle is forbidden
     */
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

    /**
     * Checks if the city and vehicle IDs exist in the database.
     *
     * @param cityId    the ID of the city
     * @param vehicleId the ID of the vehicle
     * @throws NotFoundException if the city or vehicle does not exist in the database
     */
    private void checkIdsExistence(Long cityId, Long vehicleId) {
        if (!cityRepository.existsById(cityId)) {
            throw new NotFoundException("City ID doesn't exist in the database.");
        }
        if (!vehicleRepository.existsById(vehicleId)) {
            throw new NotFoundException("Vehicle ID doesn't exist in the database.");
        }
    }
}
