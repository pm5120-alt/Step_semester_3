import java.util.LinkedHashMap;
import java.util.Map;

public class MembershipFieldReachChecker {
    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) {
            return "DENIED";
        }

        if (accessorContext.equals("SAME_CLASS")) {
            return "ALLOWED";
        }

        if (accessorContext.equals("SAME_PACKAGE")) {
            return fieldModifier.equals("private") ? "DENIED" : "ALLOWED";
        }

        if (accessorContext.equals("DIFFERENT_PACKAGE")) {
            return fieldModifier.equals("public") ? "ALLOWED" : "DENIED";
        }

        return "DENIED";
    }

    public static String summarizeByModifier(String[][] attempts) {
        Map<String, int[]> result = new LinkedHashMap<>();
        result.put("private", new int[2]);
        result.put("default", new int[2]);
        result.put("protected", new int[2]);
        result.put("public", new int[2]);

        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt == null || attempt.length < 2 || !result.containsKey(attempt[0])) {
                    continue;
                }

                if (classifyAccess(attempt[0], attempt[1]).equals("ALLOWED")) {
                    result.get(attempt[0])[0]++;
                } else {
                    result.get(attempt[0])[1]++;
                }
            }
        }

        StringBuilder answer = new StringBuilder();
        for (Map.Entry<String, int[]> entry : result.entrySet()) {
            if (answer.length() > 0) {
                answer.append(" | ");
            }
            int[] counts = entry.getValue();
            answer.append(entry.getKey())
                    .append(": ")
                    .append(counts[0])
                    .append(" allowed / ")
                    .append(counts[1])
                    .append(" denied");
        }
        return answer.toString();
    }
}

class LibraryMember {
    private final String membershipId;
    protected String branchCode;
    double finesOwed;
    public String displayName;

    public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
        if (membershipId == null || membershipId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid membershipId");
        }
        this.membershipId = membershipId.trim();
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }

    public String getMembershipId() {
        return membershipId;
    }
}
