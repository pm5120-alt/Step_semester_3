package inheritance_polymorphism.assigment_problems;

class AuditEventTicket {
    protected double basePrice;
    protected double amountPaid;
    private double[] lateFeeHistory = new double[10];
    private int feeCount;

    public AuditEventTicket(double basePrice) {
        this.basePrice = basePrice;
    }

    public void pay(double amount) {
        if (amount > 0) amountPaid += amount;
    }

    public double getBalanceDue() {
        return basePrice - amountPaid;
    }

    protected void applyLateFee(double amount) {
        amountPaid -= amount;
        lateFeeHistory[feeCount++] = amount;
    }

    public double[] getLateFeeHistory() {
        double[] copy = new double[feeCount];
        for (int i = 0; i < feeCount; i++) copy[i] = lateFeeHistory[i];
        return copy;
    }
}

class AuditWorkshopTicket extends AuditEventTicket {
    public AuditWorkshopTicket(double basePrice) {
        super(basePrice);
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}

public class Problem3LateFeeAudit {
    public static void main(String[] args) {
        AuditWorkshopTicket w = new AuditWorkshopTicket(1200);
        w.pay(1200);
        w.applyLateFee(100);
        System.out.println(w.getBalanceDue());
        double[] history = w.getLateFeeHistory();
        history[0] = 999;
        System.out.println(w.getLateFeeHistory()[0]);
    }
}
