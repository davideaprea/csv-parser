package csvparser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.structure.CSVRow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;

public class CSVParserTest {
    private final CSVParser parser = new CSVParser(CSVColumnSeparator.COMMA);

    @Test
    void testValidCases() {
        List<ValidCSVTestCase> cases = CSVTestCases.validCSVTestCases;

        for (int i = 0; i < cases.size(); i++) {
            final ValidCSVTestCase testCase = cases.get(i);

            try {
                Assertions.assertEquals(
                        testCase.output(),
                        parser
                                .from(new StringReader(testCase.input()))
                                .map(CSVRow::getColumns)
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
        List<InvalidCSVTestCase<?>> cases = CSVTestCases.invalidCSVTestCases;

        for (int i = 0; i < cases.size(); i++) {
            final InvalidCSVTestCase<?> testCase = cases.get(i);

            try {
                Throwable actualException = Assertions.assertThrows(
                        testCase.exceptionType,
                        () -> {
                            parser
                                    .from(new StringReader(testCase.input))
                                    .map(CSVRow::getColumns)
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
