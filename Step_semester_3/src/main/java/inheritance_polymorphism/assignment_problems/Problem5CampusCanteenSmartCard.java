package inheritance_polymorphism.assignment_problems;

import java.util.*;

interface PricingPlan8A {
    double finalPrice(double basePrice);
    String getName();
}

class DayScholarPlan8A implements PricingPlan8A {
    public double finalPrice(double basePrice) { return basePrice; }
    public String getName() { return "Day Scholar"; }
}

class HostellerPlan8A implements PricingPlan8A {
    public double finalPrice(double basePrice) { return basePrice * 0.90; }
    public String getName() { return "Hosteller"; }
}

class StaffPlan8A implements PricingPlan8A {
    public double finalPrice(double basePrice) { return basePrice * 0.80; }
    public String getName() { return "Staff"; }
}

class FoodItem8A {
    private final String name;
    private final double price;

    public FoodItem8A(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
}

class Transaction8A {
    private final String description;
    private final double amount;

    public Transaction8A(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount; }
}

class SmartCard8A {
    enum State { ACTIVE, BLOCKED }

    private final String cardId;
    private final PricingPlan8A pricingPlan;
    private final List<Transaction8A> transactions = new ArrayList<>();
    private final Map<String, Transaction8A> purchases = new HashMap<>();
    private final Set<String> refundedPurchases = new HashSet<>();
    private double balance;
    private State state = State.ACTIVE;

    public SmartCard8A(String cardId, PricingPlan8A pricingPlan) {
        this.cardId = cardId;
        this.pricingPlan = pricingPlan;
    }

    public void topUp(double amount) {
        ensureActive();

        if (amount < 100) {
            throw new IllegalArgumentException("Minimum top-up is ₹100.");
        }
        if (balance + amount > 5000) {
            throw new IllegalArgumentException("Maximum balance is ₹5,000.");
        }

        addTransaction("Top-up", amount);
        System.out.printf("%s topped up with ₹%.2f. Balance: ₹%.2f%n",
                cardId, amount, balance);
    }

    public double purchase(String purchaseId, FoodItem8A item) {
        ensureActive();

        if (purchases.containsKey(purchaseId)) {
            throw new IllegalStateException("Purchase ID already exists.");
        }

        double charged = pricingPlan.finalPrice(item.getPrice());
        if (charged > balance) {
            throw new IllegalStateException(String.format(
                    "Insufficient balance (required ₹%.2f, available ₹%.2f).",
                    charged, balance));
        }

        Transaction8A transaction = addTransaction(item.getName(), -charged);
        purchases.put(purchaseId, transaction);

        System.out.printf("%s purchased for ₹%.2f. Balance: ₹%.2f%n",
                item.getName(), charged, balance);
        return charged;
    }

    public void refund(String purchaseId) {
        ensureActive();

        if (refundedPurchases.contains(purchaseId)) {
            throw new IllegalStateException("Purchase has already been refunded.");
        }

        Transaction8A purchase = purchases.get(purchaseId);
        if (purchase == null) {
            throw new IllegalArgumentException("Purchase not found.");
        }

        double refundAmount = -purchase.getAmount();
        addTransaction("Refund of " + purchase.getDescription(), refundAmount);
        refundedPurchases.add(purchaseId);

        System.out.printf("Refund of ₹%.2f for %s processed. Balance: ₹%.2f%n",
                refundAmount, purchase.getDescription(), balance);
    }

    public void block() { state = State.BLOCKED; }
    public void unblock() { state = State.ACTIVE; }

    public void miniStatement() {
        System.out.print("Mini-statement for " + cardId + ": ");
        double sum = 0;

        for (int i = 0; i < transactions.size(); i++) {
            double amount = transactions.get(i).getAmount();
            sum += amount;

            System.out.printf("%s%.2f",
                    amount >= 0 ? "+" : "-", Math.abs(amount));

            if (i < transactions.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.printf(" = ₹%.2f%n", balance);
        verifyInvariant();

        if (Math.abs(sum - balance) > 0.000001) {
            throw new IllegalStateException("Balance invariant violated.");
        }
    }

    private Transaction8A addTransaction(String description, double amount) {
        double nextBalance = balance + amount;

        if (nextBalance < 0) {
            throw new IllegalStateException("Balance cannot become negative.");
        }

        Transaction8A transaction = new Transaction8A(description, amount);
        transactions.add(transaction);
        balance = nextBalance;
        verifyInvariant();
        return transaction;
    }

    private void verifyInvariant() {
        double sum = 0;

        for (Transaction8A transaction : transactions) {
            sum += transaction.getAmount();
        }

        if (Math.abs(sum - balance) > 0.000001) {
            throw new IllegalStateException("Balance invariant violated.");
        }
    }

    private void ensureActive() {
        if (state == State.BLOCKED) {
            throw new IllegalStateException("Card is blocked.");
        }
    }
}

public class Problem5CampusCanteenSmartCard {
    public static void main(String[] args) {
        SmartCard8A card = new SmartCard8A("C-2045", new HostellerPlan8A());

        card.topUp(500);
        card.purchase("P1", new FoodItem8A("Veg Thali", 120));
        card.purchase("P2", new FoodItem8A("Cold Coffee", 60));

        try {
            card.purchase("P3", new FoodItem8A("Large Order", 400));
        } catch (IllegalStateException e) {
            System.out.println("Purchase failed: " + e.getMessage());
        }

        card.refund("P1");

        try {
            card.refund("P1");
        } catch (IllegalStateException e) {
            System.out.println("Refund rejected: Veg Thali has already been refunded.");
        }

        card.miniStatement();
    }
}
