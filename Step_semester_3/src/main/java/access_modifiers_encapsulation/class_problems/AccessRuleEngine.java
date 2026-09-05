public class AccessRuleEngine {
    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) return "DENIED";

        switch (accessorContext) {
            case "SAME_CLASS":
                return "ALLOWED";
            case "SAME_PACKAGE":
                return fieldModifier.equals("private") ? "DENIED" : "ALLOWED";
            case "DIFFERENT_PACKAGE":
                return fieldModifier.equals("public") ? "ALLOWED" : "DENIED";
            case "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE":
                return fieldModifier.equals("public") || fieldModifier.equals("protected")
                        ? "ALLOWED" : "DENIED";
            case "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE":
                return fieldModifier.equals("public") ? "ALLOWED" : "DENIED";
            default:
                return "DENIED";
        }
    }

    public static String summarizeBatch(String[][] attempts) {
        int allowed = 0;
        int denied = 0;
        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt != null && attempt.length >= 2
                        && classifyAccess(attempt[0], attempt[1]).equals("ALLOWED")) {
                    allowed++;
                } else {
                    denied++;
                }
            }
        }
        return "Allowed: " + allowed + " | Denied: " + denied;
    }

    public static String describeContext(String accessorContext) {
        if (accessorContext == null || accessorContext.isEmpty()) return "";
        String[] words = accessorContext.toLowerCase().split("_");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.isEmpty()) continue;
            if (result.length() > 0) result.append(' ');
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return result.toString();
    }
}
