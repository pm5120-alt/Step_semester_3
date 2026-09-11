public class ReferenceDeskSubclassReach {
    public static String classifyAccess(String fieldModifier, String accessorContext) {
        return AccessChecker.classifyAccess(fieldModifier, accessorContext);
    }

    public static String describeContext(String accessorContext) {
        return AccessChecker.describeContext(accessorContext);
    }
}

class AccessChecker {
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

        if (accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
            return fieldModifier.equals("public") || fieldModifier.equals("protected")
                    ? "ALLOWED" : "DENIED";
        }

        if (accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE")) {
            return fieldModifier.equals("public") ? "ALLOWED" : "DENIED";
        }

        return "DENIED";
    }

    public static String describeContext(String accessorContext) {
        if (accessorContext == null || accessorContext.isEmpty()) {
            return "";
        }

        String[] words = accessorContext.toLowerCase().split("_");
        StringBuilder answer = new StringBuilder();

        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }
            if (answer.length() > 0) {
                answer.append(' ');
            }
            answer.append(Character.toUpperCase(word.charAt(0)));
            answer.append(word.substring(1));
        }

        return answer.toString();
    }
}
