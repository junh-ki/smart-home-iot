import java.io.RandomAccessFile;
import java.lang.Exception;
import java.lang.Thread;

public class RcTelemetryReader {

    private static final int DATA_BYTE_SIZE_MAX = 7;
    private static final int SAMPLING_FREQUENCY = 1;
    private static final String ABSOLUTE_PATH_DEVICE_ADS_1115 = "/sys/bus/iio/devices/iio:device0/";
    private static final String FILE_NAME_THROTTLE = "in_voltage0-voltage3_raw";
    private static final String FILE_NAME_STEERING = "in_voltage1-voltage3_raw";
    private static final String FILE_NAME_BATTERY = "in_voltage2-voltage3_raw";
    private static final String READ_MODE = "r";

    public static void main(String[] args) {
        final String throttlePath = ABSOLUTE_PATH_DEVICE_ADS_1115 + FILE_NAME_THROTTLE;
        final String steeringPath = ABSOLUTE_PATH_DEVICE_ADS_1115 + FILE_NAME_STEERING;
        final String batteryPath = ABSOLUTE_PATH_DEVICE_ADS_1115 + FILE_NAME_BATTERY;
        try {
            final RandomAccessFile throttleRAF = new RandomAccessFile(throttlePath, READ_MODE);
            final RandomAccessFile steeringRAF = new RandomAccessFile(steeringPath, READ_MODE);
            final RandomAccessFile batteryRAF = new RandomAccessFile(batteryPath, READ_MODE);
            while (true) {
                System.out.println("-----");
                throttleRAF.seek(0);
                steeringRAF.seek(0);
                batteryRAF.seek(0);
                final byte[] throttleBytes = new byte[DATA_BYTE_SIZE_MAX];
                final byte[] steeringBytes = new byte[DATA_BYTE_SIZE_MAX];
                final byte[] batteryBytes = new byte[DATA_BYTE_SIZE_MAX];
                throttleRAF.read(throttleBytes);
                steeringRAF.read(steeringBytes);
                batteryRAF.read(batteryBytes);
                final String throttleValue = new String(throttleBytes);
                final String steeringValue = new String(steeringBytes);
                final String batteryValue = new String(batteryBytes);
                System.out.println("throttleValue: " + throttleValue);
                System.out.println("steeringValue: " + steeringValue);
                System.out.println("batteryValue: " + batteryValue);
                //Thread.sleep(SAMPLING_FREQUENCY);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
