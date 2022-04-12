import ast.node.Declaration;
import ast.node.MultiExpression;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class DeclarationParser extends TokenConsumer implements Parser<Declaration> {

  private final MultiExpressionParser multiExpressionParser = new MultiExpressionParser(getStream());

  public DeclarationParser(@NotNull TokenIterator stream) {
    super(stream);
  }

  @Override
  public Declaration createNode() throws Exception {
    consume(DefaultTokenTypes.KEYWORD, "let");
    if (noIdentifierFound()) throw new Exception("No identifier found");
    String variable = consume(DefaultTokenTypes.IDENTIFIER).getContent();
    if (noSeparatorFound()) throw new Exception("No : found");
    consume(DefaultTokenTypes.SEPARATOR, ":");
    if (noIdentifierFound()) throw new Exception("No type found");
    String type = consume(DefaultTokenTypes.IDENTIFIER).getContent();

    if (separatorFound()) return new Declaration(variable, type);

    if (noAssignmentFound()) throw new Exception("No = found");
    consume(DefaultTokenTypes.ASSIGN, "=");

    MultiExpression multiExpression = multiExpressionParser.createNode();
    return new Declaration(variable, type, multiExpression);
  }

  private boolean noAssignmentFound() {
    return peek(DefaultTokenTypes.ASSIGN, "=") == null;
  }

  private boolean separatorFound() {
    return peek(DefaultTokenTypes.SEPARATOR, ";") != null;
  }

  private boolean noSeparatorFound() {
    return peek(DefaultTokenTypes.SEPARATOR) == null;
  }

  private boolean noIdentifierFound() {
    return peek(DefaultTokenTypes.IDENTIFIER) == null;
  }
}
