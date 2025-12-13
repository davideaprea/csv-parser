package csvparser.state;

import csvparser.exception.MalformedFileException;

public class InQuoted extends ParsingState {
    public InQuoted(ParsingContext context) {
        super(context);
    }

    @Override
    public void eval(char character) {
        if (character == '"') {
            context.changeState(new Escaping(context));
        } else {
            context.addCharacter(character);
        }
    }

    @Override
    public void end() {
        throw new MalformedFileException("Unclosed quoted field.");
    }
}
