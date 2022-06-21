import ast.node.Expression;
import ast.node.IfStatement;
import ast.node.NodeGroupResult;
import org.austral.ingsis.printscript.common.CoreTokenTypes;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class IfParser extends TokenConsumer implements Parser<IfStatement> {

  private final ExpressionParser expressionParser = new ExpressionParserV11(getStream());
  private final ContentParser contentParser;

  public IfParser(@NotNull TokenIterator stream, ContentParser contentParser) {
    super(stream);
    this.contentParser = contentParser;
  }

  @Override
  public IfStatement createNode() throws Exception {
    consume(DefaultTokenTypes.KEYWORD, "if");
    if (opensConditionalParenthesis()) {
      Expression condition = expressionParser.createNode();
      closeConditionalParenthesis();
      if (opensBlock()) {
        NodeGroupResult blockContent = new NodeGroupResult();
        readBlock(blockContent);
        NodeGroupResult elseBlockContent = new NodeGroupResult();
        if (hasElseBlock()) {
          readBlock(elseBlockContent);
        }
        return new IfStatement(condition, blockContent, elseBlockContent);
      }
    }
    return null;
  }

  private void readBlock(NodeGroupResult blockContent) throws Exception {
    while (!closesBlock()) {
      if (peek(CoreTokenTypes.EOF) != null) {
        throw new Exception("Unexpected end of line");
      } else {
        blockContent.addNode(contentParser.createNode());
      }
    }
  }

  private boolean opensConditionalParenthesis() throws Exception {
    if (peek(DefaultTokenTypes.SEPARATOR, "(") != null) {
      consume(DefaultTokenTypes.SEPARATOR);
      return true;
    }
    throw new Exception("No opening parenthesis found");
  }

  private void closeConditionalParenthesis() throws Exception {
    if (peek(DefaultTokenTypes.SEPARATOR, ")") != null) {
      consume(DefaultTokenTypes.SEPARATOR);
      return;
    }
    throw new Exception("No closing parenthesis found");
  }

  private boolean opensBlock() throws Exception {
    if (peek(DefaultTokenTypes.SEPARATOR, "{") != null) {
      consume(DefaultTokenTypes.SEPARATOR);
      return true;
    }
    throw new Exception("No opening block found");
  }

  private boolean closesBlock() throws Exception {
    if (peek(DefaultTokenTypes.SEPARATOR, "}") != null) {
      consume(DefaultTokenTypes.SEPARATOR);
      return true;
    }
    return false;
  }

  private boolean hasElseBlock() throws Exception {
    if (peek(DefaultTokenTypes.KEYWORD, "else") != null) {
      consume(DefaultTokenTypes.KEYWORD, "else");
      opensBlock();
      return true;
    }
    return false;
  }
}
