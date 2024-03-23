package ee.fujitsu.delivery.app.repository.extrafee;

import ee.fujitsu.delivery.app.entity.extrafee.PhenomenonFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface WeatherPhenomenonRepository  extends JpaRepository<PhenomenonFeeEntity, Long> {

    @Query("SELECT a.fee FROM PhenomenonFeeEntity a " +
            "WHERE :phenomenon = a.name " +
            "AND :vehicleId = a.vehicleId")
    Optional<BigDecimal> findFeeByPhenomenonAndVehicle(String phenomenon, Long vehicleId);
}
