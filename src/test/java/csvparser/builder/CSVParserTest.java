package csvparser.builder;

import csvparser.CSVParser;
import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.InvalidRowSizeException;
import csvparser.exception.UnexpectedCharacterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

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
        List<InvalidCSVTestCase<InvalidRowSizeException>> cases = CSVTestCases.invalidRowSizeCases;

        for (int i = 0; i < cases.size(); i++) {
            final InvalidCSVTestCase<InvalidRowSizeException> testCase = cases.get(i);

            try {
                InvalidRowSizeException actualException = Assertions.assertThrows(
                        InvalidRowSizeException.class,
                        () -> CSVParser.parse(testCase.input(), CSVColumnSeparator.COMMA)
                );

                InvalidRowSizeException expectedException = testCase.exception();

                if (
                        actualException.actualSize != expectedException.actualSize ||
                                actualException.expectedSize != expectedException.expectedSize ||
                                actualException.rowNumber != expectedException.rowNumber
                ) {
                    throw new AssertionFailedError("Exceptions indexes are different.", expectedException, actualException);
                }
            } catch (Throwable e) {
                System.out.println("Test n. " + (i + 1) + " failed.");

                throw e;
            }
        }
    }

    @Test
    void testUnexpectedCharacterExceptionCases() {
        List<InvalidCSVTestCase<UnexpectedCharacterException>> cases = CSVTestCases.unexpectedCharacterCases;

        for (int i = 0; i < cases.size(); i++) {
            final InvalidCSVTestCase<UnexpectedCharacterException> testCase = cases.get(i);

            try {
                UnexpectedCharacterException exception = Assertions.assertThrows(UnexpectedCharacterException.class, () -> CSVParser.parse(testCase.input(), CSVColumnSeparator.COMMA));

                Assertions.assertEquals(testCase.exception().unexpectedCharacter, exception.unexpectedCharacter);
            } catch (Throwable e) {
                System.out.println("Test n. " + (i + 1) + " with input " + testCase.input() + " failed.");

                throw e;
            }
        }
    }
}
