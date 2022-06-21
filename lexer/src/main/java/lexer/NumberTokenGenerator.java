package lexer;

import org.austral.ingsis.printscript.common.Token;

public class NumberTokenGenerator extends TokenGenerator {
  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isNumber(lexicalRangeState, input)) return new TokenGeneratorResult(lexicalRangeState);

    String number = getNumber(lexicalRangeState, input);
    Token token = createToken(DefaultTokenTypes.LITERAL, lexicalRangeState, number.length());
    LexicalRangeState newState = updateState(lexicalRangeState, number.length());

    return new TokenGeneratorResult(token, newState);
  }

  private boolean isNumber(LexicalRangeState lexicalRangeState, String input) {
    return firstCharIsDigit(lexicalRangeState, input);
  }
}
