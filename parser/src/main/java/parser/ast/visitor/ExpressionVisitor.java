package parser.ast.visitor;

import parser.ast.node.Operation;
import parser.ast.node.ReadInput;
import parser.ast.node.Variable;

public interface ExpressionVisitor {
  void visitOperation(Operation operation) throws Exception;

  void visitVariable(Variable variable) throws Exception;

  void visitReadInput(ReadInput readInput) throws Exception;
}
