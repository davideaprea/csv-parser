package integration;

import csv.enumeration.ColumnSeparator;
import csv.exception.UnexpectedCharacterException;
import csv.parser.Parser;
import integration.testcase.UnexpectedCharacterTestCase;
import integration.testcase.ValidHeadedRowsTestCase;
import integration.testcase.ValidRowsTestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.StringReader;

public class ParserTest {
    private final Parser parser = new Parser(ColumnSeparator.COMMA);

    @ParameterizedTest(name = "{0}")
    @MethodSource("loader.TestCaseLoader#loadValidRowsTestCases")
    void testValidRowsCases(ValidRowsTestCase testCase) {
        Assertions.assertEquals(
                testCase.output(),
                parser.from(testCase.input()).toList()
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("loader.TestCaseLoader#loadValidHeadedRowsTestCases")
    void testValidHeadedRowsCases(ValidHeadedRowsTestCase testCase) {
        Assertions.assertEquals(
                testCase.output(),
                parser.withHeadersFrom(testCase.input()).toList()
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("loader.TestCaseLoader#loadUnexpectedCharacterTestCases")
    void testUnexpectedCharacterCases(UnexpectedCharacterTestCase testCase) {
        Throwable actualException = Assertions.assertThrows(
                UnexpectedCharacterException.class,
                () -> {
                    parser
                            .from(new StringReader(testCase.input()))
                            .toList();
                }
        );

        Assertions.assertEquals(testCase.exception(), actualException);
    }
}
