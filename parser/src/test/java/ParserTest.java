import ast.node.*;
import org.austral.ingsis.printscript.common.LexicalRange;
import org.austral.ingsis.printscript.common.Token;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {

    @Test
    public void testForAssignmentWithString() throws Exception {
        Parser<Assignation> parser =
                new AssignmentParser(
                        TokenIterator.Companion.create(
                                "h = 'hello'",
                                List.of(
                                        new Token(
                                                DefaultTokenTypes.IDENTIFIER,
                                                0,
                                                1,
                                                new LexicalRange(0, 0, 3, 0)
                                        ),
                                        new Token(
                                                DefaultTokenTypes.ASSIGN,
                                                2,
                                                3,
                                                new LexicalRange(2, 0, 3, 1)
                                        ),
                                        new Token(
                                                DefaultTokenTypes.LITERAL,
                                                4,
                                                11,
                                                new LexicalRange(4, 0, 11, 1)
                                        )
                                )
                        )
                );
        Assignation assignation = new Assignation("h", new Variable("'hello'"));
        assertEquals(assignation.toString(), parser.createNode().toString());
    }

    @Test
    public void testForAssignmentWithIdentifier() throws Exception {
        Parser<Assignation> parser =
                new AssignmentParser(
                        TokenIterator.Companion.create(
                                "myVar = g",
                                List.of(
                                        new Token(
                                                DefaultTokenTypes.IDENTIFIER,
                                                0,
                                                5,
                                                new LexicalRange(0, 0, 5, 0)
                                        ),
                                        new Token(
                                                DefaultTokenTypes.ASSIGN,
                                                6,
                                                7,
                                                new LexicalRange(6, 0, 7, 1)
                                        ),
                                        new Token(
                                                DefaultTokenTypes.IDENTIFIER,
                                                8,
                                                9,
                                                new LexicalRange(8, 0, 9, 1)
                                        )
                                )
                        )
                );
        Assignation assignation = new Assignation("myVar", new Variable("g"));
        assertEquals(assignation.toString(), parser.createNode().toString());
    }

    @Test
    public void testForAssignmentWithNumber() throws Exception {
        Parser<Assignation> parser =
                new AssignmentParser(
                        TokenIterator.Companion.create(
                                "S = 10",
                                List.of(
                                        new Token(
                                                DefaultTokenTypes.IDENTIFIER,
                                                0,
                                                1,
                                                new LexicalRange(0, 0, 3, 0)
                                        ),
                                        new Token(
                                                DefaultTokenTypes.ASSIGN,
                                                2,
                                                3,
                                                new LexicalRange(2, 0, 3, 1)
                                        ),
                                        new Token(
                                                DefaultTokenTypes.LITERAL,
                                                4,
                                                6,
                                                new LexicalRange(4, 0, 6, 1)
                                        )
                                )
                        )
                );
        Assignation assignation = new Assignation("S", new Variable("10"));
        assertEquals(parser.createNode().toString(), assignation.toString());
    }


    @Test
    public void multiplicationTest() throws Exception {
        Parser<Function> parser =
                new FunctionParser(
                        TokenIterator.Companion.create(
                                "6 * 5",
                                List.of(
                                        new Token(
                                                DefaultTokenTypes.LITERAL,
                                                0,
                                                1,
                                                new LexicalRange(0, 0, 1, 0)
                                        ),
                                        new Token(
                                                DefaultTokenTypes.OPERATOR,
                                                2,
                                                3,
                                                new LexicalRange(2, 0, 3, 0)
                                        ),
                                        new Token(
                                                DefaultTokenTypes.LITERAL,
                                                4,
                                                5,
                                                new LexicalRange(4, 0, 5, 0)
                                        )
                                )
                        )
                );
        Expression expression =
                new Expression(new Variable("6"), Operator.MUL, new Variable("5"));
        assertEquals(expression.toString(), parser.createNode().toString());
    }


    @Test
    public void subsTest() throws Exception {
        Parser<Function> parser =
                new FunctionParser(
                        TokenIterator.Companion.create(
                                "3 - 3",
                                List.of(
                                        new Token(
                                                DefaultTokenTypes.LITERAL,
                                                0,
                                                1,
                                                new LexicalRange(0, 0, 1, 0)
                                        ),
                                        new Token(
                                                DefaultTokenTypes.OPERATOR,
                                                2,
                                                3,
                                                new LexicalRange(2, 0, 3, 0)
                                        ),
                                        new Token(
                                                DefaultTokenTypes.LITERAL,
                                                4,
                                                5,
                                                new LexicalRange(4, 0, 5, 0)
                                        )
                                )
                        )
                );
        Expression expression =
                new Expression(new Variable("3"), Operator.SUB, new Variable("3"));
        assertEquals(expression.toString(), parser.createNode().toString());
    }

    @Test
    public void functionTest() throws Exception {
        Parser<Function> parser =
                new FunctionParser(
                        TokenIterator.Companion.create(
                                "g - a * 'mery' + 10",
                                List.of(
                                        new Token(
                                                DefaultTokenTypes.IDENTIFIER,
                                                0,
                                                1,
                                                new LexicalRange(0, 0, 1, 0)
                                        ),
                                        new Token(
                                                DefaultTokenTypes.OPERATOR,
                                                2,
                                                3,
                                                new LexicalRange(2, 0, 3, 0)
                                        ),
                                        new Token(
                                                DefaultTokenTypes.IDENTIFIER,
                                                4,
                                                5,
                                                new LexicalRange(4, 0, 5, 0)
                                        ),
                                        new Token(
                                                DefaultTokenTypes.OPERATOR,
                                                6,
                                                7,
                                                new LexicalRange(6, 0, 7, 0)
                                        ),
                                        new Token(
                                                DefaultTokenTypes.LITERAL,
                                                8,
                                                14,
                                                new LexicalRange(8, 0, 14, 0)
                                        ),
                                        new Token(
                                                DefaultTokenTypes.OPERATOR,
                                                15,
                                                16,
                                                new LexicalRange(15, 0, 16, 0)
                                        ),
                                        new Token(
                                                DefaultTokenTypes.LITERAL,
                                                17,
                                                19,
                                                new LexicalRange(17, 0, 19, 0)
                                        )
                                )
                        )
                );
        Expression expression =
                new Expression(
                        new Expression(
                                new Variable("g"),
                                Operator.SUB,
                                new Expression(new Variable("a"), Operator.MUL, new Variable("'mery'"))
                        ),
                        Operator.ADD,
                        new Variable("10")
                );
        assertEquals(parser.createNode().toString(), expression.toString());
    }

}






