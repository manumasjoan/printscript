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
    Declaration declaration = new Declaration("var", "string", true);
    assertEquals(parser.createNode().toString(), declaration.toString());
  }

  @Test
  public void Test006_TestValidDeclarationAndAssignation() throws Exception {
    Parser<Declaration> parser =
        new DeclarationParserV10(
            TokenIterator.Companion.create(
                "let a : number = 5;",
                List.of(
                    new Token(DefaultTokenTypes.KEYWORD, 0, 3, new LexicalRange(0, 0, 3, 0)),
                    new Token(DefaultTokenTypes.IDENTIFIER, 4, 5, new LexicalRange(4, 0, 5, 0)),
                    new Token(DefaultTokenTypes.SEPARATOR, 6, 7, new LexicalRange(6, 0, 7, 0)),
                    new Token(DefaultTokenTypes.IDENTIFIER, 8, 14, new LexicalRange(8, 0, 14, 0)),
                    new Token(DefaultTokenTypes.ASSIGN, 15, 16, new LexicalRange(15, 0, 16, 0)),
                    new Token(DefaultTokenTypes.LITERAL, 17, 18, new LexicalRange(17, 0, 18, 0)),
                    new Token(
                        DefaultTokenTypes.SEPARATOR, 18, 19, new LexicalRange(18, 0, 19, 0)))));

    Declaration declaration = new Declaration("a", "number", new Variable("5"), true);
    assertEquals(parser.createNode().toString(), declaration.toString());
  }

  @Test
  public void Test007_TestContentParserV10() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V10(
                TokenIterator.Companion.create(
                    "let a : number = 5;",
                    List.of(
                        new Token(DefaultTokenTypes.KEYWORD, 0, 3, new LexicalRange(0, 0, 3, 0)),
                        new Token(DefaultTokenTypes.IDENTIFIER, 4, 5, new LexicalRange(4, 0, 5, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 6, 7, new LexicalRange(6, 0, 7, 0)),
                        new Token(
                            DefaultTokenTypes.IDENTIFIER, 8, 14, new LexicalRange(8, 0, 14, 0)),
                        new Token(DefaultTokenTypes.ASSIGN, 15, 16, new LexicalRange(15, 0, 16, 0)),
                        new Token(
                            DefaultTokenTypes.LITERAL, 17, 18, new LexicalRange(17, 0, 18, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 18, 19, new LexicalRange(18, 0, 19, 0)))));

    Declaration declaration = new Declaration("a", "number", new Variable("5"), true);
    assertEquals(parser.createNode().toString(), declaration.toString());
  }

  @Test
  public void Test008_TestContentParserV10() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V10(
                TokenIterator.Companion.create(
                    "println (5);",
                    List.of(
                        new Token(DefaultTokenTypes.KEYWORD, 0, 7, new LexicalRange(0, 0, 7, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 8, 9, new LexicalRange(8, 0, 9, 0)),
                        new Token(DefaultTokenTypes.LITERAL, 9, 10, new LexicalRange(9, 0, 10, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 10, 11, new LexicalRange(10, 0, 11, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 11, 12, new LexicalRange(11, 0, 12, 0)))));

    Println println = new Println(new Variable("5"));
    assertEquals(parser.createNode().toString(), println.toString());
  }

  @Test
  public void Test009_incorrectTokenTest() {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V10(
                TokenIterator.Companion.create(
                    "const",
                    List.of(
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 0, 5, new LexicalRange(0, 0, 5, 0)))));
    assertThrows(Exception.class, parser::createNode);
  }

  @Test
  public void Test010_TestContentParserV11() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V11(
                TokenIterator.Companion.create(
                    "let a : number = 5;",
                    List.of(
                        new Token(DefaultTokenTypes.KEYWORD, 0, 3, new LexicalRange(0, 0, 3, 0)),
                        new Token(DefaultTokenTypes.IDENTIFIER, 4, 5, new LexicalRange(4, 0, 5, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 6, 7, new LexicalRange(6, 0, 7, 0)),
                        new Token(
                            DefaultTokenTypes.IDENTIFIER, 8, 14, new LexicalRange(8, 0, 14, 0)),
                        new Token(DefaultTokenTypes.ASSIGN, 15, 16, new LexicalRange(15, 0, 16, 0)),
                        new Token(
                            DefaultTokenTypes.LITERAL, 17, 18, new LexicalRange(17, 0, 18, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 18, 19, new LexicalRange(18, 0, 19, 0)))));

    Declaration declaration = new Declaration("a", "number", new Variable("5"), true);
    assertEquals(parser.createNode().toString(), declaration.toString());
  }

  @Test
  public void Test011_TestContentParserV11() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V11(
                TokenIterator.Companion.create(
                    "println (5);",
                    List.of(
                        new Token(DefaultTokenTypes.KEYWORD, 0, 7, new LexicalRange(0, 0, 7, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 8, 9, new LexicalRange(8, 0, 9, 0)),
                        new Token(DefaultTokenTypes.LITERAL, 9, 10, new LexicalRange(9, 0, 10, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 10, 11, new LexicalRange(10, 0, 11, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 11, 12, new LexicalRange(11, 0, 12, 0)))));

    Println println = new Println(new Variable("5"));
    assertEquals(parser.createNode().toString(), println.toString());
  }

  @Test
  public void Test012_TestContentParserV11() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V11(
                TokenIterator.Companion.create(
                    "const a : number = 5;",
                    List.of(
                        new Token(DefaultTokenTypes.KEYWORD, 0, 5, new LexicalRange(0, 0, 5, 0)),
                        new Token(DefaultTokenTypes.IDENTIFIER, 6, 7, new LexicalRange(6, 0, 7, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 8, 9, new LexicalRange(8, 0, 9, 0)),
                        new Token(
                            DefaultTokenTypes.IDENTIFIER, 10, 16, new LexicalRange(10, 0, 16, 0)),
                        new Token(DefaultTokenTypes.ASSIGN, 17, 18, new LexicalRange(17, 0, 18, 0)),
                        new Token(
                            DefaultTokenTypes.LITERAL, 19, 20, new LexicalRange(19, 0, 20, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 20, 21, new LexicalRange(20, 0, 21, 0)))));

    Declaration declaration = new Declaration("a", "number", new Variable("5"), false);
    assertEquals(parser.createNode().toString(), declaration.toString());
  }

  @Test
  public void Test013_TestContentParserV11() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V11(
                TokenIterator.Companion.create(
                    "if(true){println(5);}else{println(4);}",
                    List.of(
                        new Token(DefaultTokenTypes.KEYWORD, 0, 2, new LexicalRange(0, 0, 2, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 2, 3, new LexicalRange(2, 0, 3, 0)),
                        new Token(DefaultTokenTypes.IDENTIFIER, 3, 7, new LexicalRange(3, 0, 7, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 7, 8, new LexicalRange(7, 0, 8, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 8, 9, new LexicalRange(8, 0, 9, 0)),
                        new Token(DefaultTokenTypes.KEYWORD, 9, 16, new LexicalRange(9, 0, 16, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 16, 17, new LexicalRange(16, 0, 17, 0)),
                        new Token(
                            DefaultTokenTypes.LITERAL, 17, 18, new LexicalRange(17, 0, 18, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 18, 19, new LexicalRange(18, 0, 19, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 19, 20, new LexicalRange(19, 0, 20, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 20, 21, new LexicalRange(20, 0, 21, 0)),
                        new Token(
                            DefaultTokenTypes.KEYWORD, 21, 25, new LexicalRange(21, 0, 25, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 25, 26, new LexicalRange(25, 0, 26, 0)),
                        new Token(
                            DefaultTokenTypes.KEYWORD, 26, 33, new LexicalRange(26, 0, 33, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 33, 34, new LexicalRange(33, 0, 34, 0)),
                        new Token(
                            DefaultTokenTypes.LITERAL, 34, 35, new LexicalRange(34, 0, 35, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 35, 36, new LexicalRange(35, 0, 36, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 36, 37, new LexicalRange(36, 0, 37, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 37, 38, new LexicalRange(37, 0, 38, 0)))));

    NodeGroupResult g1 = new NodeGroupResult();
    NodeGroupResult g2 = new NodeGroupResult();
    g1.addNode(new Println(new Variable("5")));
    g2.addNode(new Println(new Variable("4")));
    IfStatement ifStatement = new IfStatement(new Variable("true"), g1, g2);
    assertEquals(parser.createNode().toString(), ifStatement.toString());
  }

  @Test
  public void Test014_TestContentParserV11() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V11(
                TokenIterator.Companion.create(
                    "if(true){println(5);}",
                    List.of(
                        new Token(DefaultTokenTypes.KEYWORD, 0, 2, new LexicalRange(0, 0, 2, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 2, 3, new LexicalRange(2, 0, 3, 0)),
                        new Token(DefaultTokenTypes.IDENTIFIER, 3, 7, new LexicalRange(3, 0, 7, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 7, 8, new LexicalRange(7, 0, 8, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 8, 9, new LexicalRange(8, 0, 9, 0)),
                        new Token(DefaultTokenTypes.KEYWORD, 9, 16, new LexicalRange(9, 0, 16, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 16, 17, new LexicalRange(16, 0, 17, 0)),
                        new Token(
                            DefaultTokenTypes.LITERAL, 17, 18, new LexicalRange(17, 0, 18, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 18, 19, new LexicalRange(18, 0, 19, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 19, 20, new LexicalRange(19, 0, 20, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 20, 21, new LexicalRange(20, 0, 21, 0)))));

    NodeGroupResult g1 = new NodeGroupResult();
    NodeGroupResult g2 = new NodeGroupResult();
    g1.addNode(new Println(new Variable("5")));
    IfStatement ifStatement = new IfStatement(new Variable("true"), g1, g2);
    assertEquals(parser.createNode().toString(), ifStatement.toString());
  }

  @Test
  public void Test015_TestContentParserV11() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V11(
                TokenIterator.Companion.create(
                    "if(true){println(5)}",
                    List.of(
                        new Token(DefaultTokenTypes.KEYWORD, 0, 2, new LexicalRange(0, 0, 2, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 2, 3, new LexicalRange(2, 0, 3, 0)),
                        new Token(DefaultTokenTypes.IDENTIFIER, 3, 7, new LexicalRange(3, 0, 7, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 7, 8, new LexicalRange(7, 0, 8, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 8, 9, new LexicalRange(8, 0, 9, 0)),
                        new Token(DefaultTokenTypes.KEYWORD, 9, 16, new LexicalRange(9, 0, 16, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 16, 17, new LexicalRange(16, 0, 17, 0)),
                        new Token(
                            DefaultTokenTypes.LITERAL, 17, 18, new LexicalRange(17, 0, 18, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 18, 19, new LexicalRange(18, 0, 19, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 19, 20, new LexicalRange(19, 0, 20, 0)))));

    assertThrows(Exception.class, parser::createNode);
  }

  @Test
  public void Test016_TestContentParserV11() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V11(
                TokenIterator.Companion.create(
                    "if",
                    List.of(
                        new Token(DefaultTokenTypes.KEYWORD, 0, 2, new LexicalRange(0, 0, 2, 0)))));

    assertThrows(Exception.class, parser::createNode);
  }

  @Test
  public void Test017_TestContentParserV11() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V11(
                TokenIterator.Companion.create(
                    "no",
                    List.of(
                        new Token(DefaultTokenTypes.KEYWORD, 0, 2, new LexicalRange(0, 0, 2, 0)))));

    assertThrows(Exception.class, parser::createNode);
  }

  @Test
  public void Test018_TestContentParserV11() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V11(
                TokenIterator.Companion.create(
                    "s = 10;",
                    List.of(
                        new Token(DefaultTokenTypes.IDENTIFIER, 0, 1, new LexicalRange(0, 0, 3, 0)),
                        new Token(DefaultTokenTypes.ASSIGN, 2, 3, new LexicalRange(2, 0, 3, 0)),
                        new Token(DefaultTokenTypes.LITERAL, 4, 6, new LexicalRange(4, 0, 6, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 6, 7, new LexicalRange(6, 0, 7, 0)))));
    Assignation assignation = new Assignation("s", new Variable("10"));
    assertEquals(parser.createNode().toString(), assignation.toString());
  }

  @Test
  public void Test019_TestContentParserV11() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V11(
                TokenIterator.Companion.create(
                    "a = readInput('read');",
                    List.of(
                        new Token(DefaultTokenTypes.IDENTIFIER, 0, 1, new LexicalRange(0, 0, 1, 0)),
                        new Token(DefaultTokenTypes.ASSIGN, 2, 3, new LexicalRange(2, 0, 3, 0)),
                        new Token(DefaultTokenTypes.KEYWORD, 4, 13, new LexicalRange(4, 0, 13, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 13, 14, new LexicalRange(13, 0, 14, 0)),
                        new Token(
                            DefaultTokenTypes.LITERAL, 14, 20, new LexicalRange(14, 0, 20, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 20, 21, new LexicalRange(20, 0, 21, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 21, 22, new LexicalRange(21, 0, 22, 0)))));
    Assignation assignation = new Assignation("a", new ReadInput("'read'"));
    assertEquals(parser.createNode().toString(), assignation.toString());
  }

  @Test
  public void Test020_TestContentParserV11() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V11(
                TokenIterator.Companion.create(
                    "a = readInput;",
                    List.of(
                        new Token(DefaultTokenTypes.IDENTIFIER, 0, 1, new LexicalRange(0, 0, 1, 0)),
                        new Token(DefaultTokenTypes.ASSIGN, 2, 3, new LexicalRange(2, 0, 3, 0)),
                        new Token(DefaultTokenTypes.KEYWORD, 4, 13, new LexicalRange(4, 0, 13, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 13, 14, new LexicalRange(13, 0, 14, 0)))));

    assertThrows(Exception.class, parser::createNode);
  }

  @Test
  public void Test021_TestContentParserV11() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V11(
                TokenIterator.Companion.create(
                    "a = readInput('read'",
                    List.of(
                        new Token(DefaultTokenTypes.IDENTIFIER, 0, 1, new LexicalRange(0, 0, 1, 0)),
                        new Token(DefaultTokenTypes.ASSIGN, 2, 3, new LexicalRange(2, 0, 3, 0)),
                        new Token(DefaultTokenTypes.KEYWORD, 4, 13, new LexicalRange(4, 0, 13, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 13, 14, new LexicalRange(13, 0, 14, 0)),
                        new Token(
                            DefaultTokenTypes.LITERAL, 14, 20, new LexicalRange(14, 0, 20, 0)))));

    assertThrows(Exception.class, parser::createNode);
  }

  @Test
  public void Test022_TestContentParserV11() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V11(
                TokenIterator.Companion.create(
                    "if(true",
                    List.of(
                        new Token(DefaultTokenTypes.KEYWORD, 0, 2, new LexicalRange(0, 0, 2, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 2, 3, new LexicalRange(2, 0, 3, 0)),
                        new Token(
                            DefaultTokenTypes.IDENTIFIER, 3, 7, new LexicalRange(3, 0, 7, 0)))));

    assertThrows(Exception.class, parser::createNode);
  }

  @Test
  public void Test023_TestContentParserV11() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V11(
                TokenIterator.Companion.create(
                    "if(true)",
                    List.of(
                        new Token(DefaultTokenTypes.KEYWORD, 0, 2, new LexicalRange(0, 0, 2, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 2, 3, new LexicalRange(2, 0, 3, 0)),
                        new Token(DefaultTokenTypes.IDENTIFIER, 3, 7, new LexicalRange(3, 0, 7, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 7, 8, new LexicalRange(7, 0, 8, 0)))));

    assertThrows(Exception.class, parser::createNode);
  }

  @Test
  public void Test024_TestContentParserV11() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V11(
                TokenIterator.Companion.create(
                    "a = 12 + 345;",
                    List.of(
                        new Token(DefaultTokenTypes.IDENTIFIER, 0, 1, new LexicalRange(0, 0, 1, 0)),
                        new Token(DefaultTokenTypes.ASSIGN, 2, 3, new LexicalRange(2, 0, 3, 0)),
                        new Token(DefaultTokenTypes.LITERAL, 4, 6, new LexicalRange(4, 0, 6, 0)),
                        new Token(DefaultTokenTypes.OPERATOR, 7, 8, new LexicalRange(7, 0, 8, 0)),
                        new Token(DefaultTokenTypes.LITERAL, 9, 12, new LexicalRange(9, 0, 12, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 12, 13, new LexicalRange(12, 0, 13, 0)))));

    Assignation assignation =
        new Assignation("a", new Operation(new Variable("12"), Operator.ADD, new Variable("345")));
    assertEquals(parser.createNode().toString(), assignation.toString());
  }

  @Test
  public void Test025_TestContentParserV11() throws Exception {
    DefaultParser parser =
        new ParserBuilder()
            .buildParser_V11(
                TokenIterator.Companion.create(
                    "a = 12*2 + 345;",
                    List.of(
                        new Token(DefaultTokenTypes.IDENTIFIER, 0, 1, new LexicalRange(0, 0, 1, 0)),
                        new Token(DefaultTokenTypes.ASSIGN, 2, 3, new LexicalRange(2, 0, 3, 0)),
                        new Token(DefaultTokenTypes.LITERAL, 4, 6, new LexicalRange(4, 0, 6, 0)),
                        new Token(DefaultTokenTypes.OPERATOR, 6, 7, new LexicalRange(6, 0, 7, 0)),
                        new Token(DefaultTokenTypes.LITERAL, 7, 8, new LexicalRange(7, 0, 8, 0)),
                        new Token(DefaultTokenTypes.OPERATOR, 9, 10, new LexicalRange(9, 0, 10, 0)),
                        new Token(
                            DefaultTokenTypes.LITERAL, 11, 14, new LexicalRange(11, 0, 14, 0)),
                        new Token(
                            DefaultTokenTypes.SEPARATOR, 14, 15, new LexicalRange(14, 0, 15, 0)))));

    Assignation assignation =
        new Assignation(
            "a",
            new Operation(
                new Operation(new Variable("12"), Operator.MUL, new Variable("2")),
                Operator.ADD,
                new Variable("345")));
    assertEquals(parser.createNode().toString(), assignation.toString());
  }
}
