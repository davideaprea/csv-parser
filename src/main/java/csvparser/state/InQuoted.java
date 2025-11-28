package csvparser.state;

public class InQuoted implements ParsingState {
    @Override
    public void next(char character, ParsingContext parsingContext) {
        if (character == '"') {
            parsingContext.setParsingState(new Escaping());
        } else {
            parsingContext.addCharacter(character);
        }
    }
}
