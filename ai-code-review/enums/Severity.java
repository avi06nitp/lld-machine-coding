package enums;

public enum Severity {
    ERROR(3, "ERROR"),
    WARNING(2, "WARNING"),
    INFO(1, "INFO");

    private final int level;
    private final String label;

    Severity(int level, String label) {
        this.level = level;
        this.label = label;
    }

    public int getLevel() {
        return level;
    }

    public String getLabel() {
        return label;
    }
}
