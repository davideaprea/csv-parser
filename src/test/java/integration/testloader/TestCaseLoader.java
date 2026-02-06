package integration.testloader;

import csv.exception.UnexpectedCharacterException;
import csv.structure.HeadedRow;
import csv.structure.Row;
import integration.parsertest.ExceptionTestCase;
import integration.parsertest.ValidHeadedRowTestCase;
import integration.parsertest.ValidRowTestCase;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.stream.Stream;

public class TestCaseLoader {
    private final static String BASE_PATH = "resources/testcases/%s";

    public static Stream<ValidRowTestCase> validRows() {
        return fromPath(
                ValidRowsTestCaseDTO.class,
                BASE_PATH.formatted("valid/valid_rows_test_cases.yaml")
        ).map(testCase -> new ValidRowTestCase(
                new StringReader(testCase.input()),
                testCase.output()
                        .stream()
                        .map(Row::new)
                        .toList()
        ));
    }

    public static Stream<ValidHeadedRowTestCase> validHeadedRows() {
        return fromPath(
                ValidHeadedRowsTestCaseDTO.class,
                BASE_PATH.formatted("valid/valid_headed_rows_test_cases.yaml")
        ).map(testCase -> new ValidHeadedRowTestCase(
                new StringReader(testCase.input()),
                testCase.output().rows()
                        .stream()
                        .map(row -> new HeadedRow(
                                new Row(row),
                                testCase.output().headers()
                        ))
                        .toList()
        ));
    }

    public static Stream<ExceptionTestCase> unexpectedCharacterTestCase() {
        return fromPath(
                UnexpectedCharacterTestCaseDTO.class,
                BASE_PATH.formatted("/invalid/unexpected_character_test_cases.yaml")
        ).map(testCase -> new ExceptionTestCase(
                new StringReader(testCase.input()),
                new UnexpectedCharacterException(
                        testCase.exception().character().charAt(0),
                        testCase.exception().message()
                )
        ));
    }

    private static <T> Stream<T> fromPath(Class<T> type, String path) {
        Yaml yaml = new Yaml(new Constructor(type, new LoaderOptions()));

        try (InputStream is = new FileInputStream(path)) {
            return yaml.load(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
