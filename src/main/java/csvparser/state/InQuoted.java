package csvparser.state;

import csvparser.builder.CSVRowBuilder;

public class InQuoted extends ParsingState {
    public InQuoted(CSVRowBuilder rowBuilder) {
        super(rowBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if(character == '"') {
            return new Escaping(rowBuilder);
        }

        rowBuilder.addCharacter(character);

        return this;
    }
}
