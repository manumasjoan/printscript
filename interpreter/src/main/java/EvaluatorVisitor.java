
import ast.node.Expression;
import ast.node.Function;
import ast.node.Operator;
import ast.node.Variable;
import ast.visitor.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class EvaluatorVisitor implements ExpressionVisitor {

    @Getter
    private String output;

    private Map<String, String> variablesWithValue = new HashMap<>();
    final Map<String, String> variableWithTypes = new HashMap<>();

    private final String stringRegex = "\"[\\s\\S][^\"]*\"|'[\\s\\S][^']*'";
    private final String numberRegex = "-?[0-9]{1,9}(\\.[0-9]*)?";

    public EvaluatorVisitor(Map<String, String> variablesWithValue) {
        this.variablesWithValue = variablesWithValue;
    }

    @Override
    public void visitExpression(Expression expression) throws Exception {
        Operator operator = expression.getOperator();
        String left = getResult(expression.getLeft());
        String right = getResult(expression.getRight());

        System.out.println(left);
        System.out.println(right);

        if (variablesWithValue.containsKey(left))
            left = variablesWithValue.get(left);
        if (variablesWithValue.containsKey(right))
            right = variablesWithValue.get(right);

        String output="";

        if (isConcatenation(left, right, operator)) {
            output = concatenate(left, right);
        } else if(isNumericOperation(left, right)) {
            output = numericOperation(left, right, operator);
        } /*else {
            throw new UndeclaredVariableException("Missing variable declaration");
        }*/
        this.output = output;
    }

    private boolean isNumericOperation(String left, String right) {
        return left.matches(numberRegex) && right.matches(numberRegex);
    }

    private boolean isConcatenation(String left, String right, Operator operator) {
        System.out.println(left);
        System.out.println(right);
        System.out.println(operator);
        return (left.matches(stringRegex) && right.matches(numberRegex))
                || ((left.matches(numberRegex) && right.matches(stringRegex)))
                || (left.matches(stringRegex) && right.matches(stringRegex))
                && operator == Operator.ADD;
    }

    private String concatenate(String left, String right){
        return "\"" + (left+right).replaceAll("[\"']", "") + "\"";
    }

    private String numericOperation(String left, String right, Operator operator) {
        String output = "";
        Double leftValue = Double.parseDouble(left);
        Double rightValue = Double.parseDouble(right);

        switch (operator) {
            case ADD:
                output = String.valueOf(leftValue + rightValue);
                break;
            case SUB:
                output = String.valueOf(leftValue - rightValue);
                break;
            case MUL:
                output = String.valueOf(leftValue * rightValue);
                break;
            case DIV:
                output = String.valueOf(leftValue / rightValue);
                break;
        }
        return output;
    }

    /*private String format(double value) {
        String output = value + "";
        while (output.charAt(output.length() - 1) == '0')
            output = output.substring(0, output.length() - 1);
        if (output.charAt(output.length() - 1) == '.')
            output = output.substring(0, output.length() - 1);
        return output;
    }*/

    @Override
    public void visitVariable(Variable variable) throws Exception {
        output = variablesWithValue.containsKey(variable.getVarName()) ? variablesWithValue.get(variable.getVarName()) : variable.getVarName();
        /*if(variableHasAssignedValue(variable)){
            variablesWithValue.get(variable.getVariableName());
        }else{
            variable.getVariableName();
        }*/
        if (!output.matches(numberRegex) && !output.matches(stringRegex))
            throw new IllegalArgumentException(variable.getVarName());
    }

    private boolean variableHasAssignedValue(Variable variable) {
        return variablesWithValue.containsKey(variable.getVarName());
    }

    private String getResult(Function function) throws Exception {
        function.accept(this);
        return output;
    }

    public void declareVariable(String name) {
        variablesWithValue.put(name, null);
    }

    public void assignVariable(String name) {
        variablesWithValue.put(name, output);
    }

    public void addVariableWithType(String name, String type) {
        variableWithTypes.put(name, type);
    }

    public String getVariableType(String name) {
        return variableWithTypes.get(name);
    }

    public boolean validateType(String name, String type){
        if (type.equals("number")) {
            return output.matches(numberRegex);
        } else if (type.equals("string")) {
            return output.matches(stringRegex);
        }return false;
    }

}