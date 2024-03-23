package ee.fujitsu.delivery.app.repository;

import ee.fujitsu.delivery.app.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing weather entities.
 */
@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
}
