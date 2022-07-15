package lexer;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.austral.ingsis.printscript.common.Token;

@Getter
public class DefaultLexer implements Lexer {

  private final List<TokenGenerator> tokenGenerators;

  public DefaultLexer(List<TokenGenerator> tokenGenerators) {
    this.tokenGenerators = tokenGenerators;
  }

  @Override
  public List<Token> getTokens(String input) {
    List<Token> tokens = new ArrayList<>();

    LexicalRangeState lexicalRangeState = new LexicalRangeState();

    while (lexicalRangeState.getIndex() <= input.length() - 1) {
      for (int i = 0; i < tokenGenerators.size(); i++) {
        TokenGeneratorResult tokenGeneratorResult =
            tokenGenerators.get(i).read(lexicalRangeState, input);
        if (tokenGeneratorResult.tokenWasGenerated()) {
          if (isNotSkipLineOrSpace(tokenGeneratorResult)) {
            tokens.add(tokenGeneratorResult.getToken());
          }
          lexicalRangeState = tokenGeneratorResult.getLexicalRangeState();
          break;
        } else if (!tokenGeneratorResult.tokenWasGenerated() && i == tokenGenerators.size() - 1) {
          throw new
                  IllegalArgumentException(
              "Invalid character: " + input.charAt(lexicalRangeState.getIndex()));
        }
      }
    }

    return tokens;
  }

  private boolean isNotSkipLineOrSpace(TokenGeneratorResult tokenGeneratorResult) {
    return tokenGeneratorResult.getToken().getType() != DefaultTokenTypes.SKIP_LINE
        && tokenGeneratorResult.getToken().getType() != DefaultTokenTypes.SPACE;
  }
}
