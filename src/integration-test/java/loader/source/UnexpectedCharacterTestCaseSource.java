package loader.source;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class UnexpectedCharacterTestCaseSource {
    private String name;
    private String input;
    private Output exception;

    @Getter
    @Setter
    public static final class Output {
        private String character;
        private String message;
    }
}
