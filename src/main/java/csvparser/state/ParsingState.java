package csvparser.state;

import csvparser.builder.GridBuilder;

import java.util.List;

abstract class ParsingState {
    protected final ParsingContext context;

    protected ParsingState(ParsingContext context) {
        this.context = context;
    }

    public abstract ParsingState eval(final char character);

    public List<List<String>> end() {
        final GridBuilder gridBuilder = context.gridBuilder();

        gridBuilder.endColumn();
        gridBuilder.endRow();

        return gridBuilder.build();
    }
}
