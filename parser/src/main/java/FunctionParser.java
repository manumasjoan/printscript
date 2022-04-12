import ast.node.Function;
import ast.node.Operator;
import ast.node.Variable;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class FunctionParser extends TokenConsumer implements Parser<Function> {
  public FunctionParser(@NotNull TokenIterator stream) {
    super(stream);
  }

  @Override
  public Function createNode() throws Exception {
    if (identifierOrLiteralNotFound()) throw new Exception("No identifier or literal found");
    String val = consumeAny(DefaultTokenTypes.IDENTIFIER, DefaultTokenTypes.LITERAL).getContent();
    if (noOperatorFound()) return new Variable(val);
    Function output = new Variable(val);
    while (operatorIsFound()) {
      Operator operator = Operator.getOperator(consume(DefaultTokenTypes.OPERATOR).getContent());
      if (identifierOrLiteralNotFound()) throw new Exception("No identifier or literal found");
      String next =
          consumeAny(DefaultTokenTypes.IDENTIFIER, DefaultTokenTypes.LITERAL).getContent();
      output = output.addVariable(operator, new Variable(next));
    }
    return output;
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
