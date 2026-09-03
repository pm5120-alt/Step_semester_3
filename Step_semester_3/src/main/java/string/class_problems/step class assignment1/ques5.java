public void classifyWordLengths(String review) {
    int shortCount = 0;
    int mediumCount = 0;
    int longCount = 0;

    String[] words = review.split("\\s+");
    
    for (String word : words) {
        int len = word.replaceAll("[^a-zA-Z]", "").length();
        if (len >= 1 && len <= 4) {
            shortCount++;
        } else if (len >= 5 && len <= 8) {
            mediumCount++;
        } else if (len >= 9) {
            longCount++;
        }
    }

    System.out.printf("Short: %d | Medium: %d | Long: %d%n", shortCount, mediumCount, longCount);
}