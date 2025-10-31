package dto;

import java.util.List;

public record ValidRowParsingTest(
        String input,
        List<String> expected
) {
}
