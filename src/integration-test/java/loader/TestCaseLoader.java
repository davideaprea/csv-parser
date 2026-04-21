package loader;

import io.github.davideaprea.csvparser.exception.UnexpectedCharacterException;
import io.github.davideaprea.csvparser.model.HeadedRow;
import io.github.davideaprea.csvparser.model.Row;
import loader.source.UnexpectedCharacterTestCaseSource;
import loader.source.ValidHeadedRowsTestCaseSource;
import loader.source.ValidRowsTestCaseSource;
import parser.testcase.ExceptionTestCase;
import parser.testcase.ValidHeadedRowTestCase;
import parser.testcase.ValidRowTestCase;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.provider.Arguments;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.io.StringReader;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TestCaseLoader {
    public static Stream<Arguments> validRows() {
        return fromPath(
                ValidRowsTestCaseSource.class,
                "valid/valid_rows_test_cases.yaml"
        ).map(source -> {
                    var testCase = new ValidRowTestCase(
                            new StringReader(source.getInput()),
                            source.getOutput().stream().map(Row::new).toList()
                    );

                    return Arguments.of(Named.of(source.getName(), testCase));
                }
        );
    }

    public static Stream<Arguments> validHeadedRows() {
        return fromPath(
                ValidHeadedRowsTestCaseSource.class,
                "valid/valid_headed_rows_test_cases.yaml"
        ).map(source -> {
                    var testCase = new ValidHeadedRowTestCase(
                            new StringReader(source.getInput()),
                            source.getOutput().getRows()
                                    .stream()
                                    .map(row -> new HeadedRow(
                                            new Row(row),
                                            source.getOutput().getHeaders()
                                    ))
                                    .toList()
                    );

                    return Arguments.of(Named.of(source.getName(), testCase));
                }
        );
    }

    public static Stream<Arguments> unexpectedCharacterTestCase() {
        return fromPath(
                UnexpectedCharacterTestCaseSource.class,
                "invalid/unexpected_character_test_cases.yaml"
        ).map(source -> {
                    var testCase = new ExceptionTestCase(
                            new StringReader(source.getInput()),
                            new UnexpectedCharacterException(
                                    source.getException().getCharacter().charAt(0),
                                    source.getException().getMessage()
                            ));

                    return Arguments.of(Named.of(source.getName(), testCase));
                }
        );
    }

    private static <T> Stream<T> fromPath(Class<T> testCaseClass, String path) {
        Yaml yaml = new Yaml(new Constructor(testCaseClass, new LoaderOptions()));

        try (InputStream is = TestCaseLoader
                .class.getClassLoader()
                .getResourceAsStream("testcases/%s".formatted(path))) {
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
