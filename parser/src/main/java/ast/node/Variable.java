package ast.node;

import ast.visitor.ExpressionVisitor;
import ast.visitor.NodeVisitor;
import lombok.Getter;

@Getter
public class Variable implements MultiExpression {

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
  public MultiExpression addVariableWithOperator(Operator operator, Variable variable) {
    return new Expression(this, operator, variable);
  }

  public String toString() {
    return varName;
  }
}
