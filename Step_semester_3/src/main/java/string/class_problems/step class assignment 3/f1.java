class SrmStudent {

    String name;
    int regNo;
    double attendance;

    // Constructor
    SrmStudent(String name, int regNo, double attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }

    void addAttendanceUpdate(int newAttendance) {
        attendance = newAttendance;
    }
    boolean isEligible() {
        return attendance >= 75;
    }
    
    static double classAverage(SrmStudent[] students) {

        double total = 0;

        for (SrmStudent student : students) {
            total += student.attendance;
        }

        return total / students.length;
    }
}

public class f1 {

    public static void main(String[] args) {

        SrmStudent[] students = {
            new SrmStudent("Ravi", 101, 82),
            new SrmStudent("Anitha", 102, 68),
            new SrmStudent("Karthik", 103, 91),
            new SrmStudent("Meera", 104, 74),
            new SrmStudent("Suresh", 105, 60)
        };
        for (SrmStudent student : students) {

            String status;

            if (student.isEligible()) {
                status = "Eligible";
            } else {
                status = "Detained";
            }

            System.out.println(
                student.name + " - " +
                String.format("%.0f", student.attendance) +
                "% - " + status
            );
        }
        double average = SrmStudent.classAverage(students);

        System.out.printf("Class average: %.1f%%%n", average);
    }
}