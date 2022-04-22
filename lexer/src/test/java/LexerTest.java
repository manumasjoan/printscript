import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.austral.ingsis.printscript.common.LexicalRange;
import org.austral.ingsis.printscript.common.Token;
import org.junit.jupiter.api.Test;

public class LexerTest {

  private Lexer lexer = new DefaultLexer();

  @Test
  public void Test001_GivenInputWithLetAndPrintlnShouldReturnKeywordTokens() {

    List<Token> actual = lexer.getTokens("let \nprintln let");

    List<Token> expected =
        List.of(
            new Token(DefaultTokenTypes.KEYWORD, 0, 3, new LexicalRange(0, 0, 3, 0)),
            new Token(DefaultTokenTypes.KEYWORD, 5, 12, new LexicalRange(0, 1, 7, 1)),
            new Token(DefaultTokenTypes.KEYWORD, 13, 16, new LexicalRange(8, 1, 11, 1)));

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.containsAll(expected));
  }

  @Test
  public void Test002_GivenInputWithOperandsShouldReturnOperandTokens() {
    List<Token> actual = lexer.getTokens("+ - * /");

    List<Token> expected =
        List.of(
            new Token(DefaultTokenTypes.OPERATOR, 0, 1, new LexicalRange(0, 0, 1, 0)),
            new Token(DefaultTokenTypes.OPERATOR, 2, 3, new LexicalRange(2, 0, 3, 0)),
            new Token(DefaultTokenTypes.OPERATOR, 4, 5, new LexicalRange(4, 0, 5, 0)),
            new Token(DefaultTokenTypes.OPERATOR, 6, 7, new LexicalRange(6, 0, 7, 0)));

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.containsAll(expected));
  }

  @Test
  public void Test003_GivenInputWithStringsShouldReturnLiteralTokens() {
    List<Token> actual = lexer.getTokens("\"Testing literal\"\n 'Test'");

    List<Token> expected =
        List.of(
            new Token(DefaultTokenTypes.LITERAL, 0, 17, new LexicalRange(0, 0, 17, 0)),
            new Token(DefaultTokenTypes.LITERAL, 19, 25, new LexicalRange(1, 1, 7, 1)));

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.containsAll(expected));
  }

  @Test
  public void Test004_GivenInputWithNumbersShouldReturnLiteralTokens() {
    List<Token> actual = lexer.getTokens("123 4.56");

    List<Token> expected =
        List.of(
            new Token(DefaultTokenTypes.LITERAL, 0, 3, new LexicalRange(0, 0, 3, 0)),
            new Token(DefaultTokenTypes.LITERAL, 4, 8, new LexicalRange(4, 0, 8, 0)));

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.containsAll(expected));
  }

  @Test
  public void Test005_GivenInputShouldReturnMixedTokens() {
    List<Token> actual = lexer.getTokens("let a : String = 5;");

    List<Token> expected =
        List.of(
            new Token(DefaultTokenTypes.KEYWORD, 0, 3, new LexicalRange(0, 0, 3, 0)),
            new Token(DefaultTokenTypes.IDENTIFIER, 4, 5, new LexicalRange(4, 0, 5, 0)),
            new Token(DefaultTokenTypes.SEPARATOR, 6, 7, new LexicalRange(6, 0, 7, 0)),
            new Token(DefaultTokenTypes.IDENTIFIER, 8, 14, new LexicalRange(8, 0, 14, 0)),
            new Token(DefaultTokenTypes.ASSIGN, 15, 16, new LexicalRange(15, 0, 16, 0)),
            new Token(DefaultTokenTypes.LITERAL, 17, 18, new LexicalRange(17, 0, 18, 0)),
            new Token(DefaultTokenTypes.SEPARATOR, 18, 19, new LexicalRange(18, 0, 19, 0)));

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.containsAll(expected));
  }

  @Test
  public void Test006_GivenInputWithKeywordsAndLiteralsShouldReturnMixedTokens() {
    List<Token> actual = lexer.getTokens("let 'let' println");

    List<Token> expected =
        List.of(
            new Token(DefaultTokenTypes.KEYWORD, 0, 3, new LexicalRange(0, 0, 3, 0)),
            new Token(DefaultTokenTypes.LITERAL, 4, 9, new LexicalRange(4, 0, 9, 0)),
            new Token(DefaultTokenTypes.KEYWORD, 10, 17, new LexicalRange(10, 0, 17, 0)));

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.containsAll(expected));
  }

  @Test
  public void Test007_GivenInputWithLiteralsAndIdentifiersShouldReturnMixedTokens() {
    List<Token> actual = lexer.getTokens("37 abc6 3");

    List<Token> expected =
        List.of(
            new Token(DefaultTokenTypes.LITERAL, 0, 2, new LexicalRange(0, 0, 2, 0)),
            new Token(DefaultTokenTypes.IDENTIFIER, 3, 7, new LexicalRange(3, 0, 7, 0)),
            new Token(DefaultTokenTypes.LITERAL, 8, 9, new LexicalRange(8, 0, 9, 0)));

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.containsAll(expected));
  }
}
