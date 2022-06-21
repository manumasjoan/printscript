package parser.ast.node;

import parser.ast.visitor.NodeVisitor;

public interface Node {
  void accept(NodeVisitor visitor) throws Exception;
}
