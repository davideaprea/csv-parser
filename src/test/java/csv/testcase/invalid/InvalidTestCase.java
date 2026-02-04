package csv.testcase.invalid;

public record InvalidTestCase(
        String input,
        Throwable exceptionInstance
) {
}
