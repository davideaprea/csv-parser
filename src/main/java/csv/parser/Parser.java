package csv.parser;

import csv.enumeration.ColumnSeparator;
import csv.iterator.RowIterator;
import csv.structure.Row;

import java.io.Reader;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Parser {
    private final ColumnSeparator separator;

    public Parser(ColumnSeparator separator) {
        this.separator = separator;
    }

    public Stream<Row> from(Reader reader) {
        final RowParser rowParser = new RowParser(reader, separator);
        final RowIterator rowIterator = new RowIterator(rowParser);

        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(rowIterator, Spliterator.ORDERED),
                false
        );
    }
}
