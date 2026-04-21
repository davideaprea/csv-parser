package loader.source;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public final class ValidHeadedRowsTestCaseSource {
    private String name;
    private String input;
    private Output output;

    @Getter
    @Setter
    public static final class Output {
        private Map<String, Integer> headers;
        private List<List<String>> rows;
    }
}
