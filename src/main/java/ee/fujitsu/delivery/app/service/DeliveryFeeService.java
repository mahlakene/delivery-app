package ee.fujitsu.delivery.app.service;

import ee.fujitsu.delivery.app.dto.WeatherDto;
import ee.fujitsu.delivery.app.entity.BaseFeeEntity;
import ee.fujitsu.delivery.app.entity.CityEntity;
import ee.fujitsu.delivery.app.entity.extrafee.AirTemperatureFeeEntity;
import ee.fujitsu.delivery.app.repository.BaseFeeRepository;
import ee.fujitsu.delivery.app.repository.CityRepository;
import ee.fujitsu.delivery.app.repository.extrafee.AirTemperatureFeeRepository;
import ee.fujitsu.delivery.app.repository.extrafee.WeatherPhenomenonRepository;
import ee.fujitsu.delivery.app.repository.extrafee.WindSpeedFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
            wmoCode = cityEntity.get().getWeatherStationWmo();
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
        Optional<BigDecimal> fee = airTemperatureFeeRepository.findFeeByTemperatureAndVehicle(temperature, vehicleId);
        return fee.orElseGet(() -> BigDecimal.valueOf(0));
    }

    private BigDecimal getWindSpeedFee(Long vehicleId, Float windSpeed) {
        Optional<BigDecimal> fee = windSpeedFeeRepository.findFeeByWindSpeedAndVehicle(windSpeed, vehicleId);
        return fee.orElseGet(() -> BigDecimal.valueOf(0));
    }

    private BigDecimal getPhenomenonFee(Long vehicleId, String phenomenon) {
        Optional<BigDecimal> fee = weatherPhenomenonRepository.findFeeByPhenomenonAndVehicle(phenomenon, vehicleId);
        return fee.orElseGet(() -> BigDecimal.valueOf(0));
    }
}
