import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

public class RcTelemetryReader {

    private static final int SAMPLING_FREQUENCY = 1;
    private static final String ABSOLUTE_PATH_DEVICE_ADS_1115 = "/sys/bus/iio/devices/iio:device0/";
    private static final String FILE_NAME_THROTTLE = "in_voltage0-voltage3_raw";
    private static final String FILE_NAME_STEERING = "in_voltage1-voltage3_raw";
    private static final String FILE_NAME_BATTERY = "in_voltage2-voltage3_raw";

    public static void main(String[] args) {
        final String throttlePath = ABSOLUTE_PATH_DEVICE_ADS_1115 + FILE_NAME_THROTTLE;
        final String steeringPath = ABSOLUTE_PATH_DEVICE_ADS_1115 + FILE_NAME_STEERING;
        final String batteryPath = ABSOLUTE_PATH_DEVICE_ADS_1115 + FILE_NAME_BATTERY;
        try {
            final FileInputStream throttleFIS = new FileInputStream(throttlePath);
            final InputStreamReader throttleISR = new InputStreamReader(throttleFIS);
            final BufferedReader throttleBR = new BufferedReader(throttleISR);
            final FileChannel throttleFC = throttleFIS.getChannel();
            final FileInputStream steeringFIS = new FileInputStream(steeringPath);
            final InputStreamReader steeringISR = new InputStreamReader(steeringFIS);
            final BufferedReader steeringBR = new BufferedReader(steeringISR);
            final FileChannel steeringFC = steeringFIS.getChannel();
            final FileInputStream batteryFIS = new FileInputStream(batteryPath);
            final InputStreamReader batteryISR = new InputStreamReader(batteryFIS);
            final BufferedReader batteryBR = new BufferedReader(batteryISR);
            final FileChannel batteryFC = batteryFIS.getChannel();
            while (true) {
                System.out.println("------------------");
                System.out.println("Throttle: " + getMetric(throttleBR, throttleFC));
                System.out.println("Steering: " + getMetric(steeringBR, steeringFC));
                System.out.println("Battery: " + getMetric(batteryBR, batteryFC));
                Thread.sleep(SAMPLING_FREQUENCY);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static String getMetric(BufferedReader bufferedReader, FileChannel fileChannel) {
        try {
            final String throttleValue = bufferedReader.readLine();
            fileChannel.position(0);
            return throttleValue;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }

}
