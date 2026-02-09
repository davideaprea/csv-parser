package integration.loader.source;

import java.util.List;
import java.util.Map;

public final class ValidHeadedRowsTestCaseSource {
    private String name;
    private String input;
    private Output output;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public static final class Output {
        private Map<String, Integer> headers;
        private List<List<String>> rows;

        public Map<String, Integer> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, Integer> headers) {
            this.headers = headers;
        }

        public List<List<String>> getRows() {
            return rows;
        }

        public void setRows(List<List<String>> rows) {
            this.rows = rows;
        }
    }
}
