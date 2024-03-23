package ee.fujitsu.delivery.app.repository;

import ee.fujitsu.delivery.app.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Vehicle entities.
 */
@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    VehicleEntity findByName(String name);
}
