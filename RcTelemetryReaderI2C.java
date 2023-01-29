import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.util.Console;

public class RcTelemetryReaderI2C {

    private static final int SAMPLING_FREQUENCY = 100;
    private static final int ADDRESS_ADS1115 = 0x48;
    private static final byte CHANNEL_THROTTLE = (byte) 0x48;
    private static final byte CHANNEL_STEERING = (byte) 0x49;
    private static final byte CHANNEL_BATTERY = (byte) 0x4A;
    private static final byte CHANNEL_GROUND = (byte) 0x4B;

    public static void main(String[] args) throws Exception {
        final Console console = new Console();
        final I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
        final I2CDevice ads1115 = i2c.getDevice(ADDRESS_ADS1115);
        while (true) {
            console.println("---------------");
            console.println("Throttle: " + ads1115.read(CHANNEL_THROTTLE));
            console.println("Steering: " + ads1115.read(CHANNEL_STEERING));
            console.println("Battery: " + ads1115.read(CHANNEL_BATTERY));
            console.println("Ground: " + ads1115.read(CHANNEL_GROUND));
            Thread.sleep(SAMPLING_FREQUENCY);
        }
    }

}
