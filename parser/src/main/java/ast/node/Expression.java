package ast.node;
import ast.visitor.ExpressionVisitor;
import ast.visitor.NodeVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Expression implements Function {

    Function left;
    Operator operator;
    Function right;

    public Expression(String value) {
        this.left = new Variable(value);
    }

    @Override
    public void accept(NodeVisitor visitor) {
    }

    @Override
    public void accept(ExpressionVisitor visitor) throws Exception {
        visitor.visitExpression(this);
    }

    public Function addVariable(Operator operator, Variable variable) {
        if (operator == Operator.SUB || operator == Operator.ADD) {
            return new Expression(this, operator, variable);
        } else {
            Function right = new Expression(this.right, operator, variable);
            return new Expression(left, this.operator, right);
        }
    }

    // toString
    @Override
    public String toString() {
        String sb = "("
                + left.toString()
                + ")"
                +
                operator
                +
                "("
                + right
                + ")";
        return sb;
    }
}