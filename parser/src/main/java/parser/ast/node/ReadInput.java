package parser.ast.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import parser.ast.visitor.ExpressionVisitor;
import parser.ast.visitor.NodeVisitor;

@Getter
@AllArgsConstructor
@ToString
public class ReadInput implements Expression {

  String input;

  @Override
  public void accept(ExpressionVisitor visitor) throws Exception {
    visitor.visitReadInput(this);
  }

  @Override
  public Expression addVariableWithOperator(Operator operator, Expression variable) {
    return new Operation(this, operator, variable);
  }

  @Override
  public void accept(NodeVisitor visitor) throws Exception {}
}
