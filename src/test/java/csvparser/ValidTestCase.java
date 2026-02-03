package csvparser;

import java.util.List;

public record ValidTestCase(
        String input,
        List<List<String>> output
) {
}
