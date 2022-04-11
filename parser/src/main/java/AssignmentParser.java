import ast.node.Function;
import ast.node.Assignment;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

// Assignment -> Identifier Commons.Operator Expr Commons.Separator
public class AssignmentParser extends TokenConsumer implements Parser<Assignment> {

    private final FunctionParser expressionParser = new FunctionParser(getStream());

    public AssignmentParser(@NotNull TokenIterator stream) {
        super(stream);
    }

    @Override
    public Assignment createNode() throws Exception {
        if (peek(DefaultTokenTypes.IDENTIFIER) == null)
            throw new Exception("Expected a: \"" +
                    "identifier"
            );
        String variable = consume(DefaultTokenTypes.IDENTIFIER).getContent();
        if (peek(DefaultTokenTypes.ASSIGN) == null)
            throw new Exception("Expected a: \"" +
                    "="
            );
        consume(DefaultTokenTypes.ASSIGN);
        Function function = expressionParser.createNode();
        return new Assignment(variable, function);
    }
}