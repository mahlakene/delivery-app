package ee.fujitsu.delivery.app.repository.extrafee;

import ee.fujitsu.delivery.app.entity.extrafee.WindSpeedFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface WindSpeedFeeRepository  extends JpaRepository<WindSpeedFeeEntity, Long> {

    @Query("SELECT a.fee FROM WindSpeedFeeEntity a " +
            "WHERE :windSpeed >= a.lowerBound AND :windSpeed < a.upperBound " +
            "AND :vehicleId = a.vehicleId")
    Optional<BigDecimal> findFeeByWindSpeedAndVehicle(Float windSpeed, Long vehicleId);
}
