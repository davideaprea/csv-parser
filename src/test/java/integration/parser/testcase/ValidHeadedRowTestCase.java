package integration.parser.testcase;

import csv.structure.HeadedRow;

import java.io.Reader;
import java.util.List;

public record ValidHeadedRowTestCase(
        Reader input,
        List<HeadedRow> output
) {
}
