package csv.parser;

import csv.enumeration.ColumnSeparator;
import csv.iterator.RowIterator;
import csv.structure.HeadedRow;
import csv.structure.Row;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
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
        final RowIterator rowIterator = configIterator(reader);

        return toStream(rowIterator);
    }

    public Stream<HeadedRow> withHeadersFrom(Reader reader) {
        final RowIterator rowIterator = configIterator(reader);
        Map<String, Integer> headers = new HashMap<>();

        if (rowIterator.hasNext()) {
            Row headersRow = rowIterator.next();

            for (int i = 0; i < headersRow.size(); i++) {
                headers.put(headersRow.get(i), i);
            }
        }

        return toStream(rowIterator).map(row -> new HeadedRow(row, headers));
    }

    private RowIterator configIterator(Reader reader) {
        final RowParser rowParser = new RowParser(reader, separator);

        return new RowIterator(rowParser);
    }

    private Stream<Row> toStream(RowIterator iterator) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                false
        );
    }
}
