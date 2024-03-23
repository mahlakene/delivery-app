package ee.fujitsu.delivery.app.config;

import ee.fujitsu.delivery.app.entity.BaseFeeEntity;
import ee.fujitsu.delivery.app.entity.CityEntity;
import ee.fujitsu.delivery.app.entity.VehicleEntity;
import ee.fujitsu.delivery.app.entity.extrafee.AirTemperatureFeeEntity;
import ee.fujitsu.delivery.app.entity.extrafee.PhenomenonFeeEntity;
import ee.fujitsu.delivery.app.entity.extrafee.WindSpeedFeeEntity;
import ee.fujitsu.delivery.app.repository.BaseFeeRepository;
import ee.fujitsu.delivery.app.repository.CityRepository;
import ee.fujitsu.delivery.app.repository.VehicleRepository;
import ee.fujitsu.delivery.app.repository.WeatherRepository;
import ee.fujitsu.delivery.app.repository.extrafee.AirTemperatureFeeRepository;
import ee.fujitsu.delivery.app.repository.extrafee.WeatherPhenomenonRepository;
import ee.fujitsu.delivery.app.repository.extrafee.WindSpeedFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final CityRepository cityRepository;
    private final VehicleRepository vehicleRepository;
    private final BaseFeeRepository baseFeeRepository;
    private final AirTemperatureFeeRepository airTemperatureFeeRepository;
    private final WindSpeedFeeRepository windSpeedFeeRepository;
    private final WeatherPhenomenonRepository weatherPhenomenonRepository;


    public void run(ApplicationArguments args) {
        createCities();
        createVehicles();
        createBaseFees();
        createExtraFees();
    }

    private void createCities() {
        CityEntity newCity = new CityEntity();
        newCity.setName("Tallinn");
        newCity.setWeatherStationWmo(26038);
        cityRepository.save(newCity);

        CityEntity newCity2 = new CityEntity();
        newCity2.setName("Tartu");
        newCity2.setWeatherStationWmo(26242);
        cityRepository.save(newCity2);

        CityEntity newCity3 = new CityEntity();
        newCity3.setName("Pärnu");
        newCity3.setWeatherStationWmo(41803);
        cityRepository.save(newCity3);
    }

    private void createVehicles() {
        VehicleEntity newVehicle = new VehicleEntity();
        newVehicle.setName("Car");
        vehicleRepository.save(newVehicle);

        VehicleEntity newVehicle2 = new VehicleEntity();
        newVehicle2.setName("Scooter");
        vehicleRepository.save(newVehicle2);

        VehicleEntity newVehicle3 = new VehicleEntity();
        newVehicle3.setName("Bike");
        vehicleRepository.save(newVehicle3);
    }

    private void createBaseFees() {
        makeBaseFeeEntity("Tallinn", "Car", BigDecimal.valueOf(4));
        makeBaseFeeEntity("Tallinn", "Scooter", BigDecimal.valueOf(3.5));
        makeBaseFeeEntity("Tallinn", "Bike", BigDecimal.valueOf(3));
        makeBaseFeeEntity("Tartu", "Car", BigDecimal.valueOf(3.5));
        makeBaseFeeEntity("Tartu", "Scooter", BigDecimal.valueOf(3));
        makeBaseFeeEntity("Tartu", "Bike", BigDecimal.valueOf(2.5));
        makeBaseFeeEntity("Pärnu", "Car", BigDecimal.valueOf(3));
        makeBaseFeeEntity("Pärnu", "Scooter", BigDecimal.valueOf(2.5));
        makeBaseFeeEntity("Pärnu", "Bike", BigDecimal.valueOf(2));
    }

    private void makeBaseFeeEntity(String city, String car, BigDecimal fee) {
        BaseFeeEntity newBaseFee = new BaseFeeEntity();
        newBaseFee.setCityId(cityRepository.findByName(city).getId());
        newBaseFee.setVehicleId(vehicleRepository.findByName(car).getId());
        newBaseFee.setFee(fee);
        baseFeeRepository.save(newBaseFee);
    }

    private void createExtraFees() {
        Long scooterId = vehicleRepository.findByName("Scooter").getId();
        Long bikeId = vehicleRepository.findByName("Bike").getId();
        makeAirTemperatureExtraFee(scooterId, -200F, -10F, BigDecimal.valueOf(1));
        makeAirTemperatureExtraFee(bikeId, -200F, -10F, BigDecimal.valueOf(1));
        makeAirTemperatureExtraFee(scooterId, -10F, 0F, BigDecimal.valueOf(0.5));
        makeAirTemperatureExtraFee(bikeId, -10F, 0F, BigDecimal.valueOf(0.5));

        makeWindSpeedExtraFee(bikeId, 10F, 20F, BigDecimal.valueOf(0.5));

        makeWeatherPhenomenonExtraFee(scooterId, "Snow", BigDecimal.valueOf(1));
        makeWeatherPhenomenonExtraFee(scooterId, "Sleet", BigDecimal.valueOf(1));
        makeWeatherPhenomenonExtraFee(bikeId, "Snow", BigDecimal.valueOf(1));
        makeWeatherPhenomenonExtraFee(bikeId, "Sleet", BigDecimal.valueOf(1));
        makeWeatherPhenomenonExtraFee(scooterId, "Rain", BigDecimal.valueOf(0.5));
        makeWeatherPhenomenonExtraFee(bikeId, "Rain", BigDecimal.valueOf(0.5));
    }

    private void makeAirTemperatureExtraFee(Long vehicleId, Float lowerBound, Float upperBound, BigDecimal fee) {
        AirTemperatureFeeEntity newFee = new AirTemperatureFeeEntity();
        newFee.setVehicleId(vehicleId);
        newFee.setLowerBound(lowerBound);
        newFee.setUpperBound(upperBound);
        newFee.setFee(fee);
        airTemperatureFeeRepository.save(newFee);
    }

    private void makeWindSpeedExtraFee(Long vehicleId, Float lowerBound, Float upperBound, BigDecimal fee) {
        WindSpeedFeeEntity newFee = new WindSpeedFeeEntity();
        newFee.setVehicleId(vehicleId);
        newFee.setLowerBound(lowerBound);
        newFee.setUpperBound(upperBound);
        newFee.setFee(fee);
        windSpeedFeeRepository.save(newFee);
    }

    private void makeWeatherPhenomenonExtraFee(Long vehicleId, String phenomenon, BigDecimal fee) {
        PhenomenonFeeEntity newFee = new PhenomenonFeeEntity();
        newFee.setVehicleId(vehicleId);
        newFee.setName(phenomenon);
        newFee.setFee(fee);
        weatherPhenomenonRepository.save(newFee);
    }
}
