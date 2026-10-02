package inheritance_polymorphism.class_problems;

import java.util.*;

abstract class Vehicle8C {
    private final String vehicleId;
    private final String name;
    private boolean available = true;

    protected Vehicle8C(String vehicleId, String name) {
        this.vehicleId = vehicleId;
        this.name = name;
    }

    public abstract double calculateCharge(int days);

    public String getVehicleId() { return vehicleId; }
    public String getName() { return name; }
    public boolean isAvailable() { return available; }
    protected void setAvailable(boolean available) { this.available = available; }
}

class StandardCar8C extends Vehicle8C {
    public StandardCar8C(String vehicleId, String name) { super(vehicleId, name); }
    @Override public double calculateCharge(int days) { return 50.0 * days; }
}

class LuxuryCar8C extends Vehicle8C {
    public LuxuryCar8C(String vehicleId, String name) { super(vehicleId, name); }
    @Override public double calculateCharge(int days) { return 100.0 * days; }
}

class SUV8C extends Vehicle8C {
    public SUV8C(String vehicleId, String name) { super(vehicleId, name); }
    @Override public double calculateCharge(int days) { return 80.0 * days; }
}

class Customer8C {
    private final String customerId;
    private final String name;

    public Customer8C(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
}

class Rental8C {
    private final Customer8C customer;
    private final Vehicle8C vehicle;
    private final int days;
    private final double totalCharge;
    private boolean returned;

    public Rental8C(Customer8C customer, Vehicle8C vehicle, int days) {
        if (days <= 0) throw new IllegalArgumentException("Rental duration must be positive.");
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;
        this.totalCharge = vehicle.calculateCharge(days);
    }

    public void markReturned() {
        if (returned) throw new IllegalStateException("Vehicle already returned.");
        returned = true;
        vehicle.setAvailable(true);
    }

    public Vehicle8C getVehicle() { return vehicle; }
    public double getTotalCharge() { return totalCharge; }
}

class RentalService8C {
    private final Map<String, Vehicle8C> vehicles = new HashMap<>();
    private final List<Rental8C> rentals = new ArrayList<>();

    public void addVehicle(Vehicle8C vehicle) {
        vehicles.put(vehicle.getVehicleId(), vehicle);
    }

    public Rental8C rent(Customer8C customer, String vehicleId, int days) {
        Vehicle8C vehicle = vehicles.get(vehicleId);
        if (vehicle == null) throw new IllegalArgumentException("Vehicle not found.");
        if (!vehicle.isAvailable()) throw new IllegalStateException("Vehicle is currently rented.");

        vehicle.setAvailable(false);
        Rental8C rental = new Rental8C(customer, vehicle, days);
        rentals.add(rental);

        System.out.printf("%s rented for %d days. Total charge: $%.2f%n",
                vehicle.getName(), days, rental.getTotalCharge());
        return rental;
    }

    public void returnVehicle(Rental8C rental) {
        rental.markReturned();
        System.out.println(rental.getVehicle().getName() + " returned. Now available.");
    }
}

public class Problem2VehicleRentalSystem {
    public static void main(String[] args) {
        RentalService8C service = new RentalService8C();
        service.addVehicle(new LuxuryCar8C("V1", "Luxury Car A"));
        service.addVehicle(new StandardCar8C("V2", "Standard Car B"));

        Customer8C customer = new Customer8C("C1", "Priyanshu");

        Rental8C luxury = service.rent(customer, "V1", 3);
        service.rent(customer, "V2", 5);
        service.returnVehicle(luxury);

        service.rent(customer, "V1", 2);
    }
}
