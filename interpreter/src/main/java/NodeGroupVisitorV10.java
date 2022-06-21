import ast.node.Declaration;
import ast.node.Expression;

public class NodeGroupVisitorV10 extends NodeGroupVisitor {

  public NodeGroupVisitorV10(EvaluatorV10 evaluator) {
    super(evaluator);
  }

  @Override
  public void visit(Declaration declaration) throws Exception {
    String type = declaration.getType();
    String name = declaration.getVarName();
    Expression expression = declaration.getVal();

    evaluator.declareVariable(name);

    if (expression != null) {
      assignValue(type, name, expression);
    }

    evaluator.addVariableWithType(name, type);
  }
}
