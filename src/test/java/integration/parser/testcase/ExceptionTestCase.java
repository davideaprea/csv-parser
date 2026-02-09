package integration.parser.testcase;

import java.io.Reader;

public record ExceptionTestCase(
        Reader input,
        Throwable output
) {
}
