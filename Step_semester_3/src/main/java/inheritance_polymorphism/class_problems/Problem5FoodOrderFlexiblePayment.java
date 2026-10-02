package inheritance_polymorphism.class_problems;

import java.util.*;

interface IPaymentMethod8C {
    boolean pay(double amount);
    String getMethodName();
}

class CreditCardPayment8C implements IPaymentMethod8C {
    public boolean pay(double amount) {
        System.out.printf("Payment via Credit Card successful. Amount: $%.2f%n", amount);
        return true;
    }
    public String getMethodName() { return "Credit Card"; }
}

class DigitalWalletPayment8C implements IPaymentMethod8C {
    public boolean pay(double amount) {
        System.out.printf("Payment via Digital Wallet failed. Amount: $%.2f%n", amount);
        return false;
    }
    public String getMethodName() { return "Digital Wallet"; }
}

class CashOnDeliveryPayment8C implements IPaymentMethod8C {
    public boolean pay(double amount) {
        System.out.printf("Payment via Cash on Delivery accepted. Amount: $%.2f%n", amount);
        return true;
    }
    public String getMethodName() { return "Cash on Delivery"; }
}

interface OrderEventListener8C {
    void onEvent(String event);
}

class FoodItem8C {
    private final String name;
    private final double price;

    public FoodItem8C(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
}

class LineItem8C {
    private final FoodItem8C item;
    private final int quantity;

    public LineItem8C(FoodItem8C item, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        this.item = item;
        this.quantity = quantity;
    }

    public double getTotal() { return item.getPrice() * quantity; }
    public String getDescription() { return item.getName() + " (Qty " + quantity + ")"; }
}

class Restaurant8C {
    private final String name;
    public Restaurant8C(String name) { this.name = name; }
    public String getName() { return name; }
}

class Customer8CFood implements OrderEventListener8C {
    private final String name;

    public Customer8CFood(String name) { this.name = name; }
    public String getName() { return name; }

    @Override
    public void onEvent(String event) {
        System.out.println("Notification: " + event);
    }
}

class Order8C {
    enum Status { CREATED, PENDING_PAYMENT, PAID }

    private final String orderId;
    private final Customer8CFood customer;
    private final Restaurant8C restaurant;
    private final List<LineItem8C> items = new ArrayList<>();
    private Status status = Status.CREATED;

    public Order8C(String orderId, Customer8CFood customer, Restaurant8C restaurant) {
        this.orderId = orderId;
        this.customer = customer;
        this.restaurant = restaurant;
        System.out.println("Order created.");
    }

    public void addItem(FoodItem8C item, int quantity) {
        items.add(new LineItem8C(item, quantity));
        System.out.println("Added " + item.getName() + " (Qty " + quantity + ").");
    }

    public double getTotal() {
        return items.stream().mapToDouble(LineItem8C::getTotal).sum();
    }

    public void place(IPaymentMethod8C paymentMethod) {
        if (items.isEmpty()) {
            throw new IllegalStateException("Order must contain at least one item.");
        }

        status = Status.PENDING_PAYMENT;
        customer.onEvent("Order #" + orderId + " placed.");
        boolean successful = paymentMethod.pay(getTotal());

        if (successful) {
            status = Status.PAID;
            customer.onEvent("Order #" + orderId + " placed and paid.");
        } else {
            customer.onEvent("Order #" + orderId + " placed, awaiting payment.");
        }

        System.out.println("Order status: " + statusLabel());
    }

    private String statusLabel() {
        return status == Status.PAID ? "Paid" : "Pending Payment";
    }

    public Restaurant8C getRestaurant() { return restaurant; }
}

public class Problem5FoodOrderFlexiblePayment {
    public static void main(String[] args) {
        Customer8CFood customer = new Customer8CFood("Priyanshu");
        Restaurant8C restaurant = new Restaurant8C("Campus Bites");

        Order8C order1 = new Order8C("123", customer, restaurant);
        try {
            order1.place(new CreditCardPayment8C());
        } catch (IllegalStateException e) {
            System.out.println("Cannot place order: " + e.getMessage());
        }

        order1.addItem(new FoodItem8C("Pizza", 10.0), 2);
        order1.addItem(new FoodItem8C("Soda", 3.0), 1);
        order1.place(new CreditCardPayment8C());

        Order8C order2 = new Order8C("124", customer, restaurant);
        order2.addItem(new FoodItem8C("Burger", 8.0), 1);
        order2.place(new DigitalWalletPayment8C());
    }
}
