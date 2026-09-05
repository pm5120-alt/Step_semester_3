public class PatientProfile {
    private String patientId;
    private String name;
    private boolean discharged;
    private String lockerPinHash;

    public PatientProfile() {
        this(null, null);
    }

    public PatientProfile(String name) {
        this(null, name);
    }

    public PatientProfile(String patientId, String name) {
        this.patientId = patientId;
        this.name = name;
        this.discharged = false;
    }

    public String getPatientId() { return patientId; }

    public void setPatientId(String id) {
        if (patientId == null) patientId = id;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isDischarged() { return discharged; }
    public void setDischarged(boolean discharged) { this.discharged = discharged; }

    public void setLockerPin(String pin) {
        if (pin != null && pin.matches("\\d{4,6}")) {
            lockerPinHash = Integer.toHexString(pin.hashCode());
        }
    }
}
