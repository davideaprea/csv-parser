package integration.loader.source;

public final class UnexpectedCharacterTestCaseSource {
    private String name;
    private String input;
    private Output exception;

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

    public Output getException() {
        return exception;
    }

    public void setException(Output exception) {
        this.exception = exception;
    }

    public static final class Output {
        private String character;
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }
    }
}
