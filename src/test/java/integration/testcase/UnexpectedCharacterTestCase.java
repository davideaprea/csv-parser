package integration.testcase;

import csv.exception.UnexpectedCharacterException;

public record UnexpectedCharacterTestCase(
        String name,
        String input,
        UnexpectedCharacterException exception
) {
}
