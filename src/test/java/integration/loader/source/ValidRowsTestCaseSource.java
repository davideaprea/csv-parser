package integration.loader.source;

import java.util.List;

public final class ValidRowsTestCaseSource {
    private String name;
    private String input;
    private List<List<String>> output;

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

    public List<List<String>> getOutput() {
        return output;
    }

    public void setOutput(List<List<String>> output) {
        this.output = output;
    }
}
