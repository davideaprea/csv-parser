package csvparser.builder;

import java.util.List;

public record ValidCSVTestCase(
        String input,
        List<List<String>> output
) {
}
