package parser.ast.node;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import parser.ast.visitor.NodeVisitor;

public class NodeGroupResult implements Node {

  @Getter private final List<Node> nodes = new ArrayList<>();

  public void addNode(Node node) {
    nodes.add(node);
  }

  @Override
  public void accept(NodeVisitor visitor) throws Exception {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Node node : nodes) {
      sb.append(node.toString());
    }
    return sb.toString();
  }
}
