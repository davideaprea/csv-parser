package csv.testcase.valid;

import csv.structure.Row;

import java.io.Reader;
import java.util.List;

public record ValidRowsTestCase(
        Reader input,
        List<Row> output
) {
}
