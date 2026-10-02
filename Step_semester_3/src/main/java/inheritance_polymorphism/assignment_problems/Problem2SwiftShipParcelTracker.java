package inheritance_polymorphism.assignment_problems;

import java.util.*;

interface ShippingType8A {
    double calculateCharge(double weightKg);
    String getName();
}

class StandardShipping8A implements ShippingType8A {
    public double calculateCharge(double weightKg) { return 40 + 10 * weightKg; }
    public String getName() { return "Standard"; }
}

class ExpressShipping8A implements ShippingType8A {
    public double calculateCharge(double weightKg) { return 80 + 15 * weightKg; }
    public String getName() { return "Express"; }
}

class FragileShipping8A implements ShippingType8A {
    public double calculateCharge(double weightKg) {
        return new StandardShipping8A().calculateCharge(weightKg) + 50;
    }
    public String getName() { return "Fragile"; }
}

interface NotificationChannel8A {
    void notifyStatus(String parcelId, String status);
}

class SmsChannel8A implements NotificationChannel8A {
    public void notifyStatus(String parcelId, String status) {
        System.out.println("[SMS] " + parcelId + " is now " + status + ".");
    }
}

class EmailChannel8A implements NotificationChannel8A {
    public void notifyStatus(String parcelId, String status) {
        System.out.println("[Email] " + parcelId + " is now " + status + ".");
    }
}

class Customer8AParcel {
    private final String name;
    private final List<NotificationChannel8A> channels = new ArrayList<>();

    public Customer8AParcel(String name) { this.name = name; }

    public void subscribe(NotificationChannel8A channel) {
        channels.add(channel);
    }

    public void notifyAllChannels(String parcelId, String status) {
        for (NotificationChannel8A channel : channels) {
            channel.notifyStatus(parcelId, status);
        }
    }

    public String getName() { return name; }
}

class Parcel8A {
    enum Status { BOOKED, PICKED_UP, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED, CANCELLED }

    private final String parcelId;
    private final double weightKg;
    private final ShippingType8A shippingType;
    private final Customer8AParcel customer;
    private Status status;

    public Parcel8A(String parcelId, double weightKg, ShippingType8A shippingType,
                    Customer8AParcel customer) {
        if (weightKg <= 0) throw new IllegalArgumentException("Weight must be positive.");
        this.parcelId = parcelId;
        this.weightKg = weightKg;
        this.shippingType = shippingType;
        this.customer = customer;
        this.status = Status.BOOKED;

        System.out.printf("Parcel %s booked (%s, %.0f kg). Charge: ₹%.2f%n",
                parcelId, shippingType.getName(), weightKg, getCharge());
        notifyStatus();
    }

    public double getCharge() {
        return shippingType.calculateCharge(weightKg);
    }

    public void moveTo(Status nextStatus) {
        if (!isAllowedNextStatus(nextStatus)) {
            throw new IllegalStateException(
                    "Invalid transition: " + status + " -> " + nextStatus + " is not allowed.");
        }
        status = nextStatus;
        notifyStatus();
    }

    public void cancel() {
        if (status != Status.BOOKED) {
            throw new IllegalStateException(
                    parcelId + " can be cancelled only while BOOKED.");
        }
        status = Status.CANCELLED;
        notifyStatus();
    }

    private boolean isAllowedNextStatus(Status next) {
        return (status == Status.BOOKED && next == Status.PICKED_UP)
                || (status == Status.PICKED_UP && next == Status.IN_TRANSIT)
                || (status == Status.IN_TRANSIT && next == Status.OUT_FOR_DELIVERY)
                || (status == Status.OUT_FOR_DELIVERY && next == Status.DELIVERED);
    }

    private void notifyStatus() {
        customer.notifyAllChannels(parcelId, status.toString());
    }
}

class ParcelService8A {
    public Parcel8A bookParcel(String parcelId, double weightKg,
                               ShippingType8A type, Customer8AParcel customer) {
        return new Parcel8A(parcelId, weightKg, type, customer);
    }
}

public class Problem2SwiftShipParcelTracker {
    public static void main(String[] args) {
        ParcelService8A service = new ParcelService8A();
        Customer8AParcel customer = new Customer8AParcel("Priyanshu");
        customer.subscribe(new SmsChannel8A());
        customer.subscribe(new EmailChannel8A());

        Parcel8A parcel = service.bookParcel(
                "P101", 2, new ExpressShipping8A(), customer);

        parcel.moveTo(Parcel8A.Status.PICKED_UP);

        try {
            parcel.cancel();
        } catch (IllegalStateException e) {
            System.out.println("Cancellation failed: " + e.getMessage());
        }

        parcel.moveTo(Parcel8A.Status.IN_TRANSIT);

        try {
            parcel.moveTo(Parcel8A.Status.DELIVERED);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
