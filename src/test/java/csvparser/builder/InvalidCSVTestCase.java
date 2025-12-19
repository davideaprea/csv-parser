package csvparser.builder;

public record InvalidCSVTestCase<T extends Throwable>(
        String input,
        T exception
) {
}
