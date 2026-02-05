package integration.testcase;

import csv.structure.HeadedRow;

import java.io.Reader;
import java.util.List;

public record ValidHeadedRowsTestCase(
        Reader input,
        List<HeadedRow> output
) {
}
