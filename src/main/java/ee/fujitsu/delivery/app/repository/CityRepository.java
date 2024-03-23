package ee.fujitsu.delivery.app.repository;

import ee.fujitsu.delivery.app.entity.CityEntity;
import ee.fujitsu.delivery.app.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing City entities.
 */
@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    @Query("SELECT c.wmoCode FROM CityEntity c")
    List<Integer> findAllWeatherStationWmos();

    CityEntity findByName(String name);
}
