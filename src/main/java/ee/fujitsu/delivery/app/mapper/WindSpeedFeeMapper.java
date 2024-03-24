package ee.fujitsu.delivery.app.mapper;

import ee.fujitsu.delivery.app.dto.NumericalExtraFeeDto;
import ee.fujitsu.delivery.app.entity.extrafee.WindSpeedFeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WindSpeedFeeMapper {
    List<NumericalExtraFeeDto> toDtoList(List<WindSpeedFeeEntity> entities);

    WindSpeedFeeEntity toEntity(NumericalExtraFeeDto dto);
}
