import ast.expression.Function;
import ast.node.Assignment;
import ast.node.Declaration;
import ast.node.Node;
import ast.node.NodeException;
import ast.node.NodeGroupResult;
import ast.node.NodeVisitor;
import ast.node.Print;
import lombok.Getter;

@Getter
public class NodeGroupVisitor implements NodeVisitor {

  private PrintResult printResult = new PrintResult();

  private Evaluator evaluator = new Evaluator();

  @Override
  public void visit(NodeGroupResult nodeGroupResult) throws NodeException {
    for (Node node : nodeGroupResult.getNodes()) {
      node.accept(this);
    }
  }

  @Override
  public void visit(Declaration declaration) throws NodeException {
    String type = declaration.getType();
    String name = declaration.getVarName();
    Function function = declaration.getValue();

    evaluator.declareVariable(name);

    if (function != null) {
      assignValue(type, name, function);
    }

    evaluator.addVariableWithType(name, type);
  }

  @Override
  public void visit(Assignment assignment) throws NodeException {
    String name = assignment.getName();
    Function function = assignment.getValue();

    if (variableHasDefinedType(name)) {
      assignValue(evaluator.getVariableType(name), name, function);
    } else {
      throw new IllegalArgumentException("variable has no defined type");
    }
  }

  @Override
  public void visit(Print print) throws NodeException {
    print.getContent().accept(evaluator);
    String output = evaluator.getOutput();
    this.printResult.addContent(output);
  }

  private boolean variableHasDefinedType(String name) {
    return evaluator.getVariableType(name) != null;
  }

  private void assignValue(String type, String name, Function function) throws NodeException {
    function.accept(evaluator);
    if (!evaluator.validateType(type)) {
      throw new IllegalArgumentException("Not valid type");
    }
    evaluator.assignVariable(name);
  }
}
