package ast.node;

import ast.visitor.ExpressionVisitor;

public interface MultiExpression extends Node {
  void accept(ExpressionVisitor visitor) throws Exception;

  MultiExpression addVariableWithOperator(Operator operator, Variable variable);
}
