package ru.acgnn.grpc_area_service.model.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaDto {

    private Integer id;

    private String title;

    private LocalDateTime creationDateTime;
}
