import java.util.Arrays;

public class PatientVitals {
    private double[] readings;
    private int count;

    public PatientVitals(double[] initialReadings) {
        readings = new double[500];
        count = 0;
        if (initialReadings != null) {
            for (double reading : initialReadings) recordReading(reading);
        }
    }

    public void recordReading(double reading) {
        if (reading <= 0 || reading > 45 || count == readings.length) return;
        readings[count++] = reading;
    }

    public double getAverage() {
        if (count == 0) return 0.0;
        double sum = 0;
        for (int i = 0; i < count; i++) sum += readings[i];
        return sum / count;
    }

    public double[] getAllReadings() {
        return Arrays.copyOf(readings, count);
    }
}
