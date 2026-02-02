package csvparser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.iterator.RowIterator;
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
        final RowIterator rowIterator = new RowIterator(reader, separator);

        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(rowIterator, Spliterator.ORDERED),
                false
        );
    }
}
