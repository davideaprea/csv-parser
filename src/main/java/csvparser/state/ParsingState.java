package csvparser.state;

public abstract class ParsingState {
    protected final ParsingContext context;

    ParsingState(ParsingContext context) {
        this.context = context;
    }

    public abstract ParsingState eval(final char character);

    protected String endColumn() {
        final String column = context.stringBuilder().toString();

        resetColumn();

        return column;
    }

    protected void addColumn(final String column) {
        context.grid().getLast().add(column);
    }

    protected void resetColumn() {
        context.stringBuilder().setLength(0);
    }
}
