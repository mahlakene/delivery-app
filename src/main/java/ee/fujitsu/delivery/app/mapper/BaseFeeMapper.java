package ee.fujitsu.delivery.app.mapper;

import ee.fujitsu.delivery.app.dto.BaseFeeDto;
import ee.fujitsu.delivery.app.entity.BaseFeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BaseFeeMapper {

    List<BaseFeeDto> toDtoList(List<BaseFeeEntity> baseFeeEntities);

    BaseFeeEntity toEntity(BaseFeeDto baseFeeDto);
}
