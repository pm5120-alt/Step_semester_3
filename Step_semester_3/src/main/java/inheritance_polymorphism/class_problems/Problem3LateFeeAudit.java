package inheritance_polymorphism.class_problems;

class AuditEventTicket {
    protected double basePrice, amountPaid;
    private double[] lateFeeHistory = new double[10];
    private int feeCount;
    public AuditEventTicket(double price) { basePrice = price; }
    public void pay(double amount) { if (amount > 0) amountPaid += amount; }
    public double getBalanceDue() { return basePrice - amountPaid; }
    protected void applyLateFee(double amount) { amountPaid -= amount; lateFeeHistory[feeCount++] = amount; }
    public double[] getLateFeeHistory() { double[] copy = new double[feeCount]; for (int i=0;i<feeCount;i++) copy[i]=lateFeeHistory[i]; return copy; }
}
class AuditWorkshopTicket extends AuditEventTicket {
    public AuditWorkshopTicket(double price) { super(price); }
    @Override protected void applyLateFee(double amount) { super.applyLateFee(amount * 2); }
}
public class Problem3LateFeeAudit {
    public static void main(String[] args) {
        AuditWorkshopTicket w = new AuditWorkshopTicket(1200); w.pay(1200); w.applyLateFee(100);
        System.out.println(w.getBalanceDue()); double[] h=w.getLateFeeHistory(); h[0]=999; System.out.println(w.getLateFeeHistory()[0]);
    }
}
