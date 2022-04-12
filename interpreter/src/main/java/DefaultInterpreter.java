import ast.node.NodeException;
import ast.node.NodeGroupResult;

public class DefaultInterpreter implements Interpreter {

  private NodeGroupVisitor visitor;

  public DefaultInterpreter(NodeGroupVisitor visitor) {
    this.visitor = visitor;
  }

  @Override
  public PrintResult interpret(NodeGroupResult nodeGroupResult) throws NodeException {
    nodeGroupResult.accept(visitor);
    return visitor.getPrintResult();
  }
}
