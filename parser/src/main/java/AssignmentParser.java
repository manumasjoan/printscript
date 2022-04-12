import ast.node.Assignation;
import ast.node.MultiExpression;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class AssignmentParser extends TokenConsumer implements Parser<Assignation> {

  private final MultiExpressionParser expressionParser = new MultiExpressionParser(getStream());

  public AssignmentParser(@NotNull TokenIterator stream) {
    super(stream);
  }

  @Override
  public Assignation createNode() throws Exception {
    if (noIdentifierTokenFound()) throw new Exception("No identifier token found");
    String variable = consume(DefaultTokenTypes.IDENTIFIER).getContent();
    if (noAssignationTokenFound()) throw new Exception("No assignation token found");
    consume(DefaultTokenTypes.ASSIGN);
    MultiExpression multiExpression = expressionParser.createNode();
    return new Assignation(variable, multiExpression);
  }

  private boolean noAssignationTokenFound() {
    return peek(DefaultTokenTypes.ASSIGN) == null;
  }

  private boolean noIdentifierTokenFound() {
    return peek(DefaultTokenTypes.IDENTIFIER) == null;
  }
}
