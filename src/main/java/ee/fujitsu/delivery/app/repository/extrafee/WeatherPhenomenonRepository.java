package ee.fujitsu.delivery.app.repository.extrafee;

import ee.fujitsu.delivery.app.entity.extrafee.PhenomenonFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WeatherPhenomenonRepository  extends JpaRepository<PhenomenonFeeEntity, Long> {

    @Query("SELECT a FROM PhenomenonFeeEntity a " +
            "WHERE LOWER(:phenomenon) LIKE CONCAT('%', LOWER(a.name), '%')" +
            "AND :vehicleId = a.vehicleId")
    Optional<PhenomenonFeeEntity> findByPhenomenonAndVehicle(String phenomenon, Long vehicleId);
}
