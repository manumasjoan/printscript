import ast.node.Node;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.Content;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public abstract class ContentParser extends TokenConsumer implements Parser<Node> {

  public ContentParser(@NotNull TokenIterator stream) {
    super(stream);
  }

  @Override
  public abstract Node createNode() throws Exception;

  public void checkEndsWithSemicolon() throws Exception {
    if (peek(DefaultTokenTypes.SEPARATOR, ";") != null) {
      consume(DefaultTokenTypes.SEPARATOR, ";");
    } else {
      throw new Exception("No separator found");
    }
  }

  public boolean isPrintln(Content<String> followingToken) {
    return followingToken.getContent().equals("println");
  }

  public boolean isLet(Content<String> followingToken) {
    return followingToken.getContent().equals("let");
  }

  public boolean isConst(Content<String> followingToken) {
    return followingToken.getContent().equals("const");
  }

  public boolean isIf(Content<String> followingToken) {
    return followingToken.getContent().equals("if");
  }
}
