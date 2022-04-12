package ast.node;

import ast.visitor.ExpressionVisitor;
import ast.visitor.NodeVisitor;
import lombok.Getter;

@Getter
public class Expression implements Function {

  private final Function left;
  private final Operator operator;
  private final Function right;

  public Expression(Function left, Operator operator, Function right) {
    this.left = left;
    this.operator = operator;
    this.right = right;
  }

  @Override
  public void accept(NodeVisitor visitor) {}

  @Override
  public void accept(ExpressionVisitor visitor) throws Exception {
    visitor.visitExpression(this);
  }

  public Function addVariable(Operator operator, Variable variable) {
    if (operator == Operator.SUB || operator == Operator.ADD) {
      return new Expression(this, operator, variable);
    } else {
      Function right = new Expression(this.right, operator, variable);
      return new Expression(left, this.operator, right);
    }
  }

  @Override
  public String toString() {
    String sb = "(" + left.toString() + ")" + operator + "(" + right + ")";
    return sb;
  }
}
