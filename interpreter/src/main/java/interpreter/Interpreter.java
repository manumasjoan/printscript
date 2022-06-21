package interpreter;

import parser.ast.node.NodeGroupResult;

public interface Interpreter {
  PrintlnResult interpret(NodeGroupResult nodeGroupResult) throws Exception;
}
