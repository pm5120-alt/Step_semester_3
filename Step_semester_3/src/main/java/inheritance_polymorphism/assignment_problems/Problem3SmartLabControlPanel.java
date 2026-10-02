package inheritance_polymorphism.assignment_problems;

import java.util.*;

interface Capability8A {
    String getName();
    void apply(Object value);
}

class PowerCapability8A implements Capability8A {
    private boolean on;

    public String getName() { return "Power"; }

    public void apply(Object value) {
        if (!(value instanceof Boolean)) {
            throw new IllegalArgumentException("Power requires true or false.");
        }
        on = (Boolean) value;
        System.out.println("Power set to " + (on ? "ON" : "OFF") + ".");
    }
}

class BrightnessCapability8A implements Capability8A {
    private int brightness;

    public String getName() { return "Brightness"; }

    public void apply(Object value) {
        if (!(value instanceof Number)) {
            throw new IllegalArgumentException("Brightness requires a number.");
        }
        int next = ((Number) value).intValue();
        if (next < 0 || next > 100) {
            throw new IllegalArgumentException("Brightness must be between 0 and 100%.");
        }
        brightness = next;
        System.out.println("Brightness set to " + brightness + "%.");
    }
}

class TemperatureCapability8A implements Capability8A {
    private double temperature;

    public String getName() { return "Temperature"; }

    public void apply(Object value) {
        if (!(value instanceof Number)) {
            throw new IllegalArgumentException("Temperature requires a number.");
        }
        double next = ((Number) value).doubleValue();
        if (next < 16 || next > 30) {
            throw new IllegalArgumentException("Temperature must be between 16°C and 30°C.");
        }
        temperature = next;
        System.out.println("Temperature set to " + temperature + "°C.");
    }
}

class Device8A {
    private final String name;
    private final Map<String, Capability8A> capabilities = new LinkedHashMap<>();

    public Device8A(String name) {
        this.name = name;
    }

    public void addCapability(Capability8A capability) {
        capabilities.put(capability.getName(), capability);
        System.out.println(name + ": " + capability.getName() + " capability added.");
    }

    public boolean hasCapability(String name) {
        return capabilities.containsKey(name);
    }

    public Capability8A getCapability(String name) {
        return capabilities.get(name);
    }

    public String getName() { return name; }
}

class SceneStep8A {
    private final String capabilityName;
    private final Object value;

    public SceneStep8A(String capabilityName, Object value) {
        this.capabilityName = capabilityName;
        this.value = value;
    }

    public int applyTo(List<Device8A> devices) {
        int applied = 0;
        for (Device8A device : devices) {
            if (!device.hasCapability(capabilityName)) continue;

            device.getCapability(capabilityName).apply(value);
            applied++;
        }
        return applied;
    }
}

class Scene8A {
    private final String name;
    private final List<SceneStep8A> steps = new ArrayList<>();

    public Scene8A(String name) { this.name = name; }
    public void addStep(SceneStep8A step) { steps.add(step); }

    public void execute(List<Device8A> devices) {
        System.out.println("Scene '" + name + "' started.");
        int total = 0;
        for (SceneStep8A step : steps) {
            total += step.applyTo(devices);
        }
        System.out.println("Scene '" + name + "' completed: "
                + total + " actions applied.");
    }
}

public class Problem3SmartLabControlPanel {
    public static void main(String[] args) {
        Device8A ac = new Device8A("Lab AC");
        ac.addCapability(new PowerCapability8A());
        ac.addCapability(new TemperatureCapability8A());

        Device8A lights = new Device8A("Ceiling Lights");
        lights.addCapability(new PowerCapability8A());
        lights.addCapability(new BrightnessCapability8A());

        Device8A projector = new Device8A("Projector");
        projector.addCapability(new PowerCapability8A());

        List<Device8A> devices = Arrays.asList(ac, lights, projector);

        Scene8A lectureMode = new Scene8A("Lecture Mode");
        lectureMode.addStep(new SceneStep8A("Power", true));
        lectureMode.addStep(new SceneStep8A("Brightness", 40));
        lectureMode.addStep(new SceneStep8A("Temperature", 24.0));
        lectureMode.execute(devices);

        try {
            ac.getCapability("Temperature").apply(12);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: Lab AC " + e.getMessage());
        }

        projector.addCapability(new BrightnessCapability8A());
        projector.getCapability("Brightness").apply(70);
    }
}
