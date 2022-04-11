import ast.node.Function;
import ast.node.Print;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class PrintParser extends TokenConsumer implements Parser<Print> {
    private final FunctionParser expressionParser = new FunctionParser(getStream());

    public PrintParser(@NotNull TokenIterator stream) {
        super(stream);
    }

    @Override
    public Print createNode() throws Exception {
        consume(DefaultTokenTypes.KEYWORD, "println");
        if (peek(DefaultTokenTypes.SEPARATOR, "(") == null)
            throw new Exception("Expected a: \"" +
                    "("
            );
        consume(DefaultTokenTypes.SEPARATOR, "(");
        Function content = expressionParser.createNode();
        if (peek(DefaultTokenTypes.SEPARATOR, ")") == null)
            throw new Exception("Expected a: \"" +
                    ")"
            );
        consume(DefaultTokenTypes.SEPARATOR, ")");
        return new Print(content);
    }
}