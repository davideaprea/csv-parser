package csvparser.enumeration;

public enum ColumnParsingState {
    IN_QUOTED,
    ESCAPING,
    IN_NORMAL,
    START,
    END,
    OUT_QUOTED
}