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

/**
 * Entry point for parsing raw CSV data into {@link Row} objects.
 */
public class Parser {
    private final ColumnSeparator separator;

    /**
     * Configures a new instance with the specified column separator.
     *
     * @param separator the column separator to use while parsing
     */
    public Parser(ColumnSeparator separator) {
        this.separator = separator;
    }

    /**
     * Parses the input from the given {@link Reader} into a {@link Stream}
     * of {@link Row} objects.
     * <p>
     * Each row represents a line of input split according to the configured
     * {@link ColumnSeparator}. All rows are validated to ensure they have
     * a consistent number of columns.
     *
     * @param reader the source of CSV raw data
     * @return a sequential {@code Stream} of parsed {@code Row} instances
     */
    public Stream<Row> from(Reader reader) {
        final RowIterator rowIterator = configIterator(reader);

        return toStream(rowIterator);
    }

    /**
     * Parses the input from the given {@link Reader} into a {@link Stream}
     * of {@link HeadedRow} objects.
     * <p>
     * The first row of the input is interpreted as a header row. Each column
     * name is mapped to its index position and used to construct
     * {@link HeadedRow} instances for the remaining rows.
     *
     * @param reader the source of CSV raw data
     * @return a sequential {@code Stream} of {@code HeadedRow} instances,
     * where each row can be accessed by column name
     */
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
