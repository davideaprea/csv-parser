package csv;

import csv.enumeration.ColumnSeparator;
import csv.parser.Parser;
import csv.structure.Row;
import csv.testcase.invalid.InvalidTestCase;
import csv.testcase.TestCases;
import csv.testcase.valid.ValidTestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ParserTest {
    private final Parser parser = new Parser(ColumnSeparator.COMMA);

    @Test
    void testValidCases() {
        List<ValidTestCase> cases = TestCases.VALID_TEST_CASES;

        for (int i = 0; i < cases.size(); i++) {
            final ValidTestCase testCase = cases.get(i);

            try {
                Assertions.assertEquals(
                        testCase.output(),
                        parser
                                .from(new StringReader(testCase.input()))
                                .map(this::rowToList)
                                .toList()
                );
            } catch (Throwable e) {
                System.out.println("Test n. " + (i + 1) + " failed.");

                throw e;
            }
        }
    }

    @Test
    void testInvalidRowSizeCases() {
        List<InvalidTestCase<?>> cases = TestCases.invalidTestCases;

        for (int i = 0; i < cases.size(); i++) {
            final InvalidTestCase<?> testCase = cases.get(i);

            try {
                Throwable actualException = Assertions.assertThrows(
                        testCase.exceptionType,
                        () -> {
                            parser
                                    .from(new StringReader(testCase.input))
                                    .map(this::rowToList)
                                    .toList();
                        }
                );

                Assertions.assertTrue(testCase.isSameException(actualException));
            } catch (Throwable e) {
                System.out.println("Test n. " + (i + 1) + " failed.");

                throw e;
            }
        }
    }

    private List<String> rowToList(Row row) {
        List<String> columns = new ArrayList<>();

        row.iterator().forEachRemaining(columns::add);

        return columns;
    }
}
