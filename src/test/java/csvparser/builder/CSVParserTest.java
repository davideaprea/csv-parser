package csvparser.builder;

import csvparser.CSVParser;
import csvparser.enumeration.CSVColumnSeparator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CSVParserTest {
    @Test
    void testValidCases() {
        List<ValidCSVTestCase> cases = CSVTestCases.validCSVTestCases;

        for (int i = 0; i < cases.size(); i++) {
            final ValidCSVTestCase testCase = cases.get(i);

            try {
                Assertions.assertEquals(
                        testCase.output(),
                        CSVParser.parse(testCase.input(), CSVColumnSeparator.COMMA)
                );
            } catch (Throwable e) {
                System.out.println("Test n. " + (i + 1) + " failed.");

                throw e;
            }
        }
    }

    @Test
    void testInvalidRowSizeCases() {
        List<InvalidCSVTestCase<?>> cases = CSVTestCases.invalidCSVTestCases;

        for (int i = 0; i < cases.size(); i++) {
            final InvalidCSVTestCase<?> testCase = cases.get(i);

            try {
                Throwable actualException = Assertions.assertThrows(
                        testCase.exceptionType,
                        () -> CSVParser.parse(testCase.input, CSVColumnSeparator.COMMA)
                );

                Assertions.assertTrue(testCase.isSameException(actualException));
            } catch (Throwable e) {
                System.out.println("Test n. " + (i + 1) + " failed.");

                throw e;
            }
        }
    }
}
