import ast.node.NodeGroupResult;
import ast.node.Node;
import org.austral.ingsis.printscript.common.CoreTokenTypes;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.Content;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class MainParser extends TokenConsumer implements Parser<Node> {

    private final PrintParser printParser = new PrintParser(getStream());
    private final AssignmentParser assignmentParser = new AssignmentParser(getStream());
    private final DeclarationParser declarationParser = new DeclarationParser(getStream());

    public MainParser(@NotNull TokenIterator stream) {
        super(stream);
    }

    @Override
    public Node createNode() throws Exception {
        NodeGroupResult nodeGroup = new NodeGroupResult();

        Content<String> following;

        while (peek(CoreTokenTypes.EOF) == null) {
            following = peek(DefaultTokenTypes.KEYWORD);
            if (following != null) {
                if (following.getContent().equals("let")) {
                    nodeGroup.addNode(declarationParser.createNode());
                } else if (following.getContent().equals("println")) {
                    nodeGroup.addNode(printParser.createNode());
                } else
                    throw new Exception("Unexpected keyword: " + following.getContent());
            } else {
                nodeGroup.addNode(assignmentParser.createNode());
            }
            consume(DefaultTokenTypes.SEPARATOR, ";");
        }

        return nodeGroup;
    }
}