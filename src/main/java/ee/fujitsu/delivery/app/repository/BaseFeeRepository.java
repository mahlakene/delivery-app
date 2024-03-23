package ee.fujitsu.delivery.app.repository;

import ee.fujitsu.delivery.app.entity.BaseFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing base fees on different city and vehicle combinations.
 */
@Repository
public interface BaseFeeRepository extends JpaRepository<BaseFeeEntity, Long> {

    BaseFeeEntity findByCityIdAndVehicleId(Long cityId, Long vehicleId);
}
