import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.austral.ingsis.printscript.common.LexicalRange;
import org.austral.ingsis.printscript.common.Token;
import org.junit.jupiter.api.Test;

public class LexerTest2 {

    private final LexerBuilder builder = new LexerBuilder();
    private final Lexer lexer = builder.buildLexer2();


    @Test
    public void Test001_GivenInputWithKeywordsShouldReturnKeywordTokens() {

        List<Token> actual = lexer.getTokens("const if else readInput");

        List<Token> expected =
                List.of(
                        new Token(DefaultTokenTypes.KEYWORD, 0, 5, new LexicalRange(0, 0, 5, 0)),
                        new Token(DefaultTokenTypes.KEYWORD, 6, 8, new LexicalRange(6, 0, 8, 0)),
                        new Token(DefaultTokenTypes.KEYWORD, 9, 13, new LexicalRange(9, 0, 13, 0)),
                        new Token(DefaultTokenTypes.KEYWORD, 14, 23, new LexicalRange(14, 0, 23, 0)));

        assertEquals(expected.size(), actual.size());
        assertTrue(actual.containsAll(expected));
    }

    @Test
    public void Test002_GivenInputWithSeparatorsShouldReturnSeparatorTokens() {
        List<Token> actual = lexer.getTokens("{ } ( ) :");

        List<Token> expected =
                List.of(
                        new Token(DefaultTokenTypes.SEPARATOR, 0, 1, new LexicalRange(0, 0, 1, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 2, 3, new LexicalRange(2, 0, 3, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 4, 5, new LexicalRange(4, 0, 5, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 6, 7, new LexicalRange(6, 0, 7, 0)),
                        new Token(DefaultTokenTypes.SEPARATOR, 8, 9, new LexicalRange(8, 0, 9, 0)));

        assertEquals(expected.size(), actual.size());
        assertTrue(actual.containsAll(expected));
    }



}
