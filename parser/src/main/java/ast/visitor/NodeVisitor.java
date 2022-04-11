package ast.visitor;

import ast.node.Assignment;
import ast.node.Declaration;
import ast.node.NodeGroupResult;
import ast.node.Print;

public interface NodeVisitor {
    void visit(NodeGroupResult codeBlock) throws Exception;

    void visit(Declaration declaration) throws Exception;

    void visit(Assignment assignment) throws Exception;

    void visit(Print print) throws Exception;
}