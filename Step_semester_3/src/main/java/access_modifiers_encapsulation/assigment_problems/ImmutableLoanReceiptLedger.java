public class ImmutableLoanReceiptLedger {
    public static String processNightlyCirculation(LoanReceipt[] receipts) {
        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        if (receipts == null) {
            return "0 processed | 0 null skipped | 0 reference-only | 0 regular";
        }

        for (LoanReceipt receipt : receipts) {
            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;
            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | " + regular + " regular";
    }
}

class LoanReceipt {
    private static final String RECEIPT_LABEL;

    static {
        RECEIPT_LABEL = "Loan Receipt";
    }

    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        if (memberId == null || bookIds == null || bookIds.length > 20) {
            throw new IllegalArgumentException("Invalid receipt");
        }

        String[] copy = bookIds.clone();
        for (String bookId : copy) {
            if (!isValidBookId(bookId)) {
                throw new IllegalArgumentException("Invalid book ID");
            }
        }

        this.memberId = memberId;
        this.bookIds = copy;
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        return bookIds.clone();
    }

    public LoanReceipt withCorrectedBookId(int index, String newId) {
        if (index < 0 || index >= bookIds.length || !isValidBookId(newId)) {
            throw new IllegalArgumentException("Invalid correction");
        }

        String[] corrected = bookIds.clone();
        corrected[index] = newId;
        return new LoanReceipt(memberId, corrected);
    }

    private static boolean isValidBookId(String bookId) {
        return bookId != null && bookId.matches("BK-\\d{3}");
    }
}

class ReferenceOnlyLoanReceipt extends LoanReceipt {
    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
        super(memberId, bookIds);
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}
