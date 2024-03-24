package ee.fujitsu.delivery.app.mapper;

import ee.fujitsu.delivery.app.dto.PhenomenonExtraFeeDto;
import ee.fujitsu.delivery.app.entity.extrafee.PhenomenonFeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhenomenonMapper {

    List<PhenomenonExtraFeeDto> toDtoList(List<PhenomenonFeeEntity> entities);

    PhenomenonFeeEntity toEntity(PhenomenonExtraFeeDto dto);
}
