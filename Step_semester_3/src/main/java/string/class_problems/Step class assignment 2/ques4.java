import java.util.Scanner;

public class Main {

    static String maskPhoneNumber(String phone) {
        // Check for exactly 10 numeric digits
        if (phone.length() != 10 || !phone.matches("\\d{10}")) {
            return "Invalid phone number";
        }

        String lastFourDigits = phone.substring(6);

        StringBuilder maskedPhone = new StringBuilder();
        maskedPhone.append("XXXXXX");
        maskedPhone.append("-");
        maskedPhone.append(lastFourDigits);

        return maskedPhone.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String phone = sc.nextLine();
        System.out.println(maskPhoneNumber(phone));

        sc.close();
    }
}