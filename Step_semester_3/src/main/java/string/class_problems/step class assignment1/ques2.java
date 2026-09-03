public void checkDuplicateSeats(int[] seatNumbers) {
    boolean found = false;
    
    for (int i = 0; i < seatNumbers.length; i++) {
        for (int j = i + 1; j < seatNumbers.length; j++) {
            if (seatNumbers[i] == seatNumbers[j]) {
                boolean alreadyPrinted = false;
                for (int k = 0; k < i; k++) {
                    if (seatNumbers[k] == seatNumbers[i]) {
                        alreadyPrinted = true;
                        break;
                    }
                }
                if (!alreadyPrinted) {
                    System.out.println("Duplicate Seat Number Found: " + seatNumbers[i]);
                    found = true;
                }
            }
        }
    }
    
    if (!found) {
        System.out.println("No Duplicate Seats Found");
    }
}