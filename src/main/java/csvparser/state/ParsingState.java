package csvparser.state;

import csvparser.structure.CSVRow;

public abstract class ParsingState {
    protected final ParsingContext context;

    protected ParsingState(ParsingContext context) {
        this.context = context;
    }

    public abstract ParsingState eval(final char character);

    public CSVRow end() {
        context.rowBuilder().endColumn();

        return context.rowBuilder().build();
    }
}
