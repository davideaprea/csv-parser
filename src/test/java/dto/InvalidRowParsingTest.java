package dto;

public record InvalidRowParsingTest(
        String row,
        Class<? extends Throwable> exception
) {
}
