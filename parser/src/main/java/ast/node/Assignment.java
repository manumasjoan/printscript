package ast.node;
import ast.visitor.NodeVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

// Assignment expression -> Variable = Expression ;
@AllArgsConstructor
@Getter
public class Assignment implements Node {
    String name;
    Function val;

    @Override
    public void accept(NodeVisitor visitor) throws Exception {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Assign(" + "name='" + name + '\'' + ", val=" + val + ')';
    }
}