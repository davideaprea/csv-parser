package integration.testloader;

public record UnexpectedCharacterTestCaseDTO(
        String name,
        String input,
        Output exception
) {
    public record Output(
            String character,
            String message
    ) {
    }
}
