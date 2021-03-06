package lexer;

import java.util.List;
import lombok.Getter;
import org.austral.ingsis.printscript.common.Token;

@Getter
public class KeywordTokenGenerator extends TokenGenerator {

  private List<String> keywords;

  public KeywordTokenGenerator(List<String> keywords) {
    this.keywords = keywords;
  }

  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isKeyword(lexicalRangeState, input)) return new TokenGeneratorResult(lexicalRangeState);

    String keyword = getNextWord(lexicalRangeState, input);
    Token token = createToken(DefaultTokenTypes.KEYWORD, lexicalRangeState, keyword.length());
    LexicalRangeState newState = updateState(lexicalRangeState, keyword.length());

    return new TokenGeneratorResult(token, newState);
  }

  private boolean isKeyword(LexicalRangeState lexicalRangeState, String input) {
    return keywords.contains(getNextWord(lexicalRangeState, input));
  }
}
