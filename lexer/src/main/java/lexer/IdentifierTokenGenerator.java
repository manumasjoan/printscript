package lexer;

import org.austral.ingsis.printscript.common.Token;

public class IdentifierTokenGenerator extends TokenGenerator {

  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isIdentifier(lexicalRangeState, input)) return new TokenGeneratorResult(lexicalRangeState);

    String identifier = getNextWord(lexicalRangeState, input);
    Token token = createToken(DefaultTokenTypes.IDENTIFIER, lexicalRangeState, identifier.length());
    LexicalRangeState newState = updateState(lexicalRangeState, identifier.length());

    return new TokenGeneratorResult(token, newState);
  }

  private boolean isIdentifier(LexicalRangeState lexicalRangeState, String input) {
    return firstCharIsLetter(lexicalRangeState, input);
  }
}
