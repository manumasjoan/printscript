import static org.junit.jupiter.api.Assertions.assertEquals;

import ast.expression.Expression;
import ast.expression.Operator;
import ast.expression.Variable;
import ast.node.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class EvaluatorTest {

  private final Evaluator evaluator = new Evaluator();

  @Test
  public void test001_GivenTwoStringsWithAddOperandShouldReturnConcatenatedString()
      throws NodeException {
    Expression expression =
        new Expression(new Variable("\"Hello \""), Operator.SUM, new Variable("\"World\""));
    expression.accept(evaluator);
    assertEquals("\"Hello World\"", evaluator.getOutput());
  }

  @Test
  public void test002_GivenTwoNumbersWithAddOperatorShouldReturnAddedNumbers()
      throws NodeException {
    Expression expression = new Expression(new Variable("3"), Operator.SUM, new Variable("7"));
    expression.accept(evaluator);
    assertEquals("10.0", evaluator.getOutput());
  }

  @Test
  public void test003_GivenTwoNumbersWithSubtractOperatorShouldReturnSubtractedNumbers()
      throws NodeException {
    Expression expression = new Expression(new Variable("8"), Operator.SUB, new Variable("7"));
    expression.accept(evaluator);
    assertEquals("1.0", evaluator.getOutput());
  }

  @Test
  public void test004_GivenTwoNumbersWithMultiplicationOperatorShouldReturnMultipliedNumbers()
      throws NodeException {
    Expression expression = new Expression(new Variable("8"), Operator.MUL, new Variable("2"));
    expression.accept(evaluator);
    assertEquals("16.0", evaluator.getOutput());
  }

  @Test
  public void test005_GivenTwoNumbersWithDivitionOperatorShouldReturnDividedNumbers()
      throws NodeException {
    Expression expression = new Expression(new Variable("8"), Operator.DIV, new Variable("2"));
    expression.accept(evaluator);
    assertEquals("4.0", evaluator.getOutput());
  }

  @Test
  public void test005_GivenANumberAndAStringWithAddingOperatorShouldReturnConcatenatedStrings()
      throws NodeException {
    Expression expression =
        new Expression(new Variable("\"Hello \""), Operator.SUM, new Variable("123"));
    expression.accept(evaluator);
    assertEquals("\"Hello 123\"", evaluator.getOutput());
  }

  @Test
  public void test006_GivenTwoAlreadyAssignedVariableShouldReplaceWithValueBeforeOperating()
      throws NodeException {
    Map<String, String> variables = new HashMap<>();
    variables.put("a", "5");
    variables.put("b", "6");
    Evaluator visitor = new Evaluator(variables);
    Expression expression = new Expression(new Variable("a"), Operator.SUM, new Variable("b"));
    expression.accept(visitor);
    assertEquals("11.0", visitor.getOutput());
  }

  @Test
  public void test007_GivenAnAlreadyAssignedVariableShouldReplaceWithValue() throws NodeException {
    Map<String, String> variables = new HashMap<>();
    variables.put("a", "5");
    Evaluator visitor = new Evaluator(variables);
    Variable variable = new Variable("a");
    variable.accept(visitor);
    assertEquals("5", visitor.getOutput());
  }

  @Test
  public void test008_GivenANodeGroupResultShouldPrintContent() throws NodeException {
    Declaration declaration = new Declaration("a", "String");
    Assignment assignation = new Assignment("a", new Variable("\"Hello\""));
    Print print = new Print(new Variable("a"));
    NodeGroupResult nodeGroupResult = new NodeGroupResult();
    nodeGroupResult.addNode(declaration);
    nodeGroupResult.addNode(assignation);
    nodeGroupResult.addNode(print);

    NodeGroupVisitor visitor = new NodeGroupVisitor();
    DefaultInterpreter interpreter = new DefaultInterpreter(visitor);

    assertEquals("\"Hello\"", interpreter.interpret(nodeGroupResult).getContent());
  }

  @Test
  public void test009_GivenANodeGroupResultShouldPrintContent() throws NodeException {
    Declaration declaration = new Declaration("a", "number");
    Assignment assignation =
        new Assignment("a", new Expression(new Variable("5"), Operator.SUM, new Variable("2")));
    Print print = new Print(new Variable("a"));
    NodeGroupResult nodeGroupResult = new NodeGroupResult();
    nodeGroupResult.addNode(declaration);
    nodeGroupResult.addNode(assignation);
    nodeGroupResult.addNode(print);

    NodeGroupVisitor visitor = new NodeGroupVisitor();
    DefaultInterpreter interpreter = new DefaultInterpreter(visitor);

    assertEquals("7.0", interpreter.interpret(nodeGroupResult).getContent());
  }
}
