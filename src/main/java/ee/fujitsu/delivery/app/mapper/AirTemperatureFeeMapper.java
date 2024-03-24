package ee.fujitsu.delivery.app.mapper;

import ee.fujitsu.delivery.app.dto.NumericalExtraFeeDto;
import ee.fujitsu.delivery.app.entity.extrafee.AirTemperatureFeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AirTemperatureFeeMapper {

    List<NumericalExtraFeeDto> toDtoList(List<AirTemperatureFeeEntity> entities);

    AirTemperatureFeeEntity toEntity(NumericalExtraFeeDto dto);

}
