package parser;

import lexer.DefaultTokenTypes;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;
import parser.ast.node.Expression;
import parser.ast.node.Operator;
import parser.ast.node.Variable;

public class ExpressionParserV11 extends ExpressionParser {

  ReadInputParser readInputParser = new ReadInputParser(getStream(), this);

  public ExpressionParserV11(@NotNull TokenIterator stream) {
    super(stream);
  }

  @Override
  public Expression createNode() throws Exception {
    Expression output = null;
    if (isReadInput()) {
      output = readInputParser.createNode();
    } else if (!identifierOrLiteralNotFound()) {
      String val = getValue();
      if (noOperatorFound()) {
        return new Variable(val);
      }
      output = new Variable(val);
    }

    while (operatorIsFound()) {
      Operator operator = Operator.getOperator(consume(DefaultTokenTypes.OPERATOR).getContent());
      if (isReadInput()) {
        output = output.addVariableWithOperator(operator, readInputParser.createNode());
      } else if (!identifierOrLiteralNotFound()) {
        String followingValue = getValue();
        output = output.addVariableWithOperator(operator, new Variable(followingValue));
      }
    }
    return output;
  }

  private boolean isReadInput() {
    return peek(DefaultTokenTypes.KEYWORD, "readInput") != null;
  }
}
