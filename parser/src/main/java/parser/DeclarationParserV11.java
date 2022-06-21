package parser;

import lexer.DefaultTokenTypes;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class DeclarationParserV11 extends DeclarationParser {

  public DeclarationParserV11(@NotNull TokenIterator stream) {
    super(stream);
    expressionParser = new ExpressionParserV11(stream);
  }

  public boolean canChange() {
    if (peek(DefaultTokenTypes.KEYWORD, "let") != null) {
      consume(DefaultTokenTypes.KEYWORD, "let");
      return true;
    }
    consume(DefaultTokenTypes.KEYWORD, "const");
    return false;
  }
}
