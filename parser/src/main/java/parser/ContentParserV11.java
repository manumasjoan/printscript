package parser;

import lexer.DefaultTokenTypes;
import org.austral.ingsis.printscript.parser.Content;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;
import parser.ast.node.Node;

public class ContentParserV11 extends ContentParser {

  public final PrintlnParser printLnParser =
      new PrintlnParser(getStream(), new ExpressionParserV11(getStream()));
  public final AssignmentParser assignmentParser =
      new AssignmentParser(getStream(), new ExpressionParserV11(getStream()));
  public final DeclarationParser declarationParser = new DeclarationParserV11(getStream());
  public final IfParser ifParser = new IfParser(getStream(), this);

  public ContentParserV11(@NotNull TokenIterator stream) {
    super(stream);
  }

  @Override
  public Node createNode() throws Exception {
    Node output;

    Content<String> followingToken;
    followingToken = peek(DefaultTokenTypes.KEYWORD);
    if (followingToken == null) {
      output = assignmentParser.createNode();
    } else {
      if (isLet(followingToken) || isConst(followingToken)) {
        output = declarationParser.createNode();
      } else if (isPrintln(followingToken)) {
        output = printLnParser.createNode();
      } else if (isIf(followingToken)) {
        output = ifParser.createNode();
      } else throw new Exception("Unexpected keyword: " + followingToken.getContent());
    }
    checkEndsWithSemicolon();
    return output;
  }
}
