package csvparser;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public List<String> parse(final String line) {
        final List<String> values = new ArrayList<>();
        final StringBuilder stringBuilder = new StringBuilder();
        final ParsingStateEvaluator stateEvaluator = new ParsingStateEvaluator();

        for(int i = 0; i < line.length(); i++) {
            final char currChar = line.charAt(i);

            stateEvaluator.update(currChar);

            switch (stateEvaluator.getParsingState()) {
                c
            }

            if(currChar == '"') {
                if(parsingState == ParsingState.ESCAPING) {
                    stringBuilder.append(currChar);
                }
            }
            else if(currChar == ',') {
                values.add(stringBuilder.toString());

                stringBuilder.delete(0, stringBuilder.length());
            }
            else {
                stringBuilder.append(currChar);
            }
        }

        if(!stringBuilder.isEmpty()) {
            values.add(stringBuilder.toString());
        }

        return values;
    }
}