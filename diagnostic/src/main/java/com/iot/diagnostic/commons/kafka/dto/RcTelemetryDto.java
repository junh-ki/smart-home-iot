package com.iot.diagnostic.commons.kafka.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RcTelemetryDto {

    Double throttleVoltage;
    Double steeringVoltage;
    Double liPoVoltageCell1;
    Double liPoVoltageCell2;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;

}
