import ast.node.Expression;
import ast.node.Operator;
import ast.node.Variable;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class ExpressionParserV10 extends ExpressionParser {

  public ExpressionParserV10(@NotNull TokenIterator stream) {
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
}
