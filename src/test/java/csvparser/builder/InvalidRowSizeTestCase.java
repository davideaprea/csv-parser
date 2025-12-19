package csvparser.builder;

import csvparser.exception.InvalidRowSizeException;

public class InvalidRowSizeTestCase {
    private final String input;
    private final InvalidRowSizeException expected;

    public InvalidRowSizeTestCase(String input, InvalidRowSizeException e) {
        this.input = input;
        this.expected = e;
    }
}
