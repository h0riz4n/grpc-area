package ru.acgnn.grpc_area_service.grpc;

import org.springframework.security.access.prepost.PreAuthorize;

import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.acgnn.grpc.server.Area;
import ru.acgnn.grpc.server.AreaId;
import ru.acgnn.grpc.server.AreaList;
import ru.acgnn.grpc.server.AreaServiceGrpc.AreaServiceImplBase;
import ru.acgnn.grpc_area_service.mapper.AreaMapper;
import ru.acgnn.grpc_area_service.service.AreaService;

@GrpcService
@RequiredArgsConstructor
public class AreaGrpc extends AreaServiceImplBase {

    private final AreaService areaService;
    private final AreaMapper areaMapper;

    @Override
    @PreAuthorize("hasRole('admin')")
    public void getAreas(Empty request, StreamObserver<AreaList> responseObserver) {
        responseObserver.onNext(AreaList.newBuilder().addAllAreas(areaMapper.toGrpcAreas(areaService.getAll())).build());
        responseObserver.onCompleted();
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    public void getAreaById(AreaId request, StreamObserver<Area> responseObserver) {
        responseObserver.onNext(areaMapper.toGrpcArea(areaService.getById(request.getId())));
        responseObserver.onCompleted();
    }
}
