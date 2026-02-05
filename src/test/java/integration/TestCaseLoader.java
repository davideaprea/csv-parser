package integration;

import integration.testcase.UnexpectedCharacterTestCase;
import integration.testcase.ValidHeadedRowsTestCase;
import integration.testcase.ValidRowsTestCase;
import org.junit.jupiter.params.provider.Arguments;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TestCaseLoader {
    private TestCaseLoader() {
    }

    public static Stream<Arguments> loadUnexpectedCharacterTestCases() throws Exception {
        return load(UnexpectedCharacterTestCase.class, "/unexpected_character_test_cases.yaml");
    }

    public static Stream<Arguments> loadValidRowsTestCases() throws Exception {
        return load(ValidRowsTestCase.class, "/valid_test_cases.yaml");
    }

    public static Stream<Arguments> loadValidHeadedRowsTestCases() throws Exception {
        return load(ValidHeadedRowsTestCase.class, "/valid_headed_rows_test_cases.yaml");
    }

    private static <T> Stream<Arguments> load(Class<T> testCaseType, String testCasesPath) throws Exception {
        Yaml yaml = new Yaml(new Constructor(
                testCaseType,
                new LoaderOptions()
        ));

        try (InputStream is = ParserTest.class.getResourceAsStream(testCasesPath)) {
            Objects.requireNonNull(is, "Resource not found: " + testCasesPath);

            Iterable<Object> all = yaml.loadAll(is);

            return StreamSupport.stream(all.spliterator(), false)
                    .map(testCaseType::cast)
                    .map(Arguments::of);
        }
    }
}
