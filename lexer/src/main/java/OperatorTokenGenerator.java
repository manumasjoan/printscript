import java.util.ArrayList;
import java.util.List;
import org.austral.ingsis.printscript.common.LexicalRange;
import org.austral.ingsis.printscript.common.Token;

public class OperatorTokenGenerator implements TokenGenerator {

  List<String> operators;

  public OperatorTokenGenerator() {
    operators = new ArrayList<>();
    operators.add("+");
    operators.add("-");
    operators.add("*");
    operators.add("/");
    operators.add("=");
  }

  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isOperator(lexicalRangeState, input)) return new TokenGeneratorResult(lexicalRangeState);

    int index = lexicalRangeState.getIndex();
    Token token =
        new Token(
            DefaultTokenTypes.OPERATOR,
            index,
            index,
            new LexicalRange(
                lexicalRangeState.getColumn(),
                lexicalRangeState.getLine(),
                lexicalRangeState.getColumn(),
                lexicalRangeState.getLine()));
    LexicalRangeState newState =
        lexicalRangeState.updateState(
            index + 1, lexicalRangeState.getLine(), lexicalRangeState.getColumn() + 1);
    // return token;
    return new TokenGeneratorResult(token, newState);
  }

  private String getOperator(LexicalRangeState lexicalRangeState, String input) {
    String nextChar = String.valueOf(input.charAt(lexicalRangeState.getIndex()));
    if (operators.contains(nextChar)) {
      return nextChar;
    }
    return null;
  }

  private boolean isOperator(LexicalRangeState lexicalRangeState, String input) {
    return getOperator(lexicalRangeState, input) != null;
  }
}
