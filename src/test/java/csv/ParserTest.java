package csv;

import csv.enumeration.ColumnSeparator;
import csv.parser.Parser;
import csv.testcase.invalid.InvalidTestCase;
import csv.testcase.TestCases;
import csv.testcase.valid.ValidHeadedRowsTestCase;
import csv.testcase.valid.ValidRowsTestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;

public class ParserTest {
    private final Parser parser = new Parser(ColumnSeparator.COMMA);

    @Test
    void testValidRowsCases() {
        List<ValidRowsTestCase> cases = TestCases.VALID_ROWS_TEST_CASES;

        for (int i = 0; i < cases.size(); i++) {
            final ValidRowsTestCase testCase = cases.get(i);

            try {
                Assertions.assertEquals(
                        testCase.output(),
                        parser.from(testCase.input()).toList()
                );
            } catch (Throwable e) {
                System.out.println("Test n. " + (i + 1) + " failed.");

                throw e;
            }
        }
    }

    @Test
    void testValidHeadedRowsCases() {
        List<ValidHeadedRowsTestCase> cases = TestCases.VALID_HEADED_ROWS_TEST_CASES;

        for (int i = 0; i < cases.size(); i++) {
            final ValidHeadedRowsTestCase testCase = cases.get(i);

            try {
                Assertions.assertEquals(
                        testCase.output(),
                        parser.withHeadersFrom(testCase.input()).toList()
                );
            } catch (Throwable e) {
                System.out.println("Test n. " + (i + 1) + " failed.");

                throw e;
            }
        }
    }

    @Test
    void testInvalidRowsCases() {
        List<InvalidTestCase<?>> cases = TestCases.invalidTestCases;

        for (int i = 0; i < cases.size(); i++) {
            final InvalidTestCase<?> testCase = cases.get(i);

            try {
                Throwable actualException = Assertions.assertThrows(
                        testCase.exceptionType,
                        () -> {
                            parser
                                    .from(new StringReader(testCase.input))
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
}
