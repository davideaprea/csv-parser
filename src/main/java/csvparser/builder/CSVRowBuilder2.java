package csvparser.builder;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.state.ColumnEnd;
import csvparser.state.ColumnStart;
import csvparser.state.ParsingState;
import csvparser.state.RowEnd;

import java.util.ArrayList;
import java.util.List;

public class CSVRowBuilder2 {
    private ParsingState parsingState;
    private List<String> columnValues;

    public CSVRowBuilder2(CSVColumnSeparator separator) {
        this.parsingState = new ColumnStart(separator, new StringBuilder());
        columnValues = new ArrayList<>();
    }

    public void append(final char character) {
        if(parsingState instanceof RowEnd) {
            return;
        }

        if(parsingState instanceof ColumnEnd) {

        }
    }
}
