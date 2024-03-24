package ee.fujitsu.delivery.app.repository.extrafee;

import ee.fujitsu.delivery.app.entity.extrafee.AirTemperatureFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AirTemperatureFeeRepository  extends JpaRepository<AirTemperatureFeeEntity, Long> {

    @Query("SELECT a FROM AirTemperatureFeeEntity a " +
            "WHERE :temperature >= a.lowerBound AND :temperature < a.upperBound " +
            "AND :vehicleId = a.vehicleId")
    Optional<AirTemperatureFeeEntity> findByTemperatureAndVehicle(Float temperature, Long vehicleId);
}
