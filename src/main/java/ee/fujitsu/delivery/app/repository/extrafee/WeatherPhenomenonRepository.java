package ee.fujitsu.delivery.app.repository.extrafee;

import ee.fujitsu.delivery.app.entity.extrafee.PhenomenonFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository interface for managing weather phenomenon extra fee entities.
 */
public interface WeatherPhenomenonRepository  extends JpaRepository<PhenomenonFeeEntity, Long> {

    /**
     * Finds the entry that contains of given phenomenon string (case-insensitive) and matches vehicle.
     */
    @Query("SELECT a FROM PhenomenonFeeEntity a " +
            "WHERE LOWER(:phenomenon) LIKE CONCAT('%', LOWER(a.name), '%')" +
            "AND :vehicleId = a.vehicleId")
    Optional<PhenomenonFeeEntity> findByPhenomenonAndVehicle(String phenomenon, Long vehicleId);

    /**
     * Checks if entry with given phenomenon name and vehicle exists.
     */
    boolean existsByNameAndVehicleId(String name, Long vehicleId);
}
