package ru.acgnn.grpc_area_service.mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.google.protobuf.Timestamp;

import ru.acgnn.grpc.server.Area;
import ru.acgnn.grpc_area_service.model.entity.AreaEntity;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AreaMapper {

    @Mapping(source = "area.creationDateTime", target = "creationDateTime", qualifiedByName = "toTimestamp")
    Area toGrpcArea(AreaEntity area);

    @Named("toTimestamp")
    default Timestamp toTimestamp(LocalDateTime dateTime) {
        return Timestamp.newBuilder()
            .setSeconds(dateTime.atZone(ZoneId.of("Europe/Moscow")).toEpochSecond())
            .build();
    }

    List<Area> toGrpcAreas(List<AreaEntity> areas);
}
