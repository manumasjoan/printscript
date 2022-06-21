package parser.ast.node;

import lombok.Getter;
import parser.ast.visitor.ExpressionVisitor;
import parser.ast.visitor.NodeVisitor;

@Getter
public class Variable implements Expression {

  private final String varName;

  public Variable(String varName) {
    this.varName = varName;
  }

  @Override
  public void accept(ExpressionVisitor visitor) throws Exception {
    visitor.visitVariable(this);
  }

  @Override
  public void accept(NodeVisitor visitor) {}

  @Override
  public Expression addVariableWithOperator(Operator operator, Expression variable) {
    return new Operation(this, operator, variable);
  }

  public String toString() {
    return varName;
  }
}
