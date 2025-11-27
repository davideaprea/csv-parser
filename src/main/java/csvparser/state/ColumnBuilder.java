package csvparser.state;

public class ColumnBuilder {
    private final StringBuilder stringBuilder = new StringBuilder();

    public void addCharacter(char character) {
        stringBuilder.append(character);
    }

    public void resetColumn() {
        stringBuilder.setLength(0);
    }

    public String build() {
        return stringBuilder.toString();
    }
}
