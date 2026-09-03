import java.util.Scanner;

public class Main {

    static String normalizeReference(String raw) {
        String reference = raw.trim();

        if (reference.length() < 3) {
            return reference;
        }

        return reference.substring(0, 3).toUpperCase()
                + reference.substring(3);
    }

    static String validateAndFormat(String reference) {
        // 3 letters + 6 date digits + 5 sequence digits = 14 characters
        if (reference.length() != 14) {
            return "Invalid reference: wrong length";
        }

        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(reference.charAt(i))) {
                return "Invalid reference: non-letter bank code";
            }
        }

        for (int i = 3; i < 14; i++) {
            if (!Character.isDigit(reference.charAt(i))) {
                return "Invalid reference: non-digit body";
            }
        }

        String bankCode = reference.substring(0, 3);
        String date = reference.substring(3, 5) + "/"
                    + reference.substring(5, 7) + "/"
                    + reference.substring(7, 9);
        String sequence = reference.substring(9);

        StringBuilder result = new StringBuilder();
        result.append("[").append(bankCode).append("] ");
        result.append("DATE: ").append(date);
        result.append(" | SEQ: ").append(sequence);

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String rawReference = sc.nextLine();
        String normalizedReference = normalizeReference(rawReference);

        System.out.println(validateAndFormat(normalizedReference));

        sc.close();
    }
}