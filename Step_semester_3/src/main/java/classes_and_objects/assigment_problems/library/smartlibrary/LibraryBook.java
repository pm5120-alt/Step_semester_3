package library.smartlibrary;

public class LibraryBook {

    private String bookId;
    private String title;
    private boolean issued;

    private static String libraryName = "SRM Central Library";
    private static int bookCount = 0;

    public LibraryBook(String bookId, String title) {
        if (bookId == null || bookId.trim().isEmpty()) {
            System.out.println("Invalid book ID.");
            return;
        }

        if (title == null || title.trim().isEmpty()) {
            System.out.println("Invalid book title.");
            return;
        }

        this.bookId = bookId;
        this.title = title;
        this.issued = false;

        bookCount++;
        System.out.println("Book created: " + bookId + " - " + title);
    }

    public LibraryBook(String bookId, String title, boolean issued) {
        this(bookId, title);

        if (this.bookId != null && this.title != null) {
            this.issued = issued;
        }
    }

    public void issueBook() {
        if (issued) {
            System.out.println("Book " + bookId + " is already issued.");
        } else {
            issued = true;
            System.out.println("Book " + bookId + " issued successfully.");
        }
    }

    public void returnBook() {
        issued = false;
        System.out.println("Book " + bookId + " returned successfully.");
    }

    public static void printLibraryInfo() {
        System.out.println();
        System.out.println("Library: " + libraryName);
        System.out.println("Total valid books: " + bookCount);
    }

    public static void main(String[] args) {

        LibraryBook book1 = new LibraryBook("B101", "Java Programming");
        LibraryBook book2 = new LibraryBook("B102", "Data Structures");
        LibraryBook book3 = new LibraryBook("B103", "Database Systems", true);

        System.out.println();

        book1.issueBook();
        book1.issueBook();
        book1.returnBook();

        LibraryBook.printLibraryInfo();
    }
}