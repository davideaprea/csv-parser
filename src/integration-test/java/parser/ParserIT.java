package parser;

import io.github.davideaprea.csvparser.enumeration.ColumnSeparator;
import io.github.davideaprea.csvparser.exception.UnexpectedCharacterException;
import io.github.davideaprea.csvparser.parser.Parser;
import parser.testcase.ExceptionTestCase;
import parser.testcase.ValidHeadedRowTestCase;
import parser.testcase.ValidRowTestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ParserIT {
    private final Parser parser = new Parser(ColumnSeparator.COMMA);

    @ParameterizedTest
    @MethodSource("loader.TestCaseLoader#validRows")
    void testValidRowsCases(ValidRowTestCase testCase) {
        Assertions.assertEquals(
                testCase.output(),
                parser.from(testCase.input()).toList()
        );
    }

    @ParameterizedTest
    @MethodSource("loader.TestCaseLoader#validHeadedRows")
    void testValidHeadedRowsCases(ValidHeadedRowTestCase testCase) {
        Assertions.assertEquals(
                testCase.output(),
                parser.withHeadersFrom(testCase.input()).toList()
        );
    }

    @ParameterizedTest
    @MethodSource("loader.TestCaseLoader#unexpectedCharacterTestCase")
    void testUnexpectedCharacterCases(ExceptionTestCase testCase) {
        UnexpectedCharacterException actual = Assertions.assertThrows(
                UnexpectedCharacterException.class,
                () -> parser.from(testCase.input()).toList()
        );
        UnexpectedCharacterException expected = (UnexpectedCharacterException) testCase.output();

        Assertions.assertEquals(expected.getUnexpectedCharacter(), actual.getUnexpectedCharacter());
    }
}
