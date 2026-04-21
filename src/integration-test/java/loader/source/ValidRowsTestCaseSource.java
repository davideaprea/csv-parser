package loader.source;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class ValidRowsTestCaseSource {
    private String name;
    private String input;
    private List<List<String>> output;
}
