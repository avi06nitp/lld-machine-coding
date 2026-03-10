package enums;

public enum ReviewCategory {
    NAMING_CONVENTION("Naming Convention"),
    CODE_COMPLEXITY("Code Complexity"),
    SECURITY("Security"),
    CODE_DUPLICATION("Code Duplication"),
    BEST_PRACTICES("Best Practices");

    private final String displayName;

    ReviewCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
