package dto;

public record ParsingExceptionTest(
        String input,
        Class<? extends Throwable> exception
) {
}
