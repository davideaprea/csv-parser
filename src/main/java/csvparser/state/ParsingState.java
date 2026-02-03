package csvparser.state;

import csvparser.structure.Row;

public abstract class ParsingState {
    protected final ParsingContext context;

    protected ParsingState(ParsingContext context) {
        this.context = context;
    }

    public abstract ParsingState eval(final char character);

    public Row end() {
        context.rowBuilder().endColumn();

        return context.rowBuilder().build();
    }
}
