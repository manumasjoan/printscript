package parser;

import lexer.DefaultTokenTypes;
import org.austral.ingsis.printscript.parser.Content;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;
import parser.ast.node.Node;

public class ContentParserV10 extends ContentParser {

  public final PrintlnParser printLnParser =
      new PrintlnParser(getStream(), new ExpressionParserV10(getStream()));
  public final AssignmentParser assignmentParser =
      new AssignmentParser(getStream(), new ExpressionParserV10(getStream()));
  public final DeclarationParser declarationParser = new DeclarationParserV10(getStream());

  public ContentParserV10(@NotNull TokenIterator stream) {
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
      if (isLet(followingToken)) {
        output = declarationParser.createNode();
      } else if (isPrintln(followingToken)) {
        output = printLnParser.createNode();
      } else throw new Exception("Unexpected keyword");
    }
    checkEndsWithSemicolon();
    return output;
  }
}
