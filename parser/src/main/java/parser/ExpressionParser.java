package parser;

import lexer.DefaultTokenTypes;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;
import parser.ast.node.Expression;

public abstract class ExpressionParser extends TokenConsumer implements Parser<Expression> {

  public ExpressionParser(@NotNull TokenIterator stream) {
    super(stream);
  }

  @Override
  public abstract Expression createNode() throws Exception;

  public String getValue() throws Exception {
    if (identifierOrLiteralNotFound()) throw new Exception("No identifier or literal found");
    return consumeAny(DefaultTokenTypes.IDENTIFIER, DefaultTokenTypes.LITERAL).getContent();
  }

  public boolean operatorIsFound() {
    return peek(DefaultTokenTypes.OPERATOR) != null;
  }

  public boolean noOperatorFound() {
    return peek(DefaultTokenTypes.OPERATOR) == null;
  }

  public boolean identifierOrLiteralNotFound() {
    return peekAny(DefaultTokenTypes.IDENTIFIER, DefaultTokenTypes.LITERAL) == null;
  }
}
