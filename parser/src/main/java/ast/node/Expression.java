package ast.node;

import ast.visitor.ExpressionVisitor;

public interface Expression extends Node {
  void accept(ExpressionVisitor visitor) throws Exception;

  Expression addVariableWithOperator(Operator operator, Variable variable);
}
