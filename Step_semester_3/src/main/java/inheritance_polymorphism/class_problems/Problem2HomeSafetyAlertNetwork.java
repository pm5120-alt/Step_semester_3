package inheritance_polymorphism.class_problems;

interface Alertable { String sendAlert(String message); }

class SecuritySensor {
    protected final String zoneName;
    public SecuritySensor(String zoneName) { this.zoneName = zoneName; }
    public String getZoneName() { return zoneName; }
}

class MotionSensor extends SecuritySensor implements Alertable {
    public MotionSensor(String zoneName) { super(zoneName); }
    @Override
    public String sendAlert(String message) { return "[" + zoneName + "] " + message; }
}

class DualZoneMotionSensor extends MotionSensor {
    private final String secondZoneName;
    public DualZoneMotionSensor(String zoneName, String secondZoneName) {
        super(zoneName);
        this.secondZoneName = secondZoneName;
    }
    @Override
    public String sendAlert(String message) {
        return super.sendAlert(message) + " [also covering " + secondZoneName + "]";
    }
}

class SmokeDetector implements Alertable {
    private final String deviceId;
    public SmokeDetector(String deviceId) { this.deviceId = deviceId; }
    public String sendAlert(String message) { return "[" + deviceId + "] " + message; }
}

public class Problem2HomeSafetyAlertNetwork {
    static void broadcastAll(Alertable[] devices, String message) {
        for (Alertable x : devices) System.out.println(x.sendAlert(message));
    }
    static String getZoneIfMotionSensor(Alertable a) {
        if (a instanceof MotionSensor)
            return ((MotionSensor) a).getZoneName();
        return "Not a motion sensor";
    }

    public static void main(String[] args) {
        MotionSensor m = new MotionSensor("Living Room");
        DualZoneMotionSensor d = new DualZoneMotionSensor("Hallway", "Stairwell");
        SmokeDetector s = new SmokeDetector("SD-01");
        System.out.println(m.sendAlert("Motion detected"));
        System.out.println(d.sendAlert("Motion detected"));
        System.out.println(s.sendAlert("Smoke detected"));
        broadcastAll(new Alertable[]{m, s}, "Check now");
        System.out.println(getZoneIfMotionSensor(s));
    }
}