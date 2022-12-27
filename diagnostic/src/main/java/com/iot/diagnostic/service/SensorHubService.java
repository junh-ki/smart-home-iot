package com.iot.diagnostic.service;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;

//@Service
public class SensorHubService {

    //SensorHub Address
    private static final int SENSOR_HUB_BOARD = 0x17;
    //SensorHub's functions
    private static final byte TEMP_REG = (byte) 0x01;
    private static final byte LIGHT_REG_L = (byte) 0x02;
    private static final byte LIGHT_REG_H = (byte) 0x03;
    private static final byte STATUS_REG = (byte) 0x04;
    private static final byte ON_BOARD_TEMP_REG = (byte) 0x05;
    private static final byte ON_BOARD_HUMIDITY_REG = (byte) 0x06;
    private static final byte ON_BOARD_SENSOR_ERROR = (byte) 0x07;
    private static final byte BMP280_TEMP_REG = (byte) 0x08;
    private static final byte BMP280_PRESSURE_REG_L = (byte) 0x09;
    private static final byte BMP280_PRESSURE_REG_M = (byte) 0x0A;
    private static final byte BMP280_PRESSURE_REG_H = (byte) 0X0B;
    private static final byte BMP280_STATUS = (byte) 0x0C;
    private static final byte HUMAN_DETECT = (byte) 0x0D;
    private final I2CDevice i2CDevice;

    public SensorHubService() throws IOException, UnsupportedBusNumberException {
        final I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
        this.i2CDevice = i2c.getDevice(SENSOR_HUB_BOARD);
    }

    /**
     * true: "External board has been initialized"
     * false: "External board Can not be initialized, please enable I2C and try again!"
     */
    public Boolean isExternalBoardInitialized() {
        try {
            return this.i2CDevice.read(SENSOR_HUB_BOARD) > 0;
        } catch (IOException ignored) {}
        return null;
    }

    /**
     * 0x01: off-chip temperature sensor is over-ranged!
     * 0x02: No external temperature sensor!
     * the temp of air is: + device.read(TEMP_REG) + centigrade
     * -> The conversion of Celsius to centigrade is a simple 1:1 conversion.
     */
    public Integer getOffChipTemperatureInCelsius() {
        Integer temperature = null;
        try {
            final int centigrade = this.i2CDevice.read(TEMP_REG);
            if (centigrade != 0x01 && centigrade != 0x02) {
                temperature = centigrade;
            }
        } catch (IOException ignored) {}
        return temperature;
    }

    /**
     * 0x04: Onboard brightness sensor is over-ranged!
     * 0x08: Onboard brightness sensor failure!
     * Current onboard sensor brightness is: + (device.read(LIGHT_REG_L) | device.read(LIGHT_REG_H) << 8) + lux
     */
    public Integer getOnboardBrightnessInLux() {
        Integer brightness = null;
        try {
            final int lux = this.i2CDevice.read(STATUS_REG);
            if (lux != 0x04 && lux != 0x08) {
                brightness = this.i2CDevice.read(LIGHT_REG_L) | this.i2CDevice.read(LIGHT_REG_H) << 8;
            }
        } catch (IOException ignored) {}
        return brightness;
    }

    /**
     * Onboard temperature sensor data may not be up-to-date!
     * the temp of sensor on board is: + device.read(ON_BOARD_TEMP_REG) + centigrade
     * -> The conversion of Celsius to centigrade is a simple 1:1 conversion.
     */
    public Integer getOnboardTemperatureInCelsius() {
        try {
            if (this.i2CDevice.read(ON_BOARD_SENSOR_ERROR) == 1) {
                Logger.getLogger("Onboard temperature sensor data may not be up-to-date!");
            }
            return this.i2CDevice.read(ON_BOARD_TEMP_REG);
        } catch (IOException ignored) {}
        return null;
    }

    /**
     * Onboard humidity sensor data may not be up-to-date!
     * the humidity of sensor is: + device.read(ON_BOARD_HUMIDITY_REG) + %
     */
    public Integer getOnboardHumidityInPercentage() {
        try {
            if (this.i2CDevice.read(ON_BOARD_SENSOR_ERROR) == 1) {
                Logger.getLogger("Onboard temperature sensor data may not be up-to-date!");
            }
            return this.i2CDevice.read(ON_BOARD_HUMIDITY_REG);
        } catch (IOException ignored) {}
        return null;
    }

    /**
     * the temp of air pressure sensor is: + device.read(BMP280_TEMP_REG) + centigrade
     * -> The conversion of Celsius to centigrade is a simple 1:1 conversion.
     */
    public Integer getAirPressureSensorTemperatureInCelsius() {
        try {
            return this.i2CDevice.read(BMP280_TEMP_REG);
        } catch (IOException ignored) {}
        return null;
    }

    /**
     * The air pressure is: +
     * (device.read(BMP280_PRESSURE_REG_L) | device.read(BMP280_PRESSURE_REG_M) << 8 | device.read(BMP280_PRESSURE_REG_H) << 16)
     * + pa
     * if this.i2CDevice.read(BMP280_STATUS) != 0:
     *  Onboard barometer works abnormally!
     */
    public Integer getAirPressureInPascal() {
        try {
            if (this.i2CDevice.read(BMP280_STATUS) == 0) {
                return this.i2CDevice.read(BMP280_PRESSURE_REG_L)
                        | this.i2CDevice.read(BMP280_PRESSURE_REG_M) << 8
                        | this.i2CDevice.read(BMP280_PRESSURE_REG_H) << 16;
            }
        } catch (IOException ignored) {}
        return null;
    }

    /**
     * if this.i2CDevice.read(HUMAN_DETECT) == 1:
     *   Motion detected within 5 seconds!
     * else:
     *   No motion detected!
     */
    public Boolean isMotionDetectedWithin5Sec() {
        try {
            return this.i2CDevice.read(HUMAN_DETECT) == 1;
        } catch (IOException ignored) {}
        return null;
    }

}
