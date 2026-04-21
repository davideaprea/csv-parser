package io.github.davideaprea.csvparser.exception;

public class DuplicateColumnNameException extends RuntimeException {
    private final String name;
    private final int firstOccurrenceIndex;
    private final int conflictingOccurrenceIndex;

    public DuplicateColumnNameException(String name, int firstOccurrenceIndex, int conflictingOccurrenceIndex) {
        super("Column name %s is already defined at index %d. Found duplicate at index %d.".formatted(
                name, firstOccurrenceIndex, conflictingOccurrenceIndex
        ));

        this.name = name;
        this.firstOccurrenceIndex = firstOccurrenceIndex;
        this.conflictingOccurrenceIndex = conflictingOccurrenceIndex;
    }

    public String getName() {
        return name;
    }

    public int getFirstOccurrenceIndex() {
        return firstOccurrenceIndex;
    }

    public int getConflictingOccurrenceIndex() {
        return conflictingOccurrenceIndex;
    }
}
