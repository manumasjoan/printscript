package parser.ast.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import parser.ast.visitor.NodeVisitor;

@AllArgsConstructor
@Getter
@ToString
public class IfStatement implements Node {

  private Expression condition;
  private NodeGroupResult ifTrueBlock;
  private NodeGroupResult elseBlock;

  @Override
  public void accept(NodeVisitor visitor) throws Exception {
    visitor.visit(this);
  }
}
