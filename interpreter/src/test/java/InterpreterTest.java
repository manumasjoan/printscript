import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import interpreter.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import parser.ast.node.*;

public class InterpreterTest {

  private final Evaluator evaluatorV10 = new EvaluatorV10();
  private final Evaluator evaluatorV11 = new EvaluatorV11();

  @Test
  public void test001_GivenTwoStringsWithAddOperandShouldReturnConcatenatedString()
      throws Exception {
    Operation operation =
        new Operation(new Variable("\"Hello \""), Operator.ADD, new Variable("\"World\""));
    operation.accept(evaluatorV10);
    assertEquals("\"Hello World\"", evaluatorV10.getOutput());
  }

  @Test
  public void test002_GivenTwoNumbersWithAddOperatorShouldReturnAddedNumbers() throws Exception {
    Operation operation = new Operation(new Variable("3"), Operator.ADD, new Variable("7"));
    operation.accept(evaluatorV10);
    assertEquals("10.0", evaluatorV10.getOutput());
  }

  @Test
  public void test003_GivenTwoNumbersWithSubtractOperatorShouldReturnSubtractedNumbers()
      throws Exception {
    Operation operation = new Operation(new Variable("8"), Operator.SUB, new Variable("7"));
    operation.accept(evaluatorV10);
    assertEquals("1.0", evaluatorV10.getOutput());
  }

  @Test
  public void test004_GivenTwoNumbersWithMultiplicationOperatorShouldReturnMultipliedNumbers()
      throws Exception {
    Operation operation = new Operation(new Variable("8"), Operator.MUL, new Variable("2"));
    operation.accept(evaluatorV10);
    assertEquals("16.0", evaluatorV10.getOutput());
  }

  @Test
  public void test005_GivenTwoNumbersWithDivisionOperatorShouldReturnDividedNumbers()
      throws Exception {
    Operation operation = new Operation(new Variable("8"), Operator.DIV, new Variable("2"));
    operation.accept(evaluatorV10);
    assertEquals("4.0", evaluatorV10.getOutput());
  }

  @Test
  public void test005_GivenANumberAndAStringWithAddingOperatorShouldReturnConcatenatedStrings()
      throws Exception {
    Operation operation =
        new Operation(new Variable("\"Hello \""), Operator.ADD, new Variable("123"));
    operation.accept(evaluatorV10);
    assertEquals("\"Hello 123\"", evaluatorV10.getOutput());
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

  @Test
  public void test008_GivenANodeGroupResultShouldPrintContent() throws Exception {
    Declaration declaration = new Declaration("a", "String", true);
    Assignation assignation = new Assignation("a", new Variable("\"Hello\""));
    Println printLn = new Println(new Variable("a"));
    NodeGroupResult nodeGroupResult = new NodeGroupResult();
    nodeGroupResult.addNode(declaration);
    nodeGroupResult.addNode(assignation);
    nodeGroupResult.addNode(printLn);

    InterpreterBuilder builder = new InterpreterBuilder();
    Interpreter interpreter =
        builder.buildInterpreter_V10(new DefaultPrintEmitter(), new DefaultInputProvider());

    assertEquals("Hello", interpreter.interpret(nodeGroupResult).getContent());
  }

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

    InterpreterBuilder builder = new InterpreterBuilder();
    Interpreter interpreter = builder.buildInterpreter_V10();

    assertEquals("7.0", interpreter.interpret(nodeGroupResult).getContent());
  }

  @Test
  public void test001_GivenTwoStringsWithAddOperandShouldReturnConcatenatedStringV11()
      throws Exception {
    Operation operation =
        new Operation(new Variable("\"Hello \""), Operator.ADD, new Variable("\"World\""));
    operation.accept(evaluatorV11);
    assertEquals("\"Hello World\"", evaluatorV11.getOutput());
  }

  @Test
  public void test002_GivenTwoNumbersWithAddOperatorShouldReturnAddedNumbersV11() throws Exception {
    Operation operation = new Operation(new Variable("3"), Operator.ADD, new Variable("7"));
    operation.accept(evaluatorV11);
    assertEquals("10.0", evaluatorV11.getOutput());
  }

  @Test
  public void test003_GivenTwoNumbersWithSubtractOperatorShouldReturnSubtractedNumbersV11()
      throws Exception {
    Operation operation = new Operation(new Variable("8"), Operator.SUB, new Variable("7"));
    operation.accept(evaluatorV11);
    assertEquals("1.0", evaluatorV11.getOutput());
  }

  @Test
  public void test004_GivenTwoNumbersWithMultiplicationOperatorShouldReturnMultipliedNumbersV11()
      throws Exception {
    Operation operation = new Operation(new Variable("8"), Operator.MUL, new Variable("2"));
    operation.accept(evaluatorV11);
    assertEquals("16.0", evaluatorV11.getOutput());
  }

  @Test
  public void test005_GivenTwoNumbersWithDivisionOperatorShouldReturnDividedNumbersV11()
      throws Exception {
    Operation operation = new Operation(new Variable("8"), Operator.DIV, new Variable("2"));
    operation.accept(evaluatorV11);
    assertEquals("4.0", evaluatorV11.getOutput());
  }

  @Test
  public void test005_GivenANumberAndAStringWithAddingOperatorShouldReturnConcatenatedStringsV11()
      throws Exception {
    Operation operation =
        new Operation(new Variable("\"Hello \""), Operator.ADD, new Variable("123"));
    operation.accept(evaluatorV11);
    assertEquals("\"Hello 123\"", evaluatorV11.getOutput());
  }

  @Test
  public void test006_GivenTwoAlreadyAssignedVariableShouldReplaceWithValueBeforeOperatingV11()
      throws Exception {
    Map<String, String> variables = new HashMap<>();
    variables.put("a", "5");
    variables.put("b", "6");
    Map<String, Boolean> variablesCanChange = new HashMap<>();
    variablesCanChange.put("a", true);
    variablesCanChange.put("b", true);
    Evaluator visitor = new EvaluatorV11(variables, variablesCanChange);
    Operation operation = new Operation(new Variable("a"), Operator.ADD, new Variable("b"));
    operation.accept(visitor);
    assertEquals("11.0", visitor.getOutput());
  }

  @Test
  public void test007_GivenAnAlreadyAssignedVariableShouldReplaceWithValueV11() throws Exception {
    Map<String, String> variables = new HashMap<>();
    variables.put("a", "5");
    Map<String, Boolean> variablesCanChange = new HashMap<>();
    variablesCanChange.put("a", true);
    Evaluator visitor = new EvaluatorV11(variables, variablesCanChange);
    Variable variable = new Variable("a");
    variable.accept(visitor);
    assertEquals("5", visitor.getOutput());
  }

  @Test
  public void test008_GivenANodeGroupResultShouldPrintContentV11() throws Exception {
    Declaration declaration = new Declaration("a", "String", true);
    Assignation assignation = new Assignation("a", new Variable("\"Hello\""));
    Println printLn = new Println(new Variable("a"));
    NodeGroupResult nodeGroupResult = new NodeGroupResult();
    nodeGroupResult.addNode(declaration);
    nodeGroupResult.addNode(assignation);
    nodeGroupResult.addNode(printLn);

    NodeGroupVisitor visitor = new NodeGroupVisitorV11(new EvaluatorV11());
    DefaultInterpreter interpreter = new DefaultInterpreter(visitor);

    assertEquals("Hello", interpreter.interpret(nodeGroupResult).getContent());
  }

  @Test
  public void test009_GivenANodeGroupResultShouldPrintContentV11() throws Exception {
    Declaration declaration = new Declaration("a", "number", true);
    Assignation assignation =
        new Assignation("a", new Operation(new Variable("5"), Operator.ADD, new Variable("2")));
    Println printLn = new Println(new Variable("a"));
    NodeGroupResult nodeGroupResult = new NodeGroupResult();
    nodeGroupResult.addNode(declaration);
    nodeGroupResult.addNode(assignation);
    nodeGroupResult.addNode(printLn);

    NodeGroupVisitor visitor = new NodeGroupVisitorV11(new EvaluatorV11());
    DefaultInterpreter interpreter = new DefaultInterpreter(visitor);

    assertEquals("7.0", interpreter.interpret(nodeGroupResult).getContent());
  }

  @Test
  public void test010_TestIfStatementV11() throws Exception {
    NodeGroupResult g1 = new NodeGroupResult();
    NodeGroupResult g2 = new NodeGroupResult();
    g1.addNode(new Println(new Variable("5")));
    g2.addNode(new Println(new Variable("4")));
    IfStatement ifStatement = new IfStatement(new Variable("true"), g1, g2);

    NodeGroupResult nodeGroupResult = new NodeGroupResult();
    nodeGroupResult.addNode(ifStatement);

    NodeGroupVisitor visitor = new NodeGroupVisitorV11(new EvaluatorV11());
    DefaultInterpreter interpreter = new DefaultInterpreter(visitor);

    assertEquals("5", interpreter.interpret(nodeGroupResult).getContent());
  }

  @Test
  public void test011_TestIfStatementFalseV11() throws Exception {
    NodeGroupResult g1 = new NodeGroupResult();
    NodeGroupResult g2 = new NodeGroupResult();
    g1.addNode(new Println(new Variable("5")));
    g2.addNode(new Println(new Variable("4")));
    IfStatement ifStatement = new IfStatement(new Variable("false"), g1, g2);

    NodeGroupResult nodeGroupResult = new NodeGroupResult();
    nodeGroupResult.addNode(ifStatement);

    InterpreterBuilder builder = new InterpreterBuilder();
    Interpreter interpreter =
        builder.buildInterpreter_V11(new DefaultPrintEmitter(), new DefaultInputProvider());

    assertEquals("4", interpreter.interpret(nodeGroupResult).getContent());
  }

  @Test
  public void test012_TestIfStatementFalseV11() throws Exception {
    NodeGroupResult g1 = new NodeGroupResult();
    NodeGroupResult g2 = new NodeGroupResult();
    g1.addNode(new Println(new Variable("5")));
    g2.addNode(new Println(new Variable("4")));
    IfStatement ifStatement = new IfStatement(new Variable("false"), g1, g2);

    NodeGroupResult nodeGroupResult = new NodeGroupResult();
    nodeGroupResult.addNode(ifStatement);

    InterpreterBuilder builder = new InterpreterBuilder();
    Interpreter interpreter = builder.buildInterpreter_V11();

    assertEquals("4", interpreter.interpret(nodeGroupResult).getContent());
  }

  @Test
  public void test013_GivenANodeGroupResultShouldPrintContentV11() throws Exception {
    Declaration declaration = new Declaration("a", "number", false);
    Assignation assignation =
        new Assignation("a", new Operation(new Variable("5"), Operator.ADD, new Variable("2")));
    Println printLn = new Println(new Variable("a"));
    NodeGroupResult nodeGroupResult = new NodeGroupResult();
    nodeGroupResult.addNode(declaration);
    nodeGroupResult.addNode(assignation);
    nodeGroupResult.addNode(printLn);

    NodeGroupVisitor visitor = new NodeGroupVisitorV11(new EvaluatorV11());
    DefaultInterpreter interpreter = new DefaultInterpreter(visitor);

    assertEquals("7.0", interpreter.interpret(nodeGroupResult).getContent());
  }

  @Test
  public void test014_GivenANodeGroupResultThrowExceptionV11() throws Exception {
    Declaration declaration = new Declaration("a", "number", false);
    Assignation assignation =
        new Assignation("a", new Operation(new Variable("5"), Operator.ADD, new Variable("2")));
    Assignation assignation2 =
        new Assignation("a", new Operation(new Variable("4"), Operator.ADD, new Variable("2")));
    Println printLn = new Println(new Variable("a"));
    NodeGroupResult nodeGroupResult = new NodeGroupResult();
    nodeGroupResult.addNode(declaration);
    nodeGroupResult.addNode(assignation);
    nodeGroupResult.addNode(assignation2);
    nodeGroupResult.addNode(printLn);

    NodeGroupVisitor visitor = new NodeGroupVisitorV11(new EvaluatorV11());
    DefaultInterpreter interpreter = new DefaultInterpreter(visitor);

    assertThrows(Exception.class, () -> interpreter.interpret(nodeGroupResult));
  }

  @Test
  public void test014_GivenANodeGroupResultShouldPrintContentV11() throws Exception {
    Declaration declaration = new Declaration("a", "boolean", false);
    Assignation assignation = new Assignation("a", new Variable("true"));
    Println printLn = new Println(new Variable("a"));
    NodeGroupResult nodeGroupResult = new NodeGroupResult();
    nodeGroupResult.addNode(declaration);
    nodeGroupResult.addNode(assignation);
    nodeGroupResult.addNode(printLn);

    NodeGroupVisitor visitor = new NodeGroupVisitorV11(new EvaluatorV11());
    DefaultInterpreter interpreter = new DefaultInterpreter(visitor);

    assertEquals("true", interpreter.interpret(nodeGroupResult).getContent());
  }

  @Test
  public void test014_GivenANodeGroupWithInvalidTypeShouldThrowExceptionV11() throws Exception {
    Declaration declaration = new Declaration("a", "notValidType", false);
    Assignation assignation = new Assignation("a", new Variable("true"));
    Println printLn = new Println(new Variable("a"));
    NodeGroupResult nodeGroupResult = new NodeGroupResult();
    nodeGroupResult.addNode(declaration);
    nodeGroupResult.addNode(assignation);
    nodeGroupResult.addNode(printLn);

    NodeGroupVisitor visitor = new NodeGroupVisitorV11(new EvaluatorV11());
    DefaultInterpreter interpreter = new DefaultInterpreter(visitor);

    assertThrows(Exception.class, () -> interpreter.interpret(nodeGroupResult));
  }
}
