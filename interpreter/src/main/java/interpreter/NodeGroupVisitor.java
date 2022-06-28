package interpreter;

import lombok.Getter;
import parser.ast.node.*;
import parser.ast.visitor.NodeVisitor;

@Getter
public abstract class NodeGroupVisitor implements NodeVisitor {

  public Evaluator evaluator;
  private PrintlnResult printLnResult = new PrintlnResult();

  public NodeGroupVisitor(Evaluator evaluator) {
    this.evaluator = evaluator;
  }

  @Override
  public void visit(NodeGroupResult nodeGroupResult) throws Exception {
    for (Node node : nodeGroupResult.getNodes()) {
      node.accept(this);
    }
  }

  @Override
  public abstract void visit(Declaration declaration) throws Exception;

  @Override
  public void visit(Assignation assignation) throws Exception {
    String name = assignation.getName();
    Expression expression = assignation.getVal();

    if (variableHasDefinedType(name)) {
      assignValue(evaluator.getVariableType(name), name, expression);
    } else {
      throw new IllegalArgumentException("variable has no defined type");
    }
  }

  @Override
  public void visit(Println printLn) throws Exception {
    printLn.getContent().accept(evaluator);
    String output = evaluator.getOutput();
    this.printLnResult.addContent(output.replaceAll("[\"']", ""));
  }

  @Override
  public void visit(IfStatement expression) throws Exception {}

  public boolean variableHasDefinedType(String name) {
    return evaluator.getVariableType(name) != null;
  }

  public void assignValue(String type, String name, Expression expression) throws Exception {
    expression.accept(evaluator);
    if (!evaluator.validateType(type)) {
      throw new IllegalArgumentException("Not valid type");
    }
    evaluator.assignVariable(name);
  }
}
