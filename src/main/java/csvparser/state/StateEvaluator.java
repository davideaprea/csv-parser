package csvparser.state;

import csvparser.builder.GridBuilder;
import csvparser.enumeration.CSVColumnSeparator;

import java.util.List;

public class StateEvaluator {
    private ParsingState state;

    public StateEvaluator(CSVColumnSeparator separator) {
        ParsingContext context = new ParsingContext(new GridBuilder(), separator);
        state = new RowInit(context);
    }

    public void eval(final char character) {
        state = state.eval(character);
    }

    public List<List<String>> end() {
        return state.end();
    }
}
