package csvparser;

import java.util.List;

public record RowParsingTest(
        String rowToTest,
        List<String> expectedColumns
) {
}
