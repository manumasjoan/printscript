package ast.node;

import ast.visitor.ExpressionVisitor;

public interface Function extends Node {
  void accept(ExpressionVisitor visitor) throws Exception;

  Function addVariable(Operator operator, Variable variable);
}
