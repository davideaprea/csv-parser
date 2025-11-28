package csvparser.parser;

abstract class ParsingState {
    protected final ParsingContext context;

    ParsingState(ParsingContext context) {
        this.context = context;
    }

    abstract ParsingState eval(final char character);

    protected void buildColumn() {
        final String column = context.stringBuilder().toString();

        context.grid().getLast().add(column);

        resetColumn();
    }

    protected void resetColumn() {
        context.stringBuilder().setLength(0);
    }
}
