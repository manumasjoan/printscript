import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.austral.ingsis.printscript.common.LexicalRange;
import org.austral.ingsis.printscript.common.Token;
import org.junit.jupiter.api.Test;

public class LexerTest {

  Lexer lexer = new DefaultLexer();

  @Test
  public void Test001_getKeywordTokens() {

    List<Token> actual = lexer.getTokens("let \nprintln let");

    List<Token> expected =
        List.of(
            new Token(DefaultTokenTypes.KEYWORD, 0, 2, new LexicalRange(0, 0, 2, 0)),
            new Token(DefaultTokenTypes.KEYWORD, 5, 11, new LexicalRange(0, 1, 6, 1)),
            new Token(DefaultTokenTypes.KEYWORD, 13, 15, new LexicalRange(8, 1, 10, 1)));

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.containsAll(expected));
  }

  @Test
  public void Test002_getOperandTokens() {
    List<Token> actual = lexer.getTokens("= + - * /");

    List<Token> expected =
        List.of(
            new Token(DefaultTokenTypes.OPERATOR, 0, 0, new LexicalRange(0, 0, 0, 0)),
            new Token(DefaultTokenTypes.OPERATOR, 2, 2, new LexicalRange(2, 0, 2, 0)),
            new Token(DefaultTokenTypes.OPERATOR, 4, 4, new LexicalRange(4, 0, 4, 0)),
            new Token(DefaultTokenTypes.OPERATOR, 6, 6, new LexicalRange(6, 0, 6, 0)),
            new Token(DefaultTokenTypes.OPERATOR, 8, 8, new LexicalRange(8, 0, 8, 0)));

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.containsAll(expected));
  }

  @Test
  public void Test002() {
    List<Token> actual = lexer.getTokens("=+-*/");

    System.out.println(actual);
  }

  @Test
  public void Test003_getStringLiteralTokens() {
    List<Token> actual = lexer.getTokens("\"Testing literal\"\n 'Test'");

    List<Token> expected =
        List.of(
            new Token(DefaultTokenTypes.LITERAL, 0, 16, new LexicalRange(0, 0, 16, 0)),
            new Token(DefaultTokenTypes.LITERAL, 19, 24, new LexicalRange(1, 1, 6, 1)));

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.containsAll(expected));
  }

  @Test
  public void Test004_getNumberLiteralTokens() {
    List<Token> actual = lexer.getTokens("123 4.56");

    List<Token> expected =
        List.of(
            new Token(DefaultTokenTypes.LITERAL, 0, 2, new LexicalRange(0, 0, 2, 0)),
            new Token(DefaultTokenTypes.LITERAL, 4, 7, new LexicalRange(4, 0, 7, 0)));

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.containsAll(expected));
  }

  @Test
  public void Test005_getMixedTokens() {
    List<Token> actual = lexer.getTokens("let a = 5");

    List<Token> expected =
        List.of(
            new Token(DefaultTokenTypes.KEYWORD, 0, 2, new LexicalRange(0, 0, 2, 0)),
            new Token(DefaultTokenTypes.IDENTIFIER, 4, 4, new LexicalRange(4, 0, 4, 0)),
            new Token(DefaultTokenTypes.OPERATOR, 6, 6, new LexicalRange(6, 0, 6, 0)),
            new Token(DefaultTokenTypes.LITERAL, 8, 8, new LexicalRange(8, 0, 8, 0)));

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.containsAll(expected));
  }

  @Test
  public void Test006_getMixedTokens() {
    List<Token> actual = lexer.getTokens("let 'let' println");

    List<Token> expected =
        List.of(
            new Token(DefaultTokenTypes.KEYWORD, 0, 2, new LexicalRange(0, 0, 2, 0)),
            new Token(DefaultTokenTypes.LITERAL, 4, 8, new LexicalRange(4, 0, 8, 0)),
            new Token(DefaultTokenTypes.KEYWORD, 10, 16, new LexicalRange(10, 0, 16, 0)));

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.containsAll(expected));
  }

  @Test
  public void Test007_getMixedTokens() {
    List<Token> actual = lexer.getTokens("37 abc 3");

    List<Token> expected =
        List.of(
            new Token(DefaultTokenTypes.LITERAL, 0, 1, new LexicalRange(0, 0, 1, 0)),
            new Token(DefaultTokenTypes.IDENTIFIER, 3, 5, new LexicalRange(3, 0, 5, 0)),
            new Token(DefaultTokenTypes.LITERAL, 7, 7, new LexicalRange(7, 0, 7, 0)));

    assertEquals(expected.size(), actual.size());
    assertTrue(actual.containsAll(expected));
  }
}
