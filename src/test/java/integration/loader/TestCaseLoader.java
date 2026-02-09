package integration.loader;

import csv.exception.UnexpectedCharacterException;
import csv.structure.HeadedRow;
import csv.structure.Row;
import integration.loader.source.UnexpectedCharacterTestCaseSource;
import integration.loader.source.ValidHeadedRowsTestCaseSource;
import integration.loader.source.ValidRowsTestCaseSource;
import integration.parser.testcase.ExceptionTestCase;
import integration.parser.testcase.ValidHeadedRowTestCase;
import integration.parser.testcase.ValidRowTestCase;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.io.StringReader;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TestCaseLoader {
    private final static String BASE_PATH = "testcases/%s";

    public static Stream<ValidRowTestCase> validRows() {
        return fromPath(
                ValidRowsTestCaseSource.class,
                BASE_PATH.formatted("valid/valid_rows_test_cases.yaml")
        ).map(testCase -> new ValidRowTestCase(
                new StringReader(testCase.getInput()),
                testCase.getOutput().stream().map(Row::new).toList()
        ));
    }

    public static Stream<ValidHeadedRowTestCase> validHeadedRows() {
        return fromPath(
                ValidHeadedRowsTestCaseSource.class,
                BASE_PATH.formatted("valid/valid_headed_rows_test_cases.yaml")
        ).map(testCase -> new ValidHeadedRowTestCase(
                new StringReader(testCase.getInput()),
                testCase.getOutput().getRows()
                        .stream()
                        .map(row -> new HeadedRow(
                                new Row(row),
                                testCase.getOutput().getHeaders()
                        ))
                        .toList()
        ));
    }

    public static Stream<ExceptionTestCase> unexpectedCharacterTestCase() {
        return fromPath(
                UnexpectedCharacterTestCaseSource.class,
                BASE_PATH.formatted("invalid/unexpected_character_test_cases.yaml")
        ).map(testCase -> new ExceptionTestCase(
                new StringReader(testCase.getInput()),
                new UnexpectedCharacterException(
                        testCase.getException().getCharacter().charAt(0),
                        testCase.getException().getMessage()
                )
        ));
    }

    private static <T> Stream<T> fromPath(Class<T> testCaseClass, String path) {
        Yaml yaml = new Yaml(new Constructor(testCaseClass, new LoaderOptions()));

        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(path)) {
            Objects.requireNonNull(is, "Resource not found in test resources: " + path);

            return StreamSupport.stream(yaml.loadAll(is).spliterator(), false)
                    .map(testCaseClass::cast)
                    .toList()
                    .stream();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
