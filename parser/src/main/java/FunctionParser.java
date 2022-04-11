import ast.node.Function;
import ast.node.Operator;
import ast.node.Variable;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class FunctionParser extends TokenConsumer implements Parser<Function> {
    public FunctionParser(@NotNull TokenIterator stream) {
        super(stream);
    }

    @Override
    public Function createNode() throws Exception {
        if (peekAny(DefaultTokenTypes.IDENTIFIER, DefaultTokenTypes.LITERAL) == null)
            throw new Exception("Expected a: \"" +
                    "identifier/literal"
            );
        String val = consumeAny(DefaultTokenTypes.IDENTIFIER, DefaultTokenTypes.LITERAL).getContent();
        if (peek(DefaultTokenTypes.OPERATOR) == null)
            return new Variable(val);
        Function output = new Variable(val);
        while (peek(DefaultTokenTypes.OPERATOR) != null) {
            Operator operator = Operator.getOperand(consume(DefaultTokenTypes.OPERATOR).getContent());
            if (peekAny(DefaultTokenTypes.IDENTIFIER, DefaultTokenTypes.LITERAL) == null)
                throw new Exception("Expected a: \"" +
                        "identifier/literal"
                );
            String next = consumeAny(DefaultTokenTypes.IDENTIFIER, DefaultTokenTypes.LITERAL).getContent();
            output = output.addVariable(operator, new Variable(next));
        }
        return output;
    }
}