package parser;

import lexer.DefaultTokenTypes;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;
import parser.ast.node.ReadInput;

public class ReadInputParser extends TokenConsumer implements Parser<ReadInput> {

  ExpressionParser expressionParser;

  public ReadInputParser(@NotNull TokenIterator stream, ExpressionParser expressionParser) {
    super(stream);
    this.expressionParser = expressionParser;
  }

  @Override
  public ReadInput createNode() throws Exception {
    consume(DefaultTokenTypes.KEYWORD, "readInput");
    if (opensParenthesis()) {
      String text = consume(DefaultTokenTypes.LITERAL).getContent();
      if (closesParenthesis()) {
        return new ReadInput(text);
      }
    }
    throw new Exception("Error in parser.ReadInputParser");
  }

  public boolean opensParenthesis() throws Exception {
    if (peek(DefaultTokenTypes.SEPARATOR, "(") != null) {
      consume(DefaultTokenTypes.SEPARATOR, "(");
      return true;
    }
    throw new Exception("Expected (");
  }

  public boolean closesParenthesis() throws Exception {
    if (peek(DefaultTokenTypes.SEPARATOR, ")") != null) {
      consume(DefaultTokenTypes.SEPARATOR, ")");
      return true;
    }
    throw new Exception("Expected )");
  }
}
