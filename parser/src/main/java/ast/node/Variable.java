package ast.node;
import ast.visitor.ExpressionVisitor;
import ast.visitor.NodeVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Variable implements Function {

    private String varName;

    @Override
    public void accept(ExpressionVisitor visitor) throws Exception {
        visitor.visitVariable(this);
    }

    @Override
    public Function addVariable(Operator operator, Variable variable) {
        return new Expression(this, operator, variable);
    }

    @Override
    public void accept(NodeVisitor visitor) {
    }

    public String toString() {
        return varName;
    }
}