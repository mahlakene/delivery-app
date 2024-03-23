package ee.fujitsu.delivery.app.repository;

import ee.fujitsu.delivery.app.dto.WeatherDto;
import ee.fujitsu.delivery.app.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing weather entities.
 */
@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {

    List<WeatherEntity> findAllByWmoCodeOrderByTimeStampDesc(Integer wmoCode);

    WeatherDto toDto(WeatherEntity weatherEntity);
}
