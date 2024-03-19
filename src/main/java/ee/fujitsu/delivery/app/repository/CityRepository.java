package ee.fujitsu.delivery.app.repository;

import ee.fujitsu.delivery.app.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing City entities.
 */
@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {
}
