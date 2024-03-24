package ee.fujitsu.delivery.app.repository.extrafee;

import ee.fujitsu.delivery.app.entity.extrafee.WindSpeedFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository interface for managing wind speed extra fee entities.
 */
public interface WindSpeedFeeRepository  extends JpaRepository<WindSpeedFeeEntity, Long> {

    /**
     * Finds the right extra fee based on given temperature and vehicle.
     */
    @Query("SELECT a FROM WindSpeedFeeEntity a " +
            "WHERE :windSpeed >= a.lowerBound AND :windSpeed < a.upperBound " +
            "AND :vehicleId = a.vehicleId")
    Optional<WindSpeedFeeEntity> findByWindSpeedAndVehicle(Float windSpeed, Long vehicleId);

    /**
     * Checks if given lower or upper bound is in the range of some existing entry.
     */
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM AirTemperatureFeeEntity a " +
            "WHERE (a.lowerBound >= :lower AND a.lowerBound <= :upper) " +
            "OR (a.upperBound >= :lower AND a.upperBound <= :upper)")
    boolean existsByLowerBoundAndUpperBound(Float lower, Float upper);
}
