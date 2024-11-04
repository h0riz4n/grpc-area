package ru.acgnn.grpc_area_service.service;

import ru.acgnn.grpc.server.AreaList;

import com.google.protobuf.Empty;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.acgnn.grpc.server.Area;
import ru.acgnn.grpc.server.AreaId;
import ru.acgnn.grpc.server.AreaServiceGrpc.AreaServiceImplBase;
import ru.acgnn.grpc_area_service.exception.GrpcServiceException;
import ru.acgnn.grpc_area_service.mapper.AreaMapper;
import ru.acgnn.grpc_area_service.model.entity.AreaEntity;
import ru.acgnn.grpc_area_service.repository.AreaRepository;

@GrpcService
@RequiredArgsConstructor
public class AreaService extends AreaServiceImplBase {

    private final AreaRepository areaRepo;
    private final AreaMapper areaMapper;

    @Override
    public void getAreas(Empty request, StreamObserver<AreaList> responseObserver) {
        responseObserver.onNext(AreaList.newBuilder().addAllAreas(areaMapper.toGrpcAreas(areaRepo.findAll())).build());
        responseObserver.onCompleted();
    } 
    
    @Override
    public void getAreaById(AreaId request, StreamObserver<Area> responseObserver) {
        AreaEntity area = areaRepo.findById(request.getId())
            .orElseThrow(() -> new GrpcServiceException("Площадка не найдена", Status.NOT_FOUND));
        responseObserver.onNext(areaMapper.toGrpcArea(area));
        responseObserver.onCompleted();
    }
}
