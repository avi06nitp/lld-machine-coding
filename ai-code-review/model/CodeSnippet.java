package model;

import enums.Language;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CodeSnippet {
    private final String fileName;
    private final String content;
    private final Language language;
    private final List<String> lines;

    public CodeSnippet(String fileName, String content, Language language) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("File name cannot be blank");
        }
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
        this.fileName = fileName;
        this.content = content;
        this.language = language;
        this.lines = Collections.unmodifiableList(Arrays.asList(content.split("\n", -1)));
    }

    public String getFileName() {
        return fileName;
    }

    public String getContent() {
        return content;
    }

    public Language getLanguage() {
        return language;
    }

    public List<String> getLines() {
        return lines;
    }

    public int getLineCount() {
        return lines.size();
    }
}
