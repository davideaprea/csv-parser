package csvparser;

public class ParsingStateEvaluator {
    private ParsingState parsingState = ParsingState.COLUMN_START;
    
    public void update(char character) {
        switch (character) {
            case '"': {
                switch (parsingState) {
                    case ESCAPING, COLUMN_START -> parsingState = ParsingState.IN_QUOTES;
                    case IN_QUOTES -> parsingState = ParsingState.ESCAPING;
                    case OUT_QUOTES -> throw new RuntimeException(""); //TODO: creare eccezione custom
                }
                
                break;
            }
            
            case ',': {
                switch(parsingState) {
                    case ESCAPING, OUT_QUOTES -> parsingState = ParsingState.COLUMN_START;
                }
                
                break;
            }
            
            default: {
                if (parsingState == ParsingState.COLUMN_START) {
                    parsingState = ParsingState.OUT_QUOTES;
                }

                break;
            }
        }
    }

    public ParsingState getParsingState() {
        return parsingState;
    }
}