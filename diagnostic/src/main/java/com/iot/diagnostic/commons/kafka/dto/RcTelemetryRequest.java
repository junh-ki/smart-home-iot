package com.iot.diagnostic.commons.kafka.dto;

public record RcTelemetryRequest(double throttleVoltage, double steeringVoltage,
                                 double liPoVoltageCell1, double liPoVoltageCell2) {}
