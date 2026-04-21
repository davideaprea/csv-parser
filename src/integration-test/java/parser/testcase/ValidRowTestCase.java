package parser.testcase;

import io.github.davideaprea.csvparser.model.Row;

import java.io.Reader;
import java.util.List;

public record ValidRowTestCase(
        Reader input,
        List<Row> output
) {
}
