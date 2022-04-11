package ast.node;


import ast.visitor.NodeVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Print implements Node {
    Function content;

    @Override
    public void accept(NodeVisitor visitor) throws Exception {
        visitor.visit(this);
    }

    // toString
    @Override
    public String toString() {
        return "print(" + "content=" + content + ')';
    }
}