import org.austral.ingsis.printscript.common.Token;

public class SpaceTokenGenerator extends TokenGenerator {
  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isSpace(lexicalRangeState, input)) return new TokenGeneratorResult(lexicalRangeState);

    Token token = createToken(DefaultTokenTypes.SPACE, lexicalRangeState, 1);
    LexicalRangeState newState = updateState(lexicalRangeState, 1);

    return new TokenGeneratorResult(token, newState);
  }

  private boolean isSpace(LexicalRangeState lexicalRangeState, String input) {
    return input.charAt(lexicalRangeState.getIndex()) == ' ';
  }
}
