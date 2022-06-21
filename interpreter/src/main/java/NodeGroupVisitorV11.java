import ast.node.Declaration;
import ast.node.Expression;
import ast.node.IfStatement;
import lombok.val;

public class NodeGroupVisitorV11 extends NodeGroupVisitor {

  public NodeGroupVisitorV11(EvaluatorV11 evaluator) {
    super(evaluator);
  }

  @Override
  public void visit(Declaration declaration) throws Exception {
    String type = declaration.getType();
    String name = declaration.getVarName();
    boolean canChange = declaration.isCanChange();
    Expression expression = declaration.getVal();

    evaluator.declareVariable(name);

    if (expression != null) {
      assignValue(type, name, expression);
    }

    evaluator.declareCanChange(name, canChange);
    evaluator.addVariableWithType(name, type);
  }

  @Override
  public void visit(IfStatement expression) throws Exception {
    expression.getCondition().accept(evaluator);
    val condition = evaluator.getOutput();
    evaluateResult(expression, condition);
  }

  private void evaluateResult(IfStatement expression, String condition) throws Exception {
    if (condition.equals("true")) {
      expression.getIfTrueBlock().accept(this);
    } else if (isFalseAndElseIsDefined(expression, condition)) {
      expression.getElseBlock().accept(this);
    } else {
      throw new Exception("not valid if statement");
    }
  }

  private boolean isFalseAndElseIsDefined(IfStatement expression, String condition) {
    return condition.equals("false") && expression.getElseBlock() != null;
  }
}
