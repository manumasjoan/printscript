package parser;

import lexer.DefaultTokenTypes;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;
import parser.ast.node.Expression;
import parser.ast.node.Operator;
import parser.ast.node.Variable;

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
