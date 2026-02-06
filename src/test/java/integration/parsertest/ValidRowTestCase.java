package integration.parsertest;

import csv.structure.Row;

import java.io.Reader;
import java.util.List;

public record ValidRowTestCase(
        Reader input,
        List<Row> output
) {
}
