package parser.ast.node;

import parser.ast.visitor.ExpressionVisitor;

public interface Expression extends Node {
  void accept(ExpressionVisitor visitor) throws Exception;

  Expression addVariableWithOperator(Operator operator, Expression expression);
}
