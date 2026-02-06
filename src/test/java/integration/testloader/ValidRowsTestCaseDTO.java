package integration.testloader;

import java.util.List;

public record ValidRowsTestCaseDTO(
        String name,
        String input,
        List<List<String>> output
) {
}
