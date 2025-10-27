package csvparser.enumeration;

public enum ParsingState {
    IN_QUOTES,
    ESCAPING,
    OUT_QUOTES,
    COLUMN_START
}