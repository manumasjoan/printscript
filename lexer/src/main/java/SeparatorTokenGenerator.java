import java.util.List;
import org.austral.ingsis.printscript.common.Token;

public class SeparatorTokenGenerator extends TokenGenerator {

  private List<String> separators;

  public SeparatorTokenGenerator(List<String> separators) {
    this.separators = separators;
  }

  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isSeparator(lexicalRangeState, input)) return new TokenGeneratorResult(lexicalRangeState);

    Token token = createToken(DefaultTokenTypes.SEPARATOR, lexicalRangeState, 1);
    LexicalRangeState newState = updateState(lexicalRangeState, 1);

    return new TokenGeneratorResult(token, newState);
  }

  private boolean isSeparator(LexicalRangeState lexicalRangeState, String input) {
    String followingChar = String.valueOf(input.charAt(lexicalRangeState.getIndex()));
    return separators.contains(followingChar);
  }
}
