package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

public class ParsingContext {
    private ParsingState parsingState = new ColumnStart();

    public final CSVGridBuilder gridBuilder = new CSVGridBuilder();
    public final ColumnBuilder columnBuilder = new ColumnBuilder();
    public final CSVColumnSeparator separator;

    public ParsingContext(CSVColumnSeparator separator) {
        this.separator = separator;
    }

    public void setParsingState(ParsingState state) {
        if(state != null) {
            parsingState = state;
        }
    }
}
