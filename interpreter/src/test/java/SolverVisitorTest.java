
import ast.node.Expression;
import ast.node.Operator;
import ast.node.Variable;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SolverVisitorTest {

    @Test
    public void test001_WhenReceivingSimpleAdditionShouldReturnCorrectResult() throws Exception {
        Expression input = new Expression(new Variable("1"), Operator.ADD, new Variable("2"));
        String expected = "3.0";
        EvaluatorVisitor visitor = new EvaluatorVisitor();
        System.out.println(input);
        input.accept(visitor);
        String actual = visitor.getOutput();
        assertEquals(expected, actual);
    }

    @Test
    public void test002_WhenReceivingAdditionAndMultiplicationOperationShouldReturnCorrectResult()
            throws Exception {
        Expression input = new Expression(
                new Variable("1"),
                Operator.ADD,
                new Expression(new Variable("2"), Operator.MUL, new Variable("3"))
        );
        String expected = "7";
        EvaluatorVisitor visitor = new EvaluatorVisitor();
        input.accept(visitor);
        String actual = visitor.getOutput();
        assertEquals(expected, actual);
    }

    @Test
    public void test003_WhenReceivingComplexNumericalOperationShouldReturnCorrectResult() throws Exception {
        Expression input = new Expression(
                new Expression(new Variable("5"), Operator.DIV, new Variable("2")),
                Operator.ADD,
                new Expression(
                        new Variable("2"),
                        Operator.MUL,
                        new Expression(new Variable("3"), Operator.SUB, new Variable("4"))
                )
        );
        String expected = "0.5";
        EvaluatorVisitor visitor = new EvaluatorVisitor();
        input.accept(visitor);
        String actual = visitor.getOutput();
        assertEquals(expected, actual);
    }

    @Test
    public void test004_WhenReceivingSimpleNumberAndVariableOperationShouldReturnCorrectResult() throws Exception {
        Expression input = new Expression(new Variable("aNumber"), Operator.ADD, new Variable("2"));
        String expected = "7";

        HashMap<String, String> variables = new HashMap<>();
        variables.put("aNumber", "5");

        EvaluatorVisitor visitor = new EvaluatorVisitor(variables);
        input.accept(visitor);
        String actual = visitor.getOutput();
        assertEquals(expected, actual);
    }

    @Test
    public void test005_WhenReceivingComplexNumberAndVariableOperationShouldReturnCorrectResult() throws Exception {
        Expression input = new Expression(
                new Expression(new Variable("aNumber"), Operator.DIV, new Variable("2")),
                Operator.ADD,
                new Expression(
                        new Variable("2"),
                        Operator.MUL,
                        new Expression(new Variable("anotherNumber"), Operator.SUB, new Variable("4"))
                )
        );
        String expected = "0.5";

        HashMap<String, String> variables = new HashMap<>();
        variables.put("aNumber", "5");
        variables.put("anotherNumber", "3");

        EvaluatorVisitor visitor = new EvaluatorVisitor(variables);
        input.accept(visitor);
        String actual = visitor.getOutput();
        assertEquals(expected, actual);
    }

    @Test
    public void test006_WhenReceivingStringConcatenationShouldReturnCorrectResult() throws Exception {
        Expression input = new Expression(new Variable("'Hello'"), Operator.ADD, new Variable("\" world!\""));
        String expected = "\"Hello world!\"";
        EvaluatorVisitor visitor = new EvaluatorVisitor();
        input.accept(visitor);
        String actual = visitor.getOutput();
        assertEquals(expected, actual);
    }

    @Test
    public void test007_WhenReceivingStringConcatenationWithVariableShouldReturnCorrectResult() throws Exception {
        Expression input = new Expression(
                new Variable("'Hello'"),
                Operator.ADD,
                new Expression(new Variable("aString"), Operator.ADD, new Variable("\"!!!\""))
        );
        String expected = "\"Hello world!!!\"";

        HashMap<String, String> variables = new HashMap<>();
        variables.put("aString", "\" world\"");

        EvaluatorVisitor visitor = new EvaluatorVisitor(variables);
        input.accept(visitor);
        String actual = visitor.getOutput();
        assertEquals(expected, actual);
    }

    @Test
    public void test008_WhenReceivingStringConcatenationWithNumberShouldReturnCorrectResult() throws Exception {
        Expression input = new Expression(
                new Variable("'Hello'"),
                Operator.ADD,
                new Expression(new Variable("5.12"), Operator.ADD, new Variable("\"!!!\""))
        );
        String expected = "\"Hello5.12!!!\"";
        EvaluatorVisitor visitor = new EvaluatorVisitor();
        input.accept(visitor);
        String actual = visitor.getOutput();
        assertEquals(expected, actual);
    }

    @Test
    public void test009_WhenReceivingStringsNumbersAndVariablesOperationShouldReturnCorrectResult()
            throws Exception {
        Expression input = new Expression(
                new Expression(new Variable("'Hello'"), Operator.ADD, new Variable("\" world!\"")),
                Operator.ADD,
                new Expression(
                        new Expression(new Variable("5.5"), Operator.MUL, new Variable("2")),
                        Operator.ADD,
                        new Variable("aString")
                )
        );
        String expected = "\"Hello world!11!!!\"";

        HashMap<String, String> variables = new HashMap<>();
        variables.put("aString", "\"!!!\"");

        EvaluatorVisitor visitor = new EvaluatorVisitor(variables);
        input.accept(visitor);
        String actual = visitor.getOutput();
        assertEquals(expected, actual);
    }

    /*@Test
    public void test010_WhenReceivingUndeclaredVariableShouldThrowException() {
        Expression input = new Expression(new Variable("aVariable"), Operand.SUM, new Variable("\"2\""));
        EvaluatorVisitor visitor = new EvaluatorVisitor();
        assertThrows(UndeclaredVariableException.class, () -> input.accept(visitor));
    }

    @Test
    public void test011_WhenReceivingUndeclaredVariableShouldThrowException() {
        Expression input = new Expression(new Variable("aVariable"), Operand.SUM, new Variable("2"));
        EvaluatorVisitor visitor = new EvaluatorVisitor();
        assertThrows(UndeclaredVariableException.class, () -> input.accept(visitor));
    }

    @Test
    public void test012_WhenReceivingUndeclaredVariablesShouldThrowException() {
        Expression input = new Expression(new Variable("aVariable"), Operand.SUM, new Variable("anotherVariable"));
        EvaluatorVisitor visitor = new EvaluatorVisitor();
        assertThrows(UndeclaredVariableException.class, () -> input.accept(visitor));
    }*/

}