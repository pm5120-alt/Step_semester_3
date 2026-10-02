package inheritance_polymorphism.class_problems;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

abstract class Room8C {
    private final String roomNumber;
    private final double nightlyRate;

    protected Room8C(String roomNumber, double nightlyRate) {
        this.roomNumber = roomNumber;
        this.nightlyRate = nightlyRate;
    }

    public abstract String getCategory();

    public double calculatePrice(LocalDate start, LocalDate end) {
        long nights = ChronoUnit.DAYS.between(start, end);
        return nights * nightlyRate;
    }

    public String getRoomNumber() { return roomNumber; }
}

class StandardRoom8C extends Room8C {
    public StandardRoom8C(String roomNumber) { super(roomNumber, 150.0); }
    @Override public String getCategory() { return "Standard"; }
}

class DeluxeRoom8C extends Room8C {
    public DeluxeRoom8C(String roomNumber) { super(roomNumber, 200.0); }
    @Override public String getCategory() { return "Deluxe"; }
}

class SuiteRoom8C extends Room8C {
    public SuiteRoom8C(String roomNumber) { super(roomNumber, 300.0); }
    @Override public String getCategory() { return "Suite"; }
}

class Customer8CHotel {
    private final String name;
    public Customer8CHotel(String name) { this.name = name; }
    public String getName() { return name; }
}

class Reservation8C {
    enum Status { ACTIVE, CANCELLED }

    private final String id;
    private final Room8C room;
    private final Customer8CHotel customer;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalDate cancellationDeadline;
    private final double totalPrice;
    private Status status = Status.ACTIVE;

    public Reservation8C(String id, Room8C room, Customer8CHotel customer,
                         LocalDate startDate, LocalDate endDate,
                         LocalDate cancellationDeadline) {
        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("End date must be after start date.");
        }
        this.id = id;
        this.room = room;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cancellationDeadline = cancellationDeadline;
        this.totalPrice = room.calculatePrice(startDate, endDate);
    }

    public boolean overlaps(LocalDate start, LocalDate end) {
        return status == Status.ACTIVE
                && start.isBefore(endDate)
                && end.isAfter(startDate);
    }

    public void cancel(LocalDate currentDate) {
        if (status == Status.CANCELLED) {
            throw new IllegalStateException("Reservation already cancelled.");
        }
        if (!currentDate.isBefore(cancellationDeadline)) {
            throw new IllegalStateException("Cancellation deadline has passed.");
        }
        status = Status.CANCELLED;
    }

    public Room8C getRoom() { return room; }
    public double getTotalPrice() { return totalPrice; }
    public String getId() { return id; }
}

class HotelBookingManager8C {
    private final List<Room8C> rooms = new ArrayList<>();
    private final List<Reservation8C> reservations = new ArrayList<>();

    public void addRoom(Room8C room) { rooms.add(room); }

    public Reservation8C book(String reservationId, Customer8CHotel customer,
                               String roomNumber, LocalDate start, LocalDate end,
                               LocalDate cancellationDeadline) {
        Room8C room = rooms.stream()
                .filter(r -> r.getRoomNumber().equals(roomNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Room not found."));

        for (Reservation8C reservation : reservations) {
            if (reservation.getRoom() == room && reservation.overlaps(start, end)) {
                throw new IllegalStateException(room.getCategory() + " Room " + roomNumber
                        + " is not available for " + start + " to " + end + ".");
            }
        }

        Reservation8C reservation = new Reservation8C(
                reservationId, room, customer, start, end, cancellationDeadline);
        reservations.add(reservation);

        System.out.printf("%s Room %s booked from %s to %s. Total price: $%.2f%n",
                room.getCategory(), roomNumber, start, end, reservation.getTotalPrice());
        return reservation;
    }

    public void cancel(Reservation8C reservation, LocalDate currentDate) {
        reservation.cancel(currentDate);
        System.out.println("Reservation for Room " + reservation.getRoom().getRoomNumber()
                + " cancelled successfully.");
    }
}

public class Problem3HotelBookingSystem {
    public static void main(String[] args) {
        HotelBookingManager8C hotel = new HotelBookingManager8C();
        hotel.addRoom(new DeluxeRoom8C("101"));
        hotel.addRoom(new StandardRoom8C("205"));

        Customer8CHotel customer = new Customer8CHotel("Priyanshu");
        LocalDate cancellationDeadline = LocalDate.of(2024, 11, 30);

        Reservation8C deluxe = hotel.book(
                "R1", customer, "101",
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 5),
                cancellationDeadline);

        hotel.book(
                "R2", customer, "205",
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 7),
                cancellationDeadline);

        try {
            hotel.book(
                    "R3", customer, "101",
                    LocalDate.of(2024, 12, 3),
                    LocalDate.of(2024, 12, 7),
                    cancellationDeadline);
        } catch (IllegalStateException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        hotel.cancel(deluxe, LocalDate.of(2024, 11, 15));
    }
}
