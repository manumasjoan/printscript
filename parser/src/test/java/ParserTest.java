import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import lexer.DefaultTokenTypes;
import org.austral.ingsis.printscript.common.LexicalRange;
import org.austral.ingsis.printscript.common.Token;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.junit.jupiter.api.Test;
import parser.*;
import parser.ast.node.*;

class ParserTest {

  @Test
  public void Test001_assignmentWithString() throws Exception {
    Parser<Assignation> parser =
        new AssignmentParser(
            TokenIterator.Companion.create(
                "hello = 'hello'",
                List.of(
                    new Token(DefaultTokenTypes.IDENTIFIER, 0, 5, new LexicalRange(0, 0, 5, 0)),
                    new Token(DefaultTokenTypes.ASSIGN, 6, 7, new LexicalRange(6, 0, 7, 0)),
                    new Token(DefaultTokenTypes.LITERAL, 8, 15, new LexicalRange(8, 0, 15, 0)))));
    Assignation assignation = new Assignation("hello", new Variable("'hello'"));
    assertEquals(parser.createNode().toString(), assignation.toString());
  }

  @Test
  public void Test002_assignmentWithIdentifier() throws Exception {
    Parser<Assignation> parser =
        new AssignmentParser(
            TokenIterator.Companion.create(
                "myVar = g",
                List.of(
                    new Token(DefaultTokenTypes.IDENTIFIER, 0, 5, new LexicalRange(0, 0, 5, 0)),
                    new Token(DefaultTokenTypes.ASSIGN, 6, 7, new LexicalRange(6, 0, 7, 0)),
                    new Token(DefaultTokenTypes.IDENTIFIER, 8, 9, new LexicalRange(8, 0, 9, 0)))));
    Assignation assignation = new Assignation("myVar", new Variable("g"));
    assertEquals(assignation.toString(), parser.createNode().toString());
  }

  @Test
  public void Test003_assignmentWithNumber() throws Exception {
    Parser<Assignation> parser =
        new AssignmentParser(
            TokenIterator.Companion.create(
                "s = 10",
                List.of(
                    new Token(DefaultTokenTypes.IDENTIFIER, 0, 1, new LexicalRange(0, 0, 3, 0)),
                    new Token(DefaultTokenTypes.ASSIGN, 2, 3, new LexicalRange(2, 0, 3, 0)),
                    new Token(DefaultTokenTypes.LITERAL, 4, 6, new LexicalRange(4, 0, 6, 0)))));
    Assignation assignation = new Assignation("s", new Variable("10"));
    assertEquals(parser.createNode().toString(), assignation.toString());
  }

  @Test
  public void Test004_multiplication() throws Exception {
    Parser<Expression> parser =
        new ExpressionParserV10(
            TokenIterator.Companion.create(
                "60 * 5",
                List.of(
                    new Token(DefaultTokenTypes.LITERAL, 0, 2, new LexicalRange(0, 0, 2, 0)),
                    new Token(DefaultTokenTypes.OPERATOR, 3, 4, new LexicalRange(3, 0, 4, 0)),
                    new Token(DefaultTokenTypes.LITERAL, 5, 6, new LexicalRange(5, 0, 6, 0)))));
    Operation operation = new Operation(new Variable("60"), Operator.MUL, new Variable("5"));
    assertEquals(operation.toString(), parser.createNode().toString());
  }

  @Test
  public void Test005_incorrectTokenTest() {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V10(
                TokenIterator.Companion.create(
                    "let",
                    List.of(
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 0, 3, new LexicalRange(0, 0, 3, 0)))));
    assertThrows(Exception.class, parser::createNode);
  }

  @Test
  public void Test006_correctInitialization() throws Exception {
    Parser<Declaration> parser =
        new DeclarationParserV10(
            TokenIterator.Companion.create(
                "let var:string;",
                List.of(
                    new Token(DefaultTokenTypes.KEYWORD, 0, 3, new LexicalRange(0, 0, 3, 0)),
                    new Token(DefaultTokenTypes.IDENTIFIER, 4, 7, new LexicalRange(4, 0, 7, 0)),
                    new Token(DefaultTokenTypes.SEPARATOR, 7, 8, new LexicalRange(7, 0, 8, 0)),
                    new Token(DefaultTokenTypes.IDENTIFIER, 8, 14, new LexicalRange(8, 0, 14, 0)),
                    new Token(
                        DefaultTokenTypes.SEPARATOR, 14, 15, new LexicalRange(14, 0, 15, 0)))));
    Declaration declaration = new Declaration("var", "string", false);
    assertEquals(parser.createNode().toString(), declaration.toString());
  }
}
