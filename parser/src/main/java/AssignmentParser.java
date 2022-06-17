import ast.node.Assignation;
import ast.node.Expression;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class AssignmentParser extends TokenConsumer implements Parser<Assignation> {

  private final ExpressionParser expressionParser = new ExpressionParser(getStream());

  public AssignmentParser(@NotNull TokenIterator stream) {
    super(stream);
  }

  @Override
  public Assignation createNode() throws Exception {
    String variable = getVariable();
    lookForAssignation();
    Expression expression = expressionParser.createNode();
    return new Assignation(variable, expression);
  }

  private void lookForAssignation() throws Exception {
    if (noAssignationTokenFound()) throw new Exception("No assignation token found");
    consume(DefaultTokenTypes.ASSIGN);
  }

  private String getVariable() throws Exception {
    if (noIdentifierTokenFound()) throw new Exception("No identifier token found");
    String variable = getVariableValue();
    return variable;
  }

  private boolean noAssignationTokenFound() {
    return peek(DefaultTokenTypes.ASSIGN) == null;
  }

  private boolean noIdentifierTokenFound() {
    return peek(DefaultTokenTypes.IDENTIFIER) == null;
  }

  private String getVariableValue() {
    return consume(DefaultTokenTypes.IDENTIFIER).getContent();
  }
}
