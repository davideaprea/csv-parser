package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

import java.util.List;

public class ParsingContext {
    private ParsingState state;

    public ParsingContext(CSVColumnSeparator separator) {
        state = new RowInit(new GridBuilder());
    }

    public void eval(final char character) {
        state = state.eval(character);
    }

    public List<List<String>> end() {
        return state.end();
    }
}
