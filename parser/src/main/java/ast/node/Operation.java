package ast.node;

import ast.visitor.ExpressionVisitor;
import ast.visitor.NodeVisitor;
import lombok.Getter;

@Getter
public class Operation implements Expression {

  private final Expression left;
  private final Operator operator;
  private final Expression right;

  public Operation(Expression left, Operator operator, Expression right) {
    this.left = left;
    this.operator = operator;
    this.right = right;
  }

  @Override
  public void accept(NodeVisitor visitor) {}

  @Override
  public void accept(ExpressionVisitor visitor) throws Exception {
    visitor.visitOperation(this);
  }

  public Expression addVariableWithOperator(Operator operator, Variable variable) {
    if (operator == Operator.SUB || operator == Operator.ADD) {
      return new Operation(this, operator, variable);
    } else {
      Expression right = new Operation(this.right, operator, variable);
      return new Operation(left, this.operator, right);
    }
  }

  @Override
  public String toString() {
    String sb = "(" + left.toString() + ")" + operator + "(" + right + ")";
    return sb;
  }
}
