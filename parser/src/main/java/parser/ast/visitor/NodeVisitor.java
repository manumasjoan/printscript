package parser.ast.visitor;

import parser.ast.node.*;

public interface NodeVisitor {
  void visit(NodeGroupResult nodeGroupResult) throws Exception;

  void visit(Declaration declaration) throws Exception;

  void visit(Assignation assignation) throws Exception;

  void visit(Println printLn) throws Exception;

  void visit(IfStatement expression) throws Exception;
}
