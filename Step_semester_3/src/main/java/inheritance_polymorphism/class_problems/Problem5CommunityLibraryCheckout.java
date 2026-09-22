package inheritance_polymorphism.class_problems;

abstract class LibraryItem {
    private static int nextId = 1000;
    private final String itemId = "LIB-" + (++nextId);
    public abstract int getLoanPeriodDays();
    public String getItemId() { return itemId; }
}

interface Renewable { String renew(); }
interface Reservable { String reserve(); }

class Textbook extends LibraryItem implements Renewable, Reservable {
    private final String title;
    public Textbook(String title) { this.title = title; }
    public int getLoanPeriodDays() { return 14; }
    public String renew() { return title + " renewed"; }
    public String reserve() { return title + " reserved"; }
}

class Magazine extends LibraryItem implements Renewable {
    private final String title;
    public Magazine(String title) { this.title = title; }
    public int getLoanPeriodDays() { return 7; }
    public String renew() { return title + " renewed"; }
}

class DigitalPass implements Renewable {
    private final String resourceName;
    public DigitalPass(String resourceName) { this.resourceName = resourceName; }
    public String renew() { return resourceName + " renewed"; }
}

public class Problem5CommunityLibraryCheckout {
    static void processCheckouts(LibraryItem[] items) {
        for (LibraryItem x : items) System.out.println(x.getLoanPeriodDays());
    }

    static String reserveIfSupported(Object o) {
        if (o instanceof Reservable)
            return ((Reservable) o).reserve();
        return "Reservation not supported";
    }

    public static void main(String[] args) {
        Textbook t = new Textbook("Java Fundamentals");
        Magazine m = new Magazine("Tech Monthly");
        DigitalPass d = new DigitalPass("E-Journal Access");
        System.out.println(t.getLoanPeriodDays());
        System.out.println(t.renew());
        System.out.println(t.reserve());
        System.out.println(reserveIfSupported(m));
        System.out.println(reserveIfSupported(d));
        LibraryItem ref = t; // upcasting
        System.out.println(reserveIfSupported(ref));
    }
}