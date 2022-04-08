import org.austral.ingsis.printscript.common.LexicalRange;
import org.austral.ingsis.printscript.common.Token;
import org.jetbrains.annotations.NotNull;

public class SeparatorTokenGenerator implements TokenGenerator {

  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isSeparator(lexicalRangeState, input)) return new TokenGeneratorResult(lexicalRangeState);

    Token token = createToken(lexicalRangeState);
    LexicalRangeState newState = updateState(lexicalRangeState);

    return new TokenGeneratorResult(token, newState);
  }

  private LexicalRangeState updateState(LexicalRangeState lexicalRangeState) {
    return lexicalRangeState.updateState(
        lexicalRangeState.getIndex() + 1, lexicalRangeState.getLine() + 1, 0);
  }

  @NotNull
  private Token createToken(LexicalRangeState lexicalRangeState) {
    int index = lexicalRangeState.getIndex();
    Token token =
        new Token(
            DefaultTokenTypes.SEPARATOR,
            index,
            index,
            new LexicalRange(
                lexicalRangeState.getColumn(),
                lexicalRangeState.getLine(),
                lexicalRangeState.getColumn(),
                lexicalRangeState.getLine()));
    return token;
  }

  private boolean isSeparator(LexicalRangeState lexicalRangeState, String input) {
    return input.charAt(lexicalRangeState.getIndex()) == ';';
  }
}
