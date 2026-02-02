package csvparser.state;

import csvparser.structure.CSVRow;

public class RowInit extends ParsingState {
    public RowInit(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        ParsingState nextState = new ColumnStart(context);

        return nextState.eval(character);
    }

    @Override
    public CSVRow end() {
        return context.rowBuilder().build();
    }
}
