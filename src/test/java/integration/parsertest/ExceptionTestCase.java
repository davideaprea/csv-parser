package integration.parsertest;

import java.io.Reader;

public record ExceptionTestCase(
        Reader input,
        Throwable output
) {
}
