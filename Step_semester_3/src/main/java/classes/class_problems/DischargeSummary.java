import java.util.Arrays;

public final class DischargeSummary {
    private static final String MEDICATION_PATTERN = "MED-[A-Z]";
    private final String patientId;
    private final String[] medicationCodes;

    static {
        // One-time shared initialization for the medication validation rule.
    }

    public DischargeSummary(String patientId, String[] medicationCodes) {
        if (patientId == null || medicationCodes == null) {
            throw new IllegalArgumentException("Invalid discharge summary");
        }
        for (String code : medicationCodes) {
            if (code == null || !code.matches(MEDICATION_PATTERN)) {
                throw new IllegalArgumentException("Invalid medication code");
            }
        }
        this.patientId = patientId;
        this.medicationCodes = medicationCodes.clone();
    }

    public String getPatientId() { return patientId; }

    public String[] getMedicationCodes() {
        return medicationCodes.clone();
    }

    public DischargeSummary withCorrectedMedication(int index, String newCode) {
        if (index < 0 || index >= medicationCodes.length
                || newCode == null || !newCode.matches(MEDICATION_PATTERN)) {
            throw new IllegalArgumentException("Invalid correction");
        }
        String[] corrected = medicationCodes.clone();
        corrected[index] = newCode;
        return new DischargeSummary(patientId, corrected);
    }

    public static String processNightlyBatch(DischargeSummary[] summaries) {
        int processed = 0, nullSkipped = 0, critical = 0, routine = 0;
        if (summaries != null) {
            for (DischargeSummary summary : summaries) {
                if (summary == null) {
                    nullSkipped++;
                } else {
                    processed++;
                    if (summary instanceof CriticalCareDischargeSummary) critical++;
                    else routine++;
                }
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | "
                + critical + " critical-care | " + routine + " routine";
    }
}

class CriticalCareDischargeSummary extends DischargeSummary {
    private final int icuDays;

    public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
        super(patientId, medicationCodes);
        if (icuDays < 0) throw new IllegalArgumentException("Invalid ICU days");
        this.icuDays = icuDays;
    }

    public int getIcuDays() { return icuDays; }
}
