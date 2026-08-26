class FeeAccount {

    private int regNo;
    private double totalFee;
    private double amountPaid;
    FeeAccount(int regNo, double totalFee) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = 0;
    }

    void pay(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid payment!");
            return;
        }
        amountPaid += amount;
    }

    double getDue() {
        return totalFee - amountPaid;
    }

    void payInTwoInstallments(double amount) {
        pay(amount / 2);
        pay(amount / 2);
    }

    double effectiveDue(double scholarshipPercent) {
        return getDue() - (getDue() * scholarshipPercent / 100);
    }
}

public class f2 {

    public static void main(String[] args) {

        FeeAccount accountA = new FeeAccount(101, 200000);
        FeeAccount accountB = new FeeAccount(102, 180000);
        accountA.payInTwoInstallments(120000);

        double dueB = accountB.effectiveDue(40);

        System.out.println("Account A due: Rs " + accountA.getDue());
        System.out.println("Account B effective due: Rs " + dueB);
    }
}