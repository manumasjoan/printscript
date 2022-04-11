import ast.node.Function;
import ast.node.Declaration;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class DeclarationParser extends TokenConsumer implements Parser<Declaration> {

    private final FunctionParser functionParser = new FunctionParser(getStream());

    public DeclarationParser(@NotNull TokenIterator stream) {
        super(stream);
    }

    @Override
    public Declaration createNode() throws Exception {
        consume(DefaultTokenTypes.KEYWORD, "let");
        if (peek(DefaultTokenTypes.IDENTIFIER) == null)
            throw new Exception( "Expected a: \"" +
                    "identifier"
            );
        String variable = consume(DefaultTokenTypes.IDENTIFIER).getContent();
        if (peek(DefaultTokenTypes.SEPARATOR) == null)
            throw new Exception("Expected a: \"" +
                    ":"
            );
        consume(DefaultTokenTypes.SEPARATOR, ":");
        if (peek(DefaultTokenTypes.IDENTIFIER) == null)
            throw new Exception("Expected a: \"" +
                    "type"
            );
        String type = consume(DefaultTokenTypes.IDENTIFIER).getContent();

        if (peek(DefaultTokenTypes.SEPARATOR, ";") != null) {
            return new Declaration(variable, type);
        }

        if (peek(DefaultTokenTypes.ASSIGN, "=") == null)
            throw new Exception("Expected a: \"" +
                    "="
            );
        consume(DefaultTokenTypes.ASSIGN, "=");
        Function function = functionParser.createNode();
        return new Declaration(variable, type, function);
    }
}