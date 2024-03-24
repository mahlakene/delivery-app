package ee.fujitsu.delivery.app.mapper;

import ee.fujitsu.delivery.app.dto.Station;
import ee.fujitsu.delivery.app.dto.WeatherDto;
import ee.fujitsu.delivery.app.entity.WeatherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WeatherMapper {

    WeatherEntity toEntity(Station station);

    WeatherDto toDto(WeatherEntity weatherEntity);
}
