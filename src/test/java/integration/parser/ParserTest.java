package integration.parser;

import csv.enumeration.ColumnSeparator;
import csv.exception.UnexpectedCharacterException;
import csv.parser.Parser;
import integration.parser.testcase.ExceptionTestCase;
import integration.parser.testcase.ValidHeadedRowTestCase;
import integration.parser.testcase.ValidRowTestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ParserTest {
    private final Parser parser = new Parser(ColumnSeparator.COMMA);

    @ParameterizedTest
    @MethodSource("integration.loader.TestCaseLoader#validRows")
    void testValidRowsCases(ValidRowTestCase testCase) {
        Assertions.assertEquals(
                testCase.output(),
                parser.from(testCase.input()).toList()
        );
    }

    @ParameterizedTest
    @MethodSource("integration.loader.TestCaseLoader#validHeadedRows")
    void testValidHeadedRowsCases(ValidHeadedRowTestCase testCase) {
        Assertions.assertEquals(
                testCase.output(),
                parser.withHeadersFrom(testCase.input()).toList()
        );
    }

    @ParameterizedTest
    @MethodSource("integration.loader.TestCaseLoader#unexpectedCharacterTestCase")
    void testUnexpectedCharacterCases(ExceptionTestCase testCase) {
        Throwable actualException = Assertions.assertThrows(
                UnexpectedCharacterException.class,
                () -> parser.from(testCase.input()).toList()
        );

        Assertions.assertEquals(testCase.output(), actualException);
    }
}
