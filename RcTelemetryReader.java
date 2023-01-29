import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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
        final List<String> paths = Arrays.asList(throttlePath, steeringPath, batteryPath);
        while (true) {
            System.out.println("---------------");
            paths.forEach(path -> {
                try {
                    final List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
                    final String value = lines.isEmpty() ? null : lines.get(0);
                    final String metricName;
                    if (path.contains(FILE_NAME_THROTTLE)) {
                        metricName = "throttle";
                    } else if (path.contains(FILE_NAME_STEERING)) {
                        metricName = "steering";
                    } else {
                        metricName = "battery";
                    }
                    System.out.println(metricName + ": " + value);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                try {
                    Thread.sleep(SAMPLING_FREQUENCY);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            });
        }
    }

}
