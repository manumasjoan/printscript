package parser.ast.node;

import lombok.Getter;
import parser.ast.visitor.NodeVisitor;

@Getter
public class Println implements Node {
  private final Expression content;

  public Println(Expression content) {
    this.content = content;
  }

  @Override
  public void accept(NodeVisitor visitor) throws Exception {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    return "print(" + "content=" + content + ')';
  }
}
