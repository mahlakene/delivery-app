package ee.fujitsu.delivery.app.service;

import ee.fujitsu.delivery.app.dto.BaseFeeDto;
import ee.fujitsu.delivery.app.entity.BaseFeeEntity;
import ee.fujitsu.delivery.app.exception.FeeAlreadyExists;
import ee.fujitsu.delivery.app.exception.NotFoundException;
import ee.fujitsu.delivery.app.mapper.BaseFeeMapper;
import ee.fujitsu.delivery.app.repository.BaseFeeRepository;
import ee.fujitsu.delivery.app.repository.CityRepository;
import ee.fujitsu.delivery.app.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseFeeService {

    private final BaseFeeRepository baseFeeRepository;
    private final CityRepository cityRepository;
    private final VehicleRepository vehicleRepository;
    private final BaseFeeMapper baseFeeMapper = Mappers.getMapper(BaseFeeMapper.class);

    /**
     * Return all base fees from the database (DTO-s).
     * @return List of DTO-s
     */
    public List<BaseFeeDto> getAllBaseFees() {
        return baseFeeMapper.toDtoList(baseFeeRepository.findAll());
    }

    /**
     * Register new entry into the base fee table.
     * If base fee of the given city and vehicle is already registered before, exception is thrown.
     * @param baseFeeDto DTO
     */
    public void registerBaseFee(BaseFeeDto baseFeeDto) {
        Long cityId = baseFeeDto.getCityId();
        Long vehicleId = baseFeeDto.getVehicleId();
        checkIdsExistence(cityId, vehicleId);
        Optional<BaseFeeEntity> entity = baseFeeRepository.findByCityIdAndVehicleId(cityId, vehicleId);
        if (entity.isEmpty()) {
            baseFeeRepository.save(baseFeeMapper.toEntity(baseFeeDto));
        } else {
            throw new FeeAlreadyExists("Base fee of this city and vehicle already exists.");
        }
    }

    /**
     * Update base fee of existing table entry.
     * If base fee with given ID does not exist, exception is thrown.
     * @param id ID of the entry that will be modified
     * @param baseFeeDto DTO
     */
    public void updateBaseFee(Long id, BaseFeeDto baseFeeDto) {
        Long cityId = baseFeeDto.getCityId();
        Long vehicleId = baseFeeDto.getVehicleId();
        checkIdsExistence(cityId, vehicleId);
        Optional<BaseFeeEntity> entity = baseFeeRepository.findById(id);
        if (entity.isEmpty()) {
            throw new NotFoundException("Base fee with given ID does is not found.");
        } else {
            BaseFeeEntity item = entity.get();
            item.setFee(baseFeeDto.getFee());
            baseFeeRepository.save(item);
        }
    }

    /**
     * Delete the entry with the given ID from the database.
     * If entry with given ID does not exist, exception is thrown.
     * @param id ID of the base fee entry
     */
    public void deleteBaseFee(Long id) {
        if (baseFeeRepository.existsById(id)) {
            baseFeeRepository.deleteById(id);
        } else {
            throw new NotFoundException("Base fee with given ID does is not found.");
        }
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
