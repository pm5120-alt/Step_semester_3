import java.util.Scanner;

public class Main {

    static void parseStudentRecord(String csvLine) {
        String[] fields = csvLine.split(",");

        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        String name = fields[0].trim();
        String rollNo = fields[1].trim();
        String department = fields[2].trim();

        System.out.println("Name: " + name
                + " | Roll No: " + rollNo
                + " | Dept: " + department);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String csvLine = sc.nextLine();
        parseStudentRecord(csvLine);

        sc.close();
    }
}