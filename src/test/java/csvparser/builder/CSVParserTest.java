package csvparser.builder;

import csvparser.CSVParser;
import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.InvalidRowSizeException;
import csvparser.exception.MalformedFileException;
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
    void testMalformedFileExceptionCases() {
        String[] cases = {
                "abc\r\ndef\r",
                "abc\r\n\"def",
                "\"",
                "\"abc",
                "\"a\"\"b"
        };

        for (String csv : cases) {
            Assertions.assertThrows(MalformedFileException.class, () -> CSVParser.parse(csv, CSVColumnSeparator.COMMA));
        }
    }

    @Test
    void testUnexpectedCharacterExceptionCases() {
        List<String> cases = List.of(
                "a\nb",
                "\"abc\"x",
                "\"abc\" xyz",
                "a,b\nc",
                "a,b\r\nc\nd",
                "a,\"b\"c",
                "\"ab\"c",
                " \"abc\"",
                "a, \"b\"",
                "\"abc\";",
                "a,b\rx",
                "a,b\r c",
                "a\"bc",
                "a,b\r\nc,d\r\n1,2\n3"
        );

        for (int i = 0; i < cases.size(); i++) {
            final String csv = cases.get(i);

            Assertions.assertThrows(UnexpectedCharacterException.class, () -> CSVParser.parse(csv, CSVColumnSeparator.COMMA));
        }
    }
}
