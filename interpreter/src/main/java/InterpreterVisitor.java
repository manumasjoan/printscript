
import ast.node.Function;
import ast.node.*;
import ast.visitor.NodeVisitor;
import lombok.Getter;


public class InterpreterVisitor implements NodeVisitor {

    @Getter
    private final Output output = new Output();

    private final EvaluatorVisitor evaluatorVisitor = new EvaluatorVisitor();


    @Override
    public void visit(NodeGroupResult nodeGroupResult) throws Exception {
        for (Node node : nodeGroupResult.getNodes()) {
            node.accept(this);
        }
    }

    @Override
    public void visit(Declaration declaration) throws Exception {
        String type = declaration.getType();
        String name = declaration.getVarName();
        Function function = declaration.getVal();

        evaluatorVisitor.declareVariable(name);
        if (function != null) {
            assignValue(type, name, function);
        }

        evaluatorVisitor.addVariableWithType(name,type);
    }

    private void assignValue(String type, String name, Function function) throws Exception {
        function.accept(evaluatorVisitor);
        if(!evaluatorVisitor.validateType(name, type)){
            throw new IllegalArgumentException("NotValidTypeException");
        }
        evaluatorVisitor.assignVariable(name);
    }

    @Override
    public void visit(Assignment assignment) throws Exception {
        String name = assignment.getName();
        Function function = assignment.getVal();

        if(variableHasDefinedType(name)){
            assignValue(evaluatorVisitor.getVariableType(name),name,function);
        }else{
            throw new IllegalArgumentException("variable has no defined type");
        }

    }

    private boolean variableHasDefinedType(String name) {
        return evaluatorVisitor.getVariableType(name)==null;
    }

    @Override
    public void visit(Print print) throws Exception {
        print.getContent().accept(evaluatorVisitor);
        String output = evaluatorVisitor.getOutput();
        this.output.addText(output);
    }





}