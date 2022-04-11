package ast.node;
import ast.visitor.NodeVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Declaration implements Node {
    String varName;
    String type;
    Function val;

    public Declaration(String varName, String type) {
        this.varName = varName;
        this.type = type;
    }

    @Override
    public void accept(NodeVisitor visitor) throws Exception {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        if (val != null)
            return "Dec("
                    + "varName="
                    + varName
                    + ", type="
                    + type
                    + ", val="
                    + val
                    + ')';
        else
            return "Dec(" + "varName=" + varName + ", type=" + type + ')';
    }
}