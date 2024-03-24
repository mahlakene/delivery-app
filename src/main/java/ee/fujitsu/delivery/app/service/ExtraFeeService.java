package ee.fujitsu.delivery.app.service;

import ee.fujitsu.delivery.app.dto.NumericalExtraFeeDto;
import ee.fujitsu.delivery.app.dto.PhenomenonExtraFeeDto;
import ee.fujitsu.delivery.app.entity.extrafee.AirTemperatureFeeEntity;
import ee.fujitsu.delivery.app.entity.extrafee.PhenomenonFeeEntity;
import ee.fujitsu.delivery.app.entity.extrafee.WindSpeedFeeEntity;
import ee.fujitsu.delivery.app.exception.FeeAlreadyExists;
import ee.fujitsu.delivery.app.exception.NotFoundException;
import ee.fujitsu.delivery.app.mapper.AirTemperatureFeeMapper;
import ee.fujitsu.delivery.app.mapper.PhenomenonMapper;
import ee.fujitsu.delivery.app.mapper.WindSpeedFeeMapper;
import ee.fujitsu.delivery.app.repository.VehicleRepository;
import ee.fujitsu.delivery.app.repository.extrafee.AirTemperatureFeeRepository;
import ee.fujitsu.delivery.app.repository.extrafee.WeatherPhenomenonRepository;
import ee.fujitsu.delivery.app.repository.extrafee.WindSpeedFeeRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExtraFeeService {

    private final AirTemperatureFeeRepository airTemperatureFeeRepository;
    private final WindSpeedFeeRepository windSpeedFeeRepository;
    private final WeatherPhenomenonRepository weatherPhenomenonRepository;
    private final VehicleRepository vehicleRepository;
    private final AirTemperatureFeeMapper airTemperatureFeeMapper = Mappers.getMapper(AirTemperatureFeeMapper.class);
    private final WindSpeedFeeMapper windSpeedFeeMapper = Mappers.getMapper(WindSpeedFeeMapper.class);
    private final PhenomenonMapper phenomenonMapper = Mappers.getMapper(PhenomenonMapper.class);

    /**
     * Return all air temperature extra fees.
     * @return list of DTO-s
     */
    public List<NumericalExtraFeeDto> getAllAirTemperatureExtraFees() {
        return airTemperatureFeeMapper.toDtoList(airTemperatureFeeRepository.findAll());
    }

    /**
     * Register new air temperature extra fee.
     * If the given range of temperature conflict with some database entry, exception is thrown.
     * @param extraFeeDto DTO
     */
    public void registerAirTemperatureExtraFee(NumericalExtraFeeDto extraFeeDto) {
        Long vehicleId = extraFeeDto.getVehicleId();
        Float lower = extraFeeDto.getLowerBound();
        Float upper = extraFeeDto.getUpperBound();
        checkVehicleExistence(vehicleId);
        boolean exists = airTemperatureFeeRepository.existsByLowerBoundAndUpperBound(lower, upper);
        if (exists) {
            throw new FeeAlreadyExists("Given lower and upper bounds conflict with some existing fees.");
        }
        airTemperatureFeeRepository.save(airTemperatureFeeMapper.toEntity(extraFeeDto));
    }

    /**
     * Update an existing air temperature extra fee entity (only fee is updated).
     * If entry with given ID does not exist, exception is thrown.
     * @param id ID of entry to be updated
     * @param extraFeeDto DTO
     */
    public void updateAirTemperatureExtraFee(Long id, NumericalExtraFeeDto extraFeeDto) {
        Optional<AirTemperatureFeeEntity> entity = airTemperatureFeeRepository.findById(id);
        if (entity.isPresent()) {
            AirTemperatureFeeEntity item = entity.get();
            item.setFee(extraFeeDto.getFee());
            airTemperatureFeeRepository.save(item);
        } else {
            throw new NotFoundException("Extra fee with given ID was not found.");
        }
    }

    /**
     * Delete the entry with the given ID.
     * If entry with given ID does not exist, exception is thrown.
     * @param id ID
     */
    public void deleteAirTemperatureExtraFee(Long id) {
        if (airTemperatureFeeRepository.existsById(id)) {
            airTemperatureFeeRepository.deleteById(id);
        } else {
            throw new NotFoundException("Extra fee with given ID was not found.");
        }
    }

    /**
     * Return all wind speed extra fees.
     * @return list of DTO-s
     */
    public List<NumericalExtraFeeDto> getAllWindSpeedExtraFees() {
        return windSpeedFeeMapper.toDtoList(windSpeedFeeRepository.findAll());
    }

    /**
     * Register new wind speed extra fee.
     * If the given range of wind speed conflict with some database entry, exception is thrown.
     * @param extraFeeDto DTO
     */
    public void registerWindSpeedExtraFee(NumericalExtraFeeDto extraFeeDto) {
        Long vehicleId = extraFeeDto.getVehicleId();
        Float lower = extraFeeDto.getLowerBound();
        Float upper = extraFeeDto.getUpperBound();
        checkVehicleExistence(vehicleId);
        boolean exists = windSpeedFeeRepository.existsByLowerBoundAndUpperBound(lower, upper);
        if (exists) {
            throw new FeeAlreadyExists("Given lower and upper bounds conflict with some existing fees.");
        }
        windSpeedFeeRepository.save(windSpeedFeeMapper.toEntity(extraFeeDto));
    }

    /**
     * Update an existing wind speed extra fee entity (only fee is updated).
     * If entry with given ID does not exist, exception is thrown.
     * @param id ID of entry to be updated
     * @param extraFeeDto DTO
     */
    public void updateWindSpeedExtraFee(Long id, NumericalExtraFeeDto extraFeeDto) {
        Optional<WindSpeedFeeEntity> entity = windSpeedFeeRepository.findById(id);
        if (entity.isPresent()) {
            WindSpeedFeeEntity item = entity.get();
            item.setFee(extraFeeDto.getFee());
            windSpeedFeeRepository.save(item);
        } else {
            throw new NotFoundException("Extra fee with given ID was not found.");
        }
    }

    /**
     * Delete the entry with the given ID.
     * If entry with given ID does not exist, exception is thrown.
     * @param id ID
     */
    public void deleteWindSpeedExtraFee(Long id) {
        if (windSpeedFeeRepository.existsById(id)) {
            windSpeedFeeRepository.deleteById(id);
        } else {
            throw new NotFoundException("Extra fee with given ID was not found.");
        }
    }

    /**
     * Return all weather phenomenon extra fees.
     * @return list of DTO-s
     */
    public List<PhenomenonExtraFeeDto> getAllPhenomenonExtraFees() {
        return phenomenonMapper.toDtoList(weatherPhenomenonRepository.findAll());
    }

    /**
     * Register new weather phenomenon extra fee.
     * If the given combination of phenomenon and vehicle already exist, exception is thrown.
     * @param extraFeeDto DTO
     */
    public void registerPhenomenonExtraFee(PhenomenonExtraFeeDto extraFeeDto) {
        Long vehicleId = extraFeeDto.getVehicleId();
        String phenomenon = extraFeeDto.getName();
        checkVehicleExistence(vehicleId);
        boolean exists = weatherPhenomenonRepository.existsByNameAndVehicleId(phenomenon, vehicleId);
        if (exists) {
            throw new FeeAlreadyExists("Entry with given vehicle and phenomenon already exists.");
        }
        weatherPhenomenonRepository.save(phenomenonMapper.toEntity(extraFeeDto));
    }

    /**
     * Update an existing weather phenomenon extra fee entity (only fee is updated).
     * If entry with given ID does not exist, exception is thrown.
     * @param id ID of entry to be updated
     * @param extraFeeDto DTO
     */
    public void updatePhenomenonExtraFee(Long id, PhenomenonExtraFeeDto extraFeeDto) {
        Optional<PhenomenonFeeEntity> entity = weatherPhenomenonRepository.findById(id);
        if (entity.isPresent()) {
            PhenomenonFeeEntity item = entity.get();
            item.setFee(extraFeeDto.getFee());
            weatherPhenomenonRepository.save(item);
        } else {
            throw new NotFoundException("Extra fee with given ID was not found.");
        }
    }

    /**
     * Delete the entry with the given ID.
     * If entry with given ID does not exist, exception is thrown.
     * @param id ID
     */
    public void deletePhenomenonFee(Long id) {
        if (weatherPhenomenonRepository.existsById(id)) {
            weatherPhenomenonRepository.deleteById(id);
        } else {
            throw new NotFoundException("Extra fee with given ID was not found.");
        }
    }


    private void checkVehicleExistence(Long vehicleId) {
        if (!vehicleRepository.existsById(vehicleId)) {
            throw new NotFoundException("Given vehicle is not found from the database.");
        }
    }
}
