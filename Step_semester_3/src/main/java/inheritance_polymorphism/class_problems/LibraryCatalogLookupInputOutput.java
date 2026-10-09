package inheritance_polymorphism.class_problems;

/** Week 9 - equivalent lookup using a typed record-like class. */
public class LibraryCatalogLookupInputOutput {
    public static class Book {
        public final String isbn;
        public final String title;
        public Book(String isbn, String title) { this.isbn = isbn; this.title = title; }
    }

    public static String findBook(Book[] catalog, String targetIsbn) {
        int low = 0, high = catalog.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = catalog[mid].isbn.compareTo(targetIsbn);
            if (cmp == 0) return catalog[mid].title;
            if (cmp < 0) low = mid + 1;
            else high = mid - 1;
        }
        return "Not Found";
    }

    public static void main(String[] args) {
        Book[] books = {
            new Book("0001112223", "Intro to Algebra"),
            new Book("0002223334", "Beginning Python"),
            new Book("0003334445", "Classic Mythology")
        };
        System.out.println(findBook(books, "0002223334"));
    }
}
