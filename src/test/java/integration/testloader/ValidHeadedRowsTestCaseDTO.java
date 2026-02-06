package integration.testloader;

import java.util.List;
import java.util.Map;

public record ValidHeadedRowsTestCaseDTO(
        String name,
        String input,
        Output output
) {
    public record Output(
            Map<String, Integer> headers,
            List<List<String>> rows
    ) {
    }
}
