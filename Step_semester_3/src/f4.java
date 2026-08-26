class SrmStudent {

    String name;
    String regNo;
    double attendance;

    static String university = "SRM Institute of Science and Technology";
    static int admissionCount = 0;

    SrmStudent(String name, double attendance) {
        this.name = name;
        this.attendance = attendance;

        admissionCount++;
        regNo = "RA2311003010" + admissionCount;
    }

    void printIdCard() {
        System.out.println(name + " | " + regNo + " | " + university);
    }

    static void printTotalAdmissions() {
        System.out.println("Students admitted so far: " + admissionCount);
    }
}

public class f4 {

    public static void main(String[] args) {

        SrmStudent s1 = new SrmStudent("Ravi", 82);
        SrmStudent s2 = new SrmStudent("Meera", 75);
        SrmStudent s3 = new SrmStudent("Karthik", 91);

        s1.printIdCard();
        s2.printIdCard();
        s3.printIdCard();

        SrmStudent.printTotalAdmissions();
    }
}