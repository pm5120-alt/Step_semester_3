package f5;
class FeeAccount {

    private int regNo;
    private double totalFee;
    private double amountPaid;

    FeeAccount(int regNo, double totalFee) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        amountPaid = 0;
    }

    void pay(double amount) {
        if (amount > 0) {
            amountPaid = amountPaid + amount;
        }
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

class HostelRoom {

    String roomNo;
    int beds;
    int occupied;

    HostelRoom(String roomNo, int beds) {
        this.roomNo = roomNo;
        this.beds = beds;
        occupied = 0;
    }

    void allot(String studentName) {
        if (occupied < beds) {
            occupied++;
        } else {
            System.out.println("Room is full");
        }
    }
}

class SrmStudent {

    String name;
    String regNo;
    FeeAccount feeAccount;
    HostelRoom room;

    static int totalStudents = 0;

    SrmStudent(String name, String regNo, FeeAccount feeAccount, HostelRoom room) {
        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;
        this.room = room;

        totalStudents++;
    }

    void fullStatus() {
        System.out.println(name + " | Due: Rs " + feeAccount.getDue()
                + " | Room: " + room.roomNo);
    }
}

public class f5 {

    public static void main(String[] args) {

        FeeAccount fee1 = new FeeAccount(101, 200000);
        HostelRoom room1 = new HostelRoom("C-214", 2);
        SrmStudent s1 = new SrmStudent("Ravi", "101", fee1, room1);

        FeeAccount fee2 = new FeeAccount(102, 180000);
        HostelRoom room2 = new HostelRoom("C-507", 2);
        SrmStudent s2 = new SrmStudent("Anitha", "102", fee2, room2);

        FeeAccount fee3 = new FeeAccount(103, 200000);
        HostelRoom room3 = new HostelRoom("C-309", 2);
        SrmStudent s3 = new SrmStudent("Karthik", "103", fee3, room3);

        s1.room.allot(s1.name);
        s2.room.allot(s2.name);
        s3.room.allot(s3.name);

        s1.feeAccount.payInTwoInstallments(120000);
        s2.feeAccount.pay(0);
        s3.feeAccount.pay(56000);

        s1.fullStatus();
        s2.fullStatus();
        s3.fullStatus();

        System.out.println("Total students: " + SrmStudent.totalStudents);
    }
}