import ast.node.NodeGroupResult;
import org.austral.ingsis.printscript.common.CoreTokenTypes;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class DefaultParser extends TokenConsumer {

  ContentParser contentParser;

  public DefaultParser(@NotNull TokenIterator stream, ContentParser contentParser) {
    super(stream);
    this.contentParser = contentParser;
  }

  public NodeGroupResult createNode() throws Exception {
    NodeGroupResult nodeGroup = new NodeGroupResult();

    while (peek(CoreTokenTypes.EOF) == null) {
      nodeGroup.addNode(contentParser.createNode());
    }

    return nodeGroup;
  }
}
