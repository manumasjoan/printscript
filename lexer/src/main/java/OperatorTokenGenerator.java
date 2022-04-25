import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.austral.ingsis.printscript.common.Token;

@Getter
public class OperatorTokenGenerator extends TokenGenerator {

  private List<String> operators;

  public OperatorTokenGenerator(List<String> operators) {
    this.operators = operators;
  }

  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isOperator(lexicalRangeState, input)) return new TokenGeneratorResult(lexicalRangeState);

    Token token = createToken(DefaultTokenTypes.OPERATOR, lexicalRangeState, 1);
    LexicalRangeState newState = updateState(lexicalRangeState, 1);

    return new TokenGeneratorResult(token, newState);
  }

  private boolean isOperator(LexicalRangeState lexicalRangeState, String input) {
    String nextChar = String.valueOf(input.charAt(lexicalRangeState.getIndex()));
    return operators.contains(nextChar);
  }
}
