package csvparser.state;

import java.util.List;

public abstract class ParsingState {
    protected final ParsingContext context;

    protected ParsingState(ParsingContext context) {
        this.context = context;
    }

    public abstract ParsingState eval(final char character);

    public List<List<String>> end() {
        context.gridBuilder().endColumn();

        return context.gridBuilder().build();
    }
}
