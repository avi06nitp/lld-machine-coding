package enums;

public enum Language {
    JAVA("java"),
    PYTHON("py"),
    JAVASCRIPT("js"),
    TYPESCRIPT("ts"),
    GENERAL("txt");

    private final String extension;

    Language(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static Language fromExtension(String ext) {
        for (Language lang : values()) {
            if (lang.extension.equalsIgnoreCase(ext)) {
                return lang;
            }
        }
        return GENERAL;
    }
}
