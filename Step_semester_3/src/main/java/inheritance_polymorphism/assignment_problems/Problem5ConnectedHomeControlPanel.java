package inheritance_polymorphism.assignment_problems;

interface RemoteControllable { String connect(String appId); }
interface EnergyTrackable { double getConsumptionWatts(); }

abstract class HomeDevice {
    private static int nextId = 1000;
    private final String serialNumber = "HD-" + (++nextId);
    public abstract String activate();
    public String getSerialNumber() { return serialNumber; }
}

class WashingMachine extends HomeDevice implements RemoteControllable, EnergyTrackable {
    private final double consumptionWatts;
    public WashingMachine(double consumptionWatts) { this.consumptionWatts = consumptionWatts; }
    public String activate() {
        return "Washing machine " + getSerialNumber() + " started a cycle";
    }
    public String connect(String appId) {
        return getSerialNumber() + " connected to " + appId;
    }
    public double getConsumptionWatts() { return consumptionWatts; }
}

class Refrigerator extends HomeDevice implements EnergyTrackable {
    private final double consumptionWatts;
    public Refrigerator(double consumptionWatts) { this.consumptionWatts = consumptionWatts; }
    public String activate() { return "Refrigerator " + getSerialNumber() + " started"; }
    public double getConsumptionWatts() { return consumptionWatts; }
}

class MobileApp implements RemoteControllable {
    private final String appName;
    public MobileApp(String appName) { this.appName = appName; }
    public String connect(String appId) { return appName + " connected to " + appId; }
}

public class Problem5ConnectedHomeControlPanel {
    static void connectAll(RemoteControllable[] items, String appId) {
        for (RemoteControllable x : items) System.out.println(x.connect(appId));
    }
    static double getConsumptionIfTrackable(HomeDevice d) {
        if (d instanceof EnergyTrackable)
            return ((EnergyTrackable) d).getConsumptionWatts();
        return 0.0;
    }

    public static void main(String[] args) {
        WashingMachine wm = new WashingMachine(500.0);
        Refrigerator fridge = new Refrigerator(150.0);
        MobileApp app = new MobileApp("HomeConnect App");
        System.out.println(wm.activate());
        System.out.println(wm.connect("HomeConnect"));
        System.out.println(getConsumptionIfTrackable(fridge));
        connectAll(new RemoteControllable[]{wm, app}, "HomeConnect");
    }
}