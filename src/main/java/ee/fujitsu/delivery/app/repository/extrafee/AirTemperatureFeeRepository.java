package ee.fujitsu.delivery.app.repository.extrafee;

import ee.fujitsu.delivery.app.entity.extrafee.AirTemperatureFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface AirTemperatureFeeRepository  extends JpaRepository<AirTemperatureFeeEntity, Long> {

    @Query("SELECT a.fee FROM AirTemperatureFeeEntity a " +
            "WHERE :temperature BETWEEN a.lowerBound AND a.upperBound " +
            "AND :vehicleId = a.vehicleId")
    Optional<BigDecimal> findFeeByTemperatureAndVehicle(Float temperature, Long vehicleId);
}
