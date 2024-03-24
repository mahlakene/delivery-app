package ee.fujitsu.delivery.app.repository.extrafee;

import ee.fujitsu.delivery.app.entity.extrafee.AirTemperatureFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository interface for managing air temperature extra fee entities.
 */
public interface AirTemperatureFeeRepository  extends JpaRepository<AirTemperatureFeeEntity, Long> {

    /**
     * Finds the right extra fee based on given temperature and vehicle.
     */
    @Query("SELECT a FROM AirTemperatureFeeEntity a " +
            "WHERE :temperature >= a.lowerBound AND :temperature < a.upperBound " +
            "AND :vehicleId = a.vehicleId")
    Optional<AirTemperatureFeeEntity> findByTemperatureAndVehicle(Float temperature, Long vehicleId);

    /**
     * Checks if given lower or upper bound is in the range of some existing entry.
     */
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM AirTemperatureFeeEntity a " +
            "WHERE (a.lowerBound >= :lower AND a.lowerBound <= :upper) " +
            "OR (a.upperBound >= :lower AND a.upperBound <= :upper)")
    boolean existsByLowerBoundAndUpperBound(Float lower, Float upper);
}
