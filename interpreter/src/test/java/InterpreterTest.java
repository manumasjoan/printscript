import static org.junit.jupiter.api.Assertions.assertEquals;

import interpreter.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import parser.ast.node.*;

public class InterpreterTest {

  private final Evaluator evaluator = new EvaluatorV10();

  @Test
  public void test001_GivenTwoStringsWithAddOperandShouldReturnConcatenatedString()
      throws Exception {
    Operation operation =
        new Operation(new Variable("\"Hello \""), Operator.ADD, new Variable("\"World\""));
    operation.accept(evaluator);
    assertEquals("\"Hello World\"", evaluator.getOutput());
  }

  @Test
  public void test002_GivenTwoNumbersWithAddOperatorShouldReturnAddedNumbers() throws Exception {
    Operation operation = new Operation(new Variable("3"), Operator.ADD, new Variable("7"));
    operation.accept(evaluator);
    assertEquals("10.0", evaluator.getOutput());
  }

  @Test
  public void test003_GivenTwoNumbersWithSubtractOperatorShouldReturnSubtractedNumbers()
      throws Exception {
    Operation operation = new Operation(new Variable("8"), Operator.SUB, new Variable("7"));
    operation.accept(evaluator);
    assertEquals("1.0", evaluator.getOutput());
  }

  @Test
  public void test004_GivenTwoNumbersWithMultiplicationOperatorShouldReturnMultipliedNumbers()
      throws Exception {
    Operation operation = new Operation(new Variable("8"), Operator.MUL, new Variable("2"));
    operation.accept(evaluator);
    assertEquals("16.0", evaluator.getOutput());
  }

  @Test
  public void test005_GivenTwoNumbersWithDivisionOperatorShouldReturnDividedNumbers()
      throws Exception {
    Operation operation = new Operation(new Variable("8"), Operator.DIV, new Variable("2"));
    operation.accept(evaluator);
    assertEquals("4.0", evaluator.getOutput());
  }

  @Test
  public void test005_GivenANumberAndAStringWithAddingOperatorShouldReturnConcatenatedStrings()
      throws Exception {
    Operation operation =
        new Operation(new Variable("\"Hello \""), Operator.ADD, new Variable("123"));
    operation.accept(evaluator);
    assertEquals("\"Hello 123\"", evaluator.getOutput());
  }

  @Test
  public void test006_GivenTwoAlreadyAssignedVariableShouldReplaceWithValueBeforeOperating()
      throws Exception {
    Map<String, String> variables = new HashMap<>();
    variables.put("a", "5");
    variables.put("b", "6");
    Evaluator visitor = new EvaluatorV10(variables);
    Operation operation = new Operation(new Variable("a"), Operator.ADD, new Variable("b"));
    operation.accept(visitor);
    assertEquals("11.0", visitor.getOutput());
  }

  @Test
  public void test007_GivenAnAlreadyAssignedVariableShouldReplaceWithValue() throws Exception {
    Map<String, String> variables = new HashMap<>();
    variables.put("a", "5");
    Evaluator visitor = new EvaluatorV10(variables);
    Variable variable = new Variable("a");
    variable.accept(visitor);
    assertEquals("5", visitor.getOutput());
  }

  /*  @Test
  public void test008_GivenANodeGroupResultShouldPrintContent() throws Exception {
    Declaration declaration = new Declaration("a", "String", true);
    Assignation assignation = new Assignation("a", new Variable("\"Hello\""));
    Println printLn = new Println(new Variable("a"));
    NodeGroupResult nodeGroupResult = new NodeGroupResult();
    nodeGroupResult.addNode(declaration);
    nodeGroupResult.addNode(assignation);
    nodeGroupResult.addNode(printLn);

    NodeGroupVisitor visitor = new NodeGroupVisitorV10(new EvaluatorV10());
    DefaultInterpreter interpreter = new DefaultInterpreter(visitor);

    assertEquals("\"Hello\"", interpreter.interpret(nodeGroupResult).getContent());
  }*/

  @Test
  public void test009_GivenANodeGroupResultShouldPrintContent() throws Exception {
    Declaration declaration = new Declaration("a", "number", true);
    Assignation assignation =
        new Assignation("a", new Operation(new Variable("5"), Operator.ADD, new Variable("2")));
    Println printLn = new Println(new Variable("a"));
    NodeGroupResult nodeGroupResult = new NodeGroupResult();
    nodeGroupResult.addNode(declaration);
    nodeGroupResult.addNode(assignation);
    nodeGroupResult.addNode(printLn);

    NodeGroupVisitor visitor = new NodeGroupVisitorV10(new EvaluatorV10());
    DefaultInterpreter interpreter = new DefaultInterpreter(visitor);

    assertEquals("7.0", interpreter.interpret(nodeGroupResult).getContent());
  }
}
