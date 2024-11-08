package ru.acgnn.grpc_area_service.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.acgnn.grpc_area_service.exception.ApiServiceException;
import ru.acgnn.grpc_area_service.model.entity.AreaEntity;
import ru.acgnn.grpc_area_service.repository.AreaRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AreaService {

    private final AreaRepository areaRepo;

    public AreaEntity getById(Integer id) {
        return areaRepo.findById(id)
            .orElseThrow(() -> new ApiServiceException("Площадка не найдена", HttpStatus.NOT_FOUND));
    }

    public List<AreaEntity> getAll() {
        return areaRepo.findAll();
    }
}
