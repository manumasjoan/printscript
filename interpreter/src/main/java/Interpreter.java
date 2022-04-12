import ast.node.NodeException;
import ast.node.NodeGroupResult;

public interface Interpreter {
  PrintResult interpret(NodeGroupResult nodeGroupResult) throws NodeException;
}
