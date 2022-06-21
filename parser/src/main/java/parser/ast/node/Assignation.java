package parser.ast.node;

import lombok.Getter;
import parser.ast.visitor.NodeVisitor;

@Getter
public class Assignation implements Node {

  private final String name;
  private final Expression val;

  public Assignation(String name, Expression val) {
    this.name = name;
    this.val = val;
  }

  @Override
  public void accept(NodeVisitor visitor) throws Exception {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    return "Assign(" + "name='" + name + '\'' + ", val=" + val + ')';
  }
}
