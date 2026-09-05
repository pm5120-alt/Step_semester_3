public void analyzeInventory(int[] sectionA, int[] sectionB) {
    int sumA = 0;
    int sumB = 0;
    int maxVal = -1;
    String maxSection = "";
    int maxItem = -1;

    for (int i = 0; i < sectionA.length; i++) {
        sumA += sectionA[i];
        if (sectionA[i] > maxVal) {
            maxVal = sectionA[i];
            maxSection = "Section A";
            maxItem = i + 1;
        }
    }

    for (int i = 0; i < sectionB.length; i++) {
        sumB += sectionB[i];
        if (sectionB[i] > maxVal) {
            maxVal = sectionB[i];
            maxSection = "Section B";
            maxItem = i + 1;
        }
    }

    String status = (sumA == sumB) ? "Balanced" : "Not Balanced";

    System.out.printf("Section A Total: %d | Section B Total: %d | Status: %s | Highest Quantity: %d (%s, Item %d)%n",
        sumA, sumB, status, maxVal, maxSection, maxItem);
}