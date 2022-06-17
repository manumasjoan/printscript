import ast.node.Expression;
import ast.node.Operator;
import ast.node.Variable;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class ExpressionParser extends TokenConsumer implements Parser<Expression> {

  public ExpressionParser(@NotNull TokenIterator stream) {
    super(stream);
  }

  @Override
  public Expression createNode() throws Exception {
    String val = getValue();
    if (noOperatorFound()) return new Variable(val);
    Expression output = new Variable(val);
    while (operatorIsFound()) {
      Operator operator = Operator.getOperator(consume(DefaultTokenTypes.OPERATOR).getContent());
      String followingValue = getValue();
      output = output.addVariableWithOperator(operator, new Variable(followingValue));
    }
    return output;
  }

  private String getValue() throws Exception {
    if (identifierOrLiteralNotFound()) throw new Exception("No identifier or literal found");
    return consumeAny(DefaultTokenTypes.IDENTIFIER, DefaultTokenTypes.LITERAL).getContent();
  }

  private boolean operatorIsFound() {
    return peek(DefaultTokenTypes.OPERATOR) != null;
  }

  private boolean noOperatorFound() {
    return peek(DefaultTokenTypes.OPERATOR) == null;
  }

  private boolean identifierOrLiteralNotFound() {
    return peekAny(DefaultTokenTypes.IDENTIFIER, DefaultTokenTypes.LITERAL) == null;
  }
}
