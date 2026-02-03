package csvparser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.iterator.RowIterator;
import csvparser.parser.CSVRowParser;
import csvparser.structure.CSVRow;

import java.io.Reader;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CSVParser {
    private final CSVColumnSeparator separator;

    public CSVParser(CSVColumnSeparator separator) {
        this.separator = separator;
    }

    public Stream<CSVRow> from(Reader reader) {
        final CSVRowParser csvRowParser = new CSVRowParser(reader, separator);
        final RowIterator rowIterator = new RowIterator(csvRowParser);

        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(rowIterator, Spliterator.ORDERED),
                false
        );
    }
}
