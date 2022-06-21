package parser.ast.node;

import lombok.Getter;
import parser.ast.visitor.NodeVisitor;

@Getter
public class Declaration implements Node {
  private final String varName;
  private final String type;
  private Expression val;
  private final boolean canChange;

  public Declaration(String varName, String type, Expression val, boolean canChange) {
    this.varName = varName;
    this.type = type;
    this.val = val;
    this.canChange = canChange;
  }

  public Declaration(String varName, String type, boolean canChange) {
    this.varName = varName;
    this.type = type;
    this.canChange = canChange;
  }

  @Override
  public void accept(NodeVisitor visitor) throws Exception {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    if (val != null) return "Dec(" + "varName=" + varName + ", type=" + type + ", val=" + val + ')';
    else return "Dec(" + "varName=" + varName + ", type=" + type + ')';
  }
}
