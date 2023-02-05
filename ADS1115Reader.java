/**
 * https://pi4j.com/about/download/
 * $ sudo mv pi4j /opt/
 * $ javac -classpath .:classes:/opt/pi4j/lib/'*' ADS1115Reader.java
 * $ java -classpath .:classes:/opt/pi4j/lib/'*' ADS1115Reader
 */

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.io.i2c.I2CRegister;

public class ADS1115Reader {

    private static final int ADDRESS = 0x48;
    private static final int CONVERSION_REGISTER = 0x00;
    private static final int CONFIG_REGISTER = 0x01;
    private static final int CONFIG_REGISTER_TEMPLATE = 0b1000000110000011;
    private static final int A0_IN = 0b0100000000000000;
    private static final int A1_IN = 0b0101000000000000;
    private static final int A2_IN = 0b0110000000000000;
    private static final int A3_IN = 0b0111000000000000;
    private static final int I2C_BUS = 1;
    private static final int SLEEP_DURATION_MILLI = 50;
    private static final String DEVICE_ID = "ADS1115";

    public enum GainEnum {

        GAIN_6_144V(0b0000000000000000, 187.5/1_000_000),
        GAIN_4_096V(0b0000001000000000, 125.0/1_000_000),
        GAIN_2_048V(0b0000010000000000, 62.5/1_000_000),
        GAIN_1_024V(0b0000011000000000, 31.25/1_000_000),
        GAIN_0_512V(0b0000100000000000, 15.625/1_000_000),
        GAIN_0_256V(0b0000101000000000, 7.8125/1_000_000);

        private final int gain;
        private final double gainPerByte;

        GainEnum(int gain, double gainPerByte) {
            this.gain = gain;
            this.gainPerByte = gainPerByte;
        }

        public int getGain() {
            return gain;
        }

        public double getGainPerByte() {
            return gainPerByte;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        final Context context = Pi4J.newAutoContext();
        final I2CProvider i2CProvider = context.provider("linuxfs-i2c");
        final I2CConfig i2cConfig = I2C.newConfigBuilder(context)
                .id(DEVICE_ID)
                .bus(I2C_BUS)
                .device(ADDRESS)
                .build();
        final I2C i2c = i2CProvider.create(i2cConfig);
        final GainEnum gain = GainEnum.GAIN_6_144V;
        for (int i = 0 ; i < 500; i ++) {
            final double ain0 = getAnalogInput(GainEnum.GAIN_0_256V, A0_IN, i2c);
            final double ain1 = getAnalogInput(GainEnum.GAIN_0_256V, A1_IN, i2c);
            final double ain2 = getAnalogInput(GainEnum.GAIN_0_256V, A2_IN, i2c);
            final double ain3 = getAnalogInput(GainEnum.GAIN_0_256V, A3_IN, i2c);
            System.out.println("----------------");
            System.out.println("AIN0: " + ain0);
            System.out.println("AIN1: " + ain1);
            System.out.println("AIN2: " + ain2);
            System.out.println("AIN3: " + ain3);
        }
        i2c.close();
        System.out.println("-------END------");
    }

    private static double getAnalogInput(GainEnum gain, int aInPinAddress, I2C i2c) {
        return gain.getGainPerByte() * readIn(calculateConfig(gain, aInPinAddress), i2c);
    }

    private static int calculateConfig(GainEnum gain, int aInPinAddress) {
        return CONFIG_REGISTER_TEMPLATE | gain.getGain() | aInPinAddress;
    }

    private static int readIn(int config, I2C i2c) {
        i2c.writeRegisterWord(CONFIG_REGISTER, config);
        try {
            Thread.sleep(SLEEP_DURATION_MILLI);
        } catch (InterruptedException ignored) {}
        return i2c.readRegisterWord(CONVERSION_REGISTER);
    }

}
