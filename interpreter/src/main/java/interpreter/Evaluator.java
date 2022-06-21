package interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import parser.ast.node.*;
import parser.ast.visitor.ExpressionVisitor;

@Getter
public abstract class Evaluator implements ExpressionVisitor {

  String output;
  Map<String, String> variablesWithValue;
  Map<String, String> variableWithTypes;
  PrintEmitter printEmitter;
  InputProvider inputProvider;

  public Evaluator(PrintEmitter printEmitter, InputProvider inputProvider) {
    variablesWithValue = new HashMap<>();
    variableWithTypes = new HashMap<>();
    output = "";
    this.printEmitter = printEmitter;
    this.inputProvider = inputProvider;
  }

  public Evaluator(Map<String, String> variablesWithValue) {
    this.variablesWithValue = variablesWithValue;
    variableWithTypes = new HashMap<>();
  }

  @Override
  public void visitOperation(Operation operation) throws Exception {
    String leftOperand = getValue(operation.getLeft());
    String rightOperand = getValue(operation.getRight());
    Operator operator = operation.getOperator();

    if (isConcatenation(leftOperand, rightOperand, operator)) {
      concatenate(leftOperand, rightOperand);
    } else if (isNumericOperation(leftOperand, rightOperand)) {
      numericOperation(leftOperand, rightOperand, operator);
    }
  }

  @Override
  public void visitVariable(Variable variable) {
    if (variableHasAssignedValue(variable)) {
      output = variablesWithValue.get(variable.getVarName());
    } else {
      output = variable.getVarName();
    }
  }

  @Override
  public void visitReadInput(ReadInput readInput) throws Exception {}

  private boolean variableHasAssignedValue(Variable variable) {
    return variablesWithValue.containsKey(variable.getVarName());
  }

  private String getValue(Expression expression) throws Exception {
    expression.accept(this);
    return output;
  }

  public void declareVariable(String name) {
    variablesWithValue.put(name, null);
  }

  public abstract void assignVariable(String name) throws Exception;

  public void addVariableWithType(String name, String type) {
    variableWithTypes.put(name, type);
  }

  public String getVariableType(String name) {
    return variableWithTypes.get(name);
  }

  public boolean validateType(String type) {
    if (Objects.equals(type, "String")) {
      return isString(output);
    } else if (Objects.equals(type, "number")) {
      return isNumber(output);
    }
    return false;
  }

  public boolean isNumericOperation(String leftOperand, String rightOperand) {
    return isNumber(leftOperand) && isNumber(rightOperand);
  }

  public boolean isConcatenation(String leftOperand, String rightOperand, Operator operator) {
    return (operator == Operator.ADD)
        && (isString(leftOperand) && isString(rightOperand)
            || isString(leftOperand) && isNumber(rightOperand)
            || isNumber(leftOperand) && isString(rightOperand));
  }

  public boolean isString(String string) {
    return string.matches("\"[\\s\\S][^\"]*\"|'[\\s\\S][^']*'");
  }

  public boolean isNumber(String string) {
    return string.matches("-?[0-9]{1,9}(\\.[0-9]*)?");
  }

  public void concatenate(String left, String right) {
    output = "\"" + (left + right).replaceAll("[\"']", "") + "\"";
  }

  public void numericOperation(String leftOperand, String rightOperand, Operator operator) {
    Double leftValue = Double.parseDouble(leftOperand);
    Double rightValue = Double.parseDouble(rightOperand);

    output =
        switch (operator) {
          case ADD -> String.valueOf(leftValue + rightValue);
          case SUB -> String.valueOf(leftValue - rightValue);
          case MUL -> String.valueOf(leftValue * rightValue);
          case DIV -> String.valueOf(leftValue / rightValue);
          default -> "";
        };
  }

  public void declareCanChange(String name, boolean canChange) {}
}
