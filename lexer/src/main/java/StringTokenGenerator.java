import org.austral.ingsis.printscript.common.Token;
import org.jetbrains.annotations.NotNull;

public class StringTokenGenerator extends TokenGenerator {

  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {

    if (!startsWithQuoteMarks(lexicalRangeState, input))
      return new TokenGeneratorResult(lexicalRangeState);
    if (hasClosingMark(lexicalRangeState, input)) {
      return generateToken(lexicalRangeState, input);
    } else {
      throw new IllegalArgumentException("Missing closing quotemark");
    }
  }

  @NotNull
  private TokenGeneratorResult generateToken(LexicalRangeState lexicalRangeState, String input) {
    StringBuilder string = new StringBuilder();
    int index = lexicalRangeState.getIndex();
    int closingQuoteMark = getClosingQuoteMark(lexicalRangeState, input);
    string.append(input, index, index + closingQuoteMark + 2);

    Token token = createToken(DefaultTokenTypes.LITERAL, lexicalRangeState, string.length());
    LexicalRangeState newState = updateState(lexicalRangeState, closingQuoteMark + 2);

    return new TokenGeneratorResult(token, newState);
  }

  private boolean startsWithQuoteMarks(LexicalRangeState lexicalRangeState, String input) {
    char currChar = input.charAt(lexicalRangeState.getIndex());
    return currChar == '\'' || currChar == '"';
  }

  private boolean hasClosingMark(LexicalRangeState lexicalRangeState, String input) {
    int closingQuoteMark = getClosingQuoteMark(lexicalRangeState, input);
    return closingQuoteMark != -1;
  }

  private int getClosingQuoteMark(LexicalRangeState lexicalRangeState, String input) {
    char quotemark = input.charAt(lexicalRangeState.getIndex());
    return input.substring(lexicalRangeState.getIndex() + 1).indexOf(quotemark);
  }
}
