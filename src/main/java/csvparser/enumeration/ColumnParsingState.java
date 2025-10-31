package csvparser.enumeration;

public enum ColumnParsingState {
    IN_QUOTED_COLUMN,
    ESCAPING,
    IN_NORMAL_COLUMN,
    COLUMN_START,
    COLUMN_END,
    OUT_QUOTED_COLUMN
}