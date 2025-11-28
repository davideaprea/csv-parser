package csvparser.state;

public abstract class ParsingState {
    protected final ParsingContext context;

    ParsingState(ParsingContext context) {
        this.context = context;
    }

    public abstract ParsingState eval(final char character);

    protected void buildColumn() {
        addColumn();

        resetColumn();
    }

    protected void resetColumn() {
        context.stringBuilder().setLength(0);
    }

    protected void addColumn() {
        final String column = context.stringBuilder().toString();

        context.grid().getLast().add(column);
    }
}
