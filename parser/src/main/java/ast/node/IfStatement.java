package ast.node;

import ast.visitor.NodeVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

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
