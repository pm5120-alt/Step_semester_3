package inheritance_polymorphism.assignment_problems;

interface Exportable { String exportData(); }

class ExportCounter {
    private static int total;
    static void add() { total++; }
    static int getTotalExports() { return total; }
}

class ReportGenerator implements Exportable {
    private final String reportName;
    public ReportGenerator(String reportName) { this.reportName = reportName; }
    public String exportData() {
        ExportCounter.add();
        return "Exported report: " + reportName;
    }
}

class UserProfile implements Exportable {
    private final String username;
    public UserProfile(String username) { this.username = username; }
    public String exportData() {
        ExportCounter.add();
        return "Exported profile: " + username;
    }
}

public class Problem2OneClickDataExport {
    static void exportAll(Exportable[] items) {
        for (Exportable x : items) System.out.println(x.exportData());
    }
    static int getTotalExports() { return ExportCounter.getTotalExports(); }

    public static void main(String[] args) {
        ReportGenerator r = new ReportGenerator("Sales Q1");
        UserProfile u = new UserProfile("jane_doe");
        exportAll(new Exportable[]{r, u});
        System.out.println(getTotalExports());
    }
}