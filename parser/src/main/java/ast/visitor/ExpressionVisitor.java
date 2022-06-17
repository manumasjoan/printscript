package ast.visitor;

import ast.node.Operation;
import ast.node.Variable;

public interface ExpressionVisitor {
  void visitOperation(Operation operation) throws Exception;

  void visitVariable(Variable variable) throws Exception;
}