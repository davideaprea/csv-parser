package csv;

import csv.enumeration.ColumnSeparator;
import csv.parser.Parser;
import csv.structure.Row;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
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
                                .map(Row::getColumns)
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
                                    .map(Row::getColumns)
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
