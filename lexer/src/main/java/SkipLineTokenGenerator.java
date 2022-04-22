import org.austral.ingsis.printscript.common.Token;

public class SkipLineTokenGenerator extends TokenGenerator {

  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isLineBreak(lexicalRangeState, input)) return new TokenGeneratorResult(lexicalRangeState);

    Token token = createToken(DefaultTokenTypes.SKIP_LINE, lexicalRangeState, 1);
    LexicalRangeState newState = lexicalRangeState.lineBreak();

    return new TokenGeneratorResult(token, newState);
  }

  private boolean isLineBreak(LexicalRangeState lexicalRangeState, String input) {
    return input.charAt(lexicalRangeState.getIndex()) == '\n';
  }
}
